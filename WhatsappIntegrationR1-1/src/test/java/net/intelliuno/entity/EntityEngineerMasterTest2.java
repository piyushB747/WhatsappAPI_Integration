package net.intelliuno.entity;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import net.intelliuno.repository.RepositoryEntityEngineerMaster;
@SpringBootTest
class EntityEngineerMasterTest2 {

	@Autowired
	private RepositoryEntityEngineerMaster repositoryEntityEngineerMaster;
		
	
	@Test
	void testTo() {
		String m_strName=repositoryEntityEngineerMaster.returnEngineerFromContactNumber("7506214796");
		System.out.println(" ENGINEER   "+m_strName);
		fail("Not yet implemented");
	}
}
