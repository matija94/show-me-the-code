(ns com.matija.maps)


(def data
  {
   :CAT_M {
           1 0
           2 0
           3 1
           4 2
           5 0
           }
   :CAT_F {
           1 0
           2 3
           3 7
           4 0
           5 0

           }
   })

(defn head-0-seq
  [cat]
  (take-while (fn [[_ v]]
                (= v 0))
              cat))
(defn first-non-zero
  [cat]
  (first (drop-while (fn [[_ v]]
                       (= v 0))
                     cat)))
(defn tail-0-seq
  [cat]
  (head-0-seq (reverse cat)))

(defn last-non-zero
  [cat]
  (first (drop-while (fn [[_ v]]
                       (= v 0))
                     (reverse cat))))

(defn replace-0-head
  [cat]
  (into (filter (fn [[_ v]]
                  (not (= 0 v)))
                cat)
        (map (fn [[k _]]
               [k (second (first-non-zero cat))])
             (head-0-seq cat))))

(defn replace-0-tail
  [cat]
  (into (filter (fn [[_ v]]
                  (not (= 0 v)))
                cat)
        (map (fn [[k _]]
               [k (second (last-non-zero cat))])
             (tail-0-seq cat))))


(defn replace-0-cat
  [cat]
  (merge (into {} (replace-0-head cat)) (into {} (replace-0-tail cat))))

(defn replace-0-m
  [m]
  (zipmap (keys m)
          (map replace-0-cat (vals m))))

(println (replace-0-m data))