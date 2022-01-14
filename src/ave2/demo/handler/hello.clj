(ns ave2.demo.handler.hello
  (:require
   [integrant.core :as ig]))


(defmethod ig/init-key ::component
  [_ _]

  (fn [request]
    {:status 200
     :body "hello"}))
