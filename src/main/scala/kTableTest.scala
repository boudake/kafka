import java.time.Duration
import java.util.Properties


import org.apache.kafka.common.serialization.Serializer
import org.apache.kafka.streams.kstream.Printed
import org.apache.kafka.streams.scala.StreamsBuilder
import org.apache.kafka.streams.scala.kstream._
import org.apache.kafka.streams.{KafkaStreams, KeyValue, StreamsConfig}
object kTableTest extends App {

  import org.apache.kafka.streams.scala.Serdes._
  import org.apache.kafka.streams.scala.ImplicitConversions._

  val config: Properties = {
    val p = new Properties()
    p.put(StreamsConfig.APPLICATION_ID_CONFIG, "wordcount-scala-application")
    val bootstrapServers = if (args.length > 0) args(0) else "localhost:9092"
    p.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers)
    p
  }

  val builder = new StreamsBuilder()
  val peopleRoomTable: KTable[String, String] = builder.table[String, String]("streams-plaintext-input")
   val  roomCountTable : KTable[String, Long] =
    // <person, room>
    peopleRoomTable
      // group by <room, person> so we can count how many people per room next
      .groupBy((key, value) => {
        Tuple2(KeyValue.pair(value, key).key, KeyValue.pair(value, key).value) })
      // now count and produce <room, count>
      .count();



 // peopleRoomTable.toStream.to("streams-wordcount-output")

  val sysout = Printed
    .toSysOut[String, String]
    .withLabel("customerStream")
  peopleRoomTable.toStream.print(sysout)


  val streams: KafkaStreams = new KafkaStreams(builder.build(), config)
  streams.cleanUp()

  streams.start()

  sys.ShutdownHookThread {
    streams.close(Duration.ofSeconds(10))
  }


}
