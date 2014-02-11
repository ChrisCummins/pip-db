;; A set of useful utility functions, the purpose of which is to
;; provide generic helpers for tasks.
(ns pip-db.util
  (:use [pip-db.resources :only (resource)]))

;; ## Date utilities

;; Return the current year as a number, as set by the system clock.
(defn current-year []
  (+ 1900 (.getYear (new java.util.Date))))

;; ## Working with HTML

;; Returns an element which represents an embedded <style></style>
;; tag, containing the contents of the given path. Paths use the
;; `resources/public` directory as their root,
;; e.g. `/css/foo.inline.css`, which expands to
;; `resources/public/css/foo.inline.css`. By convention, any file
;; which is intended to be inlined should have the `.inline` suffix
;; appended to its name, in order to distinguish it from public CSS
;; files which are included using the `src` attribute.
(defn inline-css [path]
  [:style (resource path)])

;; Returns an element which represents an embedded <script></script>
;; tag, containing the contents of the given path. Paths use the
;; `resources/public` directory as their root,
;; e.g. `/js/foo.inline.js`, which expands to
;; `resources/public/js/foo.inline.js`. By convention, any file which
;; is intended to be inlined should have the `.inline` suffix appended
;; to its name, in order to distinguish it from public JS files which
;; are included using the `src` attribute.
(defn inline-js [path]
  [:script (resource path)])
