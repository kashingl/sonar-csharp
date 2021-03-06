/*
 * Sonar C# Plugin :: C# Squid :: Checks
 * Copyright (C) 2010 Jose Chillan, Alexandre Victoor and SonarSource
 * dev@sonar.codehaus.org
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02
 */
package com.sonar.csharp.checks;

import com.sonar.csharp.squid.api.CSharpKeyword;
import com.sonar.csharp.squid.parser.CSharpGrammar;
import com.sonar.sslr.api.AstNode;
import com.sonar.sslr.api.Grammar;
import org.sonar.squidbridge.checks.SquidCheck;
import org.sonar.check.Priority;
import org.sonar.check.Rule;

@Rule(
  key = "S126",
  priority = Priority.MAJOR)
public class ElseIfWithoutElseCheck extends SquidCheck<Grammar> {

  @Override
  public void init() {
    subscribeTo(CSharpGrammar.IF_STATEMENT);
  }

  @Override
  public void visitNode(AstNode node) {
    if (isElseIf(node)) {
      AstNode elseClause = node.getFirstChild(CSharpKeyword.ELSE);
      if (elseClause == null) {
        getContext().createLineViolation(this, "Add the missing \"else\" clause.", node);
      }
    }
  }

  private boolean isElseIf(AstNode node) {
    return isElse(node.getParent().getParent().getPreviousAstNode());
  }

  private boolean isElse(AstNode node) {
    return node.is(CSharpKeyword.ELSE);
  }

}
