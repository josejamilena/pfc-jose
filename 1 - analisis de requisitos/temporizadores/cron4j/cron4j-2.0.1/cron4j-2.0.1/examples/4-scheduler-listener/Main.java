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
import it.sauronsoftware.cron4j.Scheduler;

public class Main {

	public static void main(String[] args) {
		// Prepares the listener.
		MySchedulerListener listener = new MySchedulerListener();
		// Prepares the task.
		MyTask task = new MyTask();
		// Creates the scheduler.
		Scheduler scheduler = new Scheduler();
		// Registers the listener.
		scheduler.addSchedulerListener(listener);
		// Schedules the task, once every minute.
		scheduler.schedule("* * * * *", task);
		// Starts the scheduler.
		scheduler.start();
		// Stays alive for five minutes.
		try {
			Thread.sleep(5L * 60L * 1000L);
		} catch (InterruptedException e) {
			;
		}
		// Stops the scheduler.
		scheduler.stop();
	}

}
