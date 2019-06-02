package com.ripstech.challenges.utils;

import junit.framework.TestCase;

public class FileIOTest extends TestCase{

	public void testLoadFile() {
		String content = FileIO.readContent("sql-keywords.txt");
		assertNotNull(content);
	}

}
