def deStutter[A](seq: Seq[A]): Seq[A] = {
    seq.foldLeft(List[A]())( (akum, elem) => {
        if (akum == Nil) akum :+ elem
        else if (elem != akum.last) akum :+ elem
        else akum
    })
}

@main def lab6zad3(): Unit = {
    println(deStutter(Seq(1, 1, 2, 4, 4, 4, 1, 3)))
}