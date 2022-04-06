def skompresuj[A](l: List[A]): List[(A, Int)] = {
    @annotation.tailrec
    def helper(l: List[A], poprzedni: Option[A] = None, count: Int = 0, wynik: List[(A, Int)] = List()): List[(A, Int)] = l match {
        case x :: y => {
            if (poprzedni == None) helper(y, Some(x), count + 1, wynik)
            else if (x == poprzedni.get) helper(y, Some(x), count + 1, wynik)
            else helper(y, Some(x), 1, wynik :+ (poprzedni.get, count))
        }
        case _ => wynik :+ (poprzedni.get, count)
    }
    helper(l)
}

@main def lab5zad2(): Unit = {
    println(skompresuj(List('a', 'a', 'b', 'c', 'c', 'c', 'a', 'a', 'b', 'd')))
}