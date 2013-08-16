package tddbc;

import static tddbc.DrinkKind.*;

import java.util.ArrayList;
import java.util.List;

/**
 * 「飲み物の在庫」管理装置
 * @author kazuhito＿m
 */
public class DrinkStockManagementUnit {

    /** 飲み物のスロット群。 */
    private List<DrinkSlot> slots = new ArrayList<DrinkSlot>();

    /** コンストラクタ。 */
    public DrinkStockManagementUnit() {
        // イニシャライズする候補の飲み物種類と価格
        DrinkKind dks[] = { COLA, REDBULL, WATER };
        int prs[] = { 120, 200, 100 };

        // 現仕様では、決め打ちで3スロット、中身も限定。
        for (int i = 0; i < dks.length; i++) {
            DrinkSlot slot = new DrinkSlot();
            slot.setKind(dks[i]);
            slot.setPrice(prs[i]);
            for (int j = 0; j < 5; j++) {
                slot.add(new Drink(slot.getKind()));
            }
            slots.add(slot);
        }
    }

    /**
     * スロット群を取得。<br>
     * 「内部状態」であるスロットを取得するのは、<br>
     * メンテナンスできるオブジェクトだけにしたいので、<br>
     * パッケージ同一のクラスのみアクセス可能に。
     * @return スロットのリスト。
     */
    protected List<DrinkSlot> getSlots() {
        return slots;
    }

    /**
     * 指定した飲み物種類で在庫があるかを検査。
     * @param kind 飲み物種類。
     * @return 判定結果。在庫あり:true
     */
    public boolean existStock(final DrinkKind kind) {
        for (DrinkSlot slot : slots) {
            if (slot.getKind() == kind && slot.getStockCount() > 0) {
                return true;
            }
        }
        return false;
    }

    /**
     * 指定した飲み物種類の在庫を一つ取り出す。<br>
     * 一度実効されるたび、その分の在庫が先入れ先出し法にて取り除かれます。
     * @param kind 飲み物種類。
     * @return 取り出した飲み物。在庫が無い場合:null。
     */
    public Drink takeOut(final DrinkKind kind) {
        for (DrinkSlot slot : slots) {
            if (slot.getKind() == kind) {
                return slot.takeOut();
            }
        }
        return null;
    }

    /**
     * 指定された飲み物種の「在庫管理中の価格」を返す。<br>
     * 見つからない場合、不明とし-1を返す。
     * @param kind 飲み物種類。
     * @return 価格。見つからない(管理対象外の飲み物)なら-1を返す。
     */
    public int getPrice(final DrinkKind kind) {
        // 価格情報はスロットに保持している。
        for (DrinkSlot slot : slots) {
            if (slot.getKind() == kind) {
                // 最初のスロットで見つかったものを正する。
                return slot.getPrice();
            }
        }
        return -1; // 見つからなければ、不明(-1)を返す。
    }

    /**
     * この自販機(スロット群)で取り扱っている飲み物種のリストを返す。
     * @return　有効飲み物種のリスト。
     */
    public List<DrinkKind> getValiedDrinks() {
        List<DrinkKind> kinds = new ArrayList<DrinkKind>();
        for (DrinkSlot slot : slots) {
            kinds.add(slot.getKind());
        }
        return kinds;
    }
}