(ns ave2.router.reitit.component
  (:require
   [reitit.core :as r]
   [integrant.core :as ig]))


(defmethod ig/init-key ::component
  [_ {:keys [routes
             not-found]}]

  (let [router
        (r/router routes)]

    (fn [{:keys [request]}]

      (let [{:keys [uri
                    request-method]}
            request

            {:keys [data
                    path
                    path-params]}
            (r/match-by-path router uri)

            handler
            (or
             (get data :any)
             (get data request-method)
             not-found
             (ex-info "aaa" {}))

            request*
            (assoc request
                   :path path
                   :path-params path-params)]

        (handler request*)))))
