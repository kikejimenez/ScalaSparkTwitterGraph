name := "TwitterGraph"

organization := "enriqueJimenez"

scalaVersion := "2.12.10"

libraryDependencies ++= Seq(
    "org.apache.spark" %% "spark-sql"  %  "3.0.1" ,
     "org.apache.spark" %% "spark-core"  %  "3.0.1" ,
    )


    assemblyMergeStrategy in assembly := {
 case PathList("META-INF", xs @ _*) => MergeStrategy.discard
 //To add Kafka as source
 case "META-INF/services/org.apache.spark.sql.sources.DataSourceRegister" => 
 MergeStrategy.concat
 case x => MergeStrategy.first
 }
