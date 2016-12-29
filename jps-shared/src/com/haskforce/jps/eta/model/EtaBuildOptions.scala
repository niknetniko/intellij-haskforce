package com.haskforce.jps.eta.model

import com.intellij.util.xmlb.annotations.Tag

/** Serialization object for communicating build settings with the build server. */
final case class EtaBuildOptions(
  @Tag("buildType") var buildType: EtaBuildOptions.BuildType = EtaBuildOptions.BuildType.Disabled,
  @Tag("etaPath") var etaPath: String = "",
  @Tag("epmPath") var epmPath: String = ""
) {

  def disabled: Boolean = buildType == EtaBuildOptions.BuildType.Disabled

  override def toString: String = List(
    s"buildType=$buildType",
    s"etaPath=$etaPath",
    s"epmPath=$epmPath"
  ).mkString("EtaBuildOptions{", ",", "}")
}

object EtaBuildOptions {
  sealed trait BuildType
  object BuildType {
    case object Disabled extends BuildType
    case object EPM extends BuildType

    lazy val values = List(Disabled, EPM)

    def fromString(s: String): Either[String, BuildType] = {
      if (s.isEmpty) return Right(Disabled)
      mapping.get(s.toLowerCase).toRight(s"Invalid Eta build type: $s")
    }

    private lazy val mapping = values.map(x => x.toString -> x).toMap
  }
}
