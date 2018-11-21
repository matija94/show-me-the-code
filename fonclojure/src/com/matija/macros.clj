(ns com.matija.macros)


(defmacro infix
  [[operand1 op operand2]]
  (op operand1 operand2)
  )

(println (macroexpand '(infix (10 + 25))))
(println (infix (1 + 2)))

(println '(+ 1 2))
(println (list + 1 2))
(println (eval '(+ 1 2)))
(println (eval (list + 1 2)))



(def uneval_list '(+ 1 2))
(println uneval_list)
(println (eval uneval_list))

(println 'if)


(defmacro print_and_ret
  [e]
  (let [eval_e (eval e)]
    (println eval_e)
    eval_e))


(println (print_and_ret (+ 1 2)))


(defmacro my-print
  [exp]
  (list 'let ['result exp]
        (list 'println 'result)
        'result))


(println (my-print (+ 1 2)))


(println (list + 1 2))

(defmacro code-critic
  "Phrases are courtesy Hermes Conrad from Futurama"
  [bad good]
  `(do (println "Great squid of Madrid, this is bad code:"
                '~bad)
       (println "Sweet gorilla of Manila, this is good code:"
                '~good)))

(code-critic (1 + 1) (+ 1 2))



(println (quote (+ 1 2)))
(println (eval '(+ 1 2)))


(println (quote ~(1 + 2)))