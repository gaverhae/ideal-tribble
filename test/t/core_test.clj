(ns t.core-test
  (:require [expectations :refer [expect]]
            [t.core :as t]))

(defn run-program
  [inputs]
  (reductions (fn [r i] (t/main-iter (:state r) i))
              {:state (t/init-state)
               :out []}
              inputs))

(expect
  [{:out []
    :state {:image []
            :width 0
            :height 0}}
   {:out t/help-text
    :state {:image []
            :width 0
            :height 0}}
   {:out [t/no-image]
    :state {:image []
            :width 0
            :height 0}}
   {:out []
    :state {:image [["O" "O" "O" "O" "O"]
                    ["O" "O" "O" "O" "O"]
                    ["O" "O" "O" "O" "O"]
                    ["O" "O" "O" "O" "O"]
                    ["O" "O" "O" "O" "O"]
                    ["O" "O" "O" "O" "O"]]
            :width 5
            :height 6}}
   {:out []
    :state {:image [["O" "O" "O" "O" "O"]
                    ["O" "O" "O" "O" "O"]
                    ["O" "A" "O" "O" "O"]
                    ["O" "O" "O" "O" "O"]
                    ["O" "O" "O" "O" "O"]
                    ["O" "O" "O" "O" "O"]]
            :width 5
            :height 6}}
   {:out []
    :state {:image [["O" "O" "O" "O" "O"]
                    ["O" "O" "O" "O" "O"]
                    ["O" "A" "O" "O" "O"]
                    ["O" "O" "O" "O" "O"]
                    ["O" "O" "O" "O" "O"]
                    ["O" "O" "O" "O" "O"]]
            :width 5
            :height 6}}
    {:out []
     :state {:image [["J" "J" "J" "J" "J"]
                     ["J" "J" "J" "J" "J"]
                     ["J" "A" "J" "J" "J"]
                     ["J" "J" "J" "J" "J"]
                     ["J" "J" "J" "J" "J"]
                     ["J" "J" "J" "J" "J"]]
             :width 5
             :height 6}}
     {:out []
      :state {:image [["J" "J" "J" "J" "J"]
                      ["J" "J" "J" "J" "J"]
                      ["J" "W" "J" "J" "J"]
                      ["J" "W" "J" "J" "J"]
                      ["J" "J" "J" "J" "J"]
                      ["J" "J" "J" "J" "J"]]
              :width 5
              :height 6}}
     {:out []
      :state {:image [["J" "J" "J" "J" "J"]
                      ["J" "J" "Z" "Z" "J"]
                      ["J" "W" "J" "J" "J"]
                      ["J" "W" "J" "J" "J"]
                      ["J" "J" "J" "J" "J"]
                      ["J" "J" "J" "J" "J"]]
              :width 5
              :height 6}}
     {:out []
      :state {:image [["J" "J" "J" "J" "J"]
                     ["J" "J" "Z" "Z" "J"]
                     ["J" "W" "J" "J" "J"]
                     ["J" "W" "J" "J" "J"]
                     ["J" "J" "J" "J" "J"]
                     ["J" "J" "J" "J" "J"]]
             :width 5
             :height 6}}
     {:out ["Bye!"]
      :state nil}]
  (run-program ["H"
                "S"
                "I 5 6"
                "L 2 3 A"
                "S"
                "F 3 3 J"
                "V 2 3 4 W"
                "H 3 4 2 Z"
                "S"
                "X"]))
