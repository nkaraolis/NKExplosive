import cats.effect.ExitCode
import org.scalatest.FlatSpec

class ExplosiveCardGameSpec extends FlatSpec {

  import ExplosiveCardGame._

  "runGame" should "quit with success code when no blanks are left" in {
    val result = runGame(List(Explosive), 0, Nil)
    assert(result.unsafeRunSync() === ExitCode(0))
  }

  "handleInput" should "quit with success code and not run further when Quit action is called" in {
    val result = handleInput(Quit, List(Blank), 1, Nil)
    assert(result.unsafeRunSync() === ExitCode(0))
  }
  it should "draw and exit with error code when empty deck is used" in {
    val result = handleInput(Draw, Nil, 0, Nil)
    assert(result.unsafeRunSync() === ExitCode(1))
  }
  it should "draw and exit with error code when explosive card is drawn and player hand is empty" in {
    val result = handleInput(Draw, List(Explosive, Blank), 1, Nil)
    assert(result.unsafeRunSync() === ExitCode(1))
  }
  it should "draw and then end the game if the last blank is drawn" in {
    val result = handleInput(Draw, List(Blank, Explosive), 1, Nil)
    assert(result.unsafeRunSync() === ExitCode(0))
  }
  //iteration 2 tests start here
  it should "draw explosive and continue with game" in {
    //Have made blank count 0 here to make the game quit and not ask for input
    val result = handleInput(Draw, List(Explosive, Blank), 0, List(Defuse))
    assert(result.unsafeRunSync() === ExitCode(0))
  }

}
