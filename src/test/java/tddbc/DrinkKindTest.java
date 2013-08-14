package tddbc;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;

/**
 * 「飲み物の種類」定数群 のテスト。
 * @author kazuhito_m
 */
public class DrinkKindTest {
	@Test
	public void 定数をすべて回し文字変換と定数復元を行える() {
		// 意味はない。ただ「カバ100」を取ってみたくて…。
		for (DrinkKind m : DrinkKind.values()) {
			assertThat(DrinkKind.valueOf(m.name()) , is(m));
		}
	}
	
	@Test
	public void ジュース種から名前が取得できる() {
		String actual = DrinkKind.COLA.getCaption();
		assertThat(actual , is("コーラ"));
	}
}
