(ns pip-db.pages.logout
  (:require [pip-db.util :as util]))

;; ## Model

(defn logout-cookie []
  {"pip-db" {:value "expired" :expires "Thu, 01 Jan 1970 00:00:01 GMT"}})

;; ## Controller

(defn GET [request]
  {:status 302 :headers {"Location" (util/referer)}
   :cookies (logout-cookie)})
