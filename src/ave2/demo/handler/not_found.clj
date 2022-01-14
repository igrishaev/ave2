(ns ave2.demo.handler.not-found
  (:require
   [integrant.core :as ig]))


(defmethod ig/init-key ::component
  [_ _]

  (fn [request]
    {:status 404
     :body "not found"}))
