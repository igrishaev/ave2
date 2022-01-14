(ns ave2.demo.demo
  (:require

   ave2.server.jetty.component
   ave2.router.reitit.component

   ave2.demo.handler.not-found
   ave2.demo.handler.hello
   ave2.demo.handler.api

   ave2.interceptor.json.response

   [integrant.core :as ig]))


(defn some-handler [_]
  {:status 200
   :body "some handler"})


(def config

  {
   :ave2.interceptor.json.response/component
   nil

   :ave2.demo.handler.hello/component
   nil

   :ave2.demo.handler.api/component
   nil

   :ave2.demo.handler.not-found/component
   nil

   :ave2.router.reitit.component/component
   {:routes
    [["/hello"
      {:name :handler/hello
       :get (ig/ref :ave2.demo.handler.hello/component)}]

     ["/api"
      {:name :handler/api
       :get (ig/ref :ave2.demo.handler.api/component)}]

     ["/some"
      {:get some-handler}]]

    :not-found
    (ig/ref :ave2.demo.handler.not-found/component)}

   :ave2.router.jetty.component/component
   {:params
    {:port 8088}

    :handler
    (ig/ref :ave2.router.reitit.component/component)

    :interceptors
    [(ig/ref :ave2.interceptor.json.response/component)]}})


#_
(def system
  (ig/init config))

#_
(ig/halt! system)
