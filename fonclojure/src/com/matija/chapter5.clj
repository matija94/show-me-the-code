(ns com.matija.chapter5)


(defn sum
  ([vals]
    (sum vals 0))
  ([vals accumulator]
    (if (empty? vals)
      accumulator
      (sum (rest vals) (+ (first vals) accumulator)))))


(println (sum [1 2 3 4 5]))


(println ((comp inc * ) 2 3))

(defn two-comp
  [f g]
  (fn [& args]
    (f (apply g args))))

(println ((two-comp inc *) 2 3))


(defn my-comp
  [f & funs]
  (fn [& args]
    (if (empty? funs)
      (apply f args)
      (f (apply (apply my-comp funs) args)))))

(println ((my-comp #(* % 3) inc *) 2 3))
(println (apply * [2 3]))




(defn attr
  [record attribute]
  (get-in record [:attributes attribute]))


(def record
  {:name "Matija"
   :attributes {:age 23 :weight 75 :height 180 :shape "good"}})


(defn my-assoc-in
  [m [k & ks] v]
  (let [part (if (empty? ks)
               (assoc m k v)
               (assoc {} k (my-assoc-in {} ks v)))]
    (merge m part)))

(println (my-assoc-in {:name "Matija"} [:attributes :age] 23))
(println (my-assoc-in {:name "matija"} [:age] 23))

(println ((comp :age :attributes) record))
(println (attr record :age))

(defn my-update-in
  [m [k & ks] f & args]
  (if (empty? ks)
    (assoc m k (apply f (get m k) args))
    (assoc m k (apply my-update-in (get m k) ks f args))))

(println (my-update-in record [:attributes :shape] str " very"))


(def lucky-number (concat '(+ 1 2) [10]))
(println (eval lucky-number))

(eval (eval (read-string "(list println \"matija\" \"interstellar\")")))