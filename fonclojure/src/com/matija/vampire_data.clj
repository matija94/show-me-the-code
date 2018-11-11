(ns com.matija.vampire-data)


(defn my-reduce
  [f acc s]
  (loop [s (seq s) res acc]
    (if s
      (let [[part & remaining] s]
        (recur remaining (f res part)))
      res)))

(defn my-reduce-v2
  [f acc s]
  (loop [res acc s (seq s)]
    (if s
      (recur (f res (first s)) (next s))
      res)))

(println (my-reduce into [] [[1 2] [3 4] [5 6] [7]]))
(println (my-reduce + 0 []))

(defn map-using-reduce
  "Maps each element from vector using supplied function f"
  [f s]
  (my-reduce (fn [new-s e] (conj new-s (f e)))
             ()
             s))

(println (map-using-reduce #(str % " mapped") ["first" "second"]))


(println (and true true ))

(defn even-numbers
  ([] (even-numbers 0))
  ([n] (cons n (lazy-seq (even-numbers (+ n 2))))))


(def csv-data "Edward Cullen,10\nBella Swan,0\nCharlie Swan,0\nJacob Black,3\nCarlisle Cullen,6")

(def vamp-keys [:name :glitter-index])

(defn str->int
  [str]
  (Integer. str))

(def conversions {:name identity :glitter-index str->int})

(defn convert
  [vamp-key value]
  ((get conversions vamp-key) value))

(defn parse
  [csv-data]
  (map #(clojure.string/split % #",")
       (clojure.string/split csv-data #"\n")))

(defn mapify
  "Return a seq of maps like {:name \"Edward Cullen\" :glitter-index 10}"
  [rows]
  (map (fn [unmapped-row]
         (reduce (fn [row-map [vamp-key value]]
                   (assoc row-map vamp-key (convert vamp-key value)))
                 {}
                 (map vector vamp-keys unmapped-row)))
       rows))

(defn glitter-filter
  [minimum-glitter records]
  (filter #(>= (:glitter-index %) minimum-glitter) records))

(def result (glitter-filter 3 (mapify (parse csv-data))))
(println result)
; ----EXERCISES-----

(defn glitter-filter-tolist
  [glitter-filtter]
  (into [] (map :name glitter-filtter)))

(println (glitter-filter-tolist result))


(defn append-suspect
  [suspect suspects]
  (conj suspects suspect))

(println (append-suspect {:name "Matija" :glitter-index 1} result))


(defn my-and
  [& args]
  (reduce (fn [prev curr] (and prev curr))
          true
          args))

(defn validate-record
  [keys-to-vf record]
  (apply my-and (map
                  (fn [[key val]] (boolean (val (key record))))
                  keys-to-vf)))


(def student {:name "Matija" :age 23})
(defn validate-name [name] name)
(defn validate-age [age] age)
(println (validate-record {:name validate-name :age validate-age} student))

(println (boolean (validate-name (:name student))))

(defn map-to-csv-row
  [mapping]
  (clojure.string/join "," (map (fn [[_ val]] (str val))
                                mapping)))

(defn maps-to-csv
  [maps]
  (clojure.string/join "\n" (map map-to-csv-row maps)))

(println (maps-to-csv [{"name" "Matija" "age" 24} {"name" "Sale" "age" 23}]))