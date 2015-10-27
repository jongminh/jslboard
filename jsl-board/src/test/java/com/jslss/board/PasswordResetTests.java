package com.jslss.board;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.UUID;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.jslss.board.consts.Constants;
import com.jslss.board.entity.PasswordReset;
import com.jslss.board.entity.Role;
import com.jslss.board.entity.User;
import com.jslss.board.repository.PasswordResetRepository;
import com.jslss.board.repository.RoleRepository;
import com.jslss.board.repository.UserRepository;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = JslBoardApplication.class)
@WebAppConfiguration
public class PasswordResetTests implements Constants{

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private RoleRepository roleRepository;
	
	@Autowired
	private PasswordResetRepository passwordResetRepository;
	
	private User user = User.createUser("testId",
			"Jongmin",
			"Hong",
			"jhong@jslss.com",
			"jason"
			);
	
	@Before
	public void init() {
		passwordResetRepository.deleteAll();
		roleRepository.deleteAll();
		userRepository.deleteAll();
		
		Role roleUser = new Role(ROLE_USER);
		
		userRepository.save(user);
		roleUser.setUser(user);
		roleRepository.save(roleUser);
		user.addRole(roleUser);
	}
	
	@Test
	public void testCreateRetrieve()
	{
		String randomKey = UUID.randomUUID().toString();
		
		PasswordReset passwordReset = new PasswordReset(randomKey.substring(0, 10), user);
		passwordResetRepository.save(passwordReset);
		
		PasswordReset passwordResetRetrival = passwordResetRepository.findOne(passwordReset.getPwResetId());
		assertEquals(passwordReset.getResetCode(), passwordResetRetrival.getResetCode());
		
	}
	
	@Test
	public void testUpdate(){
		String randomKey = UUID.randomUUID().toString();
		
		PasswordReset passwordReset = new PasswordReset(randomKey.substring(0, 10), user);
		passwordResetRepository.save(passwordReset);
		
		//Update
		passwordReset.setResetCode("changedkeyword");
		passwordResetRepository.save(passwordReset);
		
		PasswordReset passwordResetRetrival = passwordResetRepository.findOne(passwordReset.getPwResetId());
		assertEquals(passwordReset.getResetCode(), passwordResetRetrival.getResetCode());
	}
	
	@Test
	public void testDelete(){
		String randomKey = UUID.randomUUID().toString();
		PasswordReset passwordReset = new PasswordReset(randomKey.substring(0, 10), user);
		passwordResetRepository.save(passwordReset);
		
		assertTrue("PasswordReset should exist.", passwordResetRepository.exists(passwordReset.getPwResetId()));
		
		passwordResetRepository.delete(passwordReset.getPwResetId());
		assertFalse("PasswordReset should NOT exist.", passwordResetRepository.exists(passwordReset.getPwResetId()));
	}
	
}
