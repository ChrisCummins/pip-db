(ns pip-db.controllers.search
  (:use [compojure.core :only (defroutes GET)])
  (:require [clojure.contrib.math :as math]
            [pip-db.models.search :as model]
            [pip-db.views.advanced :as advanced]
            [pip-db.views.search :as view]))

(def results-per-page 10)
(def max-page-links 10)

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
    (let [results         (data :results)
          results-count   (data :results-count)
          start-at        (start-at start results-count)
          end-at          (end-at (+ start-at results-per-page) results-count)
          pages-count     (math/ceil (/ results-count results-per-page))
          current-page    (+ (/ start-at results-per-page) 1)
          start-page      (max 1 (- current-page (/ max-page-links 2)))
          end-page        (min (+ pages-count 1) (+ start-page max-page-links))]
      (assoc data
        :results          (visible-results results results-count
                                           start-at end-at)
        :current-page     current-page
        :results-per-page results-per-page
        :pages-count      pages-count
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
