def divide[A](l: List[A]): (List[A], List[A]) = {
    @annotation.tailrec
    def helper(l: List[A], licznik: Int = 0, wynik1: List[A]= List(), wynik2: List[A] = List()): (List[A], List[A]) = l match {
        case x :: y => {
            if (licznik % 2 == 0) helper(y, licznik+1, wynik1 :+ x, wynik2)
            else helper(y, licznik+1, wynik1, wynik2 :+ x)
        }
        case _ => (wynik1, wynik2)
    } 
    helper(l)
}

@main def lab4zad4(): Unit = {
    println(divide(List(1, 3, 5, 6, 7)))
}