
import org.apache.log4j.{Level, Logger}
import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.functions.desc
import org.apache.spark.storage.StorageLevel.MEMORY_AND_DISK


/** Find the minimum temperature by weather station */
object UserWithMaxFollowers {


  case class Match(user: Int, Follower: Int)

  def mapper(line: String): Match = {
    val fields = line.split("\\s+")
    println(fields(0))
    Match(fields(0).toInt, fields(1).toInt)
  }

  def main(args: Array[String]) {

    //Set initial time
    val t1 = System.nanoTime

    /** Our main function where the action happens */

    // Set the log level to only print errors
    Logger.getLogger("org").setLevel(Level.ERROR)

    //Create Session
    val logFile = "README.md" // Should be some file on your system

    val spark = SparkSession.builder
      .appName("UserWithMaxFollowers")
      .master("local[*]")
      .getOrCreate()

    // Read in each line and construct the Match object
    val lines = spark.sparkContext.textFile("data/twitter_rv.net").map(mapper)

    // Convert to a DataSet

    import spark.implicits._

    val matchsDS = lines.toDS().persist(MEMORY_AND_DISK)

    // Some SQL-style magic to sort
    val topMatchs = matchsDS.groupBy("user").count().orderBy(desc("count")).persist(MEMORY_AND_DISK)

    println("Here is our inferred schema:")
    topMatchs.printSchema()

    println("Top five of influencers")
    topMatchs.show(5)

    spark.stop()


  }
}
