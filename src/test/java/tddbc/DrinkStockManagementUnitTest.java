package tddbc;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.List;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

/**
 * 「飲み物の在庫」管理装置クラステスト。
 * @author kazuhito＿m
 */
public class DrinkStockManagementUnitTest {
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
}
