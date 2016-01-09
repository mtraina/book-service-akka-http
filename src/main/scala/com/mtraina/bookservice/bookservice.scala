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

trait Service extends JsonSupport {
  val persistence = new Persistence

  val route =
    path(""){
      get {
        complete {
          <h1>hello book service</h1>
        }
      }
    } ~
    path("query_params"){
      parameters('id, 'isbn) { (id, isbn) =>
        complete(s"id: $id and isbn: $isbn")
      }
    } ~
    pathPrefix("path_params"){
      path(IntNumber) { id =>
        complete(s"id: $id")
      }
    } ~
    path("books"){
      get {
        complete {
          persistence.book()
        }
      }
    } ~
    pathPrefix("balls") {
      pathEnd {
        complete("all balls!")
      } ~
        path(IntNumber) { int =>
          complete(if (int % 2 == 0) "even ball" else "odd ball")
        }
    }
}

object Boot extends App with Service {
  implicit val system = ActorSystem("book-service-system")
  implicit val materializer = ActorMaterializer()

  val bindingFuture = Http().bindAndHandle(route, "localhost", 8080)

}