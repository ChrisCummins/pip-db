;; # Application middleware
(ns pip-db.middleware
  (:use [ring.middleware.cookies :only (wrap-cookies)]
        [ring.middleware.params :only (wrap-params)]
        [ring.middleware.multipart-params :only (wrap-multipart-params)]
        [compojure.core :only (defroutes GET POST)])
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
  (GET  "/"                   [:as request] (index/handler             request))
  (GET  "/advanced"           [:as request] (search/advanced-handler   request))
  (GET  "/r/:id"              [:as request] (record/handler            request))
  (GET  "/d"                  [:as request] (search/download-handler   request))
  (GET  "/s"                  [:as request] (search/handler            request))
  (GET  "/s.json"             [:as request] (search/json-handler       request))
  (GET  "/login"              [:as request] (login/get-handler         request))
  (POST "/login"              [:as request] (login/post-handler        request))
  (GET  "/logout"             [:as request] (login/logout-handler      request))
  (GET  "/upload"             [:as request] (upload/get-handler        request))
  (POST "/upload"             [:as request] (upload/post-handler       request))
  (route/resources "/")
  (route/not-found (error/status-404)))

;; In order to handle exceptions which are caused while processing
;; requests, we have an exception wrapper which can generate a 500
;; Internal Server Error response.
(defn wrap-exception [f]
  (fn [request]
    (try (f request)
         (catch Exception e {:status 500 :body (error/status-500 e)}))))

(def middleware
  (-> routes
      wrap-exception
      wrap-cookies
      wrap-params
      wrap-multipart-params))
