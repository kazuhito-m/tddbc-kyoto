package tddbc;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static tddbc.Money.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

/**
 * 紙幣・硬貨両替機クラスのテスト。
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
		srcBox.add(_1000);
		dstBox.clear();
		dstBox.addAll(Arrays.asList(new Money[] { _500, _100, _100, _100, _50,
				_50, _50, _10, _10, _10, _10, _10 }));
		assertThat(sut.sumAmount(dstBox), is(1000));
		assertThat(dstBox.size(), is(12));
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
				_50, _50, _50 }));
		assertThat(sut.sumAmount(dstBox), is(1000));
		assertThat(dstBox.size(), is(8));
		// act
		boolean actual = sut.isExchangeable(srcBox, dstBox, 660);
		// assert
		assertThat(actual, is(false));
	}

	@Test
	public void 両替先の箱からは指定金額が取り出せるが両替元にお金が無い場合() {
		// arrange
		srcBox.clear();
		srcBox.addAll(Arrays.asList(new Money[] { _500, _100, _10 }));
		assertThat(sut.sumAmount(srcBox), is(610));

		dstBox.clear();
		dstBox.addAll(Arrays.asList(new Money[] { _100, _50, _10, _10, _10 }));
		assertThat(sut.sumAmount(dstBox), is(180));

		final int THE_AMOUNT = 120; // 120円を取り出せるようにするため両替
		assertThat(sut.isGettable(dstBox, THE_AMOUNT), is(true));

		// assert
		assertThat(sut.isExchangeable(srcBox, dstBox, THE_AMOUNT), is(false));

	}

	@Test
	public void 通貨の箱から指定金額分を実際に両替する() {
		// arrange
		srcBox.clear();
		srcBox.addAll(Arrays.asList(new Money[] { _1000, _1000 }));
		int srcAmount = sut.sumAmount(srcBox);
		dstBox.clear();
		dstBox.addAll(Arrays.asList(new Money[] { _500, _100, _100, _100, _50,
				_50, _50, _10, _10, _10, _10, _10, _50 }));
		int dstAmount = sut.sumAmount(dstBox);

		// act
		boolean actual = sut.exchange(srcBox, dstBox, 660);

		// assert
		assertThat(actual, is(true));

		// src側
		List<Money> resultList = Arrays.asList(new Money[] { _1000, _500, _100,
				_50, _10, _100, _100, _50, _50, _10, _10, _10, _10 });
		assertThat(srcBox, is(resultList));
		assertThat(sut.sumAmount(srcBox), is(srcAmount));

		// dst側
		resultList = Arrays.asList(new Money[] { _50, _1000 });
		assertThat(dstBox, is(resultList));
		assertThat(sut.sumAmount(dstBox), is(dstAmount));
	}

	@Test
	public void 指定された金額が現在の通貨箱から取得可能であることを検知できる() {
		// act
		assertThat(sut.isGettable(srcBox, 560), is(true));
	}

	@Test
	public void 指定された金額が現在の通貨箱から取得不可能であることを検知できる() {
		// act
		assertThat(sut.isGettable(srcBox, 540), is(false));
	}

	@Test
	public void 通貨箱同士で両替を含むお金が移動ができる() {
		// arrange
		srcBox.clear();
		srcBox.addAll(Arrays.asList(new Money[] { _100, _100 }));
		assertThat(sut.sumAmount(srcBox), is(200));

		dstBox.clear();
		dstBox.addAll(Arrays.asList(new Money[] { _100, _50, _10, _10, _10,
				_10, _10 }));
		assertThat(sut.sumAmount(dstBox), is(200));

		// act
		boolean actual = sut.moveMoney(srcBox, dstBox, 80);

		// assert
		assertThat(actual, is(true));

		List<Money> resultList = Arrays.asList(new Money[] { _10, _10, _100 });
		assertThat(srcBox, is(resultList));
		assertThat(sut.sumAmount(srcBox), is(120));

		resultList = Arrays
				.asList(new Money[] { _100, _100, _50, _10, _10, _10 });
		assertThat(sut.sumAmount(dstBox), is(280));
		assertThat(dstBox, is(resultList));

	}

	@Test
	public void 金額を指定するとお釣りが無限に沸く両替ボックスを取得する() {
		// TODO 仕様的にグレー。売上金の概念が入れば削除予定。
		// act
		List<Money> actual = sut.createInfinityExchangeBox(0);
		// assert
		assertThat(actual, is(notNullValue()));
		assertThat(sut.sumAmount(actual), is(1000));
		assertThat(actual.size(), is(11));
	}

	@Test
	public void 無限沸き両替ボックスの師弟金額が1000を越える場合両替可能な金額が追加される() {
		// TODO 仕様的にグレー。売上金の概念が入れば削除予定。
		// act
		List<Money> actual = sut.createInfinityExchangeBox(2001);
		// assert
		assertThat(actual, is(notNullValue()));
		assertThat(sut.sumAmount(actual), is(3000));
		assertThat(actual.size(), is(13));
	}

	@Test
	public void 通貨箱同士で両替不可能な移動の場合も小銭は無限に沸く() {
		// FIXME 「売上」と「つり銭切れ」の概念を導入する時に消滅させる。
		// arrange
		srcBox.clear();
		srcBox.addAll(Arrays.asList(new Money[] { _100, _100 }));
		assertThat(sut.sumAmount(srcBox), is(200));

		dstBox.clear();
		dstBox.addAll(Arrays.asList(new Money[] { _100, _100 }));	// 両替不能
		assertThat(sut.sumAmount(dstBox), is(200));

		// act
		boolean actual = sut.moveMoney(srcBox, dstBox, 80);

		// assert
		assertThat(actual, is(true));

		List<Money> resultList = Arrays.asList(new Money[] { _10, _10, _100 });
		assertThat(srcBox, is(resultList));
		assertThat(sut.sumAmount(srcBox), is(120));

		resultList = Arrays
				.asList(new Money[] { _100, _100, _50, _10, _10, _10 });
		assertThat(sut.sumAmount(dstBox), is(280));
		assertThat(dstBox, is(resultList));
	}

	@Test
	public void 不可能な移動があっても検知でき対象に影響は無い() {
		// arrange
		List<Money> baseSrc = new ArrayList<Money>(srcBox);
		List<Money> baseDst = new ArrayList<Money>(dstBox);
		// act
		boolean actual = sut.moveMoney(srcBox, dstBox, 1670);
		// assert
		assertThat(actual, is(false));
		assertThat(srcBox, is(baseSrc));
		assertThat(dstBox, is(baseDst));
	}
	
	@Test
	public void 硬貨紙幣ごとの最小両替金額の数列を作成出来る() {
		// act
		int[] actual = sut.createMinExchangeSeries(1);
		// assert
		assertThat(actual, is(new int[] { 10, 50, 100, 500, 1000 }));
	}

	@Test
	public void 最小両替金額数列の真ん中あたりの値の場合() {
		// act
		int[] actual = sut.createMinExchangeSeries(1150);
		// assert
		assertThat(actual, is(new int[] { 1150, 1150, 1200, 1500, 2000 }));
	}

}