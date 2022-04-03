def tasuj(l1: List[Int], l2: List[Int]): List[Int] = {
   // @annotation.tailrec
    def helper(l1: List[Int], l2: List[Int], l3: List[Int] = List(), last: Int = 0): List[Int] = {
        if (l1 == Nil && l2 == Nil) l3
        else if (l1 == Nil) helper(l1, l2.tail, l3 :+ l2.head)
        else if (l2 == Nil) helper(l1.tail, l2, l3 :+ l1.head)
        else if (l1.head <= l2.head) helper(l1.tail, l2, l3 :+ l1.head)
        else  helper(l1, l2.tail, l3 :+ l2.head)
    }
    helper(l1, l2)
}



@main def lab3zad4(): Unit = {
    println(tasuj(List(2, 4, 3, 5), List(1, 2, 2, 3, 1, 5)))
}