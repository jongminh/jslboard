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
import com.jslss.board.entity.Comment;
import com.jslss.board.entity.Role;
import com.jslss.board.entity.User;
import com.jslss.board.repository.BoardRepository;
import com.jslss.board.repository.CommentRepository;
import com.jslss.board.repository.RoleRepository;
import com.jslss.board.repository.UserRepository;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = JslBoardApplication.class)
@WebAppConfiguration
public class CommentTests implements Constants{

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private RoleRepository roleRepository;
	
	@Autowired
	private BoardRepository boardRepository;
	
	@Autowired
	private CommentRepository commentRepository;
	
	private User user = User.createUser("testId",
			"Jongmin",
			"Hong",
			"jhong@jslss.com",
			"jason"
			);
	
	@Before
	public void init() {
		commentRepository.deleteAll();
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
		Comment comment = Comment.createComment("test comment", board, user);
		commentRepository.save(comment);
		board.addComment(comment);
		
		List<Comment> commentList = commentRepository.findAll();
		assertEquals("one comment should have been created and retrieve", 1, commentList.size());
	}
	
	@Test
	public void testUpdate(){
		Board board = Board.createBoard("title", "content", null, null, null, null, user);
		boardRepository.save(board);
		user.addBoard(board);
		
		Comment comment = Comment.createComment("test comment", board, user);
		commentRepository.save(comment);
		board.addComment(comment);
		
		//Update
		comment.setAction(ACTION_DELETE);
		comment.setText("Changed Comment");
		comment.setLastUpdateDate(new Date());
		commentRepository.save(comment);
		
		//retrival
		Comment commentUpdated = commentRepository.findOne(comment.getCommentId());
		assertEquals(comment.getAction(), commentUpdated.getAction());
		assertEquals(comment.getText(), commentUpdated.getText());
		assertEquals(comment.getLastUpdateDate(), commentUpdated.getLastUpdateDate());
		
	}
	
	@Test
	public void testDelete(){
		Board board = Board.createBoard("title", "content", null, null, null, null, user);
		boardRepository.save(board);
		user.addBoard(board);
		
		Comment comment = Comment.createComment("test comment", board, user);
		commentRepository.save(comment);
		board.addComment(comment);
		
		Comment comment2 = Comment.createComment("test comment 2", board, user);
		commentRepository.save(comment2);
		board.addComment(comment2);
		
		//remove
		commentRepository.delete(comment);
		board.removeComment(comment);
		
		//check
		List<Comment> commentList = commentRepository.findAllByBoard(board);
		assertEquals(1, commentList.size());
	}
	
}
