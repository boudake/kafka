import java.time.Duration
import java.util.Properties
import java.util.concurrent.TimeUnit

import org.apache.kafka.streams.kstream.{Printed, TimeWindows}
import org.apache.kafka.streams.scala.{Serdes, StreamsBuilder}
import org.apache.kafka.streams.scala.kstream.{KStream, _}
import org.apache.kafka.streams.{KafkaStreams, KeyValue, StreamsConfig}
//import java.lang.Long
object joinExemple extends App {

  import org.apache.kafka.streams.scala.Serdes._
  import org.apache.kafka.streams.scala.ImplicitConversions._

  val config: Properties = {
    val p = new Properties()
    p.put(StreamsConfig.APPLICATION_ID_CONFIG, "wordcount-scala-application")
    p.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092")
    p
  }


  val builder = new StreamsBuilder()
  val userClicksStream: KStream[String, String] = builder.stream("streams-plaintext-input")

  val sortie : KStream[String, Long] = userClicksStream.mapValues(value => value.toLong)

val userRegionsTable: KTable[String, String] = builder.table[String, String]("streams-plaintext-input1")

 val clicksPerRegion: KStream[String, (String, Long)] = sortie

    .leftJoin(userRegionsTable) { (clicks: Long, region: String) =>
      (if (region == null) "UNKNOWN"
      else
        region, clicks)
    }

 val result : KTable[String, Long] = clicksPerRegion
   .map((key: String, regionWithClicks: (String, Long)) => Tuple2(regionWithClicks._1, regionWithClicks._2))
   .groupByKey
   .reduce((v:Long, r :Long) => v+r)

   // .count()
   // Compute the total per region by summing the individual click counts per region.



  val sysout = Printed
    .toSysOut[String, Long]
    .withLabel("customerStream")
  result.toStream.print(sysout)



  val streams: KafkaStreams = new KafkaStreams(builder.build(), config)
  /*
    // Change the stream from <user> -> <region, clicks> to <region> -> <clicks>
    .map((user: String, regionWithClicks: (String, JLong))
    => new KeyValue[String, JLong](regionWithClicks._1, regionWithClicks._2))
    // Compute the total per region by summing the individual click counts per region.
    .reduceByKey(
      (firstClicks: JLong, secondClicks: JLong) => firstClicks + secondClicks
    )
  */

  streams.cleanUp()

  streams.start()

  // Add shutdown hook to respond to SIGTERM and gracefully close Kafka Streams
  sys.ShutdownHookThread {
    streams.close(Duration.ofSeconds(5))
  }
}