(ns pip-db.controllers.search
  (:use [compojure.core :only (defroutes GET)])
  (:require [clojure.contrib.math :as math]
            [pip-db.models.search :as model]
            [pip-db.views.advanced :as advanced]
            [pip-db.views.search :as view]))

(def results-per-page 10)
(def max-page-links 10)
(def max-results 20)

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

(defn paginate [request start]
  (if (nil? start)
    (paginate request 0)
    (let [results         (request :results)
          results-count   (if (> (request :results-count) max-results)
                            max-results (request :results-count))
          start-at        (start-at start results-count)
          end-at          (end-at (+ start-at results-per-page) results-count)
          pages-count     (math/ceil (/ results-count results-per-page))
          current-page    (inc (/ start-at results-per-page))
          start-page      (max 1 (- current-page (/ max-page-links 2)))
          end-page        (min (inc pages-count) (+ start-page max-page-links))]
      (assoc request
        :results          (visible-results results results-count
                                           start-at end-at)
        :current-page     current-page
        :results-per-page results-per-page
        :pages-count      pages-count
        :limited-results  (> (request :results-count) max-results)
        :pages            (range start-page end-page)))))

(defn start-param [request]
  (try (Integer/parseInt
        ((request :params) "start"))
       (catch Exception e 0)))

(defn search-results [request]
  (merge request
         (paginate (model/search (request :params)) (start-param request))))

;; Serve a search request.
(defn search-handler [request]
    (view/search (search-results request)))

;; Serve an advanced search page.
(defn advanced-handler [request] (advanced/advanced request))

(defn request-action [request]
  ((request :params) "a"))

;; Return the handler to be used for a specific search action. If the
;; action `a` is used, then we use the advanced handler, else we use
;; the normal search.
(defn response-function [request]
  (if (= "a" (request-action request)) advanced-handler search-handler))

;; Search page ring handler.
(defn handler [request]
  ((response-function request) request))
