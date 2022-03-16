@annotation.tailrec
def nwd(a: Int, b: Int): Int = {
    if (a == b) a
    else if (a>b) nwd(a-b,b)
    else nwd(a,b-a)
}

@main def lab2zad2: Unit = {
    val a:Int = io.StdIn.readInt()
    val b:Int  = io.StdIn.readInt()
    println(nwd(a,b))
}
