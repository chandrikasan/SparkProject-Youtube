import ApplicationConfig._
import org.apache.spark.sql.SaveMode
import org.apache.spark.sql.functions.col

object Application {
  def main(args: Array[String]): Unit = {

    val SRC_TYPE = SOURCE_CONFIG.getString("type")
    val APP_NAME= SOURCE_CONFIG.getString("appName")
    val sparkSession = new SparkFactory(APP_NAME, SRC_TYPE).getSparkSession();

    val tempdf = new DataReader(sparkSession, SOURCE_CONFIG).getData
    tempdf.show(10)
    val FILE_PATH = DESTINATION_CONFIG.getString("location")
    DESTINATION_CONFIG.getString("file_type").trim.toUpperCase() match {
      case "CSV" => tempdf.write.mode(SaveMode.Overwrite).csv(FILE_PATH)
    }

    val df = tempdf.withColumn("views", col("views").cast("Long"))
      .withColumn("likes", col("likes").cast("Long"))
      .withColumn("dislikes", col("dislikes").cast("Long"))
      .withColumn("comment_count", col("comment_count").cast("Long"))

    df.show(10)
    df.createOrReplaceTempView(TABLE_NAME);

    ANALYTICS_QUERIES.foreach(querySet => {
      val QUERY = querySet.getString("query");
      val STORED_LOCATION = querySet.getString("destination")
      sparkSession.sql(QUERY).show(10)
      sparkSession.sql(QUERY).coalesce(5).write.mode(SaveMode.Overwrite).csv(STORED_LOCATION)
    })


  }
}
