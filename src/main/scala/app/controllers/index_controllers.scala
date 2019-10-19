package app.controllers

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

object indexControllers {
  implicit val system = ActorSystem()
  implicit val materializer = ActorMaterializer()
  // needed for the future map/flatmap in the end and future in fetchItem and saveOrder
  implicit val executionContext = system.dispatcher
  var orders: List[Item] = Nil

  // domain model
  final case class Item(name: String, id: Long)
  final case class Order(items: List[Item])
  final case class Params(str: String, int: Long, float: Double)

  // formats for unmarshalling and marshalling
  implicit val itemFormat = jsonFormat2(Item)
  implicit val orderFormat = jsonFormat1(Order)
  implicit val paramsFormat = jsonFormat3(Params)

  // (fake) async database query api
  def fetchItem(itemId: Long): Future[Option[Item]] = Future {
    orders.find(o => o.id == itemId)
  }

  def saveOrder(order: Order): Future[Done] = {
    orders = order match {
      case Order(items) => items ::: orders
      case _            => orders
    }
    Future { Done }
  }

  def paramsPath(int: Long, float: Double, str: String): Params = {
    new Params(str, int, float)
  }

  def paramsQuery(int: String, float: String, str: String): Params = {
    try {
      var i = int.toInt
      var f = float.toDouble
      new Params(str, i, f)
    } catch {
      case _: NumberFormatException => new Params("error happened", 0, 0)
    }
  }

  def paramsBody(params: Params): Params = {
    params
  }
}
