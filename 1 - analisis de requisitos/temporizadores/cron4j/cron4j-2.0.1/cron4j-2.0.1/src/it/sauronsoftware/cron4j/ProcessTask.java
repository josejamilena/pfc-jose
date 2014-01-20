/*
 * cron4j - A pure Java cron-like scheduler
 * 
 * Copyright (C) 2007-2009 Carlo Pelliccia (www.sauronsoftware.it)
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License version
 * 2.1, as published by the Free Software Foundation.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License 2.1 for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License version 2.1 along with this program.
 * If not, see <http://www.gnu.org/licenses/>.
 */
package it.sauronsoftware.cron4j;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * <p>
 * A built-in {@link Task} implementation which can be used to run an external
 * process.
 * </p>
 * 
 * @author Carlo Pelliccia
 * @since 2.0
 */
class ProcessTask extends Task {

	/**
	 * The command to launch.
	 */
	private String[] command;

	/**
	 * Environment variables for the spawned process, in the form
	 * <em>name=value</em>. If null the process will inherit the current JVM
	 * environment variables.
	 */
	private String[] envs;

	/**
	 * Working directory for the spawned process. If null the process will
	 * inherit the current JVM working directory.
	 */
	private File directory;

	/**
	 * Standard input file (optional).
	 */
	private File stdinFile = null;

	/**
	 * Standard output file (optional).
	 */
	private File stdoutFile = null;

	/**
	 * Standard error file (optional).
	 */
	private File stderrFile = null;

	/**
	 * Creates the task.
	 * 
	 * @param command
	 *            The command to launch.
	 * @param envs
	 *            Environment variables for the spawned process, in the form
	 *            <em>name=value</em>. If null the process will inherit the
	 *            current JVM environment variables.
	 * @param directory
	 *            Working directory for the spawned process. If null the process
	 *            will inherit the current JVM working directory.
	 * @throws InvalidPatternException
	 *             The supplied pattern is not valid.
	 */
	public ProcessTask(String[] command, String[] envs, File directory)
			throws InvalidPatternException {
		this.command = command;
		this.envs = envs;
		this.directory = directory;
	}

	/**
	 * Sets the standard input file (optional). If supplied the standard input
	 * channel of the spawned process will be read from the given file.
	 * 
	 * @param stdinFile
	 *            The standard input file (optional).
	 */
	public void setStdinFile(File stdinFile) {
		this.stdinFile = stdinFile;
	}

	/**
	 * Sets the standard output file (optional). If supplied the standard output
	 * channel of the spawned process will be written in the given file.
	 * 
	 * @param stdoutFile
	 *            The standard output file (optional).
	 */
	public void setStdoutFile(File stdoutFile) {
		this.stdoutFile = stdoutFile;
	}

	/**
	 * Sets the standard error file (optional). If supplied the standard error
	 * channel of the spawned process will be written in the given file.
	 * 
	 * @param stderrFile
	 *            The standard error file (optional).
	 */
	public void setStderrFile(File stderrFile) {
		this.stderrFile = stderrFile;
	}

	public boolean canBeStopped() {
		return true;
	}

	/**
	 * Implements {@link Task#execute(TaskExecutionContext)}. Runs the given
	 * command as a separate process and waits for its end.
	 */
	public void execute(TaskExecutionContext context) throws RuntimeException {
		Process p;
		try {
			p = exec();
		} catch (IOException e) {
			throw new RuntimeException(toString() + " cannot be started", e);
		}
		InputStream in = buildInputStream(stdinFile);
		OutputStream out = buildOutputStream(stdoutFile);
		OutputStream err = buildOutputStream(stderrFile);
		if (in != null) {
			StreamBridge b = new StreamBridge(in, p.getOutputStream());
			b.start();
		}
		if (out != null) {
			StreamBridge b = new StreamBridge(p.getInputStream(), out);
			b.start();
		}
		if (err != null) {
			StreamBridge b = new StreamBridge(p.getErrorStream(), err);
			b.start();
		}
		int r;
		try {
			r = p.waitFor();
		} catch (InterruptedException e) {
			p.destroy();
			throw new RuntimeException(toString() + " has been interrupted");
		}
		if (r != 0) {
			throw new RuntimeException(toString() + " returns with error code "
					+ r);
		}
	}
	
	/**
	 * Executes the command.
	 * 
	 * @return The launched Process.
	 * @throws IOException
	 *             If an I/O error occurs.
	 */
	private Process exec() throws IOException {
		Runtime rt = Runtime.getRuntime();
		Process p;
		try {
			// java 1.3+
			p = rt.exec(command, envs, directory);
		} catch (NoSuchMethodError e) {
			// java 1.2
			p = rt.exec(command, envs);
		}
		return p;
	}

	/**
	 * Prepares an {@link InputStream} on a file and returns it.
	 * 
	 * @param file
	 *            The file.
	 * @return The stream, or null if the file is not found.
	 */
	private InputStream buildInputStream(File file) {
		if (file != null) {
			try {
				return new FileInputStream(file);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
				return null;
			}
		}
		return null;
	}

	/**
	 * Prepares an {@link OutputStream} on a file and returns it.
	 * 
	 * @param file
	 *            The file.
	 * @return The stream, or null if the file is not found.
	 */
	private OutputStream buildOutputStream(File file) {
		if (file != null) {
			try {
				return new FileOutputStream(file);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
				return null;
			}
		}
		return null;
	}
	
	/**
	 * Overrides {@link Object#toString()}.
	 */
	public String toString() {
		StringBuffer b = new StringBuffer();
		b.append("Task[");
		b.append("cmd=");
		b.append(ProcessTask.listStrings(command));
		b.append(", env=");
		b.append(ProcessTask.listStrings(envs));
		b.append(", ");
		b.append("dir=");
		b.append(directory);
		b.append("]");
		return b.toString();
	}

	/**
	 * Prints in the returned string the elements contained in the given string
	 * array.
	 * 
	 * @param arr
	 *            The array.
	 * @return A string representing the supplied array contents.
	 */
	private static String listStrings(String[] arr) {
		if (arr == null) {
			return "null";
		} else {
			StringBuffer b = new StringBuffer();
			b.append('[');
			for (int i = 0; i < arr.length; i++) {
				if (i > 0) {
					b.append(", ");
				}
				b.append(arr[i]);
			}
			b.append(']');
			return b.toString();
		}
	}

}
