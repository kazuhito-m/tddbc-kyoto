package tddbc;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 紙幣・硬貨両替機クラス。
 * @author kazuhito_m
 */
public class MoneyExchangeUnit {

	private Log log = LogFactory.getLog(this.getClass());

	/**
	 * 貨幣の箱から箱へ指定された金額を移動する。<br>
	 * @param srcBox 移動元の箱。
	 * @param dstBox 移動先の箱、。
	 * @param amount 移動金額。
	 */
	public void moveMoney(List<Money> srcBox, List<Money> dstBox, int amount) {

		// 引数の二つの箱をシャローコピーする。
		List<Money> newSrc = new ArrayList<Money>(srcBox);
		List<Money> newDst = new ArrayList<Money>(dstBox);

		// 紙幣を移動
		int restAmount = realMoveMoney(newSrc, newDst, amount);

		// すべて移動できていたら、確定する。
		if (restAmount == 0) {
			srcBox.clear();
			dstBox.clear();
			srcBox.addAll(newSrc);
			dstBox.addAll(newDst);
		} else {
			// FIXME 貨幣が足りない対策＝両替対策。
			log.debug("restAmount : " + restAmount);
		}

	}

	/**
	 * 貨幣の箱から箱へ指定された金額を移動する。<br>
	 * realと銘打ってるのは「引数のListを破壊的に変更する」ため。<br>
	 * 破壊されて困る場合は、クローンするなりして退避したものを寄越すこと推奨。
	 * @param srcBox 移動元の箱。
	 * @param dstBox 移動先の箱、。
	 * @param amount 移動金額。
	 * @return 貨幣が崩せず移動できなかった残りの金額。
	 */
	protected int realMoveMoney(List<Money> srcBox, List<Money> dstBox,
			int amount) {
		// 移動元の貨幣箱を昇順ソート。
		Collections.sort(srcBox);
		// 後ろ(つまり紙幣の高いもん順)で回して、「現在の紙幣・硬貨」で移動できるか見ていく
		for (int i = srcBox.size() - 1; i >= 0; i--) {
			Money m = srcBox.get(i);
			// 移動金額を越えないものなら
			if (amount >= m.getAmount()) {
				// その貨幣は移動
				dstBox.add(srcBox.remove(i));
				// 金額から減額。
				amount -= m.getAmount();
			}
		}
		// 残りを返す。
		// 貨幣の種類が足らず、移動できなかった場合は0以上。
		return amount;
	}

	/**
	 * 指定された金額が移動可能かを判定する。
	 * @param srcBox 移動元の箱。
	 * @param dstBox 移動先の箱、。
	 * @param amount 移動金額。
	 * @return 判定。移動可能:true。
	 */
	public Boolean isMoveable(List<Money> srcBox, List<Money> dstBox, int amount) {
		// 移動元に移動する分の金額があるか。
		return (sumAmount(srcBox) >= amount);
	}
	
	/**
	 * 通貨の箱を合算。 
	 * @param moneyBox 対象となる通貨の箱。
	 * @return 合算金額。
	 */
	public int sumAmount(List<Money> moneyBox) {
		int totalAmount = 0;
		for (Money m : moneyBox) {
			totalAmount += m.getAmount();
		}
		return totalAmount;
	}

	public boolean isExchangeable(List<Money> srcBox, List<Money> dstBox, int intentionAmount) {
		// TODO 超絶仮実装。
		return true;
	}

}
