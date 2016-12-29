package com.haskforce.eta.settings

import javax.swing.JComponent

import com.haskforce.eta.settings.ui.EtaCompilerConfigUI
import com.intellij.compiler.options.CompilerConfigurable
import com.intellij.openapi.project.Project

/**
 * Created by cary.robbins on 12/26/16.
 */
class EtaCompilerConfigurable(project: Project) extends CompilerConfigurable(project) {

  override def createComponent(): JComponent = ui.getComponent

  private lazy val ui = EtaCompilerConfigUI.create()
}


