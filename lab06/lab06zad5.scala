def isOrdered[A](seq: Seq[A])(leq: (A, A) => Boolean): Boolean = {
    val iter = seq.sliding(2)
    def helper(iter: Iterator[Seq[A]])(leq: (A, A) => Boolean) : Boolean = iter.hasNext match {
        case true => {
            val x = iter.next
            if (leq(x.head, x.last) == false) false
            else helper(iter)(leq)
        }
        case _ => true
    }
    helper(iter)(leq)
}

@main def lab6zad5(): Unit = {
    println(isOrdered(Seq(1, 2, 2, 4))(_ <= _))
}