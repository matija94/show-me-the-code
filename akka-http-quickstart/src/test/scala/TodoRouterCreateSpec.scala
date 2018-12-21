import akka.http.scaladsl.model.StatusCodes
import akka.http.scaladsl.testkit.ScalatestRouteTest
import org.scalatest.{Matchers, WordSpec}

class TodoRouterCreateSpec extends WordSpec with Matchers with ScalatestRouteTest {

  import de.heikoseeberger.akkahttpcirce.FailFastCirceSupport._
  import io.circe.generic.auto._

  private val createTodo = CreateTodo(description = "Test Todo", title = "Todo")


  "TodoRouter" should {

    "create todo with valid data" in {
      val repo = new InMemoryTodoRepository()
      val router = new TodoRouter(repo)

      Post("/todos", createTodo) ~> router.route ~> check {
        status shouldBe StatusCodes.OK
        val response = responseAs[Todo]
        response.title shouldBe createTodo.title
        response.description shouldBe createTodo.description
      }
    }

    "return emptyTitleField on invalid data" in {
      val repo = new TodoFailingRepository()
      val router = new TodoRouter(repo)

      Post("/todos", createTodo.copy(title = "")) ~> router.route ~> check {
        status shouldBe ApiError.emptyTitleField.statusCode
        val response = responseAs[String]
        response shouldBe ApiError.emptyTitleField.message
      }
    }

    "return genericError when using failing repo with valid data" in {
      val repo = new TodoFailingRepository()
      val router = new TodoRouter(repo)

      Post("/todos", createTodo) ~> router.route ~> check {
        status shouldBe ApiError.generic.statusCode
        val response = responseAs[String]
        response shouldBe ApiError.generic.message
      }
    }
  }

}
