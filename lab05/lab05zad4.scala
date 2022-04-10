def applyForAll[A, B](l: List[A])(f: A => B): List[B] = {
    @annotation.tailrec
    def helper(l: List[A], wynik: List[B] = List())(f: A => B): List[B] = l match {
        case x :: y => helper(y, wynik :+ f(x))(f)
        case _ => wynik
    }
    helper(l)(f)
}

@main def lab5zad4(): Unit = {
    println(applyForAll(List(1, 3, 5))(f = (n) => n + 3))
}