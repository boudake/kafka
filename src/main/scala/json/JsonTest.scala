package json

import java.time.Duration
import java.util.Properties

import org.apache.kafka.common.serialization.Serdes
import org.apache.kafka.streams.kstream.Printed
import org.apache.kafka.streams.scala._
import org.apache.kafka.streams.scala.kstream._
import org.apache.kafka.streams.{KafkaStreams, StreamsConfig}

//import java.lang.Long
object JsonTest extends App {

  val config: Properties = {
    val p = new Properties()
    p.put(StreamsConfig.APPLICATION_ID_CONFIG, "wordcount-scala-application")
    p.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092")
    p
  }

 // case class User(username: String, friends: String, enemies: String, isAlive: String)
 case class Customer(CliendID: String, username: String, CategoryID: String)
  val jsonString = """ {"CliendID":"123","username":"Djim","CategoryID":"retail"} """

  implicit val stringSerde = Serdes.String
  implicit val cutomerSerde = new JsonSerde[Customer]
  implicit val consumed = kstream.Consumed.`with`(stringSerde, cutomerSerde)
 // implicit val grouped = Grouped.`with`(stringSerde, userSerde)

  val builder: StreamsBuilder = new StreamsBuilder
  val customersStreams: KStream[String, Customer] =  builder.stream[String, Customer]("json-topic")

  val retailCustomerStreams: KStream[String, Customer] = customersStreams.filter((k: String, v : Customer) => {
    v.CategoryID.equals("retail")
  })



  val sysout = Printed
    .toSysOut[String, Customer]
    .withLabel("customerStream")
  retailCustomerStreams.print(sysout)



  val streams: KafkaStreams = new KafkaStreams(builder.build(), config)

  //streams.cleanUp()

  streams.start()

  // Add shutdown hook to respond to SIGTERM and gracefully close Kafka Streams
  sys.ShutdownHookThread {
    streams.close(Duration.ofSeconds(5))
  }

  def getIDCostumer(clientId: String, Url : String ) : Option[String] = {
    if( true)
      return Some("")
    None
  }


}