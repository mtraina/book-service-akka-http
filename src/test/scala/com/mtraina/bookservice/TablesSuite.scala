package com.mtraina.bookservice

import org.scalatest.concurrent.ScalaFutures
import org.scalatest.{BeforeAndAfter, FunSuite}
import slick.driver.H2Driver.api._
import slick.jdbc.meta.MTable
import slick.lifted.TableQuery

class TablesSuite extends FunSuite with BeforeAndAfter with ScalaFutures {

  val books = TableQuery[Books]

  var db: Database = _

  def createSchema() = db.run(books.schema.create).futureValue

  def insertBooks(): Int =
    db.run(books += (1, "978-1853260629", "War and Peace", "Leo Tolstoy", 1024)).futureValue

  before { db = Database.forConfig("h2mem1") }

  test("should create the schema"){
    createSchema()

    val tables = db.run(MTable.getTables).futureValue

    assert(tables.size == 1)
    assert(tables.count(_.name.name.equalsIgnoreCase("books")) == 1)
  }

  after { db.close }

}
