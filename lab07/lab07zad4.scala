def indices[A](l: List[A], el: A): Set[Int] = {
    val zipped = l.zipWithIndex
    zipped.foldLeft(List[Int]())( (acc, elem) => {
        if (elem.head == el) acc :+ elem(1)
        else acc
    }).toSet
}

@main def lab7zad4(): Unit = {
    val lista = List(1, 2, 1, 1, 5)
    println(indices(lista, 7))
}