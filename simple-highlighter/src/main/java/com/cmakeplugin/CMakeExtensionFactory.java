package com.cmakeplugin;

import com.cmakeplugin.annotator.CMakeCLionAnnotator;
import com.cmakeplugin.annotator.CMakeIdeaAnnotator;
import com.cmakeplugin.utils.CMakeProxyToJB;
import com.intellij.openapi.extensions.ExtensionFactory;

public class CMakeExtensionFactory implements ExtensionFactory {
  @Override
  public Object createInstance(String factoryArgument, String implementationClass) {
    //    Object result = null;
    //    if (factoryArgument.equals("Annotator")) {
    //      result = CMakePDC.isCLION
    //              ? new CMakeCLionAnnotator()
    //              : new CMakeIdeaAnnotator();
    //    }
    //    else if (!CMakePDC.isCLION) {
    //      switch (factoryArgument) {
    //        case "parserDefinition" : result = new CMakeParserDefinition(); break;
    //        case "syntaxHighlighterFactory" : result = new CMakeSyntaxHighlighterFactory(); break;
    //        case "braceMatcher" : result = new CMakeBraceMatcher(); break;
    //        case "refactoringSupport" : result = new CMakeRefactoringSupportProvider(); break;
    //        case "findUsagesProvider" : result = new CMakeFindUsagesProvider(); break;
    //      }
    //    }
    //    return result;
    switch (factoryArgument) {
      case "Annotator":
        return CMakeProxyToJB.isCLION ? new CMakeCLionAnnotator() : new CMakeIdeaAnnotator();
      default:
        throw new java.lang.RuntimeException(
            "Unknown factoryArgument for CMakeExtensionFactory: " + factoryArgument);
    }
  }
}
