import cats.effect.IO

object IOUtils {

  def readIn: IO[String] = IO(scala.io.StdIn.readLine)

  def showLine(msg: String): IO[Unit] = IO(println(msg))

  //Return either an outcome to finish the app or an action to keep going
  def readInput(input: String): Action = {
    input.toUpperCase match {
      case "Q" => Quit
      case "D" => Draw
      case _ => Retry
    }
  }
}

sealed trait Action

case object Quit extends Action

case object Draw extends Action

case object Retry extends Action
