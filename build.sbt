name         := "book-service-akka-http"
organization := "com.mtraina"
version      := "0.0.3"
scalaVersion := "2.11.7"

scalacOptions := Seq("-unchecked", "-deprecation", "-encoding", "utf8")

libraryDependencies ++= {
  val akkaV       = "2.4.1"
  val akkaStreamV = "2.0.1"
  val scalaTestV  = "2.2.5"
  Seq(
    "com.typesafe.akka"   %% "akka-actor"                         % akkaV,
    "com.typesafe.akka"   %% "akka-http-experimental"             % akkaStreamV,
    "com.typesafe.akka"   %% "akka-stream-experimental"           % akkaStreamV,
    "com.typesafe.akka"   %% "akka-http-xml-experimental"         % akkaStreamV,
    "com.typesafe.akka"   %% "akka-http-spray-json-experimental"  % akkaStreamV,
    "com.typesafe.slick"  %% "slick"                              % "3.1.1",
    "org.slf4j"           %  "slf4j-nop"                          % "1.7.13",
    "com.h2database"      %  "h2"                                 % "1.4.190",
    "org.scalatest"       %% "scalatest"                          % scalaTestV  % "test",
    "com.typesafe.akka"   %% "akka-http-testkit-experimental"     % "2.0-M2" % "test"
  )
}