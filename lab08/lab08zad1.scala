def mastermind(secret: List[Int], guess: List[Int]): Unit = {
  def pozycja(secret: List[Int], guess: List[Int], akum: Int = 0): Int = secret match {
    case Nil => akum
    case head :: tail => {
      if (guess.head == head) {
        pozycja(secret.tail, guess.tail, akum + 1)
      } else pozycja(secret.tail, guess.tail, akum)
    }
  }
  def istnieje(secretSorted: List[Int], guessSorted: List[Int], akum2: Int = 0): Int =
    (secretSorted, guessSorted) match {
      case (x :: y, x2 :: y2) => {
        if (x > x2) istnieje(secretSorted, guessSorted.tail, akum2)
        else if (x < x2) istnieje(secretSorted.tail, guessSorted, akum2)
        else istnieje(secretSorted.tail, guessSorted.tail, akum2 + 1)
      }
      case _ => akum2
    }
  val czarne = pozycja(secret, guess)
  val istnieja = istnieje(secret.sorted, guess.sorted)
  val biale = istnieja - czarne
  println(czarne)
  println(biale)
}

@main def lab8zad1(): Unit = {
  val secret = List(2, 3, 2, 2, 4, 5)
  val guess = List(2, 1, 2, 5, 4, 2)
  mastermind(secret, guess)
}
