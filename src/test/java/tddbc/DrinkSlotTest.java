package tddbc;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

import org.junit.Ignore;
import org.junit.Test;

/**
 * 「飲み物の内部スロット」クラス のテスト。
 * @author kazuhito_m
 */
public class DrinkSlotTest {
	
	@Test
	public void スロットにはジュース種と価格が保持できる() {
		// arrange / act
		DrinkSlot actual = new DrinkSlot();
		actual.setKind(DrinkKind.COLA);
		actual.setPrice(120);
		// assert
		assertThat(actual.getKind() , is(DrinkKind.COLA));
		assertThat(actual.getPrice() , is(120));
	}
}