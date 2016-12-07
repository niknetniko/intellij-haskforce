package com.haskforce.eta.project.template

import javax.swing.Icon

import com.haskforce.HaskellIcons
import com.intellij.ide.util.projectWizard.AbstractModuleBuilder
import com.intellij.openapi.ui.ValidationInfo
import com.intellij.platform.ProjectTemplate

/**
 * Created by crobbins on 12/6/16.
 */
class EtaProjectTemplate extends ProjectTemplate {

  override def getName: String = "Eta (JVM)"

  override def getIcon: Icon = HaskellIcons.ETA_FILE

  override def getDescription: String = "Java module with Eta support"

  override def validateSettings(): ValidationInfo = null

  override def createModuleBuilder(): AbstractModuleBuilder = new EtaModuleBuilder
}
