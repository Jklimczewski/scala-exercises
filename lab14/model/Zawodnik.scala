package jp1.akka.lab13.model

import akka.actor.Actor

object Zawodnik {
  case object Próba
  // polecenie wykonania próby (kończy się zwróceniem Wyniku,
  // za pomocą komunikatu Grupa.Wynik)
}

class Zawodnik(o: Osoba) extends Actor {
  import Zawodnik._

  // override def preStart(): Unit = {
  //   println(s"${self.path}")
  // }
  def receive: Receive = {
    case Próba => {
      val ocena = Utl.ocena()
      sender() ! Grupa.Wynik(ocena)
    }
  }
}
