(ns pip-db.test.core
  (:use clojure.test
        pip-db.core))

(deftest trivial
  (testing "nothing important"
    (is (= 4 (+ 2 2)))))
