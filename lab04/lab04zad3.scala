def usun[A](l: List[A], el: A): List[A] = {
    @annotation.tailrec
    def helper(l: List[A], el: A, wynik: List[A] = List()): List[A] = l match {
        case x :: y => {
            if (x == el) helper(y, el, wynik)
            else helper(y, el, wynik :+ x)
        }
        case _ => wynik
    }
    helper(l, el)
}


@main def lab4zad3(): Unit = {
    println(usun(List(2, 1, 4, 1, 3, 3, 1, 2), 1))
}