import sbt._
import Keys._

object ScalikeJDBCDDDRepositoryProject extends Build {

  lazy val library = Project (id = "library", base = file("."), 
    settings =  Defaults.defaultSettings ++ Seq(
      organization := "org.scalikejdbc",
      name         := "scalikejdbc-ddd-repository",
      version      := "0.1.0-SNAPSHOT",
      libraryDependencies <++= (scalaVersion) { scalaVersion => Seq(
        "org.scalikejdbc"        %% "scalikejdbc"               % "[1.7,)"  % "compile",
        "org.scalikejdbc"        %% "scalikejdbc-interpolation" % "[1.7,)"  % "compile",
        "org.scalikejdbc"        %% "scalikejdbc-test"          % "[1.7,)"  % "test",
        "com.h2database"         %  "h2"                        % "1.3.174" % "test",
        "org.json4s"             %% "json4s-ext"                % "3.2.4"   % "test",
        "org.json4s"             %% "json4s-jackson"            % "3.2.4"   % "test",
        "com.github.nscala-time" %% "nscala-time"               % "0.6.0"   % "test",
        "org.specs2"             %% "specs2"                    % "2.0"     % "test"
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
    pomExtra := <url>https://github.com/scalikejdbc/scalikejdbc-ddd-repository</url>
      <licenses>
        <license>
          <name>Apache License, Version 2.0</name>
          <url>http://www.apache.org/licenses/LICENSE-2.0.html</url>
          <distribution>repo</distribution>
        </license>
      </licenses>
      <scm>
        <url>git@github.com:scalikejdbc/scalikejdbc-ddd-repository.git</url>
        <connection>scm:git:git@github.com:scalikejdbc/scalikejdbc-ddd-repository.git</connection>
      </scm>
      <developers>
        <developer>
          <id>j5ik2o</id>
          <name>Junichi Kato</name>
          <url>https://github.com/j5ik2o</url>
        </developer>
      </developers>
  )

}

