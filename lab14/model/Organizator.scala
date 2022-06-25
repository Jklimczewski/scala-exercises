package jp1.akka.lab13.model

import akka.actor.{Actor, ActorRef, Props}

val akkaPathAllowedChars = ('a' to 'z').toSet union
  ('A' to 'Z').toSet union
  "-_.*$+:@&=,!~';.)".toSet

object Organizator {
  case object Start
  // rozpoczynamy zawody – losujemy 50 osób, tworzymy z nich zawodników
  // i grupę eliminacyjną
  case object Runda
  // polecenie rozegrania rundy (kwalifikacyjnej bądź finałowej) –  wysyłamy Grupa.Runda
  // do aktualnej grupy
  case object Wyniki
  // polecenie wyświetlenia klasyfikacji dla aktualnej grupy
  case class Wyniki(w: Map[ActorRef, Option[Ocena]])
  // wyniki zwracane przez Grupę
  case object Stop
  // kończymy działanie
}

class Organizator extends Actor {
  // importujemy komunikaty na które ma reagować Organizator
  import Organizator._

  def receive: Receive = {
    case Start => {
      // tworzenie 50. osób, opdowiadających im Zawodników
      // oraz Grupy eliminacyjnej
      val zawodnicy = List.fill(50) {
        val o = Utl.osoba()
        context.actorOf(Props(Zawodnik(o)), s"${o.imie}-${o.nazwisko}" filter akkaPathAllowedChars)
      }
      val grupa_kwal = context.actorOf(Props(Grupa(zawodnicy)), "Grupa_kwal")
      context.become(runda_kwali(grupa_kwal))
    }
    case Stop =>
      println("konczymy zawody...")
      context.system.terminate()
  }
  def runda_kwali(grupa_kwal: ActorRef): Receive = {
    case Runda => {
      grupa_kwal ! Grupa.Runda
    }
    case Wyniki(aktor_ocena: Map[ActorRef, Option[Ocena]]) => {
      context.stop(sender())
      val bez_Some = aktor_ocena.toList.map((a,b) => (a,b.getOrElse(Ocena(0,0,0))))
      val sorted = bez_Some.sortBy((a,b) => b match {
        case Ocena(nota1, nota2, nota3) => (nota1 + nota2 + nota3, nota1, nota2 , nota3) 
      }).reverse
      println("Runda kwalifikacyjna zakończona")
      context.become(wyniki_kwali(sorted))
    }
  }
  def wyniki_kwali(wyniki: List[(ActorRef, Ocena)]): Receive = {
    case Wyniki => {
      wyniki.foreach(n => n match {
        case (aktor, Ocena(nota1, nota2, nota3)) => println(s"${wyniki.indexOf(n) + 1}. ${aktor.path.name} - ${nota1}-${nota2}-${nota3} = ${nota1+nota2+nota3}")
      })
    }
    case Stop =>
      println("konczymy zawody...")
      context.system.terminate()

    case Runda => {
      val finalisci_wyniki = wyniki.take(20)
      val finalisci = finalisci_wyniki.map((a,b) => a)
      context.become(runda_finalowa(finalisci, finalisci_wyniki))
      self ! Runda
    }
  }
  def runda_finalowa(finalisci: List[ActorRef], finalisci_wyniki: List[(ActorRef, Ocena)]): Receive = {
    case Runda => {
      val grupa_final = context.actorOf(Props(Grupa(finalisci)), "Grupa_final")
      grupa_final ! Grupa.Runda
    }
    case Wyniki(aktor_ocena: Map[ActorRef, Option[Ocena]]) => {
      context.stop(sender())
      val bez_Some2 = aktor_ocena.toList.map((a,b) => (a,b.getOrElse(Ocena(0,0,0))))
      val final_result = finalisci_wyniki.foldLeft(List[(ActorRef, Ocena)]())((akum, elem) => {
        val helper = bez_Some2.filter((a,b) => a.path.name == elem(0).path.name)
        val oceny = helper.head(1)
        (oceny, elem(1)) match {
          case (Ocena(nota1, nota2, nota3), Ocena(nota4, nota5, nota6)) => akum :+ (elem(0), Ocena(nota1+nota4, nota2+nota5, nota3+nota6))
        }
      })
      val sorted = final_result.sortBy((a,b) => b match {
        case Ocena(nota1, nota2, nota3) => (nota1 + nota2 + nota3, nota1, nota2 , nota3) 
      }).reverse
      println("Runda finalowa zakończona")
      context.become(wyniki_final(sorted))
    }
  }
  def wyniki_final(wyniki: List[(ActorRef, Ocena)]): Receive = {
    case Wyniki => {
      wyniki.foreach(n => n match {
        case (aktor, Ocena(nota1, nota2, nota3)) => println(s"${wyniki.indexOf(n) + 1}. ${aktor.path.name} - ${nota1}-${nota2}-${nota3} = ${nota1+nota2+nota3}")
      })
    }
    case Stop =>
      println("konczymy zawody...")
      context.system.terminate()
  }
}
