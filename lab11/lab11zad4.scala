import akka.actor.*

case object Pileczka4
case class Graj4(listaGraczy: List[ActorRef], pierwszy: ActorRef)
case class Init2(iloscGraczy: Int)

class Gracz4 extends Actor {
  def receive: Receive = {
    case Init2(x) => {
        val l = (2 to x).toList
        val lista: List[ActorRef] = l.foldLeft(List[ActorRef]())( (akum, elem) => {
            akum :+ context.actorOf(Props[Gracz4](), s"gracz${elem}")
        })
        self ! Graj4(lista, self)
    }
    case Graj4(x, y) => x match {
        case (pierwszy :: reszta) => context.become(partia(pierwszy)); pierwszy ! Graj4(reszta, y) 
        case _ => context.become(partia(y)); y ! Pileczka4
    } 
  }
  def partia(przeciwnik: ActorRef): Receive = {
      case Pileczka4 => println(s"Pileczka u ${self.path.name}"); przeciwnik ! Pileczka4
  }
}


@main def lab11zad4(): Unit = {
  val system = ActorSystem("HaloAkka")
  val actor1 = system.actorOf(Props[Gracz4](), "gracz1")
  actor1 ! Init2(5)
}