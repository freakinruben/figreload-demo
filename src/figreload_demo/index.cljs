(ns ^{:hoplon/page "index.html"} figreload-demo.index
  (:require [hoplon.core :refer :all]
            [javelin.core :refer [cell]]
            [hoplon.jquery]))

(enable-console-print!)

(defonce name (cell "User"))
(defonce app-state (cell= {:name name}))

(html
  (head
    (meta :charset "UTF-8")
    (meta :name "viewport" :content "width=device-with, initial-scale=1")
    (link :href "css/style.css" :rel "stylesheet" :type "text/css"))
  (body
    (div :id "app"
      (h2
        :class "greetings"
        (text "Hello ~{:name app-state}"))
      (h3 "This is the Boot-reload + Figwheel template")
      (p "Checkout your developer console"))))

(defn on-js-reload []
  (.info js/console "Reloading Javascript...")
  (reset! name "Andrea"))
