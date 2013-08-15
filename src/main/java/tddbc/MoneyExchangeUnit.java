package tddbc;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import static java.util.Collections.*;

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
			log.debug("Need exchange. restAmount : " + restAmount);
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

	/**
	 * 通貨箱二つから「指定の小銭を含む両替」ができるか田舎を検査する。
	 * @param srcBox 両替元の箱。
	 * @param dstBox 両替先の箱、。
	 * @param intentionAmount 「この細かさが出せるように」という目的の金額。
	 * @return 判定。両替可能:true。
	 */
	public boolean isExchangeable(List<Money> srcBox, List<Money> dstBox,
			int intentionAmount) {
		// FIXME ここから「非破壊のお試し実装」最後は消すように。

		// お試し用通貨箱。(状態が変わってもよいようにシャローコピー)
		List<Money> srcTest = new ArrayList<Money>(srcBox); 
		List<Money> dstTest = new ArrayList<Money>(dstBox);

		// まずは「両替を持ちかける側に、希望の細かさの小銭がある」か。
		if (!isGettable(dstTest, intentionAmount)) {
			// 無いならその時点で「両替不可能」
			log.debug("両替先に " + intentionAmount + " 円の小銭が無いため両替不能。");
			return false;
		}

		// 実際に取り去ってみる
		List<Money> swapBox = new ArrayList<Money>();
		realMoveMoney(dstTest, swapBox, intentionAmount);

		// 「最小公倍数な両替金額」数列を回す
		for (int minExchange : createMinExchangeSeries(intentionAmount)) {
			if (intentionAmount == minExchange) {
				continue; // 同金額なら「そもそも両替が要らない」はず、次へ。
			}
			// まずは、両替元側から取れるか否か
			if (! isGettable(srcTest, minExchange)) {
				continue;	// 取れないなら両替不能。
			}
			// 次に、両替先から「残りの金」がジャストで取れるか
			int remaining = minExchange - intentionAmount;
			if (!isGettable(dstTest, remaining)) {
				continue;	// こちらも取れないなら両替不能。
			}
			// ここまでこれたなら、両替可能。実際に両替する。
			realMoveMoney(dstTest, swapBox, remaining);	// まず、両替先から両替分を移しきって
			realMoveMoney(srcTest, new ArrayList<Money>() , minExchange);	// 両替元から削除して
			srcTest.addAll(swapBox);	// 両替元に移動。
			// 成功
			return true;
		}
		
		// ここに来たということは、両替出来なかったということ。失敗返す。
		return false;
	}

	/**
	 * 通貨の箱から「指定された金額」を余り無く取得できるか否かを検査する。
	 * @param moneyBox 通貨の箱。
	 * @param intentionAmount 指定金額。
	 * @return 取得できるか否か。取得可能:true
	 */
	public boolean isGettable(List<Money> moneyBox, int intentionAmount) {
		// お試し用通貨箱。
		List<Money> hitTest = new ArrayList<Money>(moneyBox); // 状態が変わってもよいようにシャローコピー
		List<Money> dummy = new ArrayList<Money>();
		// 端数無(余り0円)で移動できるか否かを真偽値で返す。
		return (realMoveMoney(hitTest, dummy, intentionAmount) == 0);
	}

	public int[] createMinExchangeSeries(int amount) {
		int i = 0;
		int[] series = new int[Money.values().length];
		// 紙幣・硬貨が小さなもの順に「最小公倍数な両替金額」を検討していく
		for (Money m : Money.values()) {
			// その通貨で何枚必要かを割り算
			int div = amount / m.getAmount();
			if (amount != (m.getAmount() * div)) {
				div++;
			}
			// 最小両替金額を算出
			series[i++] = m.getAmount() * div;
		}
		return series; // 数列を返す。
	}

	public boolean exchange(List<Money> srcBox, List<Money> dstBox, int i) {
		// TODO 自動生成されたメソッド・スタブ
		return false;
	}

}