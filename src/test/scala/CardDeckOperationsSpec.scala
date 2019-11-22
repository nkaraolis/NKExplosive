import org.scalatest.FlatSpec

class CardDeckOperationsSpec extends FlatSpec {

  import CardDeckOperations._

  "createDeck" should "create a deck with correct number of blanks and explosives" in {
    val result = createDeck(1, 1, 0)
    assert(result === List(Blank, Explosive))
  }
  it should "create a deck with only explosive cards if blank count of 0 is used" in {
    val result = createDeck(0, 1, 0)
    assert(result === List(Explosive))
  }

  "shuffle" should "return list in a reverse if it has only 2 elements" in {
    val result = List(Blank, Explosive)
    assert(result.shuffle === List(Explosive, Blank))
  }

}
