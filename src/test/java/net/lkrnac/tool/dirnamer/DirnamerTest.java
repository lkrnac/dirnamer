package net.lkrnac.tool.dirnamer;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class DirnamerTest {
	private static final String TEST_RESOURCES_ROOT = String.format("src%stest%sresources", File.separator,
			File.separator);
	private static final String TEMAPLATE_DIR = TEST_RESOURCES_ROOT + File.separator + "template";
	private static final String TEST_DIR = TEST_RESOURCES_ROOT + File.separator + "test";

	private static final String TC_NORMAL_EPUB = TEST_DIR + File.separator
			+ "2015-EPUB.NAME.TEST.normal.epub";

	@BeforeClass
	private void copyFromTemplate() throws IOException {
		FileUtils.copyDirectory(new File(TEMAPLATE_DIR), new File(TEST_DIR));
	}

	@AfterClass
	private void deleteTestingDir() throws IOException {
		FileUtils.deleteDirectory(new File(TEST_DIR));
	}

	@Test
	public void testNormalPdf() throws IOException {
		// GIVEN
		FileUtils.copyDirectory(new File(TEMAPLATE_DIR), new File(TEST_DIR));

		// WHEN
		Dirnamer.main(new String[] { TEST_DIR });

		// THEN
		Assert.assertTrue(new File(TC_NORMAL_EPUB).exists());
	}

}
