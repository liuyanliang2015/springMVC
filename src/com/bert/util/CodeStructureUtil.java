package com.bert.util;

import java.io.File;
import java.io.IOException;

import org.junit.Test;

public class CodeStructureUtil {
	public static void showDir(int indent, File file) throws IOException {
		for (int i = 0; i < indent; i++)
			System.out.print('-');
		System.out.println(file.getName());
		if (file.isDirectory()) {
			File[] files = file.listFiles();
			for (int i = 0; i < files.length; i++)
				showDir(indent + 4, files[i]);
		}
	}

	@Test
	public void testReadStructure() throws IOException {
		File f = new File("F:/eclipse_demo/SpringMVC");
		showDir(0, f);
	}
}
