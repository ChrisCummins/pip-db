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
