//import com.find.estate.postgres.customer.{CustomerDAO, SlickCustomerDAO}

import com.find.estate.postgres.customer.{ CustomerDAO, SlickCustomerDAO }

import javax.inject.{ Inject, Provider, Singleton }
import com.google.inject.AbstractModule
import com.typesafe.config.Config
import play.api.inject.ApplicationLifecycle
import play.api.{ Configuration, Environment }
import slick.jdbc
import slick.jdbc.JdbcBackend
import slick.jdbc.JdbcBackend.Database

import scala.concurrent.Future

/**
  * This module handles the bindings for the API to the Slick implementation.
  *
  * https://www.playframework.com/documentation/latest/ScalaDependencyInjection#Programmatic-bindings
  */
class Module(environment: Environment, configuration: Configuration) extends AbstractModule {
  override def configure(): Unit = {
    bind(classOf[Database]).toProvider(classOf[DatabaseProvider])
    bind(classOf[CustomerDAO]).to(classOf[SlickCustomerDAO])
    bind(classOf[CustomerDAOCloseHook]).asEagerSingleton()
  }
}
@Singleton
class DatabaseProvider @Inject()(config: Config) extends Provider[Database] {
  override def get(): Database = Database.forConfig("postgres", config)
}

/** Closes database connections safely.  Important on dev restart. */
class CustomerDAOCloseHook @Inject()(dao: CustomerDAO, lifecycle: ApplicationLifecycle) {
  lifecycle.addStopHook { () =>
    Future.successful(dao.close())
  }
}
