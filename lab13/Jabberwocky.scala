import akka.actor.{ActorSystem, Actor, Props}
import scala.concurrent.duration._
import scala.util.Random
/*
  W konfiguracji projektu wykorzystana została wtyczka
  sbt-revolver. W związku z tym uruchamiamy program poleceniem

    reStart

  a zatrzymujemy pisząc (mimo przesuwających się komunikatów)

     reStop

  i naciskając klawisz ENTER. Jeśli czynności powyższe
  już wykonywaliśmy to możemy też przywołać poprzednie
  polecenia używając strzałek góra/dół na klawiaturze.
*/


case object Cyk
case class Strzelać(obroncy1: ActorRef, obroncy2: ActorRef)
case class Atak(obroncy: ActorRef)

class SiłaWyższa extends Actor {
  def receive = {
    case Cyk => {
      println("Cyk")
      // wysyłamy polacenie „Strzelać” do obu Zamków
      val jeden = context.actorOf(Props[Zamek](), "zamek1")
      val dwa = context.actorOf(Props[Zamek](), "zamek2")
      val obroncy1 = (1 to 101).toList.map(el => context.actorOf(Props[Obrońca](), s"obronca${el}"))
      val obroncy2 = (101 to 201).toList.map(el => context.actorOf(Props[Obrońca](), s"obronca${el}"))

      jeden ! Strzelać(obroncy1, obroncy2)
      dwa ! Strzelać(obroncy2, obroncy1)
    }
  }
}

class Zamek extends Actor {
  def receive: Receive = {
    case Strzelać(x, y) => x match {
      case pierwszy :: reszta => pierwszy ! Atak(y)
      case _ => 
     // Random.between(20, 30)
    }
  }
}

class Obrońca extends Actor {
  def receive: Receive = {
    case Atak(y) =>
  }
}

@main
def bitwa: Unit = {
  val system = ActorSystem("Jabberwocky")
  import system.dispatcher
  val siłaWyższa = system.actorOf(Props[SiłaWyższa](), "SilaWyzsza")

  val pantaRhei = system.scheduler.scheduleWithFixedDelay(
    Duration.Zero,     // opóźnienie początkowe
    1000.milliseconds, // odstęp pomiedzy kolejnymi komunikatami
    siłaWyższa,        // adresat „korespondencji”
    Cyk                // komunikat
  )

  // Oczywiście zatrzymanie symulacji NIE MOŻE się odbyć tak, jak poniżej
  // Thread.sleep(3000)
  // val res = if pantaRhei.cancel()
  //   then "Udało się zakończyć „cykanie”"
  //   else "Coś poszło nie tak – dalej „cyka”"
  // println(res)
  // system.terminate()
}
