package jp1.akka.lab13.model

import akka.actor.{Actor, ActorRef}

object Grupa {
  case object Runda
  // Zawodnicy mają wykonać swoje próby – Grupa
  // kolejno (sekwencyjnie) informuje zawodników
  // o konieczności wykonania próby i „oczekuje”
  // na ich wynik (typu Option[Ocena])
  case object Wyniki
  // Polecenie zwrócenia aktualnego rankingu Grupy
  // Oczywiście klasyfikowani są jedynie Zawodnicy,
  // którzy pomyślnie ukończyli swoją próbę
  case class Wynik(ocena: Option[Ocena])
  // Informacja o wyniku Zawodnika (wysyłana przez Zawodnika do Grupy)
  // np. Wynik(Some(Ocena(10, 15, 14)))
  // Jeśli zawodnik nie ukończy próby zwracana jest wartość Wynik(None)
  case object Koniec
  // Grupa kończy rywalizację
}
class Grupa(zawodnicy: List[ActorRef]) extends Actor {
  import Grupa._

  def receive: Receive = {
    case Runda => {
      zawodnicy.foreach(n => n ! Zawodnik.Próba)
      context.become(lista_wynikow(List(), zawodnicy.length))
    }
  }
  def lista_wynikow(wyniki: List[(ActorRef, Option[Ocena])], max: Int, ilosc: Int = 0): Receive = {
    case Wynik(ocena) if (ilosc + 1 == max) => {
      val final_result: Map[ActorRef, Option[Ocena]] = (wyniki :+ (sender(), ocena)).toMap
      context.parent ! Organizator.Wyniki(final_result)
    }
    case Wynik(ocena) if (ilosc + 1 != max) => {
      val result: (ActorRef, Option[Ocena]) = (sender(), ocena)
      context.become(lista_wynikow(wyniki :+ result, max, ilosc + 1))
    }
    case _ =>
  }
}
