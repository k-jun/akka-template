import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.stream.ActorMaterializer
import akka.Done
import akka.http.scaladsl.server.Route
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.model.StatusCodes
// for JSON serialization/deserialization following dependency is required:
// "com.typesafe.akka" %% "akka-http-spray-json" % "10.1.7"
import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport._
import spray.json.DefaultJsonProtocol._

import scala.io.StdIn
import scala.concurrent.Future

import app.controllers.indexControllers._

object WebServer {

  // needed to run the route
  implicit val system = ActorSystem()
  implicit val materializer = ActorMaterializer()
  // needed for the future map/flatmap in the end and future in fetchItem and saveOrder
  implicit val executionContext = system.dispatcher

  def main(args: Array[String]) {

    val route: Route =
      concat(
        pathPrefix("params") {
          concat(
            pathPrefix("path" / LongNumber / DoubleNumber / "[0-9a-zA-Z-#() ]+".r) {
              (int, dou, str) =>
                get {
                  val response: Params = paramsPath(int, dou, str)
                  complete(response)
                }
            },
            path("query") {
              get {
                parameters("int", "float", "str") {(int, dou, str) =>
                  val response: Params = paramsQuery(int, dou, str)
                  complete(response)
                }
              }
            },
            path("body") {
              post {
                entity(as[Params]) { body =>
                  val response: Params = paramsBody(body)
                  complete(response)
                }
              }
            }
          )
        }
      )

    val bindingFuture = Http().bindAndHandle(route, "localhost", 8080)
    println(s"Server online at http://localhost:8080/\nPress RETURN to stop...")
    StdIn.readLine() // let it run until user presses return
    bindingFuture
      .flatMap(_.unbind()) // trigger unbinding from the port
      .onComplete(_ => system.terminate()) // and shutdown when done

  }
}
