def swap[A](l: List[A]): List[A] = {
    val helper = l.zipWithIndex
    val mapped = helper.partition( (a,b) => b % 2 == 0)
    val x = mapped(0).map( (a,b) => a)
    val y = mapped(1).map( (a,b) => a)
    val help = y.zip(x)
    val folded = help.foldLeft(List[A]())( (akum,elem) =>{
        akum :+ elem.head :+ elem(1)
    })
    if (l.size % 2 == 1) folded :+ l.last
    else folded
}

@main def lab7zad5(): Unit = {
    val lista = List(1, 2, 3, 4, 5)
    println(swap(lista)) // ==> List(2, 1, 4, 3, 5)
}