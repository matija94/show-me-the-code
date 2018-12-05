(ns com.matija.atoms-refs-vars)


; atoms - reference type which associates succession of related values with an identity

(def fred (atom {:cuddle-hunger-level 0
                 :percent-deteriorated 0}))

(println @fred)

; update fred's value

(swap! fred
       (fn [current-state]
         (merge-with + current-state {:cuddle-hunger-level 1})))

(println @fred)

(defn increase-cuddle-hunger-level
  [current-state new-hunger-level]
  (update current-state :cuddle-hunger-level + new-hunger-level))

(swap! fred increase-cuddle-hunger-level 15)

(println @fred)

; managing previous states and new states

(let [last-fred-state @fred]
  (reset! fred {:cuddle-hunger-level 10})
  (println "Previous fred state = " last-fred-state)
  (println "Current fred state = " @fred))

; adding WATCHER to atoms

(add-watch fred :cuddle-hunger-level-watcher (fn [key watched old-state new-state]
                                               (if (> (:cuddle-hunger-level new-state) 10)
                                                 (do
                                                   (println "Fred wants to cuddle with you")
                                                   (println "This message brought to your courtesy of " key))
                                                 (println "Fred is ok"))))


(reset! fred {:cuddle-hunger-level 9})
(swap! fred increase-cuddle-hunger-level 2)

; vars - associations between symbols and values

; dynamic binding
(def ^:dynamic *dynamic-var* "matija")


(binding [*dynamic-var* "lukkovic"]
  (println "Dynamic var value = " *dynamic-var*)
  (binding [*dynamic-var* "ckilama"]
    (println "Dynamic var value = " *dynamic-var*)))

(println "Dynamic var value = " *dynamic-var*)


; convey information out of a function without returning it as argument

(defn rename
  []
  (set! *dynamic-var* "matija lukovic"))

(binding [*dynamic-var* nil]
  (rename)
  (println *dynamic-var*))


; exercises

(def safe-counter (atom 0))
(doseq [_ (range 5)]
  (swap! safe-counter inc))
(println @safe-counter)

(defn quote-word-count
  [quotes-length]
  (let [count-map (atom {})
        tasks (ref [])]
    (doseq [_ (range quotes-length)]
      (let [task (future (let [quote (slurp "https://www.braveclojure.com/random-quote")
                               words (clojure.string/split quote #"\s+")]
                           (doall (map (fn [key] (swap! count-map (fn [current-state key] (assoc current-state key (inc (get current-state key 0)))) key)) words))))]
        (dosync
          (alter tasks conj task)))
      )
    (doseq [t @tasks]
      @t
      (println (realized? t)))
    @count-map))

(println (quote-word-count 5))


(def )