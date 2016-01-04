package com.mtraina.bookservice

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport
import akka.http.scaladsl.marshallers.xml.ScalaXmlSupport._
import akka.http.scaladsl.server.Directives._
import akka.stream.ActorMaterializer
import spray.json.DefaultJsonProtocol

trait JsonSupport extends SprayJsonSupport with DefaultJsonProtocol {
  implicit val bookFormat = jsonFormat5(Book)
}

object Boot extends App with JsonSupport {
  implicit val system = ActorSystem("book-service-system")
  implicit val materializer = ActorMaterializer()

  val persistence = new Persistence

  val route =
    path(""){
      get {
        complete {
          <h1>hello book service</h1>
        }
      }
    } ~
    path("books"){
      get {
        complete {
          persistence.book()
        }
      }
    }

  val bindingFuture = Http().bindAndHandle(route, "localhost", 8080)

}