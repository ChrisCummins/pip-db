;; # Application middleware
(ns pip-db.middleware
  (:use [ring.middleware.params :only (wrap-params)]
        [ring.middleware.multipart-params :only (wrap-multipart-params)]
        [compojure.core :only (defroutes)])
  (:require [pip-db.views.error :as error]
            [pip-db.controllers.index :as index]
            [pip-db.controllers.search :as search]
            [pip-db.controllers.record :as record]
            [pip-db.controllers.login :as login]
            [pip-db.controllers.upload :as upload]
            [clojure.string :as str]
            [compojure.route :as route]))

;; ## Ring handlers
;;
;; > Ring handlers constitute the core logic of the web
;; > application. Handlers are implemented as Clojure functions that
;; > process a given request map to generate and return a response
;; > map.
;;
(defroutes routes
  index/routes
  search/routes
  record/routes
  login/routes
  upload/routes
  (route/resources "/")
  (route/not-found (error/status-404)))

;; In order to handle exceptions which are caused while processing
;; requests, we have an exception wrapper which can generate a 500
;; Internal Server Error response.
(defn wrap-exception [f]
  (fn [request]
    (try (f request)
         (catch Exception e
           (if (str/blank? (System/getenv "DEBUG"))
             {:status 500 :body (error/status-500)}
             {:status 500 :body (error/status-500 e)})))))

(def middleware
  (-> routes
      wrap-exception
      wrap-params
      wrap-multipart-params))
