;; Copyright 2014 Chris Cummins.
;;
;; This file is part of pip-db.
;;
;; pip-db is free software: you can redistribute it and/or modify it
;; under the terms of the GNU General Public License as published by
;; the Free Software Foundation, either version 3 of the License, or
;; (at your option) any later version.
;;
;; pip-db is distributed in the hope that it will be useful, but
;; WITHOUT ANY WARRANTY; without even the implied warranty of
;; MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
;; General Public License for more details.
;;
;; You should have received a copy of the GNU General Public License
;; along with pip-db.  If not, see <http://www.gnu.org/licenses/>.
;;
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
