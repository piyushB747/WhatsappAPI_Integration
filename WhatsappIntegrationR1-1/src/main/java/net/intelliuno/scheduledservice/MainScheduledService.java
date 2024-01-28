package net.intelliuno.scheduledservice;

import java.time.LocalDate;
import java.time.LocalTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import net.intelliuno.commons.CommonConstants;
import net.intelliuno.service.ServiceRunScheduledService;

@Service
//@EnableScheduling
public class MainScheduledService {

	LocalDate m_strPreviousDateForRmService = null; LocalDate m_strCurrentDate = null;

	@Autowired
	private ScheduledServiceSendMessageToRm serviceSendMessageToRm;

	@Autowired
	private ServiceRunScheduledService serviceRunScheduledService;
	

	/*** THIS SERVICE WILL RUN EVERY 1 SECONDS ***/
	//@Scheduled(fixedRate = 30000)
	public void runEveryMinute() {
		LocalTime currentTime = LocalTime.now();
           
		System.out.println("Date Of Today "+m_strCurrentDate +" Previous Date:= "+m_strPreviousDateForRmService);
		if (m_strPreviousDateForRmService == null) {
		    m_strPreviousDateForRmService = CommonConstants.currentDate.minusDays(1); // 2023-10-04
		}
		if (m_strCurrentDate == null) {
			m_strCurrentDate = CommonConstants.currentDate;
		}
		String m_strTimerToSendRM = "";

		
		if (m_strCurrentDate.equals(m_strPreviousDateForRmService) == false) {

			try {
				m_strTimerToSendRM = serviceRunScheduledService.checkTimeToSendRmWhatsappMessages();
			} catch (Exception ex) { ex.printStackTrace();
			}
			

			if (m_strTimerToSendRM != null) {
				if (m_strTimerToSendRM.equals("") == false) {
					String[] parts = m_strTimerToSendRM.split(":");
					String hour = parts[0];
					String minute = parts[1];

					LocalTime targetTime = LocalTime.of(Integer.parseInt(hour), Integer.parseInt(minute));
					LocalTime endTime = targetTime.plusMinutes(1);
					System.out.println("ROCK MADE ME TO THAT Current"+currentTime+" \n Time To Send RM "+m_strTimerToSendRM+ " \n targetTime= "+targetTime+" endTime= "+endTime);		
					
					if (currentTime.isAfter(targetTime) && currentTime.isBefore(endTime)) {
					
						System.out.println(" GOING TO RUN SERVICE ");
						m_strPreviousDateForRmService = m_strCurrentDate;
						serviceSendMessageToRm.sendMessagesToRmInTheMorining();
						
					}

				}
			}

		}

	}

}
