package com.jslss.board;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
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
public class RoleTests implements Constants{

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
	
	@Before
	public void init() {
		roleRepository.deleteAll();
		userRepository.deleteAll();
	}
	
	@Test
	public void testCreateRetrieve()
	{
		userRepository.save(user);
		Role roleUser = new Role(ROLE_USER);
		roleUser.setUser(user);
		roleRepository.save(roleUser);
		user.addRole(roleUser);

		User userRetrival = userRepository.findOne(user.getUserId());
		assertEquals(user.getRoles().get(0).getRole(), userRetrival.getRoles().get(0).getRole());
		
	}
	
	@Test
	public void testUpdate(){
		userRepository.save(user);
		Role roleUser = new Role(ROLE_USER);
		roleUser.setUser(user);
		roleRepository.save(roleUser);
		user.addRole(roleUser);
		
		//Update
		roleUser.setRole(ROLE_ADMIN);
		roleRepository.save(roleUser);
		//userRepository.save(user);
		
		//retrival
		User userRetrival = userRepository.findOne(user.getUserId());
		assertEquals(user.getRoles().get(0).getRole(), userRetrival.getRoles().get(0).getRole());
		
	}
	
	@Test
	public void testDelete(){
		userRepository.save(user);
		Role roleUser = new Role(ROLE_USER);
		Role role2User = new Role(ROLE_ADMIN);
		roleUser.setUser(user);
		role2User.setUser(user);
		roleRepository.save(roleUser);
		roleRepository.save(role2User);
		user.addRole(roleUser);
		user.addRole(role2User);
		assertEquals(2, user.getRoles().size());
		
		user.removeRole(role2User);
		userRepository.save(user);
		roleRepository.delete(role2User);
		
		
		User userRetrival = userRepository.findOne(user.getUserId());
		assertEquals(1, userRetrival.getRoles().size());
	}
	
}

