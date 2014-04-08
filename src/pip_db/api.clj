;; # Application Programmable Interface
(ns pip-db.api
  (:require [clojure.string :as str]
            [pip-db.search :as search]
            [pip-db.db :as db]
            [pip-db.blast :as blast]
            [pip-db.util :as util]))

(defn s [request]
  (util/json-response (search/search request)))

(defn r [request]
  (util/json-response (search/search (util/remap-id-param request))))

(defn ac [request]
  (util/json-response (db/autocomplete request)))

(defn ping [request]
  {:status 200 :headers {"x-pip-db-records" (str (db/no-of-records))}})
