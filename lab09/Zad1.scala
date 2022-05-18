import scala.io.Source

@main def zad1(): Unit = {
    val plik: List[String] = Source.fromResource("nazwiska.txt").getLines.toList
    val listy = plik.map(n => n.split(" ").toList)
    val max = listy.foldLeft(0)( (acc, elem) => {
        if (elem.head.distinct.size > acc) elem.head.distinct.size
        else acc
    })
    val maxNames = listy.foldLeft(List[List[String]]())( (acc, elem) => {
        if (elem.head.distinct.size == max) acc :+ elem
        else acc
    })
    val result = maxNames.foldLeft(maxNames.head)( (acc, elem) => {
        if (elem.last.size > acc.tail.size) elem
        else acc
    })
    println(result)
}
