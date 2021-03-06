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

import com.sonar.csharp.squid.parser.CSharpGrammar;
import com.sonar.sslr.api.AstNode;
import com.sonar.sslr.api.GenericTokenType;
import com.sonar.sslr.api.Grammar;
import org.sonar.squidbridge.checks.SquidCheck;
import org.sonar.api.utils.SonarException;
import org.sonar.check.BelongsToProfile;
import org.sonar.check.Priority;
import org.sonar.check.Rule;
import org.sonar.check.RuleProperty;

import java.util.regex.Pattern;

@Rule(
  key = "ClassName",
  priority = Priority.MAJOR)
@BelongsToProfile(title = CheckList.SONAR_WAY_PROFILE, priority = Priority.MAJOR)
public class ClassNameCheck extends SquidCheck<Grammar> {

  private static final String DEFAULT_FORMAT = "[A-HJ-Z][a-zA-Z0-9]++|I[a-z0-9][a-zA-Z0-9]*+";

  @RuleProperty(
    key = "format",
    defaultValue = "" + DEFAULT_FORMAT)
  public String format = DEFAULT_FORMAT;

  private Pattern pattern;

  @Override
  public void init() {
    subscribeTo(CSharpGrammar.CLASS_DECLARATION);

    try {
      pattern = Pattern.compile(format, Pattern.DOTALL);
    } catch (RuntimeException e) {
      throw new SonarException("[" + getClass().getSimpleName() + "] Unable to compile the regular expression: " + format, e);
    }
  }

  @Override
  public void visitNode(AstNode node) {
    AstNode identifier = node.getFirstChild(GenericTokenType.IDENTIFIER);

    if (!pattern.matcher(identifier.getTokenOriginalValue()).matches()) {
      getContext().createLineViolation(this, "Rename this class to match the regular expression: " + format, identifier);
    }
  }

}
