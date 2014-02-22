(ns pip-db.test.db
  (:use clojure.test)
  (:require [pip-db.db :as dut]))

(deftest sha1
  (testing "SHA1 hashing implementation"
    (is (= (dut/sha1 "foo") "0beec7b5ea3f0fdbc95d0dd47f3c5bc275da8a33"))
    (is (= (dut/sha1 "bar") "62cdb7020ff920e5aa642c3d4066950dd1f01f4d"))))

(deftest minihash
  (testing "Short hash implementation"
    (is (= (dut/minihash "foo") "MGJlZWM3YjV"))
    (is (= (dut/minihash "bar") "NjJjZGI3MDI"))))
