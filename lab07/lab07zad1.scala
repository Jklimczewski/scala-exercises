def freq[A](seq: Seq[A]): Set[(A, Int)] = {
    val x = seq.groupBy(n => (seq.count(m => m == n) ))
    x.foldLeft(Set[(A, Int)]()) ( (acc, elem) => {
        val y = (elem(1).head, elem(0))
        acc + y
    })

}
@main def lab7zad1(): Unit = {
    println(freq(Seq('a','b','a','c','c','a')))
}