/*
 * Copyright (c) 2017 Teradata
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.teradata.jaqy.command;

import com.teradata.jaqy.*;
import com.teradata.jaqy.utils.PathUtils;
import com.teradata.jaqy.utils.PropertyTableUtils;

/**
 * @author	Heng Yuan
 */
public class ClassPathCommand extends JaqyCommandAdapter
{
	public ClassPathCommand ()
	{
		super ("classpath.txt");
	}

	@Override
	public String getDescription ()
	{
		return "displays / sets the class path for a JDBC driver.";
	}

	@Override
	public CommandArgumentType getArgumentType ()
	{
		return CommandArgumentType.file;
	}

	@Override
	public void execute (String[] args, boolean silent, Globals globals, JaqyInterpreter interpreter) throws Exception
	{
		JaqyDriverManager driverManager = globals.getDriverManager ();
		if (args.length == 0)
		{
			// display the class path loaded.
			PropertyTable pt = PropertyTableUtils.createPropertyTable (driverManager.getDriverLocationMap (), new String[] { "Protocol", "Location" });
			interpreter.print (pt);
			return;
		}
		if (args.length != 2)
		{
			interpreter.error ("invalid command arguments.");
		}
		String protocol = args[0];
		String path = args[1];
		path = PathUtils.toAbsolutePath (path, interpreter.getDirectory ());
		driverManager.addDriverLocation (protocol, path);
	}
}
