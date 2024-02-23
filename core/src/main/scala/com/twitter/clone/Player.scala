package com.twitter.clone

import slick.jdbc.PostgresProfile.api._

import java.time.LocalDate

case class Player(id: Long, name: String, country: String, dob: Option[LocalDate])

class PlayerTable(tag: Tag) extends Table[Player](tag, None, "Player") {
  override def *                  = (id, name, country, dob) <> (Player.tupled, Player.unapply)
  val id: Rep[Long]               = column[Long]("player_id", O.AutoInc, O.PrimaryKey)
  val name: Rep[String]           = column[String]("name")
  val country: Rep[String]        = column[String]("country")
  val dob: Rep[Option[LocalDate]] = column[Option[LocalDate]]("dob")
}
