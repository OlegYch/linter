scalaVersion := "2.10.1"

libraryDependencies <++= (scalaVersion) { (scalaVersion) =>
  Seq(
    "org.scala-lang"           % "scala-compiler"  % scalaVersion,
    if (scalaVersion startsWith "2.9")
      "org.specs2"  % "specs2_2.9.2"     % "1.12.3"  % "test" else
      "org.specs2"  % "specs2_2.10"     % "1.13"  % "test",
    "junit"                    % "junit"           % "4.8.2"  % "test",
    "com.novocode"             % "junit-interface" % "0.7"    % "test"
  )
}

scalacOptions in console in Compile <+= (packageBin in Compile) map { pluginJar =>
  "-Xplugin:"+pluginJar
}

name := "linter"

organization := "com.foursquare.lint"

version := "0.1.0"

crossScalaVersions <<= scalaVersion { scalaVersion => Seq("2.9.2", "2.9.3", "2.10.1", "2.11.0-M3") }

publishTo := Some(Resolver.file("file",  new File( "../linteRepo/releases" )) )

//Well, if we're gonna do static analysis, why not see what the compiler already does ;)

scalacOptions ++= Seq("-unchecked", "-Xlint")

scalacOptions ++= Seq(/*"-Ywarn-all",*/ "-Ywarn-dead-code", "-Ywarn-inaccessible", "-Ywarn-nullary-override", "-Ywarn-nullary-unit", "-Ywarn-value-discard")

//Also, a self-test

//scalacOptions += "-Xplugin:../linteRepo/releases/com/foursquare/lint/linter_2.10/0.1-SNAPSHOT/linter_2.10-0.1-SNAPSHOT.jar"

//Also, what others are doing

org.scalastyle.sbt.ScalastylePlugin.Settings

//import de.johoop.findbugs4sbt.FindBugs._

seq(findbugsSettings : _*)
