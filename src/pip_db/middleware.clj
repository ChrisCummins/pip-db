;; # Application middleware
(ns pip-db.middleware
  (:use [compojure.core :only (defroutes GET POST)]
        [ring.middleware.cookies :only (wrap-cookies)]
        [ring.middleware.multipart-params :only (wrap-multipart-params)]
        [ring.middleware.params :only (wrap-params)])
  (:require [clojure.string :as str]
            [compojure.route :as route]
            [pip-db.pages.advanced :as advanced]
            [pip-db.pages.download :as download]
            [pip-db.pages.index :as index]
            [pip-db.pages.login :as login]
            [pip-db.pages.logout :as logout]
            [pip-db.pages.record :as record]
            [pip-db.pages.search :as search]
            [pip-db.pages.upload :as upload]
            [pip-db.api :as api]
            [pip-db.ui :as ui]))

;; The regular expression to match a record ID.
(def id-re #"\w{11}")

;; ## Ring handlers
;;
;; > Ring handlers constitute the core logic of the web
;; > application. Handlers are implemented as Clojure functions that
;; > process a given request map to generate and return a response
;; > map.
;;
(defroutes routes
  (GET  ["/"]                      [:as request] (index/GET       request))
  (GET  ["/advanced"]              [:as request] (advanced/GET    request))
  (GET  ["/r/:id.json", :id id-re] [:as request] (api/r           request))
  (GET  ["/r/:id",      :id id-re] [:as request] (record/GET      request))
  (GET  ["/d"]                     [:as request] (download/GET    request))
  (GET  ["/s"]                     [:as request] (search/GET      request))
  (GET  ["/s.json"]                [:as request] (api/s           request))
  (GET  ["/login"]                 [:as request] (login/GET       request))
  (POST ["/login"]                 [:as request] (login/POST      request))
  (GET  ["/logout"]                [:as request] (logout/GET      request))
  (GET  ["/upload"]                [:as request] (upload/GET      request))
  (POST ["/upload"]                [:as request] (upload/POST     request))
  (GET  ["/api/s"]                 [:as request] (api/s           request))
  (GET  ["/api/r/:id",  :id id-re] [:as request] (api/r           request))
  (GET  ["/api/ac"]                [:as request] (api/ac          request))
  (route/resources "/")
  (route/not-found (ui/page-404)))

;; In order to handle exceptions which are caused while processing
;; requests, we have an exception wrapper which can generate a 500
;; Internal Server Error response.
(defn wrap-exception [f]
  (fn [request]
    (try (f request) (catch Exception e {:status 500 :body (ui/page-500 e)}))))

(def middleware
  (-> routes
      wrap-exception
      wrap-cookies
      wrap-params
      wrap-multipart-params))
