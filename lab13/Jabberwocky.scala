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
case object Atakowany

class SiłaWyższa extends Actor {
  def receive: Receive = {
    case Cyk => {
      println("Cyk")
      // wysyłamy polacenie „Strzelać” do obu Zamków
      val jeden = context.actorOf(Props[Zamek](), "zamek1")
      val dwa = context.actorOf(Props[Zamek](), "zamek2")
      val obroncy1 = (1 to 101).toList.map(el => context.actorOf(Props[Obrońca](), s"obronca${el}"))
      val obroncy2 = (101 to 201).toList.map(el => context.actorOf(Props[Obrońca](), s"obronca${el}"))

      context.become(next(jeden, dwa, obroncy1, obroncy2))
    }
  }
  def next(jeden: ActorRef, dwa: ActorRef, obroncy1: List[ActorRef], obroncy2: List[ActorRef]): Receive = {
    case Cyk => {
      jeden ! Strzelać(obroncy1, obroncy2)
      dwa ! Strzelać(obroncy2, obroncy1)
    }
  }
}

class Zamek extends Actor {
  def receive: Receive = {
    case Strzelać(x, y) => {
      x.foreach(el => el ! Atak(y))
    }
  }
}

class Obrońca extends Actor {
  def receive: Receive = {
    case Atak(aktywni_obroncy) => {
      val index = Random.between(0, aktywni_obroncy.length)
      aktywni_obroncy(index) ! Atakowany(aktywni_obroncy)
    }
    case Atakowany(aktywni_obroncy) => {
      val szanse = aktywni_obroncy.toFloat/200.toFloat
      val losowa = Random.between(1, 101)
      if ((szanse * 100).toInt >= losowa) 
        self() ! PoisonPill
    }
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
