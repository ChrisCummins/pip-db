(ns pip-db.core
  (:require [compojure.handler :as handler]
            [ring.adapter.jetty :as ring]
            [pip-db.middleware :as middleware]
            [pip-db.models.migration :as migration])
  (:gen-class))

(defn start [port]
  (ring/run-jetty middleware/middleware {:port port :join? false}))

(defn -main []
  (migration/migrate)
  (let [port (Integer/parseInt (or (System/getenv "PORT") "5000"))]
    (start port)))
