def ciag(n: Int): Int = {
    // @annotation.tailrec
    def helper(n: Int, wart: Int = 0): Int = {
        if (n == 2) wart
        else if (n == 2) wart=wart+3
        else helper(n-1) + helper(n-2)
    }
    if (n == 0) 2
    else if (n == 1) 1
    else helper(n)
}
@main def lab3zad3(): Unit = {
    println(ciag(9))
}