package com.find.estate.postgres.company

import java.time.{ LocalDate, LocalDateTime }
import java.util.UUID

case class Company(
    id: UUID,
    email: String,
    firstName: String,
    lastName: String,
    birth_date: LocalDate,
    customerType: String,
    createdAt: LocalDateTime,
    updatedAt: Option[LocalDateTime],
    deletedAt: Option[LocalDateTime]
)