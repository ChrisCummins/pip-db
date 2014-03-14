;; # Application Programmable Interface
(ns pip-db.api
  (:require [clojure.string :as str]
            [pip-db.db :as db]
            [pip-db.blast :as blast]
            [pip-db.util :as util]))

(defn s [request]
  (util/json-response (db/search request)))

(defn r [request]
  (util/json-response (db/search (util/remap-id-param request))))

(defn ac [request]
  (util/json-response (db/autocomplete request)))

(defn blast [request]
  (util/json-response (blast/search request)))
