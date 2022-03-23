def reverse(str: String): String = {
    @annotation.tailrec
    def helper(str: String, akum: String= ""): String = {
        if (str == "") akum
        else helper(str.tail, str.head + akum)
    }
    helper(str)
}

@main def lab3zad1(): Unit = {
    println(reverse("kot"))
}