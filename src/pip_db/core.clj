(ns pip-db.core
  (:use [compojure.core :only (defroutes)]
        [ring.adapter.jetty :as ring])
  (:require [compojure.route :as route]
            [compojure.handler :as handler])
  (:gen-class))

(defn -main []
  (print "Hello, world!\n"))
