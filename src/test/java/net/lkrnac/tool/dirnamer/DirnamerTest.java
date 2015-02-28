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

	private static final String TC_NORMAL_PDF = TEST_DIR + File.separator + "2014-PDF.NAME.TEST.normal.pdf";

	private static final String TC_NO_TAIL = TEST_DIR + File.separator + "2013-TAIL.PDF.NAME.TEST.no.pdf";

	private static final String TC_TWO_DIFF_FILES_PDF = TEST_DIR + File.separator
			+ "2015-DIFFERENT.FILES.TEST.two.pdf";

	private static final String TC_TWO_DIFF_FILES_EPUB = TEST_DIR + File.separator
			+ "2015-DIFFERENT.FILES.TEST.two.1.epub";

	private static final String TC_TWO_SAME_FILES_EPUB = TEST_DIR + File.separator
			+ "2015-SAME.FILES.TEST.two.epub";

	private static final String TC_TWO_SAME_FILES_EPUB_1 = TEST_DIR + File.separator
			+ "2015-SAME.FILES.TEST.two.1.epub";

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
		Assert.assertTrue(new File(TC_NORMAL_PDF).exists());
		Assert.assertTrue(new File(TC_NO_TAIL).exists());
		Assert.assertTrue(new File(TC_TWO_DIFF_FILES_PDF).exists());
		Assert.assertTrue(new File(TC_TWO_DIFF_FILES_EPUB).exists());
		Assert.assertTrue(new File(TC_TWO_SAME_FILES_EPUB).exists());
		Assert.assertTrue(new File(TC_TWO_SAME_FILES_EPUB_1).exists());
	}

}
