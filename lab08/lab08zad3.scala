def threeNumbers(n: Int): Unit = {
  val wynik = for {
    c <- (1 to n).toList
    b <- (1 to c).toList
    a <- (1 to b).toList
    if (c * c == a * a + b * b)
  } yield (a, b, c)
  println(wynik)
}

@main def lab8zad3(): Unit = {
  threeNumbers(10)
}
