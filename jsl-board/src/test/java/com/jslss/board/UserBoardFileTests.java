package com.jslss.board;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
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
import com.jslss.board.entity.BoardFile;
import com.jslss.board.entity.Role;
import com.jslss.board.entity.User;
import com.jslss.board.repository.BoardFileRepository;
import com.jslss.board.repository.BoardRepository;
import com.jslss.board.repository.RoleRepository;
import com.jslss.board.repository.UserRepository;
import com.jslss.board.utils.FileHandler;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = JslBoardApplication.class)
@WebAppConfiguration
public class UserBoardFileTests implements Constants{

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private RoleRepository roleRepository;
	
	@Autowired
	private BoardRepository boardRepository;

	@Autowired
	private BoardFileRepository boardFileRepository;
	
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
	private final String filenameCat = "CatImage";
	private final String filenamePdf = "PdfImage";
	
	private final String jpgType = "image/jpg";
	private final String pdfType = "application/pdf";
	
	@Before
	public void init() {
		File image = new File("src/test/resources/" + filenameCat + ".jpg");
		if(image.exists())
		{
			image.delete();
		}
		File pdf = new File("src/test/resources/" + filenamePdf + ".pdf");
		if(pdf.exists())
		{
			pdf.delete();
		}
		
		List<User> userRetrieved = userRepository.findAll();
		for(int i = 0; i < userRetrieved.size() ; i++ )
		{
			User tempUser = userRetrieved.get(i);
			tempUser.setBoardFiles(null);
			userRepository.save(tempUser);
		}
			
//		List<BoardFile> boardFilesRetrieved = boardFileRepository.findAll();
//		for(int i = 0; i < boardFilesRetrieved.size() ; i++ )
//		{
//			BoardFile boardFile = boardFilesRetrieved.get(i);
//			boardFile.setUsers(null);
//		}
//		boardFileRepository.save(boardFilesRetrieved);
		boardFileRepository.deleteAll();
		boardRepository.deleteAll();
		roleRepository.deleteAll();
		userRepository.deleteAll();
		
		userRepository.save(user);
		Role roleUser = new Role(ROLE_USER);
		roleUser.setUser(user);
		roleRepository.save(roleUser);
		user.addRole(roleUser);
		
		userRepository.save(user2);
		Role roleUser2 = new Role(ROLE_USER);
		roleUser2.setUser(user2);
		roleRepository.save(roleUser2);
		user2.addRole(roleUser2);
	}
	
	@Test
	public void testCreateRetrieve() throws IOException
	{
		Board board = Board.createBoard("title", "content", null, null, null, null, user);
		boardRepository.save(board);
		user.addBoard(board);
		
		File image = new File("src/test/resources/cat.jpg");
		BoardFile boardFile = BoardFile.createBoardFile(
				filenameCat, jpgType, FileHandler.readImageOldWay(image), image.length(), board, null);
		boardFileRepository.save(boardFile);
		board.addFile(boardFile);

		List<BoardFile> boardFiles = new ArrayList<BoardFile>();
		boardFiles.add(boardFile);
		user.setBoardFiles(boardFiles);
		userRepository.save(user);
		
		// Add other file
		Board board2 = Board.createBoard("title2", "content2", null, null, null, null, user2);
		boardRepository.save(board2);
		user2.addBoard(board2);
		
		File image2 = new File("src/test/resources/cat.jpg");
		BoardFile boardFile2 = BoardFile.createBoardFile(
				filenameCat, jpgType, FileHandler.readImageOldWay(image2), image2.length(), board2, null);
		boardFileRepository.save(boardFile2);
		board2.addFile(boardFile2);

		List<BoardFile> boardFiles2 = new ArrayList<BoardFile>();
		boardFiles2.add(boardFile2);
		user2.setBoardFiles(boardFiles2);
		userRepository.save(user2);
		//
		
		//User userRetrived = userRepository.findByUserId(user.getUserId());
		boardRepository.findAllByUser(user);
		List<BoardFile> boardFilesRetrieved = boardFileRepository.findAllByBoard(board);
		//List<BoardFile> boardFilesRetrieved = userRetrived.getBoardFiles();
		assertEquals(boardFiles.size(), boardFilesRetrieved.size());
		
		BoardFile boardFileFromDB = boardFilesRetrieved.get(0);
		BoardFile boardFileOri = boardFiles.get(0);
		assertEquals(boardFileFromDB.getFileName(),boardFileFromDB.getFileName());
		assertEquals(boardFileOri.getFileSize(),boardFileOri.getFileSize());
		
	}
	
	@Test
	public void testUpdate() throws IOException{
		Board board = Board.createBoard("title", "content", null, null, null, null, user);
		boardRepository.save(board);
		user.addBoard(board);
		
		File image = new File("src/test/resources/cat.jpg");
		BoardFile boardFile = BoardFile.createBoardFile(
				filenameCat, jpgType, FileHandler.readImageOldWay(image), image.length(), board, null);
		boardFileRepository.save(boardFile);
		board.addFile(boardFile);
		
		File pdf = new File("src/test/resources/testpdf.pdf");
		BoardFile boardFile2 = BoardFile.createBoardFile(
				filenamePdf, pdfType, FileHandler.readImageOldWay(pdf), pdf.length(), board, null);
		boardFileRepository.save(boardFile2);
		board.addFile(boardFile2);
		
		ArrayList<User> userList = new ArrayList<User>();
		userList.add(user);
		boardFile.setUsers(userList);
		boardRepository.save(board);
		
		List<BoardFile> boardFiles = new ArrayList<BoardFile>();
		boardFiles.add(boardFile);
		user.setBoardFiles(boardFiles);
		userRepository.save(user);
		

		//Update
		user.setBoardFiles(new ArrayList<BoardFile>());
		
		List<User> userRetrieved = userRepository.findAll();
		for(int i = 0; i < userRetrieved.size() ; i++ )
		{
			User tempUser = userRetrieved.get(i);
			if(user.getUserId().equals(tempUser.getUserId()))
			{
				List<BoardFile> boardFiles2 = new ArrayList<BoardFile>();
				boardFiles2.add(boardFile2);
				tempUser.setBoardFiles(boardFiles2);
				userRepository.save(tempUser);
				
				//
				user.setBoardFiles(boardFiles2);
			}
		}

		//retrival
		
		//TODO :: how to check
		List<User> userRetrievedCofirm = userRepository.findAll();
		for(int i = 0; i < userRetrievedCofirm.size() ; i++ )
		{
			User tempUser = userRetrievedCofirm.get(i);
			if(user.getUserId().equals(tempUser.getUserId()))
			{
				List<Board> boardList = boardRepository.findAllByUser(tempUser);
				for(int j = 0; j < boardList.size() ; j++ )
				{
					List<BoardFile> boardFilesRetrieved = boardFileRepository.findAllByBoard(boardList.get(j));
					assertTrue(user.getBoardFiles().containsAll(boardFilesRetrieved));	
				}
			}
		}
		
	}
	
	@Test
	public void testDelete() throws IOException{
		Board board = Board.createBoard("title", "content", null, null, null, null, user);
		boardRepository.save(board);
		user.addBoard(board);
		
		File image = new File("src/test/resources/cat.jpg");
		BoardFile boardFile = BoardFile.createBoardFile(
				filenameCat, jpgType, FileHandler.readImageOldWay(image), image.length(), board, null);
		
		boardFileRepository.save(boardFile);
		board.addFile(boardFile);
		ArrayList<User> userList = new ArrayList<User>();
		userList.add(user);
		boardFile.setUsers(userList);
		boardRepository.save(board);
		
		List<BoardFile> boardFiles = new ArrayList<BoardFile>();
		boardFiles.add(boardFile);
		user.setBoardFiles(boardFiles);
		userRepository.save(user);
		
		
		//remove
		user.setBoardFiles(new ArrayList<BoardFile>());
		List<User> userRetrieved = userRepository.findAll();
		for(int i = 0; i < userRetrieved.size() ; i++ )
		{
			User tempUser = userRetrieved.get(i);
			if(user.getUserId().equals(tempUser.getUserId()))
			{
				tempUser.setBoardFiles(new ArrayList<BoardFile>());
				userRepository.save(tempUser);
			}
		}
		
		List<User> userRetrievedCofirm = userRepository.findAll();
		for(int i = 0; i < userRetrievedCofirm.size() ; i++ )
		{
			User tempUser = userRetrievedCofirm.get(i);
			if(user.getUserId().equals(tempUser.getUserId()))
			{
				List<Board> boardList = boardRepository.findAllByUser(tempUser);
				for(int j = 0; j < boardList.size() ; j++ )
				{
					List<BoardFile> boardFilesRetrieved = boardFileRepository.findAllByBoard(boardList.get(j));
					//TODO :: how to check
					assertEquals(user.getBoardFiles().size(), boardFilesRetrieved.size());	
				}
			}
		}
		
	}
	
}
