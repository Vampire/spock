/*
 * Copyright 2026 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.spockframework.compiler;

import org.codehaus.groovy.ast.AnnotatedNode;
import org.codehaus.groovy.ast.AnnotationNode;
import org.codehaus.groovy.ast.ClassCodeVisitorSupport;
import org.codehaus.groovy.control.SourceUnit;
import org.codehaus.groovy.control.messages.SimpleMessage;

import static java.lang.Boolean.TRUE;

public class StcAnnotationsUsageVisitor extends ClassCodeVisitorSupport {
  private final AstNodeCache nodeCache;
  private final SourceUnit sourceUnit;

  public StcAnnotationsUsageVisitor(AstNodeCache nodeCache, SourceUnit sourceUnit) {
    this.nodeCache = nodeCache;
    this.sourceUnit = sourceUnit;
  }

  @Override
  protected SourceUnit getSourceUnit() {
    return sourceUnit;
  }

  @Override
  public void visitAnnotations(AnnotatedNode node) {
    for (AnnotationNode an : node.getAnnotations()) {
      if (TRUE.equals(an.getNodeMetaData(MarkAnnotationsAlreadyPresentInConversionPhase.class))
        && (nodeCache.SpockCompileDynamic.equals(an.getClassNode())
        || nodeCache.SpockCompileStatic.equals(an.getClassNode())
        || nodeCache.SpockTypeChecked.equals(an.getClassNode()))) {

        sourceUnit.getErrorCollector().addErrorAndContinue(new SimpleMessage(
          "SpockCompileDynamic, SpockCompileStatic, and SpockTypeChecked annotations are not allowed outside Spock specifications, use CompileDynamic, CompileStatic, or TypeChecked instead",
          sourceUnit));
      }
    }

    super.visitAnnotations(node);
  }
}
