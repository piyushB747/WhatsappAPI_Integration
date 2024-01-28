package net.intelliuno.serviceimpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import net.intelliuno.repository.RepoEntityServiceRunningTimer;

@Service
public class ServiceRunScheduledServiceMain {

	@Autowired
	private RepoEntityServiceRunningTimer repoEntityServiceRunningTimer;

	public String generateDynamicCronExpression(String serviceName) {
		String m_strHour = "";
		try {
			m_strHour = repoEntityServiceRunningTimer.findByServiceNameSrNative("N", "SEND MESSAGE TO RM");
		} catch (Exception ex) {
			m_strHour = "09:00"; // Default value if there's an exception
		}

		String cronExpression = calculateCronExpression(m_strHour);
		return cronExpression;
	}

	private String calculateCronExpression(String scheduledTime) {
		String[] parts = scheduledTime.split(":");
		String cronExpression = "0 " + parts[1] + " " + parts[0] + " * * ?";
		return cronExpression;
	}
}
