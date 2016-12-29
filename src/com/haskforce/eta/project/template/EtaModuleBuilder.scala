package com.haskforce.eta.project.template

import java.io.File
import javax.swing.JComponent

import com.haskforce.utils.{ExecUtil, GuiUtil}
import com.intellij.ide.util.projectWizard.{JavaModuleBuilder, ModuleWizardStep, SettingsStep}
import com.intellij.openapi.module.JavaModuleType
import com.intellij.openapi.ui.{Messages, TextFieldWithBrowseButton}

/** Manages the creation of Eta modules via interaction with the user. */
class EtaModuleBuilder extends JavaModuleBuilder {

  override def modifySettingsStep(settingsStep: SettingsStep): ModuleWizardStep = {
    new EtaStep(settingsStep)
  }

  private class EtaStep(settingsStep: SettingsStep) extends ModuleWizardStep {

    private val javaStep = JavaModuleType.getModuleType.modifyProjectTypeStep(
      settingsStep, EtaModuleBuilder.this
    )
    settingsStep.addSettingsField("Eta path:", etaPathField)
    settingsStep.addSettingsField("EPM path:", epmPathField)

    override def updateDataModel(): Unit = {
      javaStep.updateDataModel()
    }

    override lazy val getComponent: JComponent = new JComponent {}

    override def disposeUIResources(): Unit = {
      super.disposeUIResources()
      javaStep.disposeUIResources()
    }

    override def validate(): Boolean = {
      var result = super.validate() && javaStep.validate()
      val errors = new scala.collection.mutable.ListBuffer[String]
      if (!new File(etaPathField.getText).canExecute) {
        errors += "Invalid Eta path: " + etaPathField.getText
        result = false
      }
      if (!new File(epmPathField.getText).canExecute) {
        errors += "Invalid EPM path: " + epmPathField.getText
        result = false
      }
      if (errors.nonEmpty) Messages.showErrorDialog(errors.mkString("\n"), "Errors")
      result
    }

    private lazy val etaPathField = newPathField("eta")
    private lazy val epmPathField = newPathField("epm")

    private def newPathField(name: String): TextFieldWithBrowseButton = {
      val field = new TextFieldWithBrowseButton()
      GuiUtil.addFolderListener(field, name)
      Option(ExecUtil.locateExecutableByGuessing(name)).foreach { field.setText }
      field
    }
  }
}
