def parzysta(n: Int): Boolean = {
 if (n % 2 == 0) true
 else false
}

@main def lab2zad1(n: Int): Unit = {
  
  if (parzysta(n) == true) println("Parzysta")
  else println("Nieparzysta")
}