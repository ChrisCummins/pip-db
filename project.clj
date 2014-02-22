(defproject pip-db "0.4.5"
  :description "Protein Isoelectric Point Database"
  :url "https://github.com/ChrisCummins/pip-db"
  :license {:name "GNU General Public License v3"
            :url "http://www.gnu.org/licenses/gpl.html"}
  :min-lein-version "2.0.0"
  :dependencies [[compojure "1.1.6"]
                 [hiccup "1.0.4"]
                 [org.clojure/clojure "1.4.0"]
                 [org.clojure/clojure-contrib "1.2.0"]
                 [org.clojure/data.json "0.2.4"]
                 [org.clojure/java.jdbc "0.2.3"]
                 [postgresql "9.1-901.jdbc4"]
                 [ring/ring-jetty-adapter "1.1.6"]
                 [clojure-csv/clojure-csv "2.0.1"]
                 [crypto-password "0.1.1"]]
  :plugins [[lein-kibit "0.0.8"]
            [lein-cloverage "1.0.2"]
            [lein-marginalia "0.7.1"]]
  :main pip-db.core
  :aot [pip-db.core])
