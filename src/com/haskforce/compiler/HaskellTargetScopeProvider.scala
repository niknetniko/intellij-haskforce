package com.haskforce.compiler

import java.util

import com.intellij.compiler.impl.BuildTargetScopeProvider
import com.intellij.openapi.compiler.{CompileScope, CompilerFilter}
import com.intellij.openapi.module.ModuleType
import com.intellij.openapi.project.Project
import org.jetbrains.jps.api.CmdlineProtoUtil
import org.jetbrains.jps.api.CmdlineRemoteProto.Message.ControllerMessage.ParametersMessage.TargetTypeBuildScope

import com.haskforce.HaskellModuleType
import com.haskforce.jps.HaskellTargetType

/** */
class HaskellTargetScopeProvider extends BuildTargetScopeProvider {
  override def getBuildTargetScopes
      (baseScope: CompileScope,
       filter: CompilerFilter,
       project: Project,
       forceBuild: Boolean)
      : util.List[TargetTypeBuildScope] = {
    if (!hasHaskellModules(baseScope)) return util.Collections.emptyList()
    util.Collections.singletonList(CmdlineProtoUtil.createTargetsScope(
      HaskellTargetType.PRODUCTION.getTypeId,
      util.Collections.singletonList(project.getName),
      forceBuild
    ))
  }

  private def hasHaskellModules(baseScope: CompileScope): Boolean = {
    baseScope.getAffectedModules.exists(
      m => ModuleType.get(m).isInstanceOf[HaskellModuleType]
    )
  }
}
