package tddbc;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;

/**
 * 「飲み物」クラスのテスト。
 * @author kazuhito_m
 */
public class DrinkTest {
	@Test
	public void 生成したらジュースの名前と販売時の価格が取得できる() {
		// arrange/act
		Drink actual = new Drink(DrinkKind.COLA , 120);
		// assert
		assertThat(actual.getCaption(), is("コーラ"));
		assertThat(actual.getAmountOfTime(), is(120));
	}
}
