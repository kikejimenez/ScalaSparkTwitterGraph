
import org.apache.log4j.{Level, Logger}
import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.functions.desc
import org.apache.spark.storage.StorageLevel.MEMORY_AND_DISK

/** Find the user with max followers */
object UserWithMaxFollowers {

  case class Match(user: Int, Follower :Int)

  def mapper(line:String): Match = {
    val fields = line.split("\\s+")
     Match(fields(0).toInt, fields(1).toInt)
  }

  def main(args: Array[String]) {

    val DATA_FILE: String = args(0)

    Logger.getLogger("org").setLevel(Level.INFO)

    val spark = SparkSession.builder
      .appName("UserWithMaxFollowers")
      .getOrCreate()

    // Read in each line and construct the Match object
    val lines = spark.sparkContext.textFile(DATA_FILE).map(mapper)


    import spark.implicits._
    val matchsDS = lines.toDS().persist(MEMORY_AND_DISK)

    val topMatchs = matchsDS.groupBy("user").count().orderBy(desc("count")).persist(MEMORY_AND_DISK)

    println("Here is our inferred schema:")
    topMatchs.printSchema()

    println("Top five of influencers")
    topMatchs.show(5)

    spark.stop()



  }
}
