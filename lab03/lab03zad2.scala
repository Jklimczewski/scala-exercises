def jestpierwsza(n: Int, x: Int = 2): Boolean = {
    @annotation.tailrec
    def helper(n: Int, x: Int = 2): Boolean = {
        assert(n >= 2) 
        if (n == x) true
        else if (n % x == 0) false
        else helper(n, x=x+1)
    }
    helper(n)
}

@main def lab3zad2(n: Int): Unit = {
    println(n + ", " + pierwsza(n))
}