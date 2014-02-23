(ns pip-db.controllers.search
  (:require [clojure.contrib.math :as math]
            [pip-db.models.search :as model]
            [pip-db.views.advanced :as advanced]
            [pip-db.views.download :as download]
            [pip-db.views.search :as view]
            [pip-db.util :as util]))

;; Perform a search from the given request map and wrap the results
;; into a `:results` key.
(defn search-results [request]
  (assoc request :results (model/search (request :params))))

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
(defn GET [request]
  ((response-function request) request))

;; Search page ring handler.
(defn GET-json [request]
  (util/json-response (model/search (request :params))))
