package net.lkrnac.tool.dirnamer;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;

public class Dirnamer {
	public static void main(String[] args) {
		File rootDir = new File(args[0]);

		// @formatter:off
		Arrays.asList(rootDir.listFiles()).stream()
			.filter(child -> child.isDirectory())
			.forEach(child -> handleDocumentDir(rootDir, child));
		// @formatter:on
	}

	private static void handleDocumentDir(File rootDir, File dir) {
		int docFileIdx = 0;
		List<File> children = Arrays.asList(dir.listFiles());
		Collections.sort(children);

		for (File child : children) {
			if (isDocFile(child)) {
				renameAndCopydFile(rootDir, child, dir.getName(), docFileIdx++);
			}
		}
	}

	private static boolean isDocFile(File child) {
		String extension = FilenameUtils.getExtension(child.getName().toLowerCase());
		return "epub".equals(extension) || "pdf".equals(extension);
	}

	private static void renameAndCopydFile(File rootDir, File file, String parentName,
			int differrenciateSuffix) {
		String extenstion = FilenameUtils.getExtension(file.getName().toLowerCase());
		String finalName = parseFileName(parentName);

		String suffix = (differrenciateSuffix == 0) ? "" : "." + differrenciateSuffix;

		String targetFile = rootDir + File.separator + finalName + suffix + "." + extenstion;
		System.out.println(file.getName() + " -> " + targetFile);
		try {
			FileUtils.copyFile(file, new File(targetFile));
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	private static String parseFileName(String parentName) {
		int endOfFirstToken = parentName.indexOf('.');
		String firstToken = parentName.substring(0, endOfFirstToken);
		Pattern yearPattern = Pattern.compile("20\\d\\d");
		Matcher matcher = yearPattern.matcher(parentName);

		matcher.find();
		int yearStart = matcher.start();
		String year = parentName.substring(yearStart, matcher.end());

		String baseName = parentName.substring(endOfFirstToken + 1, yearStart - 1);
		return year + "-" + baseName + "." + firstToken.toLowerCase();
	}
}
