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


object todoModels {
    final case class Todo(id: Long, name: String, createdAt: String, updatedAt: String)
    implicit val todoFormat = jsonFormat4(Todo)

    final case class NewTodo(name: String)
    implicit val newTodoFormat = jsonFormat1(NewTodo)
}