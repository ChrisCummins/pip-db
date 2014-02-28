(ns pip-db.pages.index
  (:require [pip-db.ui :as ui]))

;; ## View

(def body-text
  [["The isoelectric point or pI of a protein corresponds to the"
    "solution pH at which the net surface charge is zero. In the"
    "denatured state, the pI depends solely on a protein’s amino acid"
    "composition."]
   ["Since the earliest days of solution biochemistry, the pI has been"
    "recorded and reported. Literature reports of pI abound."]
   ["The protein isoelectric point database (PIP-DB) has collected and"
    "collated this legacy data to provide an increasingly comprehensive"
    "database for comparison and benchmarking purposes."]
   ["Lorem ipsum dolor sit amet, consectetur adipiscing elit. Etiam ut"
    "aliquet nisi.  Phasellus sed felis eu ipsum ultricies vehicula. Ut"
    "sed urna vitae risus dictum condimentum vel at lorem. Vivamus iaculis "
    "congue est, quis rutrum mauris tempus quis. Praesent ligula libero, "
    "lobortis vel mauris et, ornare rutrum ligula. Morbi lectus ligula, "
    "luctus nec vestibulum ac, tincidunt a urna. Cras vel lectus at nibh "
    "consectetur fermentum quis at ligula. Quisque semper magna orci, at "
    "scelerisque metus tincidunt in."]])

;; The "above the fold" content, i.e. the main logo and search bar.
(defn search-block [request]
  [:div.search.search-block.text-center
   ui/big-logo (ui/search-bar request)])

;; The "below the fold" main body of text, containing a description of
;; what the site is, it's purpose, and how to use it.
(defn text-body [request]
  [:div.col-md-12 (map #(vector :p.lead (apply str %)) body-text)])

(defn view [request]
  (ui/page request
           {:navbar {:login-only true}
            :body (list [:div.row (search-block request)]
                        [:div.row (text-body request)])}))

;; ## Controller

(defn GET [request] (view request))
