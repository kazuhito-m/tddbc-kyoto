package tddbc;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

import org.junit.Ignore;
import org.junit.Test;

public class MoneyTest {
	@Test
	public void 定数をすべて回し文字変換と定数復元を行える() {
		// 意味はない。ただ「カバ100」を取ってみたくて…。
		for (Money m : Money.values()) {
			assertThat(Money.valueOf(m.name()) , is(m));
		}
	}
	
	@Ignore
	@Test
	public void 大小比較が金額の考慮によりできる() {
		// TODO 未実装 
	}
	
}
