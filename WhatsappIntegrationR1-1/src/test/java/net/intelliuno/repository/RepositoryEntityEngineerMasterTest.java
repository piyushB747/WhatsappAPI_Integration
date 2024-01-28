package net.intelliuno.repository;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class RepositoryEntityEngineerMasterTest {

	@Autowired
	private RepositoryEntityEngineerMaster repositoryEntityEngineerMaster;
	
	@Test
	public void returnEngineerFromContactNumber() {
		
		String m_strName=repositoryEntityEngineerMaster.returnEngineerFromContactNumber("7506214796");
		System.out.println(" ENGINEER   "+m_strName);
		fail("Not yet implemented");
	}

}
