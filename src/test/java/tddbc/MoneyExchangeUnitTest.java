package tddbc;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import static tddbc.Money.*;

/**
 * 紙幣・硬貨両替機クラス。
 * @author kazuhito_m
 */
public class MoneyExchangeUnitTest {
	/** テスト対象 */
	private MoneyExchangeUnit sut;

	/** フィクスチャ 元の通貨箱。 */
	private List<Money> srcBox;

	/** フィクスチャ 先の通貨箱。 */
	private List<Money> dstBox;

	@Before
	public void setUp() {
		sut = new MoneyExchangeUnit();
		// 通貨箱をセットアップ
		boxSetup(srcBox = new ArrayList<Money>());
		boxSetup(dstBox = new ArrayList<Money>());
	}

	/** 通貨の箱セットアップ。 */
	private void boxSetup(List<Money> moneyBox) {
		for (Money m : Money.values()) {
			moneyBox.add(m);
		}
	}

	@Test
	public void 二つの通貨の箱から指定金額を移動する() {
		// arrange
		final int MOVE_AMOUNT = 160;
		int srcTotal = sut.sumAmount(srcBox);
		int dstTotal = sut.sumAmount(dstBox);
		// act
		sut.moveMoney(srcBox, dstBox, MOVE_AMOUNT);
		// assert
		assertThat(sut.sumAmount(srcBox), is(srcTotal - MOVE_AMOUNT));
		assertThat(sut.sumAmount(dstBox), is(dstTotal + MOVE_AMOUNT));
	}

	@Test
	public void 二つの通貨の箱から指定金額を移動不可能な場合検知できる() {
		// act and assert
		assertThat(sut.isMoveable(srcBox, dstBox, 1661), is(false));
	}

	@Test
	public void 二つの通貨の箱から指定金額を移動可能な場合検知できる() {
		// act and assert
		assertThat(sut.isMoveable(srcBox, dstBox, 1660), is(true));
	}

	@Test
	public void 通貨の箱から指定金額分を両替できる() {
		// arrange
		srcBox.clear();
		srcBox.add(Money._1000);
		dstBox.clear();
		dstBox.addAll(Arrays.asList(new Money[] { _500, _100, _100, _100, _50,
				_50, _50, _10, _10, _10, _10, _10 }));
		assertThat(sut.sumAmount(dstBox) , is(1000));	
		assertThat(dstBox.size() , is(12));
		// act
		boolean actual = sut.isExchangeable(srcBox, dstBox, 660);
		// assert
		assertThat(actual, is(true));
	}

	@Test
	public void 通貨の箱から指定金額分を両替できない場合検知できる() {
		// arrange
		srcBox.clear();
		srcBox.add(Money._1000);
		dstBox.clear();
		dstBox.addAll(Arrays.asList(new Money[] { _500, _100, _100, _100, _50,
				_50, _50, _50}));
		assertThat(sut.sumAmount(dstBox) , is(1000));	
		assertThat(dstBox.size() , is(9));
		// act
		boolean actual = sut.isExchangeable(srcBox, dstBox, 660);
		// assert
		assertThat(actual, is(false));
	}

}