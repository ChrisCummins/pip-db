(ns pip-db.core
  (:use [compojure.core :only (defroutes)]
        [ring.adapter.jetty :as ring])
  (:require [compojure.route :as route]
            [compojure.handler :as handler]
            [pip-db.views.layout :as layout]
            [pip-db.models.migration :as migration]
            [pip-db.controllers.index :as index]
            [pip-db.controllers.login :as login])
  (:gen-class))

(defroutes routes
  index/routes
  login/routes
  (route/resources "/")
  (route/not-found (layout/not-found)))

(def application (handler/site routes))

(defn start [port]
  (run-jetty application {:port port :join? false}))

(defn -main []
  (migration/migrate)
  (let [port (Integer/parseInt (or (System/getenv "PIP_DB_PORT") "5000"))]
    (start port)))
