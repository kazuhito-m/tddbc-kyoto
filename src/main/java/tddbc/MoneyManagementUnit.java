package tddbc;

import java.util.ArrayList;
import java.util.List;

/**
 * お金(硬貨・紙幣)周り管理装置クラス。
 * @author kazuhito_m
 */
public class MoneyManagementUnit {

	/** 預かり金プール。 */
	private List<Money> depositPool = new ArrayList<Money>();

	/** 売上ボックス。 */
	private List<Money> incomeBox = new ArrayList<Money>();

	/** つり銭ボックス。 */
	private List<Object> changeBox = new ArrayList<Object>();

	/** 紙幣・硬貨両替機。 */
	private MoneyExchangeUnit exchanger = new MoneyExchangeUnit();

	/**
	 * 総合計額を計算する。
	 */
	public int calcTotalAmount() {
		int total = 0;
		for (Money m : depositPool) {
			total += m.getAmount();
		}
		return total;
	}

	/**
	 * つり銭箱を返す。
	 * @return つり銭箱という名の紙幣・硬貨リスト。
	 */
	public List<Object> getChangeBox() {
		return changeBox;
	}

	/**
	 * (お金などの)投入を受ける。
	 * @param money 投入するモノ。
	 */
	public void receive(Object money) {
		if (money instanceof Money) {
			depositPool.add((Money) money);
		} else {
			changeBox.add(money);
		}
	}

	/**
	 * 返金する。
	 */
	public void refund() {
		changeBox.addAll(depositPool);
		depositPool.clear();
	}

	/**
	 * 指定された金額分を預かり金としてプールしているかを検査。<br>
	 * 「貨幣で取り出せるか」ではなく、あくまで「合計額中に含まれているか」で判定している。
	 * @param amount 指定金額。
	 * @return 判定結果。プールしている。
	 */
	public Boolean isDeposited(int amount) {
		return (exchanger.sumAmount(depositPool) >= amount);
	}
}
