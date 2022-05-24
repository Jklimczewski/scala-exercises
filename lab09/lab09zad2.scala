import scala.io.Source

@main def lab9zad2(): Unit = {
  val plik = Source.fromResource("ogniem-i-mieczem.txt").getLines.toList
  val folded = plik.foldLeft(List[List[Char]]())((akum, elem) => {
      val literki = (elem.toLowerCase).foldLeft(List[Char]())((acc, el) => {
          if (el.isLetter) acc :+ el.toLower
          else acc
      })
      akum :+ literki
    })
    val list = folded.flatten
    val grouped = list.groupBy(n => n)
    val literki = grouped.keys
    val ilosc = grouped.values.map(n => n.size)
    val zipped = literki.zip(ilosc).map((a,b) => (a, "*" * b))
    zipped.foreach(el => println(s"${el(0)}: ${el(1)}"))
}