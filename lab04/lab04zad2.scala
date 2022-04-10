def maksimum(l1: List[Double], l2: List[Double]): List[Double] = {
    @annotation.tailrec
    def helper(l1: List[Double], l2: List[Double], wynik: List[Double] = List()): List[Double] = (l1,l2) match {
        case (x :: y, Nil ) => helper(y, l2, wynik :+ x)
        case (Nil, x :: y) => helper(l1, y, wynik :+ x)
        case (x :: y, z :: m) => {
            if (x > z) (helper(y, m, wynik :+ x))
            else helper(y, m, wynik :+ z)
        }
        case _ => wynik
    }
    helper(l1, l2)
}

@main def lab4zad2(): Unit = {
    println(maksimum(List(2.0, -1.6, 3.2, 5.4, -8.4), List(3.3, -3.1, 3.2, -4.1, -0.4, 5.5)))
}