def hipoteza(n: Int, x: Int = 2): Unit = {
    if (n == x+1) println("Nie udalo sie znalezc")
    else {
        if (pierwsza(x) == true) {
            if (pierwsza(n-x) == true) {
                println(n + " = " + x + " + " + (n-x))
            }
            else hipoteza(n, x=x+1)
        }
        else hipoteza(n, x=x+1)
    }
}


def pierwsza(x: Int, y: Int = 2): Boolean = {
    if (x == y) true
    else if (x % y == 0) false
    else pierwsza(x, y=y+1)
}

@main def lab2zad4(n: Int): Unit = {
    if (n % 2 == 0 && n > 2) hipoteza(n)
    else println("Podales zla liczbe")
}	