def mastermind(secret: List[Int], guess: List[Int]): Unit = {
    def pozycja(secret: List[Int], guess: List[Int], akum: Int = 0): Int = secret match {
        case Nil => akum
        case head :: tail => {
            if (guess.head == head) {
                pozycja(secret.tail, guess.tail, akum+1)
            }
            else pozycja(secret.tail, guess.tail, akum)
        }
    }
    def istnieje(secretSorted: List[Int], guessSorted: [Int], akum: Int = 0) secret match {
        case Nil => akum
        case head :: tail => {
            if (guessSorted == head) {
                istnieje(secretSorted.tail, guessSorted.tail, akum+1)
            }
            else
        } 
    }
    val czarne = pozycja(secret, guess)
    
}

@main def lab8zad1(): Unit = {
    val secret = List(1, 3, 2, 8, 4, 5)
    val guess = List(2, 1, 2, 4, 7, 2)
    println(mastermind(secret, guess))
}