import akka.actor.*

case object Pileczka3
case class Graj3(przeciwnik: ActorRef, nastepnik: ActorRef)

class Gracz3 extends Actor {
  def receive: Receive = {
    case Graj3(x, y) => x ! Graj3(y, self); context.become(next(x))
  }
  def next(przeciwnik: ActorRef): Receive = {
      case Graj3(x, y) => println(s"Pileczka u ${self.path.name}"); przeciwnik ! Pileczka3
      case Pileczka3 => println(s"Pileczka u ${self.path.name}"); przeciwnik ! Pileczka3
  }
}


@main def lab11zad3(): Unit = {
  val system = ActorSystem("HaloAkka")
  val actor1 = system.actorOf(Props[Gracz3](), "gracz1")
  val actor2 = system.actorOf(Props[Gracz3](), "gracz2")
  val actor3 = system.actorOf(Props[Gracz3](), "gracz3")

  actor1 ! Graj3(actor2, actor3)
}