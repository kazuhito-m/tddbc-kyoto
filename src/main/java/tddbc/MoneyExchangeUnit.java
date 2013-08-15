package tddbc;

import java.util.List;

/**
 * 紙幣・硬貨両替機クラス。
 * @author kazuhito_m
 */
public class MoneyExchangeUnit {

	public void moveMoney(List<Money> srcBox, List<Money> dstBox, int amount) {
		// TODO 仮実装
		for (int i = 0; i < 3; i++) {
			dstBox.add(srcBox.remove(0));
		}
	}

}
