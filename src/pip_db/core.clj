(ns pip-db.core
  (:require [compojure.handler :as handler]
            [ring.adapter.jetty :as ring]
            [pip-db.db :as db]
            [pip-db.middleware :as middleware]
            [pip-db.util :as util])
  (:gen-class))

(defn start [port]
  (ring/run-jetty middleware/middleware {:port port :join? false}))

(defn -main []
  (db/migrate)
  (start util/port))
