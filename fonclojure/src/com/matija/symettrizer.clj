(ns com.matija.symettrizer)

(def asym-hobbit-body-parts [{:name "head" :size 3}
                             {:name "left-eye" :size 1}
                             {:name "left-ear" :size 1}
                             {:name "mouth" :size 1}
                             {:name "nose" :size 1}
                             {:name "neck" :size 2}
                             {:name "left-shoulder" :size 3}
                             {:name "left-upper-arm" :size 3}
                             {:name "chest" :size 10}
                             {:name "back" :size 10}
                             {:name "left-forearm" :size 3}
                             {:name "abdomen" :size 6}
                             {:name "left-kidney" :size 1}
                             {:name "left-hand" :size 2}
                             {:name "left-knee" :size 2}
                             {:name "left-thigh" :size 4}
                             {:name "left-lower-leg" :size 3}
                             {:name "left-achilles" :size 1}
                             {:name "left-foot" :size 2}])


(defn matching-part
  [part]
  {:name (clojure.string/replace (:name part) #"^left-" "right-")
   :size (:size part)})

(defn symmetrize-body-parts
  "Expects a seq of maps that have a :name and :size"
  [asym-body-parts]
  (loop [remaining-asym-parts asym-body-parts
         final-body-parts []]
    (if (empty? remaining-asym-parts)
      final-body-parts
      (let [[part & remaining] remaining-asym-parts]
        (recur remaining
               (into final-body-parts
                     (set [part (matching-part part)])))))))

(defn symmetrize-body-parts-reduce-distinct
  "Expects a seq of maps that have a :name and :size"
  [asym-body-parts]
  (reduce (fn [final-body-parts part]
            (into final-body-parts (set [part (matching-part part)])))
          []
          asym-body-parts))

(defn hit
  "Pick body part randomly. The bigger the part is the bigger chance is for it being hit"
  [asym-body-parts]
  (let [sym-parts (symmetrize-body-parts-reduce-distinct asym-body-parts)
        body-part-size-sum (reduce + (map :size sym-parts))
        target (rand body-part-size-sum)]
    (loop [[part & remaining] sym-parts
           accumulated-size (:size part)]
      (if (> accumulated-size target)
        part
        (recur remaining (+ accumulated-size (:size (first remaining))))))))


(def hits (hit asym-hobbit-body-parts))


; ===== EXERCISES =====


(def hashmap1 (hash-map :name "matija" :age 23))
(def hashset1 (hash-set "lukovic" "lukovic"))
(def mylist(list 1 1 2 3 4))

(defn decmaker
  [num]
  (fn [num1] (- num1 num)))

(defn mapset
  "Works likes a map but returning set instead of list"
  [f coll]
  (set (map f coll)))

(defn mapset-loop
  [f coll]
  (loop [remaining coll
         res #{}]
    (if (empty? remaining)
      res
      (let [[head & tail] remaining]
        (recur tail
               (conj res (f head)))))))

(defn make-alien-body-part
  "Make 5 parts of each if part == (eye | arm | leg)"
  ([n res part]
   (if (re-matches #".*(eye|arm|leg)" (:name part))
     (into res (repeat n part))
     (conj res part)))
  ([res part] (make-alien-body-part 5 res part)))

(defn alien-symmetrize-body-parts
  "Symmetrize body parts such that there are five eyes, arms and legs"
  [body-parts]
  (reduce make-alien-body-part [] body-parts))


(println hits)
(println (alien-symmetrize-body-parts asym-hobbit-body-parts))
(println (map inc mylist))
(println (mapset inc mylist))
(println (mapset-loop inc mylist))
(println (fn [x] x))
(def v (vector 1 2 3))
(def z (update v 1 #(+ 1 %)))
(println z)
(def s #{1 3 5})
(println (contains? s 1))