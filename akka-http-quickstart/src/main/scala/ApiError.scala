import akka.http.scaladsl.model.StatusCode
import akka.http.scaladsl.model.StatusCodes

final case class ApiError private(statusCode: StatusCode, message: String)

object ApiError {
  private def apply(errorCode: Int, message: String): ApiError = new ApiError(errorCode, message)

  val generic: ApiError = ApiError(StatusCodes.InternalServerError, "Unknown error.")

  val emptyTitleField: ApiError = new ApiError(StatusCodes.BadRequest, "The title field must not be empty")

  def todoNotFound(id: String): ApiError =
    new ApiError(StatusCodes.NotFound, s"Todo with id $id not found.")
}