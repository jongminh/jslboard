package com.jslss.board.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

public class FileHandler {

	public static byte[] readImageOldWay(File file) throws IOException
	{
		Logger.getLogger(FileHandler.class.getName()).log(Level.INFO, "[Open File] " + file.getAbsolutePath());
		InputStream is = new FileInputStream(file);
		// Get the size of the file
		long length = file.length();
		// You cannot create an array using a long type.
		// It needs to be an int type.
		// Before converting to an int type, check
		// to ensure that file is not larger than Integer.MAX_VALUE.
		if (length > Integer.MAX_VALUE)
		{
			// File is too large
		}
		// Create the byte array to hold the data
		byte[] bytes = new byte[(int) length];
		// Read in the bytes
		int offset = 0;
		int numRead = 0;
		while (offset < bytes.length && (numRead = is.read(bytes, offset, bytes.length - offset)) >= 0)
		{
			offset += numRead;
		}
		// Ensure all the bytes have been read in
		if (offset < bytes.length)
		{
			throw new IOException("Could not completely read file " + file.getName());
		}
		// Close the input stream and return bytes
		is.close();
		return bytes;
	}

	public static void writeFile(File file, byte[] data) throws IOException
	{
		OutputStream fo = new FileOutputStream(file);
		// Write the data
		fo.write(data);
		// flush the file (down the toilet)
		fo.flush();
		// Close the door to keep the smell in.
		fo.close();
	}
}
