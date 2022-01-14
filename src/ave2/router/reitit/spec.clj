(ns ave2.router.reitit.component
  (:require
   [clojure.spec.alpha :as s]))


(s/def ::routes
  vector?)

(s/def ::not-found
  keyword?)

(s/def ::cmd->handler
  (s/map-of keyword fn?))

(s/def ::config
  (s/keys :req-un [::routes
                   ::not-found
                   ::cmd->handler]))
