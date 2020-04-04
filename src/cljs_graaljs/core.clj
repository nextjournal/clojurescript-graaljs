(ns cljs-graaljs.core
  (:require [cljs.closure :as closure]
            [cljs.compiler :as comp]
            [cljs.util :as util]
            [clojure.data.json :as json]))

(defn target [opts]
  (let [module          (get (:modules opts) (:module-name opts))
        asset-path      (or (:asset-path opts)
                            (util/output-directory opts))
        closure-defines (json/write-str (:closure-defines opts))]
    (closure/add-header
     opts
     (str (when (or (not module) (= :cljs-base (:module-name opts)))
            (str "var CLJS_OUTPUT_DIR = \"" asset-path "\";\n"
                 "load(\""asset-path "/goog/bootstrap/graaljs.js\");\n"
                 "load(\""asset-path "/goog/base.js\");\n"
                 "load(\""asset-path "/goog/deps.js\");\n"
                 "load(\""asset-path "/cljs_deps.js\");\n"
                 "goog.global.CLOSURE_UNCOMPILED_DEFINES = " closure-defines ";\n"
                 (apply str (closure/preloads (:preloads opts)))))
          (apply str
                 (map (fn [entry]
                        (str "goog.require(\"" (comp/munge entry) "\");\n"))
                      (if-let [entries (when module (:entries module))]
                        entries
                        [(:main opts)])))))))
