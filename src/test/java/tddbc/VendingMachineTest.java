package tddbc;

import static org.hamcrest.core.Is.*;
import static org.junit.Assert.*;

import java.util.List;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

public class VendingMachineTest {

	/** テスト対象 */
	private VendingMachine sut;

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		sut = new VendingMachine();
	}

	@Test
	public void トータル金額を確認できる() {
		// arrange
		// act
		int actual = sut.displayTotalAmount();
		// assert
		assertThat(actual, is(0));
	}

	@Test
	public void つり銭箱が確認できる() {
		// arrange
		// act
		List<Integer> actual = sut.getChangeBox();
		// assert
		assertThat(actual.size() , is(0));
	}

	@Test
	public void お金以外を入れたらトータル金額に加算されていない() {
        sut.receive(5);
        int actual = sut.displayTotalAmount();
        assertThat(actual, is(0));
	}

	@Ignore
	@Test
	public void お金以外を入れたらトータル金額につり銭箱にたまる() {
        sut.receive(5);
        List<Integer> actual = sut.getChangeBox();
        assertThat(actual.size(), is(1));
	}

}
