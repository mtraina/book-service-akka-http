package com.mtraina.bookservice

import org.scalatest.concurrent.ScalaFutures
import org.scalatest.time.{Millis, Span}
import org.scalatest.{BeforeAndAfter, FunSuite}
import slick.driver.H2Driver.api._
import slick.jdbc.meta.MTable
import slick.lifted.TableQuery

import scala.concurrent.Future

class TablesSuite extends FunSuite with BeforeAndAfter with ScalaFutures {
  implicit val defaultPatience = PatienceConfig(timeout = Span(300, Millis), interval = Span(30, Millis))

  val books = TableQuery[Books]

  var db: Database = _

  def createSchema() = db.run(books.schema.create).futureValue

  def insertBook(): Int =
    db.run(books += Book(1, "978-1853260629", "War and Peace", "Leo Tolstoy", 1024)).futureValue

  before { db = Database.forConfig("h2memTest") }

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

    val action = books.result
    val results: Future[Seq[Book]] = db.run(action)
    val allBooks = results.futureValue
    assert(allBooks.size === 1)
    assert(allBooks.head.isbn === "978-1853260629")
  }

  test("should select the book with id 1"){
    createSchema()
    insertBook()

    val book = db.run(books.filter(_.id === 1).map(_.title).result.head).futureValue
    assert(book === "War and Peace")
  }

  after { db.close }
}
