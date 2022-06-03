import akka.actor.*

case object Pileczka
case class Graj1(przeciwnik: ActorRef)

class Gracz1 extends Actor {
  def receive: Receive = {
    case Graj1(x) => println(self.path.name + " zaczyna"); x ! Pileczka
    case Pileczka => println("Pileczka u gracza: " + self.path.name); sender() ! Pileczka
  }
}

@main def lab11zad1: Unit = {
  val system = ActorSystem("HaloAkka")
  val actor1 = system.actorOf(Props[Gracz1](), "gracz1")
  val actor2 = system.actorOf(Props[Gracz1](), "gracz2")
  actor1 ! Graj1(actor2)
}