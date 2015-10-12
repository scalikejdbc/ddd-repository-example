import sbt._
import Keys._

object ScalikeJDBCDDDRepositoryProject extends Build {

  val scalikeJDBCVersion = "2.2.8"
  val skinnyORMVersion = "1.3.20"

  lazy val ddd = Project (id = "ddd", base = file("."), 
    settings =  Seq(
      organization := "org.scalikejdbc",
      name         := "ddd-repository-example",
      scalaVersion := "2.11.7",
      version      := "0.1.0-SNAPSHOT",
      libraryDependencies <++= (scalaVersion) { scalaVersion => Seq(
        "org.scalikejdbc"        %% "scalikejdbc"        % scalikeJDBCVersion % "compile",
        "org.skinny-framework"   %% "skinny-orm"         % skinnyORMVersion   % "compile",
        "org.scalikejdbc"        %% "scalikejdbc-test"   % scalikeJDBCVersion % "test",
        "com.h2database"         %  "h2"                 % "1.4.189"    % "test",
        "ch.qos.logback"         %  "logback-classic"    % "1.1.3"      % "test",
        "org.specs2"             %% "specs2-core"        % "2.4.9"      % "test"
      )}
    ) ++ sonatypeSettings
  )

  lazy val sonatypeSettings = Seq(
    publishTo <<= version { (v: String) =>
      val nexus = "https://oss.sonatype.org/"
      if (v.trim.endsWith("SNAPSHOT")) Some("snapshots" at nexus + "content/repositories/snapshots")
      else Some("releases" at nexus + "service/local/staging/deploy/maven2")
    },
    publishMavenStyle := true,
    sbtPlugin := false,
    scalacOptions ++= Seq("-unchecked"),
    publishMavenStyle := true,
    publishArtifact in Test := false,
    pomIncludeRepository := { x => false },
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
  )

}

