name := "KafkaStream"

version := "0.1"

scalaVersion := "2.11.9"

libraryDependencies += "javax.ws.rs" % "javax.ws.rs-api" % "2.1.1" artifacts(Artifact("javax.ws.rs-api", "jar", "jar"))
libraryDependencies += "org.apache.kafka" %% "kafka" % "2.1.0"
libraryDependencies += "org.apache.kafka" % "kafka-clients" % "2.1.0"
libraryDependencies += "org.apache.kafka" % "kafka-streams" % "2.1.0"
libraryDependencies += "org.apache.kafka" %% "kafka-streams-scala" % "2.1.0"
libraryDependencies += "com.fasterxml.jackson.module" %% "jackson-module-scala" % "2.9.8"
libraryDependencies += "com.typesafe" % "config" % "1.3.4"
libraryDependencies += "com.typesafe.akka"   %% "akka-http-spray-json" % "10.0.9"
libraryDependencies += "com.typesafe.akka"   %% "akka-http" % "10.0.9"
libraryDependencies += "org.rocksdb"         % "rocksdbjni" % "5.6.1"
// https://mvnrepository.com/artifact/com.typesafe.play/play-json
libraryDependencies += "com.typesafe.play" %% "play-json" % "2.7.1"

//libraryDependencies +=  "org.apache.commons"  % "commons-lang3" % "3.6"
//libraryDependencies += "ch.qos.logback" % "logback-classic" % "1.1.3"
// https://mvnrepository.com/artifact/log4j/log4j
libraryDependencies += "log4j" % "log4j" % "1.2.17"
libraryDependencies += "org.scala-lang.modules" %% "scala-xml" % "1.2.0"


assemblyMergeStrategy in assembly := {
  case PathList("META-INF", xs@_*) => MergeStrategy.discard
  case PathList("stuff", xs@_*) => MergeStrategy.discard
  case x => MergeStrategy.first
}