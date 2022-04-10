def isOrdered[A](l: List[A])(leq: (A, A) => Boolean): Boolean = {
    @annotation.tailrec
    def helper(l: List[A], poprzedni: Option[A])(leq: (A, A) => Boolean): Boolean = l match{
        case x :: y => {
            if (leq(poprzedni.get, x) == false) false
            else helper(y, Some(x))(leq)
        }
        case _ => true
    }

    helper(l.tail, Some(l.head))(leq)
}

@main def lab5zad3(): Unit = {
    println(isOrdered(List(1, 2, 2, 4))(_ <= _))
}