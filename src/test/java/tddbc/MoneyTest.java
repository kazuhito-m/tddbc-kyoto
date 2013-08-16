package tddbc;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static tddbc.Money.*;

import org.junit.Test;

public class MoneyTest {
    @Test
    public void 定数をすべて回し文字変換と定数復元を行える() {
        // 意味はない。ただ「カバ100」を取ってみたくて…。
        for (Money m : Money.values()) {
            assertThat(Money.valueOf(m.name()), is(m));
        }
    }

    @Test
    public void 大小比較が金額の考慮によりできる() {
        // act and assert
        assertThat(_10.compareTo(_100), is(-2));
        assertThat(_1000.compareTo(_100), is(2));
        assertThat(_50.compareTo(_500), is(-2));
        assertThat(_10.compareTo(_1000), is(-4));
    }
}