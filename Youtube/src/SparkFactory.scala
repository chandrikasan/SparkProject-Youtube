import org.apache.spark.sql.SparkSession
class SparkFactory(appName: String, masterName: String) {

  def this() {
    this("test", "local")
  }

  def this(appName: String) {
    this(appName, "local");
  }


  def getSparkSession(): SparkSession = {
    SparkSession.builder().appName(appName).master(masterName).getOrCreate();
  }

}
