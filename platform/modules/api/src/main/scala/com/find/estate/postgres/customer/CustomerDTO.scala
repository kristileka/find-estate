package com.find.estate.postgres.customer

import java.time.{ LocalDate, LocalDateTime }
import java.util.UUID

case class CustomerDTO(
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
