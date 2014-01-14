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
    (is (= (dut/page {})
           (str "<!DOCTYPE html>\n<html class=\"no-js\" lang=\"en\"><head>"
                "<meta charset=\"utf-8\"><meta content=\"IE=edge,chrome=1\" "
                "http-equiv=\"X-UA-Compatible\"><meta content=\"width=device"
                "-width, initial-scale=1\" name=\"viewport\"><meta content=\""
                "Protein Isoelectric Point Database.\" name=\"msapplication-"
                "tooltip\"><title>pip-db </title><link href=\"/css/styles.css\""
                " rel=\"stylesheet\" type=\"text/css\"><script "
                "src=\"/js/modernizr-2.7.0.min.js\" type=\"text/javascript\">"
                "</script><script>(function(a,e,f,g,b,c,d)"
                "{a.GoogleAnalyticsObject=b;a[b]=a[b]||function()"
                "{(a[b].q=a[b].q||[]).push(arguments)};a[b].l=1*new Date;"
                "c=e.createElement(f);d=e.getElementsByTagName(f)[0];c.async=1;"
                "c.src=g;d.parentNode.insertBefore(c,d)})(window,document,"
                "\"script\",\"//www.google-analytics.com/analytics.js\","
                "\"ga\");ga(\"create\",\"UA-47069255-1\",\"pip-db.org\");"
                "ga(\"send\",\"pageview\");\n</script></head><body><div "
                "id=\"wrap\"><div class=\"container\"></div></div><div "
                "class=\"navbar navbar-default navbar-bottom\"><div "
                "class=\"container\"><div class=\"navbar-header\"><button "
                "class=\"navbar-toggle\" data-target=\"navbar-collapse\" "
                "data-toggle=\"collapse\" type=\"button\">"
                "<span class=\"icon-bar\"></span><span class=\"icon-bar\">"
                "</span><span class=\"icon-bar\"></span></button></div><div "
                "class=\"navbar-collapse collapse\"><ul class=\"nav "
                "navbar-nav\"><li><a href=\"#\">About</a></li><li><a "
                "href=\"#\">Contact</a></li></ul><ul class=\"nav navbar-nav "
                "navbar-right\"><li><a href=\"#\">Terms</a></li><li><a "
                "href=\"#\">Privacy</a></li></ul></div></div></div><script "
                "src=\"//ajax.googleapis.com/ajax/libs/jquery/1.10.2/jquery."
                "min.js\" type=\"text/javascript\"></script><script>window."
                "jQuery || document.write('<script src=\"/js/jquery-1.10.2.min."
                "js\"><\\/script>');</script><script src=\"/js/bootstrap-3.0.1."
                "min.js\" type=\"text/javascript\"></script><script "
                "src=\"/js/main.js\" type=\"text/javascript\"></script>"
                "<script src=\"/js/moment.min.js\" type=\"text/javascript\">"
                "</script></body></html>"))))

  (testing "Visible navbar"
    (is (= (dut/page {:navbar {}})
           (str "<!DOCTYPE html>\n<html class=\"no-js\" lang=\"en\"><head><meta"
                " charset=\"utf-8\"><meta content=\"IE=edge,chrome=1\" http-equ"
                "iv=\"X-UA-Compatible\"><meta content=\"width=device-width, ini"
                "tial-scale=1\" name=\"viewport\"><meta content=\"Protein Isoel"
                "ectric Point Database.\" name=\"msapplication-tooltip\"><title"
                ">pip-db </title><link href=\"/css/styles.css\" rel=\"styleshee"
                "t\" type=\"text/css\"><script src=\"/js/modernizr-2.7.0.min.js"
                "\" type=\"text/javascript\"></script><script>(function(a,e,f,g"
                ",b,c,d){a.GoogleAnalyticsObject=b;a[b]=a[b]||function(){(a[b]."
                "q=a[b].q||[]).push(arguments)};a[b].l=1*new Date;c=e.createEle"
                "ment(f);d=e.getElementsByTagName(f)[0];c.async=1;c.src=g;d.par"
                "entNode.insertBefore(c,d)})(window,document,\"script\",\"//www"
                ".google-analytics.com/analytics.js\",\"ga\");ga(\"create\",\"U"
                "A-47069255-1\",\"pip-db.org\");ga(\"send\",\"pageview\");\n</s"
                "cript></head><body><div class=\"navbar navbar-fixed-top navbar"
                "-default\"><div class=\"container\"><div class=\"navbar-header"
                "\"><button class=\"navbar-toggle\" data-target=\"navbar-collap"
                "se\" data-toggle=\"collapse\" type=\"button\"><span class=\"ic"
                "on-bar\"></span><span class=\"icon-bar\"></span><span class=\""
                "icon-bar\"></span></button><a class=\"navbar-brand\" href=\"/"
                "\">pip-db</a></div><div class=\"navbar-collapse collapse\"><ul"
                " class=\"nav navbar-nav navbar-right\"><li><a href=\"/login\">"
                "Login</a></li></ul></div></div></div><div id=\"wrap\"><div cla"
                "ss=\"container\"></div></div><div class=\"navbar navbar-defaul"
                "t navbar-bottom\"><div class=\"container\"><div class=\"navbar"
                "-header\"><button class=\"navbar-toggle\" data-target=\"navbar"
                "-collapse\" data-toggle=\"collapse\" type=\"button\"><span cla"
                "ss=\"icon-bar\"></span><span class=\"icon-bar\"></span><span c"
                "lass=\"icon-bar\"></span></button></div><div class=\"navbar-co"
                "llapse collapse\"><ul class=\"nav navbar-nav\"><li><a href=\"#"
                "\">About</a></li><li><a href=\"#\">Contact</a></li></ul><ul cl"
                "ass=\"nav navbar-nav navbar-right\"><li><a href=\"#\">Terms</a"
                "></li><li><a href=\"#\">Privacy</a></li></ul></div></div></div"
                "><script src=\"//ajax.googleapis.com/ajax/libs/jquery/1.10.2/j"
                "query.min.js\" type=\"text/javascript\"></script><script>windo"
                "w.jQuery || document.write('<script src=\"/js/jquery-1.10.2.mi"
                "n.js\"><\\/script>');</script><script src=\"/js/bootstrap-3.0."
                "1.min.js\" type=\"text/javascript\"></script><script src=\"/js"
                "/main.js\" type=\"text/javascript\"></script><script src=\"/js"
                "/moment.min.js\" type=\"text/javascript\"></script></body></ht"
                "ml>"))))

  (testing "Hidden footer"
    (is (= (dut/page {:hide-footer true})
           (str "<!DOCTYPE html>\n<html class=\"no-js\" lang=\"en\"><head><meta"
                " charset=\"utf-8\"><meta content=\"IE=edge,chrome=1\" http-equ"
                "iv=\"X-UA-Compatible\"><meta content=\"width=device-width, ini"
                "tial-scale=1\" name=\"viewport\"><meta content=\"Protein Isoel"
                "ectric Point Database.\" name=\"msapplication-tooltip\"><title"
                ">pip-db </title><link href=\"/css/styles.css\" rel=\"styleshee"
                "t\" type=\"text/css\"><script src=\"/js/modernizr-2.7.0.min.js"
                "\" type=\"text/javascript\"></script><script>(function(a,e,f,g"
                ",b,c,d){a.GoogleAnalyticsObject=b;a[b]=a[b]||function(){(a[b]."
                "q=a[b].q||[]).push(arguments)};a[b].l=1*new Date;c=e.createEle"
                "ment(f);d=e.getElementsByTagName(f)[0];c.async=1;c.src=g;d.par"
                "entNode.insertBefore(c,d)})(window,document,\"script\",\"//www"
                ".google-analytics.com/analytics.js\",\"ga\");ga(\"create\",\"U"
                "A-47069255-1\",\"pip-db.org\");ga(\"send\",\"pageview\");\n</s"
                "cript></head><body><div id=\"wrap\"><div class=\"container\"><"
                "/div></div><script src=\"//ajax.googleapis.com/ajax/libs/jquer"
                "y/1.10.2/jquery.min.js\" type=\"text/javascript\"></script><sc"
                "ript>window.jQuery || document.write('<script src=\"/js/jquery"
                "-1.10.2.min.js\"><\\/script>');</script><script src=\"/js/boot"
                "strap-3.0.1.min.js\" type=\"text/javascript\"></script><script"
                " src=\"/js/main.js\" type=\"text/javascript\"></script><script"
                " src=\"/js/moment.min.js\" type=\"text/javascript\"></script><"
                "/body></html>"))))

  (testing "Body contents"
    (is (= (dut/page {:body [:span "Hello, World!"]})
           (str "<!DOCTYPE html>\n<html class=\"no-js\" lang=\"en\"><head><meta"
                " charset=\"utf-8\"><meta content=\"IE=edge,chrome=1\" http-equ"
                "iv=\"X-UA-Compatible\"><meta content=\"width=device-width, ini"
                "tial-scale=1\" name=\"viewport\"><meta content=\"Protein Isoel"
                "ectric Point Database.\" name=\"msapplication-tooltip\"><title"
                ">pip-db </title><link href=\"/css/styles.css\" rel=\"styleshee"
                "t\" type=\"text/css\"><script src=\"/js/modernizr-2.7.0.min.js"
                "\" type=\"text/javascript\"></script><script>(function(a,e,f,g"
                ",b,c,d){a.GoogleAnalyticsObject=b;a[b]=a[b]||function(){(a[b]."
                "q=a[b].q||[]).push(arguments)};a[b].l=1*new Date;c=e.createEle"
                "ment(f);d=e.getElementsByTagName(f)[0];c.async=1;c.src=g;d.par"
                "entNode.insertBefore(c,d)})(window,document,\"script\",\"//www"
                ".google-analytics.com/analytics.js\",\"ga\");ga(\"create\",\"U"
                "A-47069255-1\",\"pip-db.org\");ga(\"send\",\"pageview\");\n</s"
                "cript></head><body><div id=\"wrap\"><div class=\"container\"><"
                "span>Hello, World!</span></div></div><div class=\"navbar navba"
                "r-default navbar-bottom\"><div class=\"container\"><div class="
                "\"navbar-header\"><button class=\"navbar-toggle\" data-target="
                "\"navbar-collapse\" data-toggle=\"collapse\" type=\"button\"><"
                "span class=\"icon-bar\"></span><span class=\"icon-bar\"></span"
                "><span class=\"icon-bar\"></span></button></div><div class=\"n"
                "avbar-collapse collapse\"><ul class=\"nav navbar-nav\"><li><a "
                "href=\"#\">About</a></li><li><a href=\"#\">Contact</a></li></u"
                "l><ul class=\"nav navbar-nav navbar-right\"><li><a href=\"#\">"
                "Terms</a></li><li><a href=\"#\">Privacy</a></li></ul></div></d"
                "iv></div><script src=\"//ajax.googleapis.com/ajax/libs/jquery/"
                "1.10.2/jquery.min.js\" type=\"text/javascript\"></script><scri"
                "pt>window.jQuery || document.write('<script src=\"/js/jquery-1"
                ".10.2.min.js\"><\\/script>');</script><script src=\"/js/bootst"
                "rap-3.0.1.min.js\" type=\"text/javascript\"></script><script s"
                "rc=\"/js/main.js\" type=\"text/javascript\"></script><script s"
                "rc=\"/js/moment.min.js\" type=\"text/javascript\"></script></b"
                "ody></html>")))))
