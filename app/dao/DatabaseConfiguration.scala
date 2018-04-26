/*
package dao

import com.typesafe.config.ConfigFactory
import slick.driver.MySQLDriver.api._

object DatabaseConfiguration {

  val url=ConfigFactory.load().getString("slick.dbs.default.db.url")
  val user=ConfigFactory.load().getString("slick.dbs.default.db.user")
  val password=ConfigFactory.load().getString("slick.dbs.default.db.password")
  val driver=ConfigFactory.load().getString("slick.dbs.default.db.driver")

  lazy val db={
    val tmpDb=Database.forURL(url,user,password,null,driver,executor = AsyncExecutor("ingestion",numThreads=10,queueSize=5000))
    val session=tmpDb.createSession()
    try session.force() finally session.close()
    tmpDb
  }


}
*/
