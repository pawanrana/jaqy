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

import com.teradata.jaqy.Globals;
import com.teradata.jaqy.JaqyInterpreter;
import com.teradata.jaqy.utils.SessionUtils;

/**
 * @author	Heng Yuan
 */
public class AutoCommitCommand extends OnOffCommand
{
	@Override
	public String getDescription ()
	{
		return "turns auto-commit on / off";
	}

	@Override
	void execute (Globals globals, JaqyInterpreter interpreter, boolean on) throws SQLException
	{
		SessionUtils.checkOpen (interpreter);
		interpreter.getSession ().getConnection ().setAutoCommit (on);
	}

	@Override
	void info (Globals globals, JaqyInterpreter interpreter) throws SQLException
	{
		SessionUtils.checkOpen (interpreter);
		boolean auto = interpreter.getSession ().getConnection ().getAutoCommit ();
		interpreter.println (getCommand () + " " + (auto ? "on" : "off"));
	}
}
