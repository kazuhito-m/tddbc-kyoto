package tddbc;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

/**
 * 紙幣・硬貨両替機クラス。
 * @author kazuhito_m
 */
public class MoneyExchangeUnitTest {
	/** テスト対象 */
	private MoneyExchangeUnit sut;

	@Before
	public void setUp() {
		sut = new MoneyExchangeUnit();
	}

	@Ignore
	@Test
	public void 二つの通貨の箱から指定金額を移動する() {
		// TODO 未実装
	}

	@Ignore
	@Test
	public void 二つの通貨の箱から指定金額を移動不可能な場合検知できる() {
		// TODO 未実装
	}

	@Ignore
	@Test
	public void 通貨の箱から指定金額分を両替できる() {
		// TODO 未実装
	}

	@Ignore
	@Test
	public void 通貨の箱から指定金額分を両替できない場合検知できる() {
		// TODO 未実装
	}

}
