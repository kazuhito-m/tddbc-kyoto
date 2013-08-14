package tddbc;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

/**
 * 「飲み物の内部スロット」クラス のテスト。
 * @author kazuhito_m
 */
public class DrinkSlotTest {

	/** テスト対象 */
	private DrinkSlot sut = new DrinkSlot();

	@Before
	public void setUp() {
		sut = new DrinkSlot();
	}

	@Test
	public void スロットにはジュース種と価格が保持できる() {
		// arrange / act
		sut.setKind(DrinkKind.COLA);
		sut.setPrice(120);
		// assert
		assertThat(sut.getKind(), is(DrinkKind.COLA));
		assertThat(sut.getPrice(), is(120));
	}
	
	@Test
	public void スロットにジュースを補充できる() {
		// act
		sut.add(new Drink(DrinkKind.COLA));
	}
	
	@Test
	public void スロット中のジュースの個数を確認できる() {
		// arrange
		this.スロットにジュースを補充できる();
		// assert
		assertThat(sut.getStockCount() , is(1));
	}

	@Ignore
	@Test
	public void スロットにジュースを一個取り出せる() {
		
	}

	@Ignore
	@Test
	public void スロットからジュースを取り出すと総数が一つ減る() {
	
	}

	@Ignore
	@Test
	public void スロットから取り出したジュースの名前と価格はスロット設定と同じ() {
		
	}


}