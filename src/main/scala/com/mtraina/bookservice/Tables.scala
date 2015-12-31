package com.mtraina.bookservice

import slick.driver.H2Driver.api._
import slick.lifted.{Rep, Tag, ProvenShape}

// A Books table with columns: isbn, title, author, pages
class Books(tag: Tag) extends Table[(Int, String, String, String, Int)](tag, "BOOKS"){

  def id: Rep[Int] = column[Int]("id", O.PrimaryKey)
  def isbn: Rep[String] = column[String]("isbn")
  def title: Rep[String] = column[String]("title")
  def author: Rep[String] = column[String]("author")
  def pages: Rep[Int] = column[Int]("pages")

  // Every table needs a * projection with the same type as the table's type parameter
  def * : ProvenShape[(Int, String, String, String, Int)] = (id, isbn, title, author, pages)
}
