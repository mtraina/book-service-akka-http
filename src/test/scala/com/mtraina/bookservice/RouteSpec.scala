package com.mtraina.bookservice

import akka.http.scaladsl.testkit.ScalatestRouteTest
import org.scalatest.{WordSpec, Matchers}

class RouteSpec extends WordSpec with Matchers with ScalatestRouteTest with Service {

    "The book service" should {

      "return an hello message when call the root path" in {
        Get() ~> route ~> check {
          responseAs[String] shouldEqual "<h1>hello book service</h1>"
        }
      }

    }
}
