# Twitter Graph Dataset

The dataset consists of the user-follower pairs (separated with the tab key).

## Excercises

### 1. Find the user with the Maximun number of followers

#### Solution

The following table contains the top five users with greatest number of followers

```SQL
+--------+-------+
|    user|  count|
+--------+-------+
|19058681|2997469|
|15846407|2679639|
|16409683|2674874|
|  428333|2450749|
|19397785|1994926|
+--------+-------+
```

The code for the solution is in 'src/main/scala/UserWithMaxFollowers.scala'

## Run the code

### *Create the .JAR files.*

 Activate the sbt (Scala Build Tool) in a docker container with the following command:

 ```shell
 docker run -it --rm -v $PWD:/wd -w /wd mozilla/sbt sbt shell
 ```

and run the `package` command in the sbt-shell.

### *Spark-submit*

The following code calls `UserWithMaxFollowers` in the *.jar* file, stores the result in *out/result* and the log-info in *out/info*. Also, the time total execution time is stored in  *out/time*

```shell
/usr/bin/time -o out/time -f '\t%E ' \
docker run -v $PWD:/wd -w /wd openjdk:8 \
spark-3.0.0-bin-hadoop2.7/bin/spark-submit \
--class "UserWithMaxFollowers" \
--master "local[*]" \
target/scala-2.12/twittergraph_2.12-0.1.0-SNAPSHOT.jar \
data/twitter_rv_sample.net \
2> out/info 1> out/result &
```

The job is run with the `spark-submit` command in the directory `spark-3.0.0-bin-hadoop2.7` of the spark application. This
directory is not in the repo and must be downloaded from the Spark's site.
The jar file expects the data file as an argument.

## Author

 Enrique Jimenez
