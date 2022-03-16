def pierwsza(n: Int, x: Int = 2): Boolean = {
    assert(n >= 2) 
    if (n == x) true
    else if (n % x == 0) false
    else pierwsza(n, x=x+1)
}

@main
def lab2zad3(n: Int): Unit = {
    println(n + ", " + pierwsza(n))
}