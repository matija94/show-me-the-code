import akka.http.scaladsl.model.StatusCodes
import akka.http.scaladsl.testkit.ScalatestRouteTest
import org.scalatest.{Matchers, WordSpec}

class TodoRouterListSpec extends WordSpec with Matchers with ScalatestRouteTest {


  import de.heikoseeberger.akkahttpcirce.FailFastCirceSupport._
  import io.circe.generic.auto._

  private val pendingTodo = Todo("1", "Learn akka-http", "I suck at akka-http at the moment", false)
  private val doneTodo = Todo("2", "Learn scala basics", "Basics are important", true)

  private val todos = Seq(pendingTodo, doneTodo)

  "TodoRouter" should {

    "return all todos" in {
      val repo = new InMemoryTodoRepository(todos)
      val router = new TodoRouter(repo)

      Get("/todos") ~> router.route ~> check {
        status shouldBe StatusCodes.OK
        val response = responseAs[Seq[Todo]]
        response shouldBe todos
      }
    }

    "return all done todos" in {
      val repo = new InMemoryTodoRepository(todos)
      val router = new TodoRouter(repo)

      Get("/todos/done") ~> router.route ~> check {
        status shouldBe StatusCodes.OK
        val response = responseAs[Seq[Todo]]
        response shouldBe Seq(doneTodo)
      }
    }

    "return all pending todos" in {
      val repo = new InMemoryTodoRepository(todos)
      val router = new TodoRouter(repo)

      Get("/todos/pending") ~> router.route ~> check {
        status shouldBe StatusCodes.OK
        val response = responseAs[Seq[Todo]]
        response shouldBe Seq(pendingTodo)
      }
    }

    "return all todos using failing repo" in {
      val repo = new TodoFailingRepository()
      val router = new TodoRouter(repo)

      Get("/todos") ~> router.route ~> check {
        status shouldBe ApiError.generic.statusCode
        val response = responseAs[String]
        response shouldBe ApiError.generic.message
      }
    }

    "return all done todos using failing repo" in {
      val repo = new TodoFailingRepository()
      val router = new TodoRouter(repo)

      Get("/todos/done") ~> router.route ~> check {
        status shouldBe ApiError.generic.statusCode
        val response = responseAs[String]
        response shouldBe ApiError.generic.message
      }
    }
  }

}
