import scala.io.Source

@main def lab9zad2(): Unit = {
  val plik = Source.fromResource("ogniem-i-mieczem.txt").getLines.toList
  val max: Int = 2

  val fold = plik.foldLeft(List[List[Char]]())((akum, elem) => {
    val literki = elem.foldLeft(List[Char]())((acc, el) => el match {
      case el if el.isLetter => acc :+ el.toLower
      case _ => acc
    })
    akum :+ literki
  }).flatten
  val grouped = fold.groupBy(n => n).transform((k, v) => v.size)
  val stars = grouped.map((a,b) => (a,b) match {
    case (x, y) if y > max => (a, "*" * max)
    case _ => (a, "*" * b)
  })
  stars.foreach(el => println(s"${el.head}: ${el(1)}"))
}