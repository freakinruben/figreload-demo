(set-env!
 :resource-paths #{"src"}
 :dependencies '[[org.clojure/clojure         "1.8.0"]
                 [org.clojure/clojurescript "1.9.293"]

                 [adzerk/boot-cljs "1.7.228-3" :scope "test"]

                 [adzerk/boot-reload "0.4.13-figwheel" :scope "test"]
                 [figwheel "0.5.9-SNAPSHOT" :scope "test"]
                 [figwheel-sidecar "0.5.9-SNAPSHOT" :scope "test"]

                 [pandeiro/boot-http "0.7.6" :scope "test"]
                 [crisptrutski/boot-cljs-test "0.2.2" :scope "test"]

                 [adzerk/boot-cljs-repl "0.3.3" :scope "test"]
                 [com.cemerick/piggieback "0.2.1"  :scope "test"]
                 [weasel "0.7.0"  :scope "test"]
                 [org.clojure/tools.nrepl "0.2.12" :scope "test"]])

(task-options! pom {:project "cljs-repl-web"
                    :version "0.1.0-SNAPSHOT"
                    :url "https://github.com/arichiardi/fig-boot-reload"
                    :description "A sample project for trying lein-figwheel integration in boot-reload."
                    :license {:name "Unlicense"
                              :url "http://unlicense.org/"
                              :distribution :repo}})

(require
 '[adzerk.boot-cljs      :refer [cljs]]
 '[adzerk.boot-cljs-repl :refer [cljs-repl start-repl]]
 '[adzerk.boot-reload    :refer [reload]]
 '[crisptrutski.boot-cljs-test  :refer [exit! test-cljs]]
 '[pandeiro.boot-http    :refer [serve]])

(deftask testing []
  (merge-env! :resource-paths #{"test"})
  identity)

(deftask auto-test []
  (comp (testing)
        (watch)
        (speak)
        (test-cljs)))

(deftask dev []
  (comp (serve :dir "assets/")
        (watch)
        (notify)
        (reload :on-jsload 'fig-boot-reload.core/on-js-reload
                :client-opts {:debug true})
        (cljs-repl :nrepl-opts {:port 5055})
        (cljs :source-map true :optimizations :none)))

(deftask test []
  (comp (testing)
        (test-cljs)
        (exit!)))

(deftask build []
  (cljs :optimizations :advanced))