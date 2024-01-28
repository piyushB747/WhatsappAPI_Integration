package net.intelliuno.repository;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import net.intelliuno.entity.EntityServiceRunningTimer;

@SpringBootTest
class TestRepoEntityServiceRunningTimerTest {

	@Autowired
	private RepoEntityServiceRunningTimer repoEntityServiceRunningTimer;
	
	
	public void testToRunService() {
		
		EntityServiceRunningTimer l_objServiceRunner=new EntityServiceRunningTimer();
		l_objServiceRunner.setServiceNameSr("SEND MESSAGE TO RM");
		l_objServiceRunner.setServiceTimerSr("9");
		l_objServiceRunner.setDeleteFlagSr("N");
		repoEntityServiceRunningTimer.save(l_objServiceRunner);
		
		fail("Not yet implemented");
	}
	
	@Test
	public void testToFindOutStringReturn() {
		String x=repoEntityServiceRunningTimer.findByServiceNameSrNative("N","SEND MESSAGE TO RM");
		System.out.println(x+"  MUJHE  ");
	}

}
