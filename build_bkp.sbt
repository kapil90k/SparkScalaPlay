name := """play-scala-starter-example"""

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayScala)
scalaVersion := "2.11.11"

/*libraryDependencies += cache
libraryDependencies += javaWs

routesGenerator := InjectedRoutesGenerator*/

resolvers += Resolver.sbtPluginRepo("releases")

libraryDependencies += "org.json" % "json" % "20090211"
libraryDependencies += "com.typesafe.play" %% "play-slick" % "1.1.1"
libraryDependencies += "com.typesafe.play" %% "play-slick-evolutions" % "1.1.1"
libraryDependencies += "com.fasterxml.jackson.module" %% "jackson-module-scala" % "2.8.7"
dependencyOverrides += "com.fasterxml.jackson.core" % "jackson-databind" % "2.8.7"
dependencyOverrides += "com.fasterxml.jackson.core" % "jackson-core" % "2.8.7"
dependencyOverrides += "com.fasterxml.jackson.core" % "jackson-annotations" % "2.8.7"
