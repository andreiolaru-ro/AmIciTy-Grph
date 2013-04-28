/*******************************************************************************
 * Copyright (C)  Andrei Olaru and contributors (see AUTHORS).
 * 
 * This file is part of Visualization of Context Graphs.
 * 
 * Visualization of Context Graphs is free software: you can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software Foundation, either version 3 of the License, or any later version.
 * 
 * Visualization of Context Graphs is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License along with Visualization of Context Graphs.  If not, see <http://www.gnu.org/licenses/>.
 ******************************************************************************/
package util.logging;

import java.io.OutputStream;

import core.interfaces.Logger;


public class Log4JWrapper extends Logger
{
	protected org.apache.log4j.Logger	theLog	= null;
	
	public Log4JWrapper(String name)
	{
		theLog = org.apache.log4j.Logger.getLogger(name);
	}
	
	@Override
	public void setLevel(Level level)
	{
		theLog.setLevel(org.apache.log4j.Level.toLevel(level.toString()));
	}
	
	@Override
	public void addDestination(String format, OutputStream destination)
	{
		theLog.addAppender(new WriterAppender(new PatternLayout(format), destination));		
	}

	@Override
	public void error(String message)
	{
		theLog.error(message);
	}
	
	@Override
	public void warn(String message)
	{
		theLog.warn(message);
	}
	
	@Override
	public void info(String message)
	{
		theLog.info(message);
	}
	
	@Override
	public void trace(String message)
	{
		theLog.trace(message);
	}
	
}
