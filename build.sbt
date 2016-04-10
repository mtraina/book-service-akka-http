name         := "book-service-akka-http"
organization := "com.mtraina"
version      := "0.1.0"
scalaVersion := "2.11.7"

scalacOptions := Seq("-unchecked", "-deprecation", "-encoding", "utf8")

libraryDependencies ++= {
  val akkaV       = "2.4.3"
  val scalaTestV  = "2.2.6"
  Seq(
    "com.typesafe.akka"   %% "akka-http-core"                     % akkaV,
    "com.typesafe.akka"   %% "akka-http-xml-experimental"         % akkaV,
    "com.typesafe.akka"   %% "akka-http-spray-json-experimental"  % akkaV,
    "com.typesafe.slick"  %% "slick"                              % "3.1.1",
    "org.slf4j"           %  "slf4j-nop"                          % "1.7.13",
    "com.h2database"      %  "h2"                                 % "1.4.190",
    "org.scalatest"       %% "scalatest"                          % scalaTestV  % "test",
    "com.typesafe.akka"   %% "akka-http-testkit"                  % akkaV % "test"
  )
}