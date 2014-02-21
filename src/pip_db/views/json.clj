(ns pip-db.views.json
  (:require [clojure.data.json :as json]))

(defn response [data]
  {:status 200 :headers {"Content-Type" "application/json"}
   :body (with-out-str (json/pprint data))})
