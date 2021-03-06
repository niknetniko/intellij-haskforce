package com.haskforce.cabal.resolve

import com.intellij.openapi.util.io.FileUtil

import com.haskforce.resolve.HaskellResolveTestCase

class CabalResolveTest
  extends HaskellResolveTestCase(FileUtil.join("cabal", "resolve")) {

  def testModule00001() = doTest()
  def testModule00002() = doTestResolvedDir("Yesod", "Routes")

  protected def doTestResolvedDir(part1: String, parts: String*): Unit = {
    val vDir = myFixture.getTempDirFixture.findOrCreateDir(FileUtil.join(part1 +: parts: _*))
    val psiDir = getPsiManager.findDirectory(vDir)
    if (psiDir == null) throw new AssertionError("Could not find psi directory: " + vDir)
    resolvedElement = psiDir
    doTest()
  }
}
