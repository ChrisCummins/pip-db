(ns pip-db.router
  (:use [compojure.core :only (defroutes)])
  (:require [compojure.route :as route]
            [pip-db.views.page :as page]
            [pip-db.controllers.index :as index]
            [pip-db.controllers.search :as search]
            [pip-db.controllers.record :as record]
            [pip-db.controllers.login :as login]
            [pip-db.controllers.upload :as upload]))

(defroutes routes
  index/routes
  search/routes
  record/routes
  login/routes
  upload/routes
  (route/resources "/")
  (route/not-found (page/not-found)))
