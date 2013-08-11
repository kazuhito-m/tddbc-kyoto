package tddbc;

/**
 * Created with IntelliJ IDEA. User: kyon_mm Date: 2013/08/10 Time: 14:28 To
 * change this template use File | Settings | File Templates.
 */
public enum Money {
	_10, _50, _100, _500, _1000;
	/** 定数の表す金額を整数値で返す。 */
	public int getAmount() {
		return Integer.parseInt(this.name().replaceFirst("_", ""));
	}
}
