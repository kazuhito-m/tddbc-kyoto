package tddbc;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Test;

/**
 * 「飲み物の内部スロット」クラス のテスト。
 * @author kazuhito_m
 */
public class DrinkSlotTest {

	/** テスト対象 */
	private DrinkSlot sut = new DrinkSlot();

	@Before
	public void setUp() {
		sut = new DrinkSlot();
	}

	@Test
	public void スロットにはジュース種と価格が保持できる() {
		// arrange / act
		sut.setKind(DrinkKind.COLA);
		sut.setPrice(120);
		// assert
		assertThat(sut.getKind(), is(DrinkKind.COLA));
		assertThat(sut.getPrice(), is(120));
	}
}