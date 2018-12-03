(ns com.matija.macros)


(defmacro infix
  [[operand1 op operand2]]
  (list op operand1 operand2)
  )

(defmacro print_and_ret
  [e]
  (let [eval_e (eval e)]
    (println eval_e)
    eval_e))

(defmacro my-print
  [exp]
  (list 'let ['result exp]
        (list 'println 'result)
        'result))

(defn code-criticism
  [criticism code]
  `(println ~criticism '~code))

(defmacro code-critic
  [bad good]
  `(do ~@(map #(apply code-criticism %)
            [["This is good code:" good]
             ["This is bad code:" bad]])))
(defmacro unless
  [test & branches]
  (conj (reverse branches) test 'if))



(def order-details-validations
  {:name
   ["Please enter a name" not-empty]

   :email
   ["Please enter an email address" not-empty

    "Your email address doesn't look like an email address"
    #(or (empty? %) (re-seq #"@" %))]})

(defn error-messages-for
  [to-validate message-validator-pairs]
  (map first (filter #(not ((second %) to-validate))
                     (partition 2 message-validator-pairs))))



(defmacro when-valid
  [data validator print-success fn-success]
  `(let [result# (~validator ~data)]
    (if result# (do
                 ~print-success
                 ~fn-success))
    nil))

(println (when-valid "mat" not-empty (println "is not empty") 1))
(println (macroexpand '(when-valid "mat" not-empty (println "is not empty") 1)))