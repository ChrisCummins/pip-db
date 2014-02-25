;; # Application Programmable Interface
(ns pip-db.api
  (:require [clojure.string :as str]
            [pip-db.db :as db]
            [pip-db.util :as util]))

;; Perform a search and return the number of matched records.
(defn s [request]
  (util/json-response (db/search request)))

(defn r [request]
  (util/json-response (db/search (util/remap-id-param request))))
