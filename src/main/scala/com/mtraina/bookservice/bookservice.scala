package com.mtraina.bookservice

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.marshallers.xml.ScalaXmlSupport._
import akka.stream.ActorMaterializer

object Boot extends App {
  implicit val system = ActorSystem("book-service-system")
  implicit val materializer = ActorMaterializer()

  val route = path(""){
    get {
      complete {
        <h1>hello book service</h1>
      }
    }
  }

  val bindingFuture = Http().bindAndHandle(route, "localhost", 8080)

}