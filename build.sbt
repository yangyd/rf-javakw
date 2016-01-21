
lazy val root = (project in file("."))
  .settings(
    name := "javakw-1",
    version := "1.0",
    scalaVersion := "2.11.7"
  )
  .settings(
    libraryDependencies += "org.apache.httpcomponents" % "httpasyncclient" % "4.1.1")
