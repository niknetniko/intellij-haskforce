package com.haskforce.jps.eta

import com.haskforce.jps.eta.model.{EtaBuildOptions, JpsEtaBuildOptionsExtension}
import com.intellij.execution.configurations.GeneralCommandLine
import org.jetbrains.jps.ModuleChunk
import org.jetbrains.jps.builders.DirtyFilesHolder
import org.jetbrains.jps.builders.java.JavaSourceRootDescriptor
import org.jetbrains.jps.incremental.ModuleLevelBuilder.{ExitCode, OutputConsumer}
import org.jetbrains.jps.incremental._
import org.jetbrains.jps.model.java.JpsJavaSdkType
import org.jetbrains.jps.model.serialization.JpsModelSerializationDataService

import scala.collection.JavaConverters._

/**
 * Created by cary.robbins on 12/25/16.
 */
class EpmBuilder extends ModuleLevelBuilder(BuilderCategory.TRANSLATOR) {

  override def getPresentableName: String = "Eta EPM builder"

  override def build(
    context: CompileContext,
    chunk: ModuleChunk,
    dirtyFilesHolder: DirtyFilesHolder[JavaSourceRootDescriptor, ModuleBuildTarget],
    outputConsumer: OutputConsumer
  ): ExitCode = {
    val project = context.getProjectDescriptor.getProject
    val opts = JpsEtaBuildOptionsExtension.getOrCreate(project).getOptions
    if (opts.disabled) return ExitCode.NOTHING_DONE
    if (opts.epmPath.isEmpty) throw new ProjectBuildException("Path to EPM is not specified in build settings")
    if (opts.etaPath.isEmpty) throw new ProjectBuildException("Path to Eta is not specified in build settings")
    filterEPMModules(chunk).foldLeft(
      Right(ExitCode.OK): Either[ExitCode, ExitCode]
    ) { (acc, x) => x match { case (sdk, module, dir) =>
      val cmd = createCommandLine(opts)
      val res = runBuild(cmd)
      acc.right.flatMap(_ => res)
    }}.fold(identity, identity)
  }

  private def filterEPMModules(chunk: ModuleChunk) = {
    for {
      module <- chunk.getModules.iterator().asScala
      // Ensure we have a Java SDK
      sdk <- Option(module.getSdk(JpsJavaSdkType.INSTANCE)).toIterator
      dir = JpsModelSerializationDataService.getBaseDirectory(module)
      if dir.isDirectory && dir.list().exists(_.endsWith(".cabal"))
    } yield (sdk, module, dir)
  }

  private def createCommandLine(opts: EtaBuildOptions): GeneralCommandLine = {
    ???
  }

  private def runBuild(cmd: GeneralCommandLine): Either[ExitCode, ExitCode] = {
    ???
  }
}
