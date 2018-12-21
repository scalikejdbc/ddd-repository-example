val scalikeJDBCVersion = "3.3.2"
val skinnyORMVersion = "3.0.1"

organization := "org.scalikejdbc"

name         := "ddd-repository-example"

scalaVersion := "2.12.8"

version      := "0.1.0-SNAPSHOT"

libraryDependencies ++= Seq(
  "org.scalikejdbc"        %% "scalikejdbc"        % scalikeJDBCVersion % "compile",
  "org.skinny-framework"   %% "skinny-orm"         % skinnyORMVersion   % "compile",
  "org.scalikejdbc"        %% "scalikejdbc-test"   % scalikeJDBCVersion % "test",
  "com.h2database"         %  "h2"                 % "1.4.197"    % "test",
  "ch.qos.logback"         %  "logback-classic"    % "1.2.3"      % "test",
  "org.specs2"             %% "specs2-core"        % "4.3.6"      % "test"
)

publishTo := {
  val nexus = "https://oss.sonatype.org/"
  if (isSnapshot.value) Some("snapshots" at nexus + "content/repositories/snapshots")
  else Some("releases" at nexus + "service/local/staging/deploy/maven2")
}

publishMavenStyle := true

scalacOptions ++= Seq("-unchecked")

publishMavenStyle := true

publishArtifact in Test := false

pomIncludeRepository := { x => false }

pomExtra := <url>https://github.com/scalikejdbc/ddd-repository-example</url>
  <licenses>
    <license>
      <name>Apache License, Version 2.0</name>
      <url>http://www.apache.org/licenses/LICENSE-2.0.html</url>
      <distribution>repo</distribution>
    </license>
  </licenses>
  <scm>
    <url>git@github.com:scalikejdbc/ddd-repository-example.git</url>
    <connection>scm:git:git@github.com:scalikejdbc/ddd-repository-example.git</connection>
  </scm>
  <developers>
    <developer>
      <id>j5ik2o</id>
      <name>Junichi Kato</name>
      <url>https://github.com/j5ik2o</url>
    </developer>
    <developer>
      <id>seratch</id>
      <name>Kazuhiro Sera</name>
      <url>https://github.com/seratch</url>
    </developer>
  </developers>

