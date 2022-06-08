import akka.actor.*
import scala.io.Source

case class Zlecenie(tekst: List[String])
case class Init(liczbaPracownikow: Int)
case class Wykonaj(linia: String)
case class Wynik(liczba: Int)
case object Start


class Szef extends Actor {
    def receive: Receive = {
        case Init(n) => {
            val p = for {
                x <- (1 to n)
                } yield context.actorOf(Props[Pracownik](), s"Pracownik${x}")
            context.become(zlecenia(p.toList))
        }
    }
    def zlecenia(pracownicy: List[ActorRef]): Receive = {
        case Zlecenie(y) => {
            context.become(wysylanie(y, pracownicy, pracownicy, y.length, 0))
            self ! Start
        }
        case _ =>
    }
    def wysylanie(dane: List[String], wszyscyPracownicy: List[ActorRef], pracownicy: List[ActorRef],
        ileTrzeba: Int, ileDoszlo: Int, wynik: Int = 0): Receive = {
        case Start => (dane, pracownicy) match {
            case (linia :: reszta, pracownik :: reszta2) => {
                pracownik ! Wykonaj(linia)
                context.become(wysylanie(reszta, wszyscyPracownicy, reszta2, ileTrzeba, ileDoszlo, wynik))
                self ! Start
            }
            case (_,_) =>
        }
        case Wynik(n) if (ileDoszlo + 1 < ileTrzeba) => dane match {
            case linia :: reszta => {
                sender() ! Wykonaj(linia)
                context.become(wysylanie(reszta, wszyscyPracownicy, pracownicy, ileTrzeba, ileDoszlo + 1, wynik + n))
            }
            case _ => context.become(wysylanie(dane, wszyscyPracownicy, pracownicy, ileTrzeba, ileDoszlo + 1, wynik + n))
        }
        case Wynik(n) if ileDoszlo + 1 == ileTrzeba => {
            println(wynik + n)
            context.become(zlecenia(wszyscyPracownicy))
        }
    }
}

class Pracownik extends Actor {
    def receive: Receive = {
        case Wykonaj(linia) => {
            sender() ! Wynik(linia.split(" ").toList.length)
        }
        case _ => 
    }
}

@main def lab12zad1(): Unit = {
    val system = ActorSystem("HaloAkka")
    val plik = Source.fromResource("ogniem-i-mieczem.txt").getLines.toList
    val actor = system.actorOf(Props[Szef](), "szef")
    actor ! Init(5)
    actor ! Zlecenie(plik)
}
