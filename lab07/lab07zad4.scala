def indices[A](l: List[A], el: A): Unit = {
    l.foldLeft(Set[Int]())( (acc, elem) => {
        val 
    })
}

@main def lab7zad4(): Unit = {
    val lista = List(1, 2, 1, 1, 5)
    println(indices(lista, 1))
}