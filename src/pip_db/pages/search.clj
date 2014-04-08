(ns pip-db.pages.search
  (:use [clojure.core :only (slurp)])
  (:require [clojure.string :as str]
            [pip-db.pages.advanced :as advanced]
            [pip-db.search :as search]
            [pip-db.ui :as ui]
            [pip-db.util :as util]))

;; ## View

(defn view [request]
  (ui/page
   request
   {:title ((request :params) "q")
    :navbar {:search true}
    :heading {:meta true :download true}
    :body [:div.sresults
           [:div.accordion]
           ui/no-results-found-message]
    :javascript (list (util/inline-data-js "data" (request :results))
                      (util/inline-js "/js/search.inline.js"))}))

;; Generate a HTTP 302 redirect response pointing to the given
;; record's URL.
(defn record-redirect [record]
  {:status 302 :headers {"Location" (record :Available-At)}})

;; Accepts a search results map and dispatches a record redirect to
;; the first record.
(defn single-record-view [results-map]
  (record-redirect (first ((results-map :results) :Records))))

;; ## Controller

;; Serve a search request.
(defn search-handler [request]
  (let [results           (assoc request :results (search/search request))
        no-of-results     ((results :results) :No-Of-Records-Returned)
        view-handler      (if (= no-of-results 1) single-record-view view)]
    (view-handler results)))

;; Serve an advanced search page.
(defn advanced-handler [request] (advanced/GET request))

;; Return the handler to be used for a specific search action. If the
;; action `a` is used, then we use the advanced handler, else we use
;; the normal search.
(defn response-function [request]
  (if (= "a" ((request :params) "a")) advanced-handler search-handler))

;; Search page ring handler.
(defn GET [request]
  ((response-function request) request))

;; Search page ring handler with support for sequence upload from file.
(defn POST [request]
  (util/with-tmp-file file ((request :params) "f")
    (let [params      (dissoc (request :params) "f")
          sequence    (slurp file)
          sequence?   (not (str/blank? sequence))
          sequence    (if sequence? sequence (params "seq"))
          params      (assoc params "seq" sequence)
          request     (assoc request :params params)]
      ((response-function request) request))))
