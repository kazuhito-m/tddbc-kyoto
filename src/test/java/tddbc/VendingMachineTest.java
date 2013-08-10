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

	@Before
	public void setUp(){
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
        setUp();
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
        お金以外を入れたらトータル金額につり銭箱にたまる(_((Object)5),_((Object)5));
        お金以外を入れたらトータル金額につり銭箱にたまる(_((Object)5000),_((Object)5000));
	}

    public void お金以外を入れたらトータル金額につり銭箱にたまる(List<Object> money, List<Object> expectedObject) {
        setUp();
        for(Object o : money){
            sut.receive(o);
        }
        List<Object> actual = sut.getChangeBox();
        assertThat(actual, is(expectedObject));
    }

    @Test
    public void 有効硬貨を投入できる(){
        sut.receive(Money._10);
    }

    @Test
    public void 有効硬貨を複数回投入できる(){

    }

    @Test
    public void 入れたお金が総計に反映されている(){

    }

    @Test
    public void 払い戻すと釣り銭箱に入っている(){

    }

}
