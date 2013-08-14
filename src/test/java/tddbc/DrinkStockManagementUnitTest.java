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
	
	@Ignore
	@Test
	public void 初期状態でコーラを5本格納している() {
		
	}
}
