import cats.effect.ExitCode
import org.scalatest.FlatSpec

class ExplosiveCardGameSpec extends FlatSpec {

  import ExplosiveCardGame._

  "runGame" should "quit with success code when no blanks are left" in {
    val result = runGame(List(Explosive), 0)
    assert(result.unsafeRunSync() === ExitCode(0))
  }

  "handleInput" should "quit with success code and not run further when Quit action is called" in {
    val result = handleInput(Quit, List(Blank), 1)
    assert(result.unsafeRunSync() === ExitCode(0))
  }
  it should "draw and exit with error code when empty deck is used" in {
    val result = handleInput(Draw, Nil, 0)
    assert(result.unsafeRunSync() === ExitCode(1))
  }
  it should "draw and exit with error code when explosive card is drawn" in {
    val result = handleInput(Draw, List(Explosive, Blank), 1)
    assert(result.unsafeRunSync() === ExitCode(1))
  }
  it should "draw and then end the game if the last blank is drawn" in {
    val result = handleInput(Draw, List(Blank, Explosive), 1)
    assert(result.unsafeRunSync() === ExitCode(0))
  }

}
