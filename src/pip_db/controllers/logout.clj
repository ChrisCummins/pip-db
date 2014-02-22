(ns pip-db.controllers.logout
  (:require [pip-db.util :as util]
            [pip-db.models.login :as model]))

(defn GET [request]
  {:status 302 :headers {"Location" (util/referer)}
   :cookies (model/logout-cookie)})
