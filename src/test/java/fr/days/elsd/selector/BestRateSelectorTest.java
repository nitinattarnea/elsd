/**
###############################################################################
# Contributors:
#     Damien VILLENEUVE - initial API and implementation
###############################################################################
 */
package fr.days.elsd.selector;

import java.util.ArrayList;

import junit.framework.Assert;

import org.junit.BeforeClass;
import org.junit.Test;

import fr.days.elsd.model.FileTypeEnum;
import fr.days.elsd.model.SubtitleResult;
import fr.days.elsd.processors.Processor;

/**
 * @author dvilleneuve
 * 
 */
public class BestRateSelectorTest extends SelectorTest {

	@BeforeClass
	public static void setup() {
		selector = new BestRateSelector();
	}

	@Test
	public void selectOne_DifferentElements() {
		ArrayList<SubtitleResult> list = new ArrayList<SubtitleResult>();

		SubtitleResult subtitleResult1 = new SubtitleResult(Processor.class, "1234",
				"7c0100307de11000002078031000c00d", "Falling Skies - s01e04.srt", "Falling Skies", "fre", 1, 4,
				"http://localhost/dl.zip", FileTypeEnum.ZIP, "sub-7654", 5.0f);
		SubtitleResult subtitleResult2 = new SubtitleResult(Processor.class, "1234",
				"7c0100307de11000002078031000c00d", "Falling Skies - s01e04.srt", "Falling Skies", "fre", 1, 4,
				"http://localhost/dl.zip", FileTypeEnum.ZIP, "sub-7655", 6.0f);
		SubtitleResult subtitleResult3 = new SubtitleResult(Processor.class, "1234",
				"7c0100307de11000002078031000c00d", "Falling Skies - s01e04.srt", "Falling Skies", "fre", 1, 4,
				"http://localhost/dl.zip", FileTypeEnum.ZIP, "sub-7656", 10.0f);
		list.add(subtitleResult1);
		list.add(subtitleResult2);
		list.add(subtitleResult3);

		SubtitleResult selectedSubtitle = selector.selectOne(list);
		Assert.assertEquals(subtitleResult3, selectedSubtitle);
	}

	@Test
	public void selectOne_MultipleSameRate() {
		ArrayList<SubtitleResult> list = new ArrayList<SubtitleResult>();

		SubtitleResult subtitleResult1 = new SubtitleResult(Processor.class, "1234",
				"7c0100307de11000002078031000c00d", "Falling Skies - s01e04.srt", "Falling Skies", "fre", 1, 4,
				"http://localhost/dl.zip", FileTypeEnum.ZIP, "sub-7654", 5.0f);
		SubtitleResult subtitleResult2 = new SubtitleResult(Processor.class, "1234",
				"7c0100307de11000002078031000c00d", "Falling Skies - s01e04.srt", "Falling Skies", "fre", 1, 4,
				"http://localhost/dl.zip", FileTypeEnum.ZIP, "sub-7655", 6.0f);
		SubtitleResult subtitleResult3 = new SubtitleResult(Processor.class, "1234",
				"7c0100307de11000002078031000c00d", "Falling Skies - s01e04.srt", "Falling Skies", "fre", 1, 4,
				"http://localhost/dl.zip", FileTypeEnum.ZIP, "sub-7656", 6.0f);
		list.add(subtitleResult1);
		list.add(subtitleResult2);
		list.add(subtitleResult3);

		SubtitleResult selectedSubtitle = selector.selectOne(list);
		Assert.assertEquals(subtitleResult2, selectedSubtitle);
	}

}
