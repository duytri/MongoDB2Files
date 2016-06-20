import sbt._
import Keys._
import sbtassembly.AssemblyPlugin.autoImport._

name := "MongoDB2Files"
version := "0.2-SNAPSHOT"
organization := "uit.islab"
scalaVersion := "2.10.6"

unmanagedBase <<= baseDirectory { base => base / "libs" }
unmanagedResourceDirectories in Compile += baseDirectory.value / "src" / "main"
excludeFilter in unmanagedResourceDirectories := "*.scala"

scalacOptions += "-deprecation"
assemblyJarName in assembly := name.value+"-"+version.value+".jar"
mainClass in assembly := Some("main.scala.MongoDB2Files")
test in assembly := {}
