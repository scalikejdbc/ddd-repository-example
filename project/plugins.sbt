addSbtPlugin("org.scalariform" % "sbt-scalariform" % "1.8.2")

addSbtPlugin("net.virtual-void" % "sbt-dependency-graph" % "0.9.0")

addSbtPlugin("com.timushev.sbt" % "sbt-updates" % "0.4.0")

scalacOptions ++= Seq("-unchecked", "-deprecation", "-feature")

