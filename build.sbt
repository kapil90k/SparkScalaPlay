name := """play-scala-starter-example"""

version := "0.0.1"

//disable the PlayLogback, cannot start the server with amp-source-adapter dependencies
lazy val root = (project in file(".")).enablePlugins(PlayScala)
//  .
//  disablePlugins(PlayLogback)

scalaVersion := "2.11.7"
scalaBinaryVersion := "2.11"

//logLevel := Level.Debug

libraryDependencies ++= Seq(

//    "org.kie" % "kie-ci" % "7.5.0.Final",
//    "org.drools" % "drools-core" % "7.5.0.Final",
//    "org.drools" % "drools-compiler" % "7.5.0.Final",
//    "org.drools" % "drools-decisiontables" % "7.5.0.Final",

    "org.apache.hbase" % "hbase-client" % "1.2.0-cdh5.11.0",
    "org.apache.hbase" % "hbase-common" % "1.2.0-cdh5.11.0",
    "org.apache.hbase" % "hbase-hadoop2-compat" % "1.2.0-cdh5.11.0",
    //"org.apache.hadoop", "hadoop-common", "2.6.0-cdh5.11.0",
    "org.apache.hadoop" % "hadoop-common" % "2.4.0" exclude("javax.servlet.jsp", "jsp-api") exclude("com.sun.jersey", "jersey-server"),


  "com.typesafe.play" %% "play-json" % "2.5.8" exclude("com.fasterxml.jackson.core", "jackson-databind"),
  "com.fasterxml.jackson.core" % "jackson-databind" % "2.8.2",
  "com.fasterxml.jackson.module" % "jackson-module-scala_2.11" % "2.8.2",
  "org.json4s" % "json4s-jackson_2.11" % "3.5.0",
  "io.swagger" %% "swagger-play2" % "1.5.3",

  "org.scala-lang" % "scala-library" % "2.11.8",
  "org.scala-lang" % "scala-reflect" % "2.11.8",
  "org.scala-lang" % "scala-compiler" % "2.11.8",
  "org.scalatra.scalate" % "scalate-core_2.11" % "1.7.1",

  "org.scalikejdbc" %% "scalikejdbc"                  % "3.2.2",
  "org.scalikejdbc" %% "scalikejdbc-config"           % "3.2.2",
  "org.scalikejdbc"      %% "scalikejdbc-play-initializer"  % "2.5.1",

  "mysql" % "mysql-connector-java" % "5.1.18",
  "com.univocity" % "univocity-parsers" % "2.6.3",

//  jdbc,
  cache,
  evolutions,
  filters,
  ws,
  specs2 % Test
)

libraryDependencies += "com.typesafe" % "config" % "1.2.0"
libraryDependencies += "org.json" % "json" % "20171018"


excludeDependencies += "org.slf4j" % "slf4j-simple"
excludeDependencies += "org.slf4j" % "slf4j-log4j12"

// force use of log4j for logging
libraryDependencies ++= Seq(
  "org.slf4j" % "slf4j-api"       % "1.7.7",
  "org.slf4j" % "jcl-over-slf4j"  % "1.7.7"
).map(_.force())

libraryDependencies ~= { _.map(_.exclude("org.slf4j", "slf4j-jdk14")) }

resolvers += Resolver.mavenLocal
resolvers += "Local Maven Repository" at "file:///"+Path.userHome+"/.m2/repository"
resolvers += "scalaz-bintray" at "http://dl.bintray.com/scalaz/releases"
resolvers += "typesafe" at "http://repo.typesafe.com/typesafe/releases/"
resolvers += "cloudera" at "https://repository.cloudera.com/artifactory/cloudera-repos"
resolvers += "Sonatype OSS Snapshots" at "https://oss.sonatype.org/content/repositories/snapshots"

libraryDependencies += guice