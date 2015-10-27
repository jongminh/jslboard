package com.jslss.board;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Date;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.jslss.board.consts.Constants;
import com.jslss.board.entity.Role;
import com.jslss.board.entity.User;
import com.jslss.board.repository.RoleRepository;
import com.jslss.board.repository.UserRepository;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = JslBoardApplication.class)
@WebAppConfiguration
public class UserTests implements Constants{

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private RoleRepository roleRepository;
	
	private User user = User.createUser("testId",
			"Jongmin",
			"Hong",
			"jhong@jslss.com",
			"jason"
			);
	private User user2 = User.createUser("testId2",
			"Jongmin2",
			"Hong2",
			"jhong2@jslss.com",
			"jason2"
			);	
	private User user3 = User.createUser("testId3",
			"Jongmin3",
			"Hong3",
			"jhong3@jslss.com",
			"jason3"
			);
	
	private User user4 = User.createUser("testId4",
			"Jongmin4",
			"Hong4",
			"jhong4@jslss.com",
			"jason4"
			);	
	
	@Before
	public void init() {
		roleRepository.deleteAll();
		userRepository.deleteAll();
	}
	
	@Test
	public void testCreateRetrieve()
	{
		Role roleUser = new Role(ROLE_USER);
		
		userRepository.save(user);
		roleUser.setUser(user);
		roleRepository.save(roleUser);
		user.addRole(roleUser);
		
		Role roleUser2 = new Role(ROLE_USER);
		Role role2User2 = new Role(ROLE_ADMIN);
		
		userRepository.save(user2);
		roleUser2.setUser(user2);
		role2User2.setUser(user2);
		roleRepository.save(roleUser2);
		roleRepository.save(role2User2);
		user2.addRole(roleUser2);
		user2.addRole(role2User2);
		
		List<User> users1 = userRepository.findAll();
		assertEquals("two user should have been created and retrieve", 2, users1.size());
		User user4 = userRepository.findOne(user.getUserId());
		assertEquals("Inserted user should match retrieved", user.getUserId(), user4.getUserId());
		
		Role role2User3 = new Role(ROLE_ADMIN);
		
		userRepository.save(user3);
		role2User3.setUser(user3);
		roleRepository.save(role2User3);
		user3.addRole(role2User3);
		
		List<User> users2 = userRepository.findAll();
		assertEquals("Should be three retrieved users.", 3, users2.size());
		
	}
	
	@Test
	public void testExists(){
		
		Role roleUser4 = new Role(ROLE_USER);
		
		userRepository.save(user4);
		roleUser4.setUser(user4);
		roleRepository.save(roleUser4);
		user3.addRole(roleUser4);
		
		assertTrue("User should exist.", userRepository.exists(user4.getUserId()));
		assertFalse("User should NOT exist.", userRepository.exists("test111"));
		
	}
	
	@Test
	public void testUpdate(){
		PasswordEncoder encod = new BCryptPasswordEncoder(); 
	        
		Role roleUser1 = new Role(ROLE_USER);
		
		userRepository.save(user);
		roleUser1.setUser(user);
		roleRepository.save(roleUser1);
		user.addRole(roleUser1);
		
		//Update
		user.setEnabled(true);
		user.setAction(ACTION_DELETE);
		user.setFirstName("Changed F");
		user.setLastName("Changed L");
		user.setPassword(encod.encode("jasonchanged"));
		user.setEmail("mergwy@jslss.com");
		user.setLastLogIn(new Date());
		user.setLastLogOut(new Date());
		userRepository.save(user);
		
		Role role2User1 = new Role(ROLE_ADMIN);
		role2User1.setUser(user);
		roleRepository.save(role2User1);
		user.addRole(role2User1);
		
		//retrival
		User userUpdated = userRepository.findOne(user.getUserId());
		assertEquals(user.isEnabled(), userUpdated.isEnabled());
		assertEquals(user.isAction(), userUpdated.isAction());
		assertEquals(user.getFirstName(), userUpdated.getFirstName());
		assertEquals(user.getPassword(), userUpdated.getPassword());
		assertEquals(user.getEmail(), userUpdated.getEmail());
		assertEquals(user.getLastLogIn(), userUpdated.getLastLogIn());
		assertEquals(user.getLastLogOut(), userUpdated.getLastLogOut());
		assertEquals(user.getRoles().size(), userUpdated.getRoles().size());
		
	}
	
	@Test
	public void testDelete(){
		userRepository.save(user);
		assertTrue("User should exist.", userRepository.exists(user.getUserId()));
		
		userRepository.delete(user.getUserId());
		assertFalse("User should NOT exist.", userRepository.exists(user.getUserId()));
	}
	
}
