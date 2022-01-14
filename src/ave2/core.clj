(ns ave2.core
  (:require
   [sieppari.core :as s]
   sieppari.async.manifold

   [exoscale.interceptor :as ei]
   exoscale.interceptor.manifold
   [ring.adapter.jetty :as jetty]

   [manifold.deferred :as d]

   [integrant.core :as ig]

   )



  (:gen-class))



(def i-1
  {:enter (fn [ctx]
            (-> ctx
                (d/chain (fn [ctx]
                           (assoc ctx :foo 1)))))})

(def i-2
  {:enter (fn [ctx]
            (-> ctx
                (d/chain (fn [ctx]
                           (assoc ctx :bar 2)))))})




(defmethod ig/init-key :interceptor/foo
  [_ _]
  {:enter (fn [ctx]
            ctx
            #_
            (-> ctx
                (d/chain (fn [ctx]
                           (assoc ctx :aaa 1)))))})

(defmethod ig/init-key :interceptor/bar
  [_ _]
  {:enter (fn [ctx]
            ctx
            #_
            (-> ctx
                (d/chain (fn [ctx]
                           (assoc ctx :ccc 1)))))})


(defn i-http [handler]
  {:enter (fn [ctx]
            (assoc ctx :response (handler ctx)))})



(def config

  {

   :handler/not-found nil
   :handler/hello nil
   :handler/help nil

   :ave2.router.reitit.component/component
   {:kw->handler
    {:handler/not-found (ig/ref :sdfsdf)
     :handler/hello (ig/ref :handler/hello)
     :handler/help (ig/ref :handler/help)}

    :not-found :handler/not-found

    :routes
    [["/hello" {:get :handler/hello}]
     ["/help" {:get :handler/help}]]}


   ::jetty {:params       {:port 8088}
            :handler      (ig/ref :handler/hello)
            :interceptors [(ig/ref :interceptor/foo)
                           (ig/ref :interceptor/bar)]}}


  )


(defmethod ig/init-key :handler/hello
  [_ _]
  (fn [{:as ctx :keys [request]}]
    {:status 200
     :body "hello"}))



(defmethod ig/init-key ::jetty
  [_ {:keys [params handler interceptors]}]

  (let [
        stack
        (-> [{:leave :response}]
            (into interceptors)
            (conj (i-http handler)))

        -handler
        (fn [request]
          ;;exoscale.interceptor.manifold

          (ei/execute {:request request} stack))]

    (jetty/run-jetty -handler (assoc params :join? false))))


(defmethod ig/halt-key! ::jetty
  [_ ^org.eclipse.jetty.server.Server server]
  (.stop server))

#_
(d/chain 0 inc)





(def i1
  {:enter (fn [x]
            (-> x
                (d/chain inc)))

   ;; :leave (fn [ctx]
   ;;          ctx)
   }

  )


(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (println "Hello, World!"))
