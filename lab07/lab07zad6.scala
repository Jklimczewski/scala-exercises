@main def lab7zad6(): Unit = {
    val strefy: Seq[String] = java.util.TimeZone.getAvailableIDs.toSeq
    val europed = strefy.filter(n => n.slice(0,6) == "Europe")
    val stripped = europed.foldLeft(List[String]())( (akum, elem) => {
        akum :+ elem.stripPrefix("Europe/")
    })
    println(stripped.sortBy(n => (n.length)))
    
}