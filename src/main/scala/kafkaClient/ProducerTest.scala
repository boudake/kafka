package kafkaClient

import java.util.Properties
import org.apache.kafka.clients.producer.Producer
import org.apache.kafka.clients.producer.KafkaProducer
import org.apache.kafka.clients.producer.ProducerRecord
//import ProducerRecord packages
//import util.properties packages

object ProducerTest extends App {

  //Assign topicName to string variable
  val topicName = "producer";

  val props = new Properties()
  props.put("bootstrap.servers", "localhost:9092")
  props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer")
  props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer")

  import org.apache.kafka.clients.producer.KafkaProducer

  val producer = new KafkaProducer[String, String](props)

  for( i <- 1 to 10)
    producer.send(new ProducerRecord[String, String](topicName,Integer.toString(i), Integer.toString(i)));
    println("Message sent successfully")

  producer.close();

}
