package io.idev.storeapi.jobs;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import lombok.Getter;
import lombok.Setter;

@Configuration
@Getter
@Setter
public class JobCronProperties {

	@Value("${ccm.reminder.job.cron}")
	private String reminderMessagesCron;
	
	
}
