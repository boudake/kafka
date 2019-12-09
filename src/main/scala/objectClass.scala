import java.time.Duration
import java.util.Properties
import java.util.concurrent.TimeUnit

import org.apache.kafka.streams.kstream.{Printed, TimeWindows}
import org.apache.kafka.streams.scala.{Serdes, StreamsBuilder}
import org.apache.kafka.streams.scala.kstream.{KStream, _}
import org.apache.kafka.streams.{KafkaStreams, KeyValue, StreamsConfig}
object objectClass extends App {

  import org.apache.kafka.streams.scala.Serdes._
  import org.apache.kafka.streams.scala.ImplicitConversions._

  val config: Properties = {
    val p = new Properties()
    p.put(StreamsConfig.APPLICATION_ID_CONFIG, "wordcount-scala-application")
    p.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092")
    p
  }

  case class Person(name: String, number: Long)

  val builder = new StreamsBuilder()
  val myStream: KStream[String, String] = builder.stream("streams-plaintext-input")

  val result = myStream.map((key: String, value: String) => Tuple2(key, Person(key, value.toLong)))

  val sysout = Printed
    .toSysOut[String, Person]
    .withLabel("Person")
  result.print(sysout)

  val streams: KafkaStreams = new KafkaStreams(builder.build(), config)


  streams.cleanUp()
  streams.start()

  // Add shutdown hook to respond to SIGTERM and gracefully close Kafka Streams
  sys.ShutdownHookThread {
    streams.close(Duration.ofSeconds(5))
  }
}