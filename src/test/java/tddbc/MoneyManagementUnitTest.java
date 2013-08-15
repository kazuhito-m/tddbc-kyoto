package tddbc;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static tddbc.Money._10;

import java.util.List;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.experimental.theories.DataPoints;
import org.junit.experimental.theories.Theories;
import org.junit.experimental.theories.Theory;
import org.junit.runner.RunWith;

/**
 * お金(硬貨・紙幣)周り管理装置クラス のテストクラス。
 * @author kazuhito_m
 */
@RunWith(Enclosed.class)
public class MoneyManagementUnitTest {

	public static class 初期状態から {

		/** テスト対象 */
		private MoneyManagementUnit sut;

		@Before
		public void setUp() {
			sut = new MoneyManagementUnit();
		}

		@Test
		public void トータル金額を確認できる() {
			// act
			int actual = sut.calcTotalAmount();
			// assert
			assertThat(actual, is(0));
		}

		@Test
		public void つり銭箱が確認できる() {
			// act
			List<Object> actual = sut.getChangeBox();
			// assert
			assertThat(actual.size(), is(0));
		}

		@Test
		public void 有効硬貨を投入できる() {
			sut.receive(_10);
			int actual = sut.calcTotalAmount();
			assertThat(actual, is(10));
		}

	}

	public static class 有効硬貨が入った状態 {

		/** テスト対象 */
		private MoneyManagementUnit sut;

		@Before
		public void setUp() {
			sut = new MoneyManagementUnit();
			// arrange
			for (Money validMoney : Money.values()) {
				sut.receive(validMoney);
			}
		}

		@Test
		public void 有効硬貨を複数回投入した金額が総計に反映されている() {
			// act
			int actual = sut.calcTotalAmount();
			// assert
			assertThat(actual, is(1660));
		}

		@Test
		public void 払い戻すと釣り銭箱に入っている() {
			// act
			sut.refund();
			// assert
			assertThat(sut.getChangeBox().size(), is(5));
		}

		@Test
		public void 払い戻した後は投入金額の総計がゼロになっている() {
			// act
			sut.refund();
			// assert
			assertThat(sut.calcTotalAmount(), is(0));
		}
		
		@Ignore
		@Test
		public void 指定の金額が預かり金プールにあるかを確認できる() {
			// TODO
		}
		
		@Ignore
		@Test
		public void 指定の金額が預かり金プールに無いかを確認できる() {
			// TODO
		}

		@Ignore
		@Test
		public void 指定の金額分を預かり金プールから売上ボックスへ計上する() {
			
		}
		
		@Ignore
		@Test
		public void 預かり金プールから売上ボックスへ計上できないことを検知できる() {
			
		}

	}

	@RunWith(Theories.class)
	public static class 硬貨紙幣以外のパラメタライズテスト {

		/** テスト対象 */
		private MoneyManagementUnit sut;

		@Before
		public void setUp() {
			sut = new MoneyManagementUnit();
		}

		@DataPoints
		public static Object[][] V = { { 1, 0 }, { 5, 0 }, { 5000, 0 },
				{ "＄", 0 } };

		@Theory
		public void お金以外を入れたらトータル金額に加算されない(Object[] values) {
			// arrange
			sut.receive(values[0]);
			int expected = (Integer) values[1];
			// act
			int actual = sut.calcTotalAmount();
			// assert
			assertThat(actual, is(expected));
		}

		@Theory
		public void お金以外を投入するとつり銭箱へ吐き出す(Object[] values) {
			// arrange
			sut.receive(values[0]);
			// act
			List<Object> actual = sut.getChangeBox();
			// assert
			assertThat(actual.size(), is(1));
			assertThat(actual.get(0), is(values[0]));
		}

	}

}
