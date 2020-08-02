import java.io.File

import com.typesafe.config.{Config, ConfigFactory}

import scala.collection.JavaConverters._
import scala.collection.mutable


object ApplicationConfig {
    private val CONFIG = ConfigFactory.parseFile(new File("youtubeApp.conf"))
    val SOURCE_CONFIG: Config = CONFIG.getConfig("data").getConfig("source")
    val DESTINATION_CONFIG: Config = CONFIG.getConfig("data").getConfig("destination")
    val ANALYTICS_CONFIG: Config = CONFIG.getConfig("analytics")
    val TABLE_NAME: String = ANALYTICS_CONFIG.getString("tablename")
    val ANALYTICS_QUERIES = ANALYTICS_CONFIG.getConfigList("analysis").asScala
}
