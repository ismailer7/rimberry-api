package io.idev.storeapi.jobs;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jobrunr.scheduling.JobScheduler;
import org.springframework.stereotype.Component;

import io.idev.storeapi.controller.AuthController;

@Component
public class ReminderMessagesJobScheduler {
	
	private static Logger logger = LogManager.getLogger(AuthController.class.toString());
	
	private final JobCronProperties properties;
	
	public ReminderMessagesJobScheduler(JobScheduler jobScheduler, JobCronProperties properties) {
		this.properties = properties;
		logger.info("Scheduling Reminder Messages job to run with Cron {}", properties.getReminderMessagesCron());
	
		jobScheduler.scheduleRecurrently(properties.getReminderMessagesCron(), this::reminderJob);
	}
	
	public void reminderJob() {
		logger.info("Starting Reminder Job..");
		start();
	}
	
	public void start() {
		System.out.println("Reminder Messages Running..");
	}
}
