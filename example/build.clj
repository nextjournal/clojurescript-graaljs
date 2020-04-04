
(require '[cljs.build.api :as cljs]
         '[cljs-graaljs.core])

(do
  (cljs/build
   "example/src"
   {:target :graaljs
    :output-to "example/out/example.js"
    :output-dir "example/out"
    :main 'foo
    :target-fn 'cljs-graaljs.core/target
    :optimizations :none})
  :ok)
