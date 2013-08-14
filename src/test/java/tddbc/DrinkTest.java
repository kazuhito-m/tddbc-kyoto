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
	public void 生成したらジュースの名前が取得できる() {
		// arrange/act
		Drink actual = new Drink(DrinkKind.COLA);
		// assert
		assertThat(actual.getCaption(), is("コーラ"));
	}
	
	@Test
	public void メンテナンス責務を持ったクラスからは後に内容を変更できる() {
		// メンテナンス責務=パッケージが同一なら、ととらえる。
		// arrange/act
		Drink actual = new Drink(DrinkKind.COLA);
		// act
		actual.setKind(DrinkKind.APPLE_JUICE);
		actual.setAmountOfTime(150);
		// assert
		assertThat(actual.getCaption(), is("アップルジュース"));
		assertThat(actual.getAmountOfTime(), is(150));
		
	}
}
