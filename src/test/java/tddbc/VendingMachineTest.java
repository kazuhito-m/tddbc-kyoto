package tddbc;

import static org.hamcrest.core.Is.*;
import static org.junit.Assert.*;

import java.util.ArrayList;
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
		List<Object> actual = sut.getChangeBox();
		// assert
		assertThat(actual.size() , is(0));
	}

	@Test
	public void お金以外を入れたらトータル金額に加算されていない() {
        お金以外を入れたらトータル金額に加算されていない(1, 0);
        お金以外を入れたらトータル金額に加算されていない(5, 0);
        お金以外を入れたらトータル金額に加算されていない(5000, 0);
        お金以外を入れたらトータル金額に加算されていない("ドル", 0);
	}

    public void お金以外を入れたらトータル金額に加算されていない(Object money, int expected){
        sut.receive(money);
        int actual = sut.displayTotalAmount();
        assertThat(actual, is(expected));
    }

    public <E> List<E>_(E... elelements){
        List<E> ls = new ArrayList<E>();
        for(E e : elelements){
            ls.add(e);
        }
        return ls;
    }

	@Test
	public void お金以外を入れたらトータル金額につり銭箱にたまる() {
        sut.receive(5);
        List<Object> actual = sut.getChangeBox();
        List<Object> expected = _((Object)5);
        assertThat(actual, is(expected));
	}

    public void お金以外を入れたらトータル金額につり銭箱にたまる(Object money, int expectedSize, List<Object> expectedObject) {
        sut.receive();
        List<Object> actual = sut.getChangeBox();
        List<Object> expected = _((Object)5);
        assertThat(actual.size(), is(1));
        assertThat(actual, is(expected));
    }


}
