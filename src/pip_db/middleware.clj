;; # Application middleware
(ns pip-db.middleware
  (:use [ring.middleware.cookies :only (wrap-cookies)]
        [ring.middleware.params :only (wrap-params)]
        [ring.middleware.multipart-params :only (wrap-multipart-params)]
        [compojure.core :only (defroutes GET POST)])
  (:require [pip-db.views.error :as error]
            [pip-db.controllers.index :as index]
            [pip-db.controllers.advanced :as advanced]
            [pip-db.controllers.download :as download]
            [pip-db.controllers.search :as search]
            [pip-db.controllers.record :as record]
            [pip-db.controllers.login :as login]
            [pip-db.controllers.logout :as logout]
            [pip-db.controllers.upload :as upload]
            [clojure.string :as str]
            [compojure.route :as route]))

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
  (GET  ["/r/:id.json", :id id-re] [:as request] (record/GET-json request))
  (GET  ["/r/:id",      :id id-re] [:as request] (record/GET      request))
  (GET  ["/d"]                     [:as request] (download/GET    request))
  (GET  ["/s"]                     [:as request] (search/GET      request))
  (GET  ["/s.json"]                [:as request] (search/GET-json request))
  (GET  ["/login"]                 [:as request] (login/GET       request))
  (POST ["/login"]                 [:as request] (login/POST      request))
  (GET  ["/logout"]                [:as request] (logout/GET      request))
  (GET  ["/upload"]                [:as request] (upload/GET      request))
  (POST ["/upload"]                [:as request] (upload/POST     request))
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
