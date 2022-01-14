(ns ave2.interceptor.json.response
  (:require
   [ring.middleware.json :refer [json-response]]
   [integrant.core :as ig]))


(defn make [& [params]]
  {:leave
   (fn [ctx]
     (update ctx :response json-response params))})


(defmethod ig/init-key ::component
  [_ params]
  (make params))
