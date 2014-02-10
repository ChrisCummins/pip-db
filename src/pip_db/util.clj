;; A set of useful utility functions, the purpose of which is to
;; provide generic helpers for tasks.
(ns pip-db.util)

;; Return the current year as a number, as set by the system clock.
(defn current-year []
  (+ 1900 (.getYear (new java.util.Date))))
