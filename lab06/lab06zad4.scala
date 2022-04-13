def remElems[A](seq: Seq[A], k: Int): Seq[A] = {
    val zipped = seq.zipWithIndex
    val filtered = zipped.filter((a,b) => b != k)
    filtered.map((a,b) => a)
}

@main def lab6zad4(): Unit = {
    println(remElems(Seq(1, 1, 2, 4, 4, 4, 1, 3), 0))
}




// List((1,0), (1,1), (2,2), (4,3), (4,4), (4,5), (1,6), (3,7))