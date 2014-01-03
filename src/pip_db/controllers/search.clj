(ns pip-db.controllers.search
  (:use [compojure.core :only (defroutes GET)])
  (:require [pip-db.models.search :as model]
            [pip-db.views.search :as view]))

(defn search [query results]
  (view/search query results))

(defroutes routes
  (GET "/s" [] (search "alkaline"
                       [["243" "prot" "src" "loc" "pi"]
                        ["243" "prot" "src" "loc" "pi"]
                        ["243" "prot" "src" "loc" "pi"]
                        ["243" "prot" "src" "loc" "pi"]
                        ["243" "prot" "src" "loc" "pi"]
                        ["243" "prot" "src" "loc" "pi"]])))
