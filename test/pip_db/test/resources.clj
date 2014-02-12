(ns pip-db.test.resources
  (:use [clojure.test])
  (:require [pip-db.resources :as dut]))

(deftest resource-path
  (testing "Path to resources is correct"
    (is (= (dut/resource-path "foo")
           "resources/public/foo"))))

;; Public assets

(deftest image-path
  (testing "Base directory"
    (is (= (dut/image-path)
           (dut/image-path ""))))

  (testing "Image paths"
    (is (not (= (dut/image-path "foo")
                (dut/image-path "bar"))))))
