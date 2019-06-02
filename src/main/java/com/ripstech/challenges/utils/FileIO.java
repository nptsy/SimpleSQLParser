package com.ripstech.challenges.utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;

import org.apache.log4j.Logger;

import com.ripstech.challenges.models.ast.ASTTree;

public class FileIO {
	
	private final static Logger LOGGER = Logger.getLogger(FileIO.class);
	
	
	public static String readContent(String inFilePath) {
		StringBuilder strContent = new StringBuilder();
		if (inFilePath != null && inFilePath.length() > 0) {
			FileReader reader;
			try {
				reader = new FileReader(inFilePath);
				
				BufferedReader bufferedReader;
				// create buffer for content
				bufferedReader = new BufferedReader(reader);
				
				String temp = bufferedReader.readLine();
				while (temp != null) {
					strContent.append("\n" + temp) ;
					temp = bufferedReader.readLine();
				}
				bufferedReader.close();
				
			} catch (FileNotFoundException e) {
				LOGGER.error(e.getMessage());
			} catch (IOException e) {
				LOGGER.error(e.getMessage());
			}
		}
		return strContent.toString();
	}

	public static LinkedList<String> readLines (String inFilePath){
		LinkedList<String> lstLines = new LinkedList<String>();
		
		if (inFilePath != null && inFilePath.length() > 0) {
			FileReader reader;
			try {
				reader = new FileReader(inFilePath);
				
				BufferedReader bufferedReader;
				// create buffer for content
				bufferedReader = new BufferedReader(reader);
				
				String temp = bufferedReader.readLine();
				while (temp != null) {
					lstLines.add(temp);
					temp = bufferedReader.readLine();
				}
				bufferedReader.close();
				
			} catch (FileNotFoundException e) {
				LOGGER.error(e.getMessage());
			} catch (IOException e) {
				LOGGER.error(e.getMessage());
			}
		}
		return lstLines;
	}
	
	public Set<String> readLineContents (String inFilePath) {
		Set<String> setLines = new HashSet<String>();
		if (inFilePath != null && inFilePath.length() > 0) {
			FileReader reader;
			try {
				reader = new FileReader(inFilePath);
				
				BufferedReader bufferedReader;
				// create buffer for content
				bufferedReader = new BufferedReader(reader);
				
				String temp = bufferedReader.readLine();
				while (temp != null) {
					if(temp.startsWith("/") || temp.startsWith("%")  || temp.startsWith("#")) {
						continue;
					}
					setLines.add(temp) ;
					temp = bufferedReader.readLine();
				}
				bufferedReader.close();
				
			} catch (FileNotFoundException e) {
				LOGGER.error(e.getMessage());
			} catch (IOException e) {
				LOGGER.error(e.getMessage());
			}
		}
		return setLines;
	}
	
	public static boolean writeContent(String content, String outFilePath) {
		FileWriter writer;
		try {
			writer = new FileWriter(outFilePath);
			BufferedWriter bufferedWriter = new BufferedWriter(writer);
			bufferedWriter.write(content);
			bufferedWriter.close();
		} catch (IOException e) {
			System.out.println("Error during writing result");
			LOGGER.error("Error during writing result: " +e.getMessage());
			return false;
		}
		return true;
	}
}
