import ApplicationConfig._
import com.typesafe.config.Config
import org.apache.spark.sql.{DataFrame, SparkSession}
class DataReader(sparkSession: SparkSession, sourceConfig: Config) {

  val SOURCE_TYPE: String = SOURCE_CONFIG.getString("file_type")
  val SOURCE_LOCATION: String = SOURCE_CONFIG.getString("location")
  val SCHEMA: String = SOURCE_CONFIG.getString("schema")

  val data: DataFrame =
    SOURCE_TYPE.trim.toUpperCase match {
      case "CSV" =>
        sparkSession.read.option("header", "true").csv(SOURCE_LOCATION).toDF(SCHEMA.split(",").toSeq : _ *)
      case "JSON" =>
        sparkSession.read.json(SOURCE_LOCATION).toDF(SCHEMA.split(",").toSeq : _ *)
    }

  def getData: DataFrame = data


}
