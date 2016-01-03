package com.mtraina.bookservice

import org.scalatest.concurrent.ScalaFutures
import org.scalatest.time.{Millis, Seconds, Span}
import org.scalatest.{BeforeAndAfter, FunSuite}
import slick.driver.H2Driver.api._
import slick.jdbc.meta.MTable
import slick.lifted.TableQuery

import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global

class TablesSuite extends FunSuite with BeforeAndAfter with ScalaFutures {
  implicit val defaultPatience = PatienceConfig(timeout = Span(5, Seconds), interval = Span(500, Millis))

  val books = TableQuery[Books]

  var db: Database = _

  def createSchema() = db.run(books.schema.create).futureValue

  def insertBook(): Int =
    db.run(books += Book(1, "978-1853260629", "War and Peace", "Leo Tolstoy", 1024)).futureValue

  before { db = Database.forConfig("h2mem1") }

  test("should create the schema"){
    createSchema()

    val tables = db.run(MTable.getTables).futureValue

    assert(tables.size == 1)
    assert(tables.count(_.name.name.equalsIgnoreCase("books")) == 1)
  }

  test("should insert a book"){
    createSchema()

    val insertCount = insertBook()
    assert(insertCount == 1)
  }

  test("should select all the books"){
    createSchema()
    insertBook()

    val results = db.run(books.result).futureValue
    assert(results.size == 1)
    assert(results.head.id == 1)
  }

  after { db.close }

}
