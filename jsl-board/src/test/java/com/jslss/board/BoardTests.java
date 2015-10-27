package com.jslss.board;

import static org.junit.Assert.assertEquals;

import java.util.Date;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.jslss.board.consts.Constants;
import com.jslss.board.entity.Board;
import com.jslss.board.entity.Role;
import com.jslss.board.entity.User;
import com.jslss.board.repository.BoardRepository;
import com.jslss.board.repository.RoleRepository;
import com.jslss.board.repository.UserRepository;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = JslBoardApplication.class)
@WebAppConfiguration
public class BoardTests implements Constants{

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private RoleRepository roleRepository;
	
	@Autowired
	private BoardRepository boardRepository;
	
	private User user = User.createUser("testId",
			"Jongmin",
			"Hong",
			"jhong@jslss.com",
			"jason"
			);
	
	@Before
	public void init() {
		boardRepository.deleteAll();
		roleRepository.deleteAll();
		userRepository.deleteAll();
		
		userRepository.save(user);
		Role roleUser = new Role(ROLE_USER);
		roleUser.setUser(user);
		roleRepository.save(roleUser);
		user.addRole(roleUser);
	}
	
	@Test
	public void testCreateRetrieve()
	{
		Board board = Board.createBoard("title", "content", null, null, null, null, user);
		boardRepository.save(board);
		user.addBoard(board);
		
		List<Board> boardList = boardRepository.findAll();
		assertEquals("one board should have been created and retrieve", 1, boardList.size());
	}
	
	@Test
	public void testUpdate(){
		Board board = Board.createBoard("title", "content", null, null, null, null, user);
		boardRepository.save(board);
		user.addBoard(board);
		
		//Update
		board.setAction(ACTION_DELETE);
		board.setContent("Changed content");
		board.setLastUpdateDate(new Date());
		board.setNotice("new notice");
		board.setNoticeExpired(new Date());
		board.setTitle("Changed Title");
		board.setViewCount(100);
		boardRepository.save(board);
		
		//retrival
		Board boardUpdated = boardRepository.findOne(board.getBoardId());
		assertEquals(board.getAction(), boardUpdated.getAction());
		assertEquals(board.getContent(), boardUpdated.getContent());
		assertEquals(board.getLastUpdateDate(), boardUpdated.getLastUpdateDate());
		assertEquals(board.getNotice(), boardUpdated.getNotice());
		assertEquals(board.getNoticeExpired(), boardUpdated.getNoticeExpired());
		assertEquals(board.getTitle(), boardUpdated.getTitle());
		assertEquals(board.getViewCount(), boardUpdated.getViewCount());
		
	}
	
	@Test
	public void testDelete(){
		Board board = Board.createBoard("title", "content", null, null, null, null, user);
		boardRepository.save(board);
		user.addBoard(board);
		
		Board board2 = Board.createBoard("title2", "content2", null, null, null, null, user);
		boardRepository.save(board2);
		user.addBoard(board2);
		
		//remove
		boardRepository.delete(board);
		user.removeBoard(board);
		
		//check
		List<Board> boardList = boardRepository.findAllByUser(user);
		assertEquals( 1, boardList.size());
	}
	
}
