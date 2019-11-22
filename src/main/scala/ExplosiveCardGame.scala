import CardDeckOperations._
import cats.effect.{ExitCode, IO, IOApp}
import cats.implicits._
import scalaz.{-\/, \/-}

object ExplosiveCardGame extends IOApp {

  val blankCount = 16

  override def run(args: List[String]): IO[ExitCode] = {
    val startDeck = CardDeckOperations.createDeck(blankCount).shuffle
    runGame(startDeck, blankCount)
  }

  def runGame(deck: List[Card], blankCount: Int): IO[ExitCode] = {
    if (blankCount == 0)
      IOUtils.showLine("No blank cards left, player has won the game!").as(ExitCode.Success)
    else
      for {
        _ <- IOUtils.showLine("Enter d to draw or q to quit")
        input <- IOUtils.readIn.map(IOUtils.readInput)
        res <- handleInput(input, deck, blankCount)
      } yield res
  }

  def handleInput(action: Action, deck: List[Card], blankCount: Int): IO[ExitCode] =
    action match {
      case Quit => IOUtils.showLine("Quit was entered, stopping app").as(ExitCode.Success)
      case Retry => IOUtils.showLine("Bad input found, enter next action") >> runGame(deck, blankCount)
      case Draw => CardDeckOperations.drawCard(deck) match {
        case -\/(error) =>
          IOUtils.showLine(error).as(ExitCode.Error)
        case \/-(drawnCard) =>
          if (drawnCard == Blank)
            IOUtils.showLine("Blank was drawn, continue with the game") >> runGame(deck.tail, blankCount - 1)
          else
            IOUtils.showLine("Explosive card was drawn, ending the game").as(ExitCode.Error)
      }
    }
}

sealed trait Card

case object Explosive extends Card

case object Blank extends Card
