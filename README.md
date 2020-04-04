# GraalJS Support for ClojureScript

This library provides support for building for GraalJS with `:optimizations :none`.

To use it, provide add these compiler options:


```clojure
{
   ,,,
   :target    :graaljs
   :target-fn 'cljs-graaljs.core/target 
}
```

## Example

For a complete example see `example/build.clj`. Once built, you can run that with the `js` tool bundled with GraalVM:

```bash
$ clj example/build.clj
$ js --jvm --js.java-package-globals=false --experimental-options example/out/example.js
```

## License

MIT
