def subSeq[A](seq: Seq[A], begIdx: Int, endIdx: Int): Seq[A] = {
    seq.take(endIdx).drop(begIdx)
}

@main def lab6zad1(): Unit = {
    println(subSeq(Seq(1, 1, 2, 4, 4, 4, 1, 3), 2, 4))
}