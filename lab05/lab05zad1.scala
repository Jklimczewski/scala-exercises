def oczysc[A](l: List[A]): List[A] = {
    @annotation.tailrec
    def helper(l: List[A], poprzedni: Option[A] = None, wynik: List[A] = List()): List[A] = l match{
        case x :: y => {
            if (wynik.isEmpty) helper(y, Some(x), wynik :+ x) 
            else if (x != poprzedni.get) helper(y, Some(x), wynik :+ x) 
            else helper(y, poprzedni, wynik)
        }
        case _ => wynik
    }
    helper(l)
}


@main def lab5zad1(): Unit = {
    println(oczysc(List(1, 1, 2, 4, 4, 4, 1, 3)))
}