(ns pip-db.core
  (:use [ring.middleware.params]
        [ring.middleware.multipart-params]
        [ring.adapter.jetty :as ring]
        [pip-db.middleware])
  (:require [compojure.handler :as handler]
            [pip-db.router :as router]
            [pip-db.models.migration :as migration])
  (:gen-class))

(def application
  (-> router/routes
      wrap-exception
      wrap-params
      wrap-multipart-params))

(defn start [port]
  (run-jetty application {:port port :join? false}))

(defn -main []
  (migration/migrate)
  (let [port (Integer/parseInt (or (System/getenv "PORT") "5000"))]
    (start port)))
