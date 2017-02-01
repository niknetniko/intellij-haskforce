package com.haskforce.utils

import com.intellij.execution.configurations.GeneralCommandLine
import com.intellij.openapi.project.Project

/**
 * Created by cary.robbins on 1/8/17.
 */
object StackExecutor {

  def create(project: Project, workDir: Option[String]): Either[Error, StackExecutor] = ???

  sealed trait Error
}

class StackExecutor private (factory: () => GeneralCommandLine) {

  def rawCommandLine(): GeneralCommandLine = factory()

  def rawCommandLine(args: String*): GeneralCommandLine = {
    val cmd = factory()
    cmd.addParameters(args: _*)
    cmd
  }
}
