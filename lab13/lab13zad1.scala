import akka.actor.*
import scala.concurrent.duration._
import scala.util.Random


object SiłaWyższa {
  case class Init2(obroncy: List[ActorRef], zamekPrzeciwnika: ActorRef)
  case object Cyk
  case object Strzelać
  case class Atak(zamekPrzeciwnika: ActorRef)
  case object Strzala
  case class Cios(aktywni_obroncy: Int)
}

class SiłaWyższa extends Actor {
  import SiłaWyższa._
  
  def receive: Receive = {
    case Cyk => {
        val zamekA = context.actorOf(Props[Zamek](), "zamekA")
        val zamekB = context.actorOf(Props[Zamek](), "zamekB")
        val obroncyA = ((1 to 100).toList).map(n => context.actorOf(Props[Obrońca](), s"obronca_${n}A"))
        val obroncyB = ((1 to 100).toList).map(n => context.actorOf(Props[Obrońca](), s"obronca_${n}B"))
        zamekA ! Init2(obroncyA, zamekB)
        zamekB ! Init2(obroncyB, zamekA)
        context.become(next(zamekA, zamekB))
    }
  }
  def next(zamekA: ActorRef, zamekB: ActorRef): Receive = {
    case Cyk => zamekA ! Strzelać; zamekB ! Strzelać
  }
}

class Zamek extends Actor {
  def receive: Receive = {
    case SiłaWyższa.Init2(obroncy, zamek) => {
      obroncy.foreach(obronca => context.watch(obronca))
      context.become(walka(obroncy, zamek))
    }
  }
  def walka(obroncyMoi: List[ActorRef], zamekPrzeciwnika: ActorRef): Receive = {
    case SiłaWyższa.Strzelać => {
        println(s"Obroncy ${self.path.name}: ${obroncyMoi.length}")
        obroncyMoi.foreach(n => n ! SiłaWyższa.Atak(zamekPrzeciwnika))
    }
    case SiłaWyższa.Strzala => {
        val index = Random.between(0, obroncyMoi.length)
        obroncyMoi(index) ! SiłaWyższa.Cios(obroncyMoi.length)
    }
    case Terminated(zabity_obronca) => {
        if (obroncyMoi.length - 1 == 0) {
          println(s"Koniec walki, wygral ${zamekPrzeciwnika.path.name}")
          context.system.terminate()
        }
        else context.become(walka(obroncyMoi.filter(n => n != zabity_obronca), zamekPrzeciwnika))
    }
  }
}

class Obrońca extends Actor {
  def receive: Receive = {
    case SiłaWyższa.Atak(p) => {
      p ! SiłaWyższa.Strzala
    }

    case SiłaWyższa.Cios(aktywni_obroncy) => {
        val szanse = aktywni_obroncy.toFloat/200.toFloat
        val losowa = Random.between(0, 100)
        if ((szanse * 100).toInt >= losowa) self ! PoisonPill
    }
  }
}

@main
def lab13zad1: Unit = {
  val system = ActorSystem("Jabberwocky")
  import system.dispatcher

  val siłaWyższa = system.actorOf(Props[SiłaWyższa](), "SilaWyzsza")

  val pantaRhei = system.scheduler.scheduleWithFixedDelay(
    Duration.Zero,     // opóźnienie początkowe
    10.milliseconds, // odstęp pomiedzy kolejnymi komunikatami
    siłaWyższa,        // adresat „korespondencji”
    SiłaWyższa.Cyk     // komunikat
  )
}
