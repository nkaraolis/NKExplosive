import scalaz.Scalaz._
import scalaz.\/

import scala.util.Random

object CardDeckOperations {

  def createDeck(blankCount: Int): List[Card] = List.fill(blankCount)(Blank) :+ Explosive

  //Return either an error or the remaining cards if any
  def drawCard(deck: List[Card]): String \/ Card = {
    if (deck.isEmpty)
      "Should always have at least 1 card left in the deck".left
    else
      deck.head.right
  }

  //Assumption that there are at least 2 different card types passed in
  //otherwise it will always return same list since all values are the same
  implicit class DeckOperations(deck: List[Card]) {
    def shuffle: List[Card] =
    //shuffle doesn't re order lists of 2 values so do a manual reverse
      if (deck.size == 2)
        deck.reverse
      else
        Random.shuffle(deck)

  }

}
