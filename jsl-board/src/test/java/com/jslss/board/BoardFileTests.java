package com.jslss.board;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import java.io.File;
import java.io.IOException;
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
public class BoardFileTests implements Constants{

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
	private final String filenameCat = "CatImage";
	private final String filenamePdf = "PdfImage";
	private final String filenameDog = "DogImage";
	
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
		File imgage2 = new File("src/test/resources/" + filenameDog + ".jpg");
		if(imgage2.exists())
		{
			imgage2.delete();
		}
		
		boardFileRepository.deleteAll();
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
		
		File image = new File("src/test/resources/cat.jpg");
		try
		{
			BoardFile boardFile = BoardFile.createBoardFile(
					filenameCat, jpgType, FileHandler.readImageOldWay(image), image.length(), board, null);
			boardFileRepository.save(boardFile);
			board.addFile(boardFile);
		}
		catch (IOException ex)
		{
			//Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
		}
		File pdf = new File("src/test/resources/testpdf.pdf");
		try
		{
			BoardFile boardFile2 = BoardFile.createBoardFile(
					filenamePdf, pdfType, FileHandler.readImageOldWay(pdf), pdf.length(), board, null);
			boardFileRepository.save(boardFile2);
			board.addFile(boardFile2);
		}
		catch (IOException ex)
		{
			//Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
		}
		
		List<BoardFile> boardFiles = boardFileRepository.findAll();
		File outfile = null;
		// Go through the list returned, looking for PDF/JPG files.
		for (int i = 0; i < boardFiles.size(); i++)
		{
			if (boardFiles.get(i).getContentType().equalsIgnoreCase(pdfType))
			{
				// write out the pdf file
				outfile = new File("src/test/resources/"+boardFiles.get(i).getFileName()+".pdf");
				try
				{
					FileHandler.writeFile(outfile, boardFiles.get(i).getContent());
				}
				catch (IOException e)
				{
					//Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, e);
				}
			}
			else if (boardFiles.get(i).getContentType().equalsIgnoreCase(jpgType))
			{
				// write out the pdf file
				outfile = new File("src/test/resources/"+boardFiles.get(i).getFileName()+".jpg");
				try
				{
					FileHandler.writeFile(outfile, boardFiles.get(i).getContent());
				}
				catch (IOException e)
				{
					//Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, e);
				}
			}
			else
			{
				//Logger.getLogger(Main.class.getName()).log(Level.SEVERE, "Unknown file type");
			}
		}
		
		File imageOutput = new File("src/test/resources/" + filenameCat + ".jpg");
		File pdfOutput = new File("src/test/resources/" + filenamePdf + ".pdf");
		
		assertEquals(image.length(), imageOutput.length());
		assertEquals(pdf.length(), pdfOutput.length());
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

		//Update
		File imageDog = new File("src/test/resources/cat.jpg");
		boardFile.setAction("D");
		boardFile.setContent(FileHandler.readImageOldWay(imageDog));
		boardFile.setFileSize(imageDog.length());
		boardFile.setContentType(jpgType);
		boardFile.setFileName(filenameDog);
		boardFileRepository.save(boardFile);

		//retrival
		//TODO:: Large Objects may not be used in auto-commit mode. >> when lob
		List<BoardFile> boardFileLis2t = boardFileRepository.findByBoard(board);
		List<BoardFile> boardFileList = boardFileRepository.findAllByBoard(board);
		
		BoardFile boardFileRetrieved = boardFileRepository.findOne(boardFile.getFileId());
		File outfile = new File("src/test/resources/"+boardFileRetrieved.getFileName()+".jpg");
		FileHandler.writeFile(outfile, boardFileRetrieved.getContent());
		
		File imageOutput = new File("src/test/resources/" + filenameDog + ".jpg");
		assertEquals(image.length(), imageOutput.length());
		
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
		File pdf = new File("src/test/resources/testpdf.pdf");
		BoardFile boardFile2 = BoardFile.createBoardFile(
				filenamePdf, pdfType, FileHandler.readImageOldWay(pdf), pdf.length(), board, null);
		boardFileRepository.save(boardFile2);
		board.addFile(boardFile2);
		
		//remove
		boardFileRepository.delete(boardFile);
		user.removeBoard(board);
		
		//check
//		List<BoardFile> boardFileList = boardFileRepository.findAll();
//		List<BoardFile> boardFileList2 = boardFileRepository.findAllByBoard(board);
		
		assertFalse(boardFileRepository.exists(boardFile.getFileId()));
	}
	
}
