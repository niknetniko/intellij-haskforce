package com.haskforce.eta.settings

import com.haskforce.jps.eta.model.{EtaBuildOptions, JpsEtaBuildOptionsConstants}
import com.intellij.openapi.components.PersistentStateComponent
import com.intellij.openapi.components.State
import com.intellij.openapi.components.Storage

/**
 * Created by cary.robbins on 12/8/16.
 */
@State(
  name = JpsEtaBuildOptionsConstants.ETA_BUILD_OPTIONS_COMPONENT,
  storages = Array(new Storage(JpsEtaBuildOptionsConstants.ETA_BUILD_OPTIONS_FILE))
)
class EtaBuildSettings extends PersistentStateComponent[EtaBuildOptions] {

  private var state = EtaBuildOptions()

  override def getState: EtaBuildOptions = state

  override def loadState(state: EtaBuildOptions): Unit = { this.state = state }

  def setEtaPath(x: String): Unit = { state.etaPath = x }

  def getEtaPath: String = state.etaPath

  def setEpmPath(x: String): Unit = { state.epmPath = x }

  def getEpmPath: String = state.epmPath
}
