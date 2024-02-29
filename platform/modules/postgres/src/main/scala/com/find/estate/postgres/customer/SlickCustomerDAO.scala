package com.find.estate.postgres.customer

import com.find.estate.postgres.Tables
import slick.jdbc.JdbcBackend.Database
import slick.jdbc.JdbcProfile

import java.util.UUID
import javax.inject.{ Inject, Singleton }
import scala.concurrent.{ ExecutionContext, Future }

/**
  * A User DAO implemented with Slick, leveraging Slick code gen.
  *
  * Note that you must run "flyway/flywayMigrate" before "compile" here.
  *
  * @param db the slick database that this user DAO is using internally, bound through Module.
  * @param ec a CPU bound execution context.  Slick manages blocking JDBC calls with its
  *    own internal thread pool, so Play's default execution context is fine here.
  */
@Singleton
class SlickCustomerDAO @Inject()(db: Database)(implicit ec: ExecutionContext) extends CustomerDAO with Tables {

  override val profile: JdbcProfile = _root_.slick.jdbc.PostgresProfile

  import profile.api._

  def lookup(id: UUID): Future[Option[CustomerDTO]] = ???

  def all: Future[Seq[CustomerDTO]] = Future { Seq() }

  def update(customer: CustomerDTO): Future[Int] = ???

  def delete(id: UUID): Future[Int] = ???

  def create(user: CustomerDTO): Future[Int] = ???

  def close(): Future[Unit] = ???

  private def userToUsersRow(user: Customer): CustomerRow = ???

  private def usersRowToUser(usersRow: CustomerRow): Customer = ???
}
