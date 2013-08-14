package tddbc;

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

	@Ignore
	@Test
	public void ジュースを一種類格納している() {
		
		// act
//		int actual = sut.displayTotalAmount();
		// assert
//		assertThat(actual, is(0));
	}
	
	@Ignore
	@Test
	public void 初期状態でコーラを5本格納している() {
		
	}
}
