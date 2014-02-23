(ns pip-db.test.util
  (:use clojure.test)
  (:require [pip-db.util :as dut]))

;; Our Google Analytics tracking snippet. See:
;; resources/public/js/google-analytics.inline.js.
(def tracking-code (str "(function(a,e,f,g,b,c,d){a.GoogleAnalyticsObject=b;"
                        "a[b]=a[b]||function(){(a[b].q=a[b].q||[])"
                        ".push(arguments)};a[b].l=1*new Date;c=e"
                        ".createElement(f);d=e.getElementsByTagName(f)[0];"
                        "c.async=1;c.src=g;d.parentNode.insertBefore(c,d)})"
                        "(window,document,\"script\",\"//"
                        "www.google-analytics.com/analytics.js\",\"ga\");"
                        "ga(\"create\",\"UA-47069255-1\",\"pip-db.org\");"
                        "ga(\"send\",\"pageview\");"))

(deftest dates
  (testing "Retrieving the current year"
    (is (number? (dut/current-year)))
    (is (> (dut/current-year) 1900))))

(deftest inline-css
  (testing "Stylesheet contents"
    (is (= (dut/inline-css "/js/google-analytics.inline.js")
           [:style (str tracking-code "\n")]))))

(deftest inline-js
  (testing "Javascript contents"
    (is (= (dut/inline-js "/js/google-analytics.inline.js")
           [:script (str tracking-code "\n")]))))

(deftest sha1
  (testing "SHA1 hashing implementation"
    (is (= (dut/sha1 "foo") "0beec7b5ea3f0fdbc95d0dd47f3c5bc275da8a33"))
    (is (= (dut/sha1 "bar") "62cdb7020ff920e5aa642c3d4066950dd1f01f4d"))))

(deftest minihash
  (testing "Short hash implementation"
    (is (= (dut/minihash "foo") "MGJlZWM3YjV"))
    (is (= (dut/minihash "bar") "NjJjZGI3MDI"))))
