package com.find.estate.postgres.customer

import java.util.UUID
import scala.concurrent.Future

/** An implementation dependent DAO.  This could be implemented by Slick, Cassandra, or a REST API.
  */
trait CustomerDAO {

  def lookup(id: UUID): Future[Option[CustomerDTO]]

  def all: Future[Seq[CustomerDTO]]

  def update(user: CustomerDTO): Future[Int]

  def delete(id: UUID): Future[Int]

  def create(user: CustomerDTO): Future[Int]

  def close(): Future[Unit]
}
