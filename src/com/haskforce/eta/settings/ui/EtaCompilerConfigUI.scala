package com.haskforce.eta.settings.ui

import java.awt.{Color, GridBagLayout}
import javax.swing._

import com.haskforce.ui.GC
import com.intellij.openapi.ui.TextFieldWithBrowseButton

import scala.collection.mutable

/**
 * Interface for creating and interacting with the Eta compiler configuration UI.
 * See the implementation in [[EtaCompilerConfigUIImpl]].
 * The implementation takes care of building the appropriate UI elements and
 * exposing field data. This allows us to separate the UI logic from configuration logic.
 */
trait EtaCompilerConfigUI {
  def getComponent: JComponent
}

object EtaCompilerConfigUI {
  def create(): EtaCompilerConfigUI = new EtaCompilerConfigUIImpl
}

private class EtaCompilerConfigUIImpl extends EtaCompilerConfigUI {

  override lazy val getComponent = new JPanel(new GridBagLayout) {
    // Default grid constraints
    val gc = GC.pad(10, 5).northWest

    // Start at y position 0 and increment for each vertical element.
    var gridY = 0

    // Helper method for adding radio buttons to the panel in a consistent manner
    def addRadioButton(r: JRadioButton) = add(r, gc.width(2).weight(x = 1).grid(0, gridY))

    def addExeField(e: ExeField) = {
      add(e.label, gc.grid(0, gridY))
      add(e, gc.fillHorizontal.grid(1, gridY))
      gridY += 1
      add(e.errorsField, gc.fillHorizontal.grid(1, gridY))
    }

    addExeField(etaPath)

    gridY += 1
    addRadioButton(buildTypeRadioButtons.disabled)

    gridY += 1
    addRadioButton(buildTypeRadioButtons.epm)

    gridY += 1
    addExeField(epmPath)
    buildTypeRadioButtons.epm.associatedFields += epmPath
  }

  private lazy val etaPath = new ExeField("Eta Path:", "eta")
  private lazy val epmPath = new ExeField("EPM Path:", "epm")

  sealed class ExeField(labelText: String, exe: String) extends TextFieldWithBrowseButton {

    lazy val label = new JLabel(labelText)

    lazy val errorsField = new JLabel
    errorsField.setForeground(Color.red)
  }

  private object buildTypeRadioButtons {

    sealed abstract class BuildTypeRadio(label: String) extends JRadioButton(label) {

      // Assign radio button to our button group.
      group.add(this)

      // Track all fields marked as "associated" with our radio buttons.
      allAssociatedFields ++= associatedFields

      // Add an event to toggle associated fields on and any other fields off.
      addActionListener { e =>
        associatedFields.foreach { _.setEnabled(true) }
        getUnassociatedFields.foreach { _.setEnabled(false) }
      }

      /**
       * Field list which will be toggled *on* when this button is selected.
       * This is defined as a mutable Set to allow the UI code to add associated
       * fields as it adds them to the content pane. This avoids needing to define
       * the associated field list in multiple places.
       */
      val associatedFields = new mutable.HashSet[JComponent]

      /** Field list which will be toggled *off* when this button is selected. */
      final protected def getUnassociatedFields: Set[JComponent] = {
        allAssociatedFields.diff(associatedFields).toSet
      }
    }

    lazy val disabled = new BuildTypeRadio("Disable Eta compilers") {
      override protected def getAssociatedFields = Set.empty
    }

    lazy val epm = new BuildTypeRadio("Build with EPM") {
      override protected def getAssociatedFields = Set(
        ???
      )
    }

    // All build type radio buttons should be a part of this group
    private lazy val group = new ButtonGroup

    // Used to keep track of all fields associated with radio buttons.
    // This way we can easily toggle off the "unassociated" fields based on
    // which ones were associated.
    private lazy val allAssociatedFields = new mutable.HashSet[JComponent]
  }
}
