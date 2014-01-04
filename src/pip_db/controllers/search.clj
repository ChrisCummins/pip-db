(ns pip-db.controllers.search
  (:use [compojure.core :only (defroutes GET)])
  (:require [pip-db.models.search :as model]
            [pip-db.views.advanced :as advanced]
            [pip-db.views.search :as view]))

(def results-per-page 20)

(defn search [query results]
  (view/search query results))

(defn start-at [start results-count]
  (if (< start results-count)
    (if (< 0 start) (- start (mod start results-per-page)) 0)
    (- results-count 1)))

(defn end-at [end results-count]
  (if (< end results-count)
    end results-count))

(defn visible-results [results results-count start end]
  (subvec results
          (start-at start results-count)
          (end-at end results-count)))

(defn paginate [data start]
  (if (nil? start)
    (paginate data 0)
    (assoc data :results (visible-results (data :results)
                                          (data :results-count)
                                          start (+ start results-per-page)))))

(defn start-at [params]
  (try (Integer/parseInt
        (get params "start"))
       (catch Exception e 0)))

(defroutes routes
  (GET "/advanced" [] (advanced/advanced))
  (GET "/s" {params :params}
       (search (get params "q") (paginate (model/search params)
                                          (start-at params)))))
