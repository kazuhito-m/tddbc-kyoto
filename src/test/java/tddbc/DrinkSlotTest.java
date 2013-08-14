package tddbc;

import static org.hamcrest.CoreMatchers.notNullValue;
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

	@Test
	public void スロットにジュースを一個取り出せる() {
		// arrange
		this.スロットにジュースを補充できる();
		// act
		Drink actual = sut.takeOut();
		// assert
		assertThat(actual, is(notNullValue()));
	}

	@Test
	public void スロットからジュースを取り出すと総数が一つ減る() {
		// arrange
		スロットにジュースを一個取り出せる();
		// assert
		assertThat(sut.getStockCount(), is(0)); // 一個補充→一個減らす、でゼロ
	}

	@Test
	public void スロットから取り出したジュースの名前と価格はスロット設定と同じ() {
		// arrange
		this.スロットにジュースを補充できる();
		// act
		Drink actual = sut.takeOut();
		// assert
		assertThat(actual.getCaption(), is("コーラ"));
		assertThat(actual.getAmountOfTime(), is(120));
	}
	
	@Ignore
	@Test
	public void 在庫がない場合に取り出すとnullが取り出される() {
		
	}


}