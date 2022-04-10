def sumuj(l: List[Option[Double]]): Option[Double] = {
    @annotation.tailrec
    def helper(l: List[Option[Double]], wynik: Option[Double] = None): Option[Double] = l match {
        case x :: y => {
            if (x.getOrElse(0.0) > 0) helper(y, Some(wynik.getOrElse(0.0) + x.get))
            else helper(y, wynik)
        }
        case _ => wynik
    }
    helper(l)
}

@main def lab4zad1(): Unit = {
    println(sumuj(List(Some(2.0), Some(4.0), Some(-3.0), None, Some(-3.0), None, Some(1.0))))
}