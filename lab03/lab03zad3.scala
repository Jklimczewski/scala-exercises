def ciag(n: Int): Int = {
    def helper(n: Int, prev: Int = 1, wynik: Int = 3, licznik: Int = 3): Int = {
        if (n == licznik) wynik
        else helper(n, wynik, wynik + prev, licznik + 1)
    }
    if (n == 1) 2
    else if (n == 2) 1
    else helper(n)
}



@main def lab3zad3(): Unit = {
    println(ciag(9))
}