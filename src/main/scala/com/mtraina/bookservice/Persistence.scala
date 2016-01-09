package com.mtraina.bookservice

import slick.driver.H2Driver.api._
import slick.lifted.TableQuery

import scala.concurrent.Await
import scala.concurrent.duration._

class Persistence {

  val books = TableQuery[Books]

  val db = Database.forConfig("h2memApp")

  Await.result(db.run(books.schema.create), 2 seconds)
  Await.result(db.run(books += Book(1, "978-1853260629", "War and Peace", "Leo Tolstoy", 1024)), 2 seconds)

  def book() = db.run(books.filter(_.id === 1).result.head)

}
