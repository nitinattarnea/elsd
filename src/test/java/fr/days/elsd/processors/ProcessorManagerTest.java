/**
###############################################################################
# Contributors:
#     Damien VILLENEUVE - initial API and implementation
###############################################################################
 */
package fr.days.elsd.processors;

import java.io.File;

import junit.framework.Assert;

import org.junit.Test;

import fr.days.elsd.model.FileTypeEnum;
import fr.days.elsd.model.SubtitleResult;
import fr.days.elsd.processors.impl.FakeProcessor;
import fr.days.elsd.processors.impl.FakeProcessorBestResult;
import fr.days.elsd.processors.impl.FakeProcessorNoResults;
import fr.days.elsd.selector.BestRateSelector;
import fr.days.elsd.selector.FirstSelector;

/**
 * @author dvilleneuve
 * 
 */
public class ProcessorManagerTest {

	private static ProcessorManager processorManager;
	private static File video;

	public ProcessorManagerTest() {
		processorManager = new ProcessorManager("fre");
		processorManager.addProcessor(new FakeProcessor());
		processorManager.addProcessor(new FakeProcessorNoResults());
		processorManager.addProcessor(new FakeProcessorBestResult());
		processorManager.setSubtitleSelector(new FirstSelector());

		video = new File("src/Test/resources/Falling Skies - 1x04 - Grace.HDTV.fqm.fr.avi");
	}

	@Test(expected = IllegalArgumentException.class)
	public void searchWithoutProcessorsTest() {
		ProcessorManager illegalProcessorManager = new ProcessorManager("fre");
		illegalProcessorManager.searchSubtitle(video);
	}

	@Test(expected = IllegalArgumentException.class)
	public void searchWithoutVideoTest() {
		processorManager.searchSubtitle(null);
	}

	@Test
	public void searchFirstResultTest() {
		SubtitleResult searchSubtitle = processorManager.searchSubtitle(video);
		SubtitleResult expectedSubtitle = new SubtitleResult(FakeProcessor.class, "12345",
				"7c0100307de11000002078031000c00d", "Falling Skies - s01e04.srt", "Falling Skies", "fre", 1, 4,
				"http://localhost/dl.zip", FileTypeEnum.ZIP, "sub-1234", 5.0f);

		Assert.assertEquals(expectedSubtitle, searchSubtitle);
	}

	@Test
	public void searchBestResultTest() {
		processorManager.setSubtitleSelector(new BestRateSelector());
		SubtitleResult searchSubtitle = processorManager.searchSubtitle(video);
		SubtitleResult expectedSubtitle = new SubtitleResult(FakeProcessorBestResult.class, "12345",
				"7c0100307de11000002078031000c00d", "Falling Skies - s01e04.srt", "Falling Skies", "fre", 1, 4,
				"http://localhost/dl.zip", FileTypeEnum.ZIP, "sub-7654", 10.0f);

		Assert.assertEquals(expectedSubtitle, searchSubtitle);
	}

}
