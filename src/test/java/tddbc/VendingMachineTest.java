package tddbc;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static tddbc.Money._10;
import static tddbc.Money._50;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

public class VendingMachineTest {

	/** テスト対象 */
	private VendingMachine sut;

	@Before
	public void setUp() {
		sut = new VendingMachine();
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

}
