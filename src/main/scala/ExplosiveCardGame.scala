import CardDeckOperations._
import cats.effect.{ExitCode, IO, IOApp}
import cats.implicits._
import scalaz.{-\/, \/-}

object ExplosiveCardGame extends IOApp {

  private val blankCount = 16
  private val explosiveCount = 1
  private val defuseCount = 2
  private val startingHand = List(Defuse)

  override def run(args: List[String]): IO[ExitCode] = {
    val startDeck = CardDeckOperations.createDeck(blankCount, explosiveCount, defuseCount).shuffle
    runGame(startDeck, blankCount, startingHand)
  }

  //We keep track of how many blanks/explosives are left in the deck so that we know when to exit
  def runGame(deck: List[Card], blankCount: Int, playerHand: List[Card]): IO[ExitCode] = {
    if (blankCount == 0)
      IOUtils.showLine("No blank cards left, player has won the game!").as(ExitCode.Success)
    else
      for {
        //Uncomment below line to debug what the deck is
        //_ <- IOUtils.showLine(s"Deck is $deck")
        _ <- IOUtils.showLine("Enter d to draw or q to quit")
        input <- IOUtils.readIn.map(IOUtils.readInput)
        res <- handleInput(input, deck, blankCount, playerHand)
      } yield res
  }

  def handleInput(action: Action, deck: List[Card], blankCount: Int, playerHand: List[Card]): IO[ExitCode] =
    action match {
      case Quit => IOUtils.showLine("Quit was entered, stopping app").as(ExitCode.Success)
      case Retry => IOUtils.showLine("Bad input found, enter next action") >> runGame(deck, blankCount, playerHand)
      case Draw => CardDeckOperations.drawCard(deck) match {
        case -\/(error) =>
          IOUtils.showLine(error).as(ExitCode.Error)
        case \/-(drawnCard) => drawnCard match {
          case Blank => IOUtils.showLine("Blank was drawn, continue with the game") >>
            runGame(deck.tail, blankCount - 1, playerHand)
          case Defuse => IOUtils.showLine("Defuse was drawn, continue with the game") >>
            runGame(deck.tail, blankCount, playerHand :+ Defuse)
          case Explosive =>
            if (playerHand.isEmpty)
              IOUtils.showLine("Explosive card was drawn and no defuse cards to use, ending the game").as(ExitCode.Error)
            else
              IOUtils.showLine("Explosive card was drawn using a defuse card to continue with the game") >>
                runGame(deck.shuffle, blankCount, playerHand.tail)
        }
      }
    }
}
