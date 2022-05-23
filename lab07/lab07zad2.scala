def sumOpts(seq: Seq[Option[Double]]): Option[Double] = {
    val folded = seq.foldLeft(Some(0.0))( (akum, elem) => {
        Some(akum.get + elem.getOrElse(0.0))
    })
    if (folded.get == 0.0) None
    else folded
}
@main def lab7zad2(): Unit = {
    val lista = List(Some(5.4), Some(-2.0), Some(1.0), None, Some(2.6))
    println( sumOpts(lista) == Some(7.0) ) // ==> true
    println( sumOpts(List()) == None) // ==> true
    println( sumOpts(List(None, None)) == None) // ==> true
}