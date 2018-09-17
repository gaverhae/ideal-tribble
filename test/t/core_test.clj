(ns t.core-test
  (:require [expectations :refer [expect]]
            [t.core :as t]))

(defn run-program
  [inputs]
  (let [r (atom nil)]
    {:out (with-out-str
            (reset! r (reductions t/main-iter (t/init-state) inputs)))
     :states @r}))

(expect
  {:out "OOOOO\nOOOOO\nOAOOO\nOOOOO\nOOOOO\nOOOOO\nJJJJJ\nJJZZJ\nJWJJJ\nJWJJJ\nJJJJJ\nJJJJJ\n"
   :states [{:image []
             :width 0
             :height 0}
            {:image [["O" "O" "O" "O" "O"]
                     ["O" "O" "O" "O" "O"]
                     ["O" "O" "O" "O" "O"]
                     ["O" "O" "O" "O" "O"]
                     ["O" "O" "O" "O" "O"]
                     ["O" "O" "O" "O" "O"]]
             :width 5
             :height 6}
            {:image [["O" "O" "O" "O" "O"]
                     ["O" "O" "O" "O" "O"]
                     ["O" "A" "O" "O" "O"]
                     ["O" "O" "O" "O" "O"]
                     ["O" "O" "O" "O" "O"]
                     ["O" "O" "O" "O" "O"]]
             :width 5
             :height 6}
            {:image [["O" "O" "O" "O" "O"]
                     ["O" "O" "O" "O" "O"]
                     ["O" "A" "O" "O" "O"]
                     ["O" "O" "O" "O" "O"]
                     ["O" "O" "O" "O" "O"]
                     ["O" "O" "O" "O" "O"]]
             :width 5
             :height 6}
            {:image [["J" "J" "J" "J" "J"]
                     ["J" "J" "J" "J" "J"]
                     ["J" "A" "J" "J" "J"]
                     ["J" "J" "J" "J" "J"]
                     ["J" "J" "J" "J" "J"]
                     ["J" "J" "J" "J" "J"]]
             :width 5
             :height 6}
            {:image [["J" "J" "J" "J" "J"]
                     ["J" "J" "J" "J" "J"]
                     ["J" "W" "J" "J" "J"]
                     ["J" "W" "J" "J" "J"]
                     ["J" "J" "J" "J" "J"]
                     ["J" "J" "J" "J" "J"]]
             :width 5
             :height 6}
            {:image [["J" "J" "J" "J" "J"]
                     ["J" "J" "Z" "Z" "J"]
                     ["J" "W" "J" "J" "J"]
                     ["J" "W" "J" "J" "J"]
                     ["J" "J" "J" "J" "J"]
                     ["J" "J" "J" "J" "J"]]
             :width 5
             :height 6}
            {:image [["J" "J" "J" "J" "J"]
                     ["J" "J" "Z" "Z" "J"]
                     ["J" "W" "J" "J" "J"]
                     ["J" "W" "J" "J" "J"]
                     ["J" "J" "J" "J" "J"]
                     ["J" "J" "J" "J" "J"]]
             :width 5
             :height 6}
            nil]}
  (run-program ["I 5 6"
                "L 2 3 A"
                "S"
                "F 3 3 J"
                "V 2 3 4 W"
                "H 3 4 2 Z"
                "S"
                "X"]))
