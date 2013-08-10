package tddbc;

import static org.hamcrest.core.Is.*;
import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

/**
 * @author kazuhito_m
 */
public class SampleTubeTest {

	/** テスト対象 */
	private SampleTube sut;

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		sut = new SampleTube();
	}

	@Test
	public void mainも一応回しとく() {
		SampleTube.main(new String[] { "test" });
	}

	@Test
	public void outSampleMessageをとりあえず回す() {
		// arrange - 準備
		final String errMsg = "outSampleMessageが単純なメソッドなのにたったtrueを返すこともできないなんて。";
		// act - 実行
		boolean actual = sut.outSampleMessage(new String[] { errMsg });
		// assert - 検証
		assertThat(errMsg, actual, is(true));
	}
}