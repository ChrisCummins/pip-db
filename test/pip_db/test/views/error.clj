(ns pip-db.test.views.error
  (:use clojure.test)
  (:require [pip-db.views.error :as dut]))

(deftest status-404
  (let [page (dut/status-404)]

    (testing "Response type"
      (is (map? page)))

    (testing "Response status"
      (is (= 404 (page :status))))

    (testing "Content Body"
      (is (contains? page :body))
      (is (= (class (page :body))
             java.lang.String)))))

(deftest status-500
  (let [page (dut/status-500 (Exception. "Test page"))]

    (testing "Response type"
      (is (map? page)))

    (testing "Response status"
      (is (= 500 (page :status))))

    (testing "Content Body"
      (is (contains? page :body))
      (is (= (class (page :body))
             java.lang.String)))))
