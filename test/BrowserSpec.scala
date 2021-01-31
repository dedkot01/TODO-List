import org.junit.runner._
import org.specs2.mutable._
import org.specs2.runner._
import play.api.test._

/**
 * An integration test will fire up a whole play application in a real (or headless) browser
 */
@RunWith(classOf[JUnitRunner])
class BrowserSpec extends Specification {

  "Application" should {

    "have right title in index" in new WithBrowser {

      browser.goTo("http://localhost:" + port)

      browser.pageSource must contain("TO-DO List")
    }

    "have right title in addTask" in new WithBrowser {

      browser.goTo("http://localhost:" + port + "/addTask")

      browser.pageSource must contain("Добавить задачу")
    }

  }

}
