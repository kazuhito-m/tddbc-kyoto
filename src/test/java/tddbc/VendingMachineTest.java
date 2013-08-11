package tddbc;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static tddbc.Money.*;

import java.util.ArrayList;
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
	public void お金以外を入れたらトータル金額に加算されていない() {
		お金以外を入れたらトータル金額に加算されていない(1, 0);
		お金以外を入れたらトータル金額に加算されていない(5, 0);
		お金以外を入れたらトータル金額に加算されていない(5000, 0);
		お金以外を入れたらトータル金額に加算されていない("ドル", 0);
	}

	public void お金以外を入れたらトータル金額に加算されていない(Object money, int expected) {
		setUp();
		sut.receive(money);
		int actual = sut.displayTotalAmount();
		assertThat(actual, is(expected));
	}

	public <E> List<E> _(E... elelements) {
		List<E> ls = new ArrayList<E>();
		for (E e : elelements) {
			ls.add(e);
		}
		return ls;
	}

	@Test
	public void お金以外を入れたらトータル金額につり銭箱にたまる() {
		お金以外を入れたらトータル金額につり銭箱にたまる(_((Object) 5), _((Object) 5));
		お金以外を入れたらトータル金額につり銭箱にたまる(_((Object) 5000), _((Object) 5000));
	}

	public void お金以外を入れたらトータル金額につり銭箱にたまる(List<Object> money,
			List<Object> expectedObject) {
		setUp();
		for (Object o : money) {
			sut.receive(o);
		}
		List<Object> actual = sut.getChangeBox();
		assertThat(actual, is(expectedObject));
	}

	@Test
	public void 有効硬貨を投入できる() {
		sut.receive(_10);
		int actual = sut.displayTotalAmount();
		assertThat(actual, is(10));
	}

	@Test
	public void 有効硬貨を複数回投入した金額が総計に反映されている() {
		sut.receive(_10);
		sut.receive(_50);
		sut.receive(_100);
		sut.receive(_500);
		sut.receive(_1000);
		int actual = sut.displayTotalAmount();
		assertThat(actual, is(1660));
	}

	@Test
	public void 払い戻すと釣り銭箱に入っている() {
		有効硬貨を複数回投入した金額が総計に反映されている();
		sut.refund();
		List<Object> actual = sut.getChangeBox();
		assertThat(actual.size(), is(5));
	}
	
	@Test
	public void 払い戻した後は投入金額の総計がゼロになっている() {
		払い戻すと釣り銭箱に入っている();
		int actual = sut.displayTotalAmount();
		assertThat(actual , is(0));
	}

}
