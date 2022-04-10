@main def lab4zad5(): Unit = {
    type Pred[A] = A => Boolean
    val x = (n: Int) => n < 9
    val y = (n: Int) => n > 5
    val oraz = and(x,y)
    val lub = or(x,y)
    val nie = not(x)
    val to = imp(x,y)
    println(oraz(6))
    println(lub(5))
    println(nie(5))
    println(to(1))
} 
type Pred[A] = A => Boolean

def and[A](p: Pred[A], q: Pred[A]): Pred[A] = {
    n => p(n) && q(n)
}

def or[A](p: Pred[A], q: Pred[A]): Pred[A] = {
    n => p(n) || q(n)
}

def not[A](p: Pred[A]): Pred[A] = {
    n => !p(n)
}

def imp[A](p: Pred[A], q: Pred[A]): Pred[A] = {
    n => {
        if (p(n)) q(n)
        else !p(n)
    }
}
