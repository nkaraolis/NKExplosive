import org.scalatest.FlatSpec

class IOUtilsSpec extends FlatSpec {

  import IOUtils._

  "readInput" should "return quit if q is entered" in {
    assert(readInput("q") === Quit)
  }
  it should "return draw if d is entered" in {
    assert(readInput("d") === Draw)
  }
  it should "return retry if anything but d or q are entered" in {
    assert(readInput("rq") === Retry)
  }

}
