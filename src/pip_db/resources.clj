(ns pip-db.resources
  (:use [clojure.core :only (slurp)]))

(def resource-root "resources/public/")

(defn resource-path [path]
  (str resource-root path))

(defn resource [path]
  (slurp (resource-path path)))
