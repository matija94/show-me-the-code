(ns com.matija.safe-queue)


(defmacro enqueue
  ([q promise-name body serialized]
    `(let [~promise-name (promise)]
       (future (deliver ~promise-name ~body))
       (deref ~q)
       ~serialized
       ~promise-name))
  ([promise-name body serialized]
    `(enqueue (future) ~promise-name ~body ~serialized)))


(defmacro wait
  [timeout & body]
  `(do (Thread/sleep ~timeout) ~@body))

