(ns pip-db.controllers.search
  (:use [compojure.core :only (defroutes GET)])
  (:require [clojure.contrib.math :as math]
            [pip-db.models.search :as model]
            [pip-db.views.advanced :as advanced]
            [pip-db.views.search :as view]))

(def results-per-page 10)
(def max-page-links 10)
(def max-results 50)

(defn search [query results]
  (view/search query results))

(defn start-at [start results-count]
  (if (< start results-count)
    (if (pos? start) (- start (mod start results-per-page)) 0)
    (dec results-count)))

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
    (let [results         (data :results)
          results-count   (if (> (data :results-count) max-results)
                            max-results (data :results-count))
          start-at        (start-at start results-count)
          end-at          (end-at (+ start-at results-per-page) results-count)
          pages-count     (math/ceil (/ results-count results-per-page))
          current-page    (inc (/ start-at results-per-page))
          start-page      (max 1 (- current-page (/ max-page-links 2)))
          end-page        (min (inc pages-count) (+ start-page max-page-links))]
      (assoc data
        :results          (visible-results results results-count
                                           start-at end-at)
        :current-page     current-page
        :results-per-page results-per-page
        :pages-count      pages-count
        :limited-results  (> (data :results-count) max-results)
        :pages            (range start-page end-page)))))

(defn start-param [params]
  (try (Integer/parseInt
        (get params "start"))
       (catch Exception e 0)))

(defroutes routes
  (GET "/advanced" [] (advanced/advanced))
  (GET "/s" {params :params}
       (search (get params "q") (paginate (model/search params)
                                          (start-param params)))))
