(ns ave2.server.jetty.component
  (:require
   [exoscale.interceptor :as ei]
   [ring.adapter.jetty :as jetty]
   [integrant.core :as ig]))


(defn handler->interceptor
  [handler]
  {:enter
   (fn [ctx]
     (assoc ctx :response (handler ctx)))})


(defmethod ig/init-key ::component
  [_ {:keys [params
             handler
             interceptors]}]

  (let [stack
        (-> [{:leave :response}]
            (into interceptors)
            (conj (handler->interceptor handler)))

        -handler
        (fn [request]
          (ei/execute {:request request} stack))

        params*
        (assoc params :join? false)]

    (jetty/run-jetty -handler params*)))


(defmethod ig/halt-key! ::component
  [_ ^org.eclipse.jetty.server.Server server]
  (.stop server))
