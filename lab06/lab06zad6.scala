def freqMax[A](list: List[A]): (Set[A],Int) = {
    val max = list.foldLeft(0)( (akum, elem) => {
        val ilosc = list.count(n => n == elem)
        if (ilosc > akum) ilosc
        else akum
    })
    val mapped = list.map(n => (n, list.count(m => m == n)))
    val filtered = mapped.filter( (a,b) => b == max)
    (filtered.map((a,b) => a).toSet, max)
}

@main def lab6zad6(): Unit = {
    val l = List(1, 1, 2, 4, 4, 3, 4, 1, 3)
    println( freqMax(l) == (Set(1, 4), 3) ) // ==> true
}