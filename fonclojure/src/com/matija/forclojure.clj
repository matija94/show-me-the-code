(ns com.matija.forclojure)


(defn word-cnt55
  [seq]
  (reduce (fn [m num]
            (assoc m num (inc (get m num 0))))
          {}
          seq))

(defn balance-num115
  [int_num]
  (let [str_int (str int_num)
        n (count str_int)
        sum-str-nums (fn [str]
                       (reduce (fn [acc chr]
                                 (+ acc (- (int chr) 48)))
                               0
                               str))]
    (= (sum-str-nums (subs str_int 0 (/ n 2)))
       (sum-str-nums (subs str_int (Math/ceil (/ n 2)))))))


(defn prime-numbers67
  [n]
  (let [prime-number? (fn [num]
                        (every? #(= false %) (map #(= (mod num %) 0) (range 2 num))))]
    (drop 2 (take (+ n 2) (filter prime-number? (range))))))
