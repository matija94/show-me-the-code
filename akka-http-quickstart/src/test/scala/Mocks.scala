import scala.concurrent.Future

class TodoFailingRepository extends TodoRepository {
  override def all(): Future[Seq[Todo]] = Future.failed(new Exception("mocked"))

  override def done(): Future[Seq[Todo]] = Future.failed(new Exception("mocked"))

  override def pending(): Future[Seq[Todo]] = Future.failed(new Exception("mocked"))

  override def create(createTodo: CreateTodo): Future[Todo] = Future.failed(new Exception("mocked"))

  override def update(id: String, updateTodo: UpdateTodo): Future[Todo] = Future.failed(new Exception("mocked"))
}
