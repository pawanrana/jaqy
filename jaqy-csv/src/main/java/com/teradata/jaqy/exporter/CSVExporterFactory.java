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
package com.teradata.jaqy.exporter;

import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.charset.Charset;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.Option;
import org.apache.commons.csv.CSVFormat;

import com.teradata.jaqy.JaqyInterpreter;
import com.teradata.jaqy.interfaces.JaqyExporter;
import com.teradata.jaqy.utils.CSVUtils;
import com.teradata.jaqy.utils.JaqyHandlerFactoryImpl;

/**
 * @author	Heng Yuan
 */
public class CSVExporterFactory extends JaqyHandlerFactoryImpl<JaqyExporter>
{
	public static Charset DEFAULT_CHARSET = Charset.forName ("utf-8");

	public CSVExporterFactory ()
	{
		addOption ("c", "charset", true, "sets the file character set");
		addOption ("d", "delimiter", true, "specifies the delimiter");
		Option option = new Option ("t", "type", true, "sets the csv type.");
		option.setArgName ("default | excel | rfc4180 | mysql | tdf");
		addOption (option);
	}

	@Override
	public String getName ()
	{
		return "csv";
	}

	@Override
	public JaqyExporter getHandler (CommandLine cmdLine, JaqyInterpreter interpreter) throws Exception
	{
		Charset charset = DEFAULT_CHARSET;
		CSVFormat format = CSVFormat.DEFAULT;

		for (Option option : cmdLine.getOptions ())
		{
			switch (option.getOpt ().charAt (0))
			{
				case 'c':
				{
					charset = Charset.forName (option.getValue ());
					break;
				}
				case 'd':
				{
					char delimiter = CSVUtils.getChar (option.getValue ());
					if (delimiter == 0)
						throw new IllegalArgumentException ("invalid delimiter: " + option.getValue ());
					format = format.withDelimiter (delimiter);
					break;
				}
				case 't':
				{
					format = CSVUtils.getFormat (option.getValue ());
					break;
				}
			}
		}
		String[] args = cmdLine.getArgs ();
		if (args.length == 0)
			throw new IllegalArgumentException ("missing file name.");
		Writer fw = new OutputStreamWriter (new FileOutputStream (interpreter.getFile (args[0])), charset);
		return new CSVExporter (format, fw);
	}
}
