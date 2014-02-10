(ns pip-db.test.views.layout
  (:use clojure.test)
  (:require [pip-db.views.layout :as dut]))

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

(deftest heading
  (testing "No data"
    (is (= (dut/heading {})
           [:div.page-title [:div.page-title-inner nil nil nil] [:hr]])))

  (testing "Title text"
    (is (= (dut/heading {:title "Foo"})
           [:div.page-title [:div.page-title-inner nil [:h3 "Foo"] nil] [:hr]])))

  (testing "Download link"
    (is (= (dut/heading {:download true})
           [:div.page-title
            [:div.page-title-inner
             [:div.download [:a.btn.btn-warning {:href true} "Download"]]
             nil nil] [:hr]])))

  (testing "Zero results count"
    (is (= (dut/heading {:meta true :meta-results-count 0})
           [:div.page-title
            [:div.page-title-inner nil nil
             [:div.info [:ul.meta-tags [:li "Found 0 results..."]]]] [:hr]])))

  (testing "Single result count"
    (is (= (dut/heading {:meta true :meta-results-count 1})
           [:div.page-title
            [:div.page-title-inner nil nil
             [:div.info [:ul.meta-tags [:li "Found 1 result..."]]]] [:hr]])))

  (testing "Multiple results"
    (is (= (dut/heading {:meta true :meta-results-count 10})
           [:div.page-title
            [:div.page-title-inner nil nil
             [:div.info [:ul.meta-tags [:li "Found 10 results..."]]]] [:hr]]))))

(deftest google-analytics
  (testing "Google analytics tracking code"
    (is (= (dut/google-analytics)
           [:script (str tracking-code "\n")]))))

(deftest page
  (testing "No data"
    (is (= (class (dut/page {}))
           java.lang.String))))
