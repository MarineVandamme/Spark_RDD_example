name := "BDD_Reparties_2"

version := "0.1"

scalaVersion := "2.11.8"

logLevel:= Level.Error

libraryDependencies += "org.jsoup" % "jsoup" % "1.6.1"

libraryDependencies ++= Seq(
  "org.apache.spark" %% "spark-core" % "2.3.2",
  "org.apache.spark" %% "spark-sql" % "2.3.2",
  "org.apache.spark" %% "spark-graphx" % "2.3.2"
)