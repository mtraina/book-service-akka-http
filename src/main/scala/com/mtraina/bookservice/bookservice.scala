package com.mtraina.bookservice

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.marshallers.xml.ScalaXmlSupport._
import akka.stream.ActorMaterializer
import spray.json.DefaultJsonProtocol

final case class Item(id: Long, name: String)

trait JsonSupport extends SprayJsonSupport with DefaultJsonProtocol {
  implicit val itemFormat = jsonFormat2(Item)
}

object Boot extends App with JsonSupport {
  implicit val system = ActorSystem("book-service-system")
  implicit val materializer = ActorMaterializer()

  val route =
    path(""){
      get {
        complete {
          <h1>hello book service</h1>
        }
      }
    } ~
    path("items"){
      get {
        complete {
          Item(1, "Table")
        }
      }
    }

  val bindingFuture = Http().bindAndHandle(route, "localhost", 8080)

}