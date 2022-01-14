(ns ave2.demo.handler.api
  (:require
   [integrant.core :as ig]))


(defmethod ig/init-key ::component
  [_ _]

  (fn [request]
    {:status 200
     :body {:foo 42 :test [1 2 3]}}))
