(ns com.matija.async
  (:require [clojure.core.async
             :as a
             :refer [>! <! >!! <!! go chan buffer close! thread
                     alts! alts!! timeout]]))

; >! put into channel and park current thread
; >!! -||- block current thread
; <! take from channel and park current thread if channel is empty
; <!! -||- block current thread

(defn hot-dog-machine
  []
  (let [in (chan)
        out (chan)]
    (go (<! in)
        (>! out "hot dog"))
    [in out]))


(let [[in out] (hot-dog-machine)]
  (>!! in "pocket lint")
  (<!! out))


(defn hot-dog-machine-v2
  [hot-dog-count]
  (let [in (chan)
        out (chan)]
    (go (loop [hc hot-dog-count]
          (if (> hc 0)
            (let [input (<! in)]
              (if (= input 3)
                (do
                  (>! out "hot dog")
                  (recur (dec hc)))
                (do
                  (>! out "wilted lettuce")
                  (recurc hc))))
            (do (close! in)
                (close! out)))))
    [in out]))


(let [[in out] (hot-dog-machine-v2 2)]
  (>!! in "pocket lint")
  (println (<!! out))

  (>!! in 3)
  (println (<!! out))

  (>!! in 3)
  (println (<!! out))

  (>!! in 3)
  (<!! out))