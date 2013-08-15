package tddbc;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

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

	/** 通貨の箱を合算。 */
	private int sumAmount(List<Money> moneyBox) {
		int totalAmount = 0;
		for (Money m : moneyBox) {
			totalAmount += m.getAmount();
		}
		return totalAmount;
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

	@Ignore
	@Test
	public void 通貨の箱から指定金額分を両替できる() {
		// TODO 未実装
	}

	@Ignore
	@Test
	public void 通貨の箱から指定金額分を両替できない場合検知できる() {
		// TODO 未実装
	}

}