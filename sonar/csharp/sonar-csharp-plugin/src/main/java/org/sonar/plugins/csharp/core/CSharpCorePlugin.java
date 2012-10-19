/*
 * Sonar C# Plugin :: Core
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

package org.sonar.plugins.csharp.core;

import org.sonar.api.Extension;
import org.sonar.api.SonarPlugin;
import org.sonar.plugins.csharp.api.CSharp;

import java.util.ArrayList;
import java.util.List;

/**
 * C# Core plugin class.
 */
public class CSharpCorePlugin extends SonarPlugin {

  /**
   * {@inheritDoc}
   */
  public List<Class<? extends Extension>> getExtensions() {
    List<Class<? extends Extension>> extensions = new ArrayList<Class<? extends Extension>>();

    extensions.add(CSharp.class);
    extensions.add(CSharpProjectInitializer.class);

    // Sensors
    extensions.add(CSharpSourceImporter.class);

    // Common Rules
    extensions.add(CSharpCommonRulesEngineProvider.class);

    return extensions;
  }
}