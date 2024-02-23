package com.twitter.clone

import slick.jdbc.PostgresProfile.api._

object TestObject {

  lazy val players = TableQuery[PlayerTable]

  def add() = {
    val db          = Connection.db
    val insertQuery = players += Player(1, "Latte", "US", None)
    db.run(insertQuery)
  }

  def get() =
    players.map(_.name)
}
