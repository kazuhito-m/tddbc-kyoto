package tddbc;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static tddbc.DrinkKind.*;
import static tddbc.Money._10;
import static tddbc.Money._100;
import static tddbc.Money._50;
import static tddbc.Money._500;

import java.util.List;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

public class VendingMachineTest {

	/** テスト対象 */
	private VendingMachine sut;

	@Before
	public void setUp() {
		sut = new VendingMachine();
	}

	/** ヘルパーメソッド「在庫を吐き出させる」。 */
	private void clearStock() {
		DrinkStockManagementUnit dsm = sut.getDrinkStockManager();
		while (dsm.existStock(COLA)) {
			dsm.takeOut(COLA);
		}
		// 在庫はカラっぽに。
		assertThat(dsm.existStock(COLA), is(false));
	}

	@Test
	public void トータル金額を確認できる() {
		// act
		int actual = sut.displayTotalAmount();
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

	// TODO 少し保留。
	// @Ignore
	// @Test
	// public void パスコードを指定しなくては外部から金額系は制御出来ない() {
	// // TODO
	// }
	//
	// @Ignore
	// @Test
	// public void パスコードを指定しなくては外部から在庫系は制御出来ない() {
	// // TODO
	// }

	@Test
	public void 有効硬貨を複数回投入した金額が総計に反映されている() {
		sut.receive(_10);
		sut.receive(_50);
		int actual = sut.displayTotalAmount();
		assertThat(actual, is(60));
	}

	@Test
	public void 払い戻すと釣り銭箱に入っている() {
		有効硬貨を複数回投入した金額が総計に反映されている();
		sut.refund();
		List<Object> actual = sut.getChangeBox();
		assertThat(actual.size(), is(2));
	}

	@Test
	public void 投入硬貨と在庫でコーラが購入可能かを検知できる() {
		// arrange
		sut.receive(_100);
		sut.receive(_50);
		// act
		boolean actual = sut.isSellable(COLA);
		// assert
		assertThat(actual, is(true));
	}

	@Test
	public void 金額が足らずコーラが買えないことを検知() {
		// arrange
		sut.receive(_100);
		sut.receive(_10);
		// act
		boolean actual = sut.isSellable(COLA);
		// assert
		assertThat(actual, is(false));
	}

	@Test
	public void 在庫が無くコーラが買えないことを検知() {
		// arrange
		// 特殊な操作。缶をスロットからしこたま抜く。
		clearStock();
		sut.receive(_100);
		sut.receive(_10);
		sut.receive(_10);
		// act
		boolean actual = sut.isSellable(COLA);
		// assert
		assertThat(actual, is(false));
	}

	@Test
	public void 扱えない種類の飲み物も買えないと検知() {
		// arrange
		sut.receive(_100);
		sut.receive(_10);
		sut.receive(_10);
		// act
		boolean actual = sut.isSellable(APPLE_JUICE);
		// assert
		assertThat(actual, is(false));
	}

	@Test
	public void 金額と在庫ともに足りた状態で購入操作ができる() {
		// arrange
		sut.receive(_500);
		// act
		boolean actual = sut.sale(COLA);
		// assert
		assertThat(actual, is(true));
		assertThat(sut.getOutTray().size(), is(1));
		Drink actualDrink = sut.getOutTray().get(0);
		assertThat(actualDrink.getCaption(), is("コーラ"));
	}

	@Test
	public void 購入操作成功後は在庫が減っている() {
		// arrange
		sut.receive(_500);
		// act
		sut.sale(COLA);
		// assert
		DrinkSlot slot = sut.getDrinkStockManager().getSlots().get(0);
		assertThat(slot.getStockCount(), is(4));
	}

	@Test
	public void 購入操作成功後は売上金額が増えている() {
		// arrange
		sut.receive(_500);
		// act
		sut.sale(COLA);
		// assert
		assertThat(sut.getMoneyManager().calcTotalIncome(), is(120));
	}

	@Test
	public void 購入操作成功後は投入金額が減っている() {
		// arrange
		sut.receive(_500);
		// act
		sut.sale(COLA);
		// assert
		assertThat(sut.displayTotalAmount(), is(380));
	}

	@Test
	public void 投入金額が足りない場合は購入操作しても後何も起きない() {
		// arrange
		sut.receive(_100);
		sut.receive(_10);
		// act
		boolean actual = sut.sale(COLA);
		// assert
		// ※何も起きていない…という証明は難しい
		assertThat(actual, is(false));
		assertThat(sut.getOutTray().isEmpty(), is(true));
		assertThat(sut.displayTotalAmount(), is(110));
		assertThat(sut.getMoneyManager().calcTotalIncome(), is(0));
	}

	@Test
	public void 在庫が無い場合は購入操作しても後何も起きない() {
		// arrange
		// 特殊な操作。缶をスロットからしこたま抜く。
		clearStock();
		sut.receive(_100);
		sut.receive(_10);
		sut.receive(_10); // 金額は足りている。
		// act
		boolean actual = sut.sale(COLA);
		// assert
		// ※何も起きていない…という証明は難しい
		assertThat(actual, is(false));
		assertThat(sut.getOutTray().isEmpty(), is(true));
		assertThat(sut.displayTotalAmount(), is(120));
		assertThat(sut.getMoneyManager().calcTotalIncome(), is(0));
	}

	@Test
	public void 幾度かの購入操作後に見合った売上金額が取得できる() {
		// arrange
		sut.receive(_500);
		// act
		sut.sale(COLA);
		sut.sale(COLA);
		sut.sale(COLA);
		// assert
		assertThat(sut.getMoneyManager().calcTotalIncome(), is(360));
	}

	@Test
	public void 購入操作後に払い戻し操作で差し引かれたお金がつり銭箱に出る() {
		// arrange
		sut.receive(_500);
		sut.sale(COLA);
		sut.sale(COLA);
		// act
		sut.refund();
		// assert
		assertThat(sut.getChangeBox().isEmpty(), is(false));
		// つり銭箱の合計計算。
		int actual = 0;
		for (Object o : sut.getChangeBox()) {
			actual += ((Money) o).getAmount();
		}
		assertThat(actual, is(260));
	}
}
