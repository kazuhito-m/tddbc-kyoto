package tddbc;

/**
 * 貨幣(紙幣・硬貨)定数群。
 */
public enum Money {
    // 順番が重要。絶えず「昇順」となるよう書く必要あり。
    _10, _50, _100, _500, _1000;
    /** 定数の表す金額を整数値で返す。 */
    public int getAmount() {
        return Integer.parseInt(this.name().replaceFirst("_", ""));
    }

}
