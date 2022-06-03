import akka.actor.*

case object Pileczka2
case class Graj2(przeciwnik: ActorRef, maks: Int)
case class PierwszeOdbicie(maks: Int)

class Gracz2 extends Actor {
  def receive: Receive = {
    case Graj2(x, y) => println(self.path.name + " zaczyna"); x ! PierwszeOdbicie(y); context.become(check(2, y))
    case PierwszeOdbicie(z) => println(s"1 odbicie, gracz ${self.path.name}"); context.become(check(3, z)); sender() ! Pileczka2
  }
  def check(licznik: Int, maks: Int): Receive = {
    case Pileczka2 if licznik <= maks => {
      println(s"${licznik} odbicie, gracz ${self.path.name}")
      context.become(check(licznik + 2, maks))
      sender() ! Pileczka2
    }
    case _ =>
  }
}

@main def lab11zad2(): Unit = {
  val system = ActorSystem("HaloAkka")
  val actor1 = system.actorOf(Props[Gracz2](), "gracz1")
  val actor2 = system.actorOf(Props[Gracz2](), "gracz2")
  actor1 ! Graj2(actor2, 10)
}