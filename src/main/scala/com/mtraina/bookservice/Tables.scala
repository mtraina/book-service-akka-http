package com.mtraina.bookservice

import slick.driver.H2Driver.api._
import slick.lifted.{Rep, Tag}

// A Books table with columns: isbn, title, author, pages
case class Book(id: Int, isbn: String, title: String, author: String, pages: Int)

class Books(tag: Tag) extends Table[Book](tag, "books"){
  def id: Rep[Int] = column[Int]("id", O.PrimaryKey)
  def isbn: Rep[String] = column[String]("isbn")
  def title: Rep[String] = column[String]("title")
  def author: Rep[String] = column[String]("author")
  def pages: Rep[Int] = column[Int]("pages")

  // Every table needs a * projection with the same type as the table's type parameter
  def * = (id, isbn, title, author, pages) <> (Book.tupled, Book.unapply)
}
