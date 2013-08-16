package tddbc;

/**
 * 「飲み物の種類」定数群。
 * @author kazuhito_m
 */
public enum DrinkKind {
    COLA("コーラ"), APPLE_JUICE("アップルジュース"), REDBULL("レッドブル"), WATER("水");

    /** 内部「飲み物の名前」 */
    private final String caption;

    /** コンストラクタ。 */
    DrinkKind(final String caption) {
        this.caption = caption;
    }

    /**
     * 「ジュースの名前」を返す。
     * @return ジュースの名前文字列。
     */
    public String getCaption() {
        return this.caption;
    }
}
