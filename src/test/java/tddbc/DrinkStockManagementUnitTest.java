package tddbc;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;

import static tddbc.DrinkKind.*;

/**
 * 「飲み物の在庫」管理装置クラステスト。
 * @author kazuhito＿m
 */
@RunWith(Enclosed.class)
public class DrinkStockManagementUnitTest {

	public static class 初期状態から {
		/** テスト対象 */
		private DrinkStockManagementUnit sut;

		@Before
		public void setUp() {
			sut = new DrinkStockManagementUnit();
		}

		@Test
		public void ジュースを一種類格納している() {
			// act
			List<DrinkSlot> actual = sut.getSlots();
			// assert
			assertThat(actual.size(), is(1));
		}

		@Test
		public void 初期状態で価格120円のコーラを5本格納している() {
			// act
			List<DrinkSlot> slots = sut.getSlots();
			// assert
			DrinkSlot actual = slots.get(0);
			assertThat(actual.getKind().getCaption(), is("コーラ"));
			assertThat(actual.getPrice(), is(120));
			assertThat(actual.getStockCount(), is(5));
		}

		@Test
		public void 指定した種類の在庫があるかを確認できる() {
			// act
			boolean actual = sut.existStock(COLA);
			// assert
			assertThat(actual, is(true));
		}

		@Test
		public void 指定した種類の在庫を一つ取り出す() {
			// act
			Drink actual = sut.takeOut(COLA);
			// assert
			assertThat(actual, is(notNullValue()));
			assertThat(actual.getCaption(), is(COLA.getCaption()));
		}

		@Test
		public void 指定した種類の在庫を取り出した後一つ減っている() {
			// act
			sut.takeOut(COLA);
			// assert
			DrinkSlot slot = sut.getSlots().get(0);
			assertThat(slot.getStockCount(), is(4));
		}

		@Test
		public void 別種類の飲み物を指定しても取得出来ない() {
			// act
			Drink actual = sut.takeOut(APPLE_JUICE);
			// assert
			assertThat(actual, is(nullValue()));
		}
	}

	public static class 在庫を空まで減らした状態 {
		/** テスト対象 */
		private DrinkStockManagementUnit sut;

		@Before
		public void setUp() {
			sut = new DrinkStockManagementUnit();
			// 要らんくらい、絶対なくなるくらい取り出す。
			while (true) {
				if (sut.takeOut(COLA) == null) {
					break;
				}
			}
		}

		@Test
		public void すべての在庫を取り出し切り在庫がないことを確認する() {
			// act
			boolean actual = sut.existStock(COLA);
			// assert
			assertThat(actual, is(false));
		}

		@Test
		public void 在庫が一つも無い状態で取り出すとないことが検知出来て在庫状態も変わらない() {
			// act
			Drink actual = sut.takeOut(COLA);
			// assert
			assertThat(actual, is(nullValue()));
			assertThat(sut.existStock(COLA), is(false));
		}
	}
}