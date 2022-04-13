def pairPosNeg(seq: Seq[Double]):(Seq[Double], Seq[Double]) = {
    val (wynik1, wynik2) = seq.partition(n => n != 0.0)
    wynik1.partition(n => n < 0.0)
}

@main def lab6zad2(): Unit = {
    println(pairPosNeg(Seq(1.0, 1.0, 2.0, -4.0, 4.0, -5.0, -1.0, 3.0, 0.0)))
}