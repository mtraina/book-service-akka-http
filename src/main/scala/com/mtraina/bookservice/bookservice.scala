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

trait Service {
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
          parameterMap { params =>
            def paramString(param: (String, String)): String = s"""${param._1} = '${param._2}'"""
            complete(s"The parameters are ${params.map(paramString).mkString(", ")}")
          }
          //        complete {
          //          persistence.book()
          //        }
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

object Boot extends App with JsonSupport with Service {
  implicit val system = ActorSystem("book-service-system")
  implicit val materializer = ActorMaterializer()

  val persistence = new Persistence

  val bindingFuture = Http().bindAndHandle(route, "localhost", 8080)

}