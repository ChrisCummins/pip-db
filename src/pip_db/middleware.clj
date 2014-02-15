;; # Application middleware
(ns pip-db.middleware
  (:require [pip-db.views.error :as page]))

;; ## Ring handlers
;;
;; > Ring handlers constitute the core logic of the web
;; > application. Handlers are implemented as Clojure functions that
;; > process a given request map to generate and return a response
;; > map.
;;

;; In order to handle exceptions which are caused while processing
;; requests, we have an exception wrapper which can generate a 500
;; Internal Server Error response.
(defn wrap-exception [f]
  (fn [request]
    (try (f request)
      (catch Exception e
        {:status 500 :body (page/status-500)}))))
