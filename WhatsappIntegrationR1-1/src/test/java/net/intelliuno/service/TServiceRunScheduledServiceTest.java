package net.intelliuno.service;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class TServiceRunScheduledServiceTest {

	@SuppressWarnings("unused")
	@Autowired
	private ServiceRunScheduledService serviceScheduled;
	
	@Test
	void testToSendMessagesToRm() {
		//serviceScheduled.sendMessagesToRmInTheMorining();
		fail("Not yet implemented");
	}

}
