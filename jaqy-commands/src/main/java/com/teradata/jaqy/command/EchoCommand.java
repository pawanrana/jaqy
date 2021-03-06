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

import java.sql.SQLException;

import com.teradata.jaqy.CommandArgumentType;
import com.teradata.jaqy.Echo;
import com.teradata.jaqy.Globals;
import com.teradata.jaqy.JaqyInterpreter;
import com.teradata.jaqy.interfaces.Display;

/**
 * @author	Heng Yuan
 */
public class EchoCommand extends JaqyCommandAdapter
{
	@Override
	public String getDescription ()
	{
		return "turns echo on / off";
	}

	@Override
	public String getLongDescription ()
	{
		return "usage: " + getCommand () + " [on | off | auto]";
	}

	@Override
	public CommandArgumentType getArgumentType ()
	{
		return CommandArgumentType.file;
	}

	@Override
	public void execute (String[] args, boolean silent, Globals globals, JaqyInterpreter interpreter) throws SQLException
	{
		Display display = interpreter.getDisplay ();
		if (args.length == 0)
		{
			interpreter.println (getCommand () + " " + display.getEcho ());
		}
		else if ("auto".equals (args[0]))
		{
			display.setEcho (Echo.auto);
		}
		else if ("on".equals (args[0]))
		{
			display.setEcho (Echo.on);
		}
		else if ("off".equals (args[0]))
		{
			display.setEcho (Echo.off);
		}
		else
		{
			interpreter.error ("invalid command arguments");
		}
	}
}
