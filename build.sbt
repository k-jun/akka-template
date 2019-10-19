name := "akka-test"

version := "1.0"

scalaVersion := "2.12.10"

libraryDependencies ++= Seq(
    "com.typesafe.akka" %% "akka-http"   % "10.1.10",
    "com.typesafe.akka" %% "akka-stream" % "2.5.23",
    "com.typesafe.akka" %% "akka-http-spray-json" % "10.1.10",
    "io.spray" %%  "spray-json" % "1.3.4",

    "com.typesafe.slick" %% "slick" % "3.3.1",
    "org.slf4j" % "slf4j-nop" % "1.7.26",
    "com.typesafe.slick" %% "slick-hikaricp" % "3.3.1",
    "mysql" % "mysql-connector-java" % "6.0.6"
)