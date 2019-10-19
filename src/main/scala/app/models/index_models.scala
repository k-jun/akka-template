package app.models

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


object indexModels {
    final case class Params(str: String, int: Long, float: Double)
    implicit val paramsFormat = jsonFormat3(Params)

    final case class Item(name: String, id: Long)    
    implicit val itemFormat = jsonFormat2(Item)

    final case class Order(items: List[Item])
    implicit val orderFormat = jsonFormat1(Order)
}