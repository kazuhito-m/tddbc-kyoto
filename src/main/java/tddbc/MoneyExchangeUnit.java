package tddbc;

import static tddbc.Money.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 紙幣・硬貨両替機クラス。
 * @author kazuhito_m
 */
public class MoneyExchangeUnit {

    private final Log log = LogFactory.getLog(this.getClass());

    /**
     * 指定された金額が移動可能かを判定する。
     * @param srcBox 移動元の箱。
     * @param dstBox 移動先の箱、。
     * @param amount 移動金額。
     * @return 判定。移動可能:true。
     */
    public boolean isMoveable(final List<Money> srcBox,
            final List<Money> dstBox, final int amount) {
        // 実際にシミュレーションする。引数の二つの箱をシャローコピーする。
        List<Money> newSrc = new ArrayList<Money>(srcBox);
        List<Money> newDst = new ArrayList<Money>(dstBox);
        // まず、シミュレーションで可能かを見る。
        return realMoveMoney(newSrc, newDst, amount);
    }

    /**
     * 貨幣の箱から箱へ指定された金額を移動する。<br/>
     * @param srcBox 移動元の箱。
     * @param dstBox 移動先の箱、。
     * @param amount 移動金額。
     * @return 成功判定。移動成功:true。
     */
    public boolean moveMoney(final List<Money> srcBox,
            final List<Money> dstBox, final int amount) {
        // 引数の二つの箱をシャローコピーする。
        List<Money> newSrc = new ArrayList<Money>(srcBox);
        List<Money> newDst = new ArrayList<Money>(dstBox);
        // まず、シミュレーションで可能かを見る。
        boolean result = realMoveMoney(newSrc, newDst, amount);
        if (result) {
            // 処理成功。結果を確定する。
            srcBox.clear();
            dstBox.clear();
            srcBox.addAll(newSrc);
            dstBox.addAll(newDst);
        }
        return result;
    }

    /**
     * 貨幣の箱から箱へ指定された金額を移動する。<br/>
     * realと銘打ってるのは「引数のListを破壊的に変更する」ため。<br/>
     * 破壊されて困る場合は、クローンするなりして退避したものを寄越すこと推奨。
     * @param srcBox 移動元の箱。
     * @param dstBox 移動先の箱、。
     * @param amount 移動金額。
     * @return 貨幣が崩せず移動できなかった残りの金額。
     */
    protected boolean realMoveMoney(final List<Money> srcBox,
            final List<Money> dstBox, final int amount) {

        // 処理前チェック。移動元に移動する分の金額があるか。
        if (sumAmount(srcBox) < amount) {
            log.debug("移動不能。移動金額が移動元に足りない。");
            return false;
        }

        // 「現在の紙幣・硬貨」で移動できるだけ移動する(端数はほっといて
        List<Money> movedMonay = fuzzyRemove(srcBox, amount);
        dstBox.addAll(movedMonay);
        // 移動出来なかった金額
        int restAmount = amount - sumAmount(movedMonay);

        // 貨幣の種類が足らず、移動できなかった場合は0以上。
        if (restAmount != 0) {
            // 移動先からの両替
            log.debug("両替が必要。貨幣不足金額 : " + restAmount);
            // 移動先のお金を借りて両替する。
            if (!exchange(srcBox, dstBox, restAmount)) {
                // 今度こそ両替不能。
                log.debug("移動先からも両替不能。");
                // FIXME 「売上」と「つり銭切れ」の概念を導入する時に消滅させる。
                // 無限に沸く両替機(つり銭切れが無い)から借りて両替
                List<Money> infinityExBox = createInfinityExchangeBox(restAmount);
                exchange(srcBox, infinityExBox, restAmount);
            }
            // 両替成功。余ってる金額も移動。
            List<Money> moveMoney = fuzzyRemove(srcBox, restAmount);
            // １円とか「もう貨幣の無い移動」を強いてきているようであれば…
            if (sumAmount(moveMoney) != restAmount) {
                return false; // エラーとする。
            }
            dstBox.addAll(moveMoney);
        }
        // ここまで来たなら処理に落ち度なし。成功。
        return true;
    }

    /**
     * 貨幣の箱から「曖昧に」指定金額に寄せるように削除する。
     * @param moneyBox 貨幣箱。
     * @return 削除した分をいれた箱。
     */
    protected List<Money> fuzzyRemove(final List<Money> moneyBox,
            final int amount) {
        int restAmount = amount; // 残り金額。
        // 移動元の貨幣箱を昇順ソート。
        Collections.sort(moneyBox);
        // 後ろから回す(降順に要素を取り出す)
        List<Money> removeBox = new ArrayList<>();
        for (int i = moneyBox.size() - 1; i >= 0; i--) {
            Money m = moneyBox.get(i);
            // 移動金額を越えないものなら
            if (restAmount >= m.getAmount()) {
                // その貨幣は移動
                removeBox.add(moneyBox.remove(i));
                // 金額から減額。
                restAmount -= m.getAmount();
            }
        }
        return removeBox;
    }

    /**
     * 通貨の箱を合算。
     * @param moneyBox 対象となる通貨の箱。
     * @return 合算金額。
     */
    public int sumAmount(final List<Money> moneyBox) {
        int totalAmount = 0;
        for (Money m : moneyBox) {
            totalAmount += m.getAmount();
        }
        return totalAmount;
    }

    /**
     * 通貨箱二つから「指定の小銭を含む両替」ができるか否かを検査する。
     * @param srcBox 両替元の箱。
     * @param dstBox 両替先の箱、。
     * @param intentionAmount 「この細かさが出せるように」という目的の金額。
     * @return 判定。両替可能:true。
     */
    public boolean isExchangeable(final List<Money> srcBox,
            final List<Money> dstBox, final int intentionAmount) {
        // お試し用通貨箱。(状態が変わってもよいようにシャローコピー)
        List<Money> srcTest = new ArrayList<Money>(srcBox);
        List<Money> dstTest = new ArrayList<Money>(dstBox);
        // ダミーを使って、実際に両替を行って見た結果をそのまま検査結果として返す。
        return realExchange(srcTest, dstTest, intentionAmount);
    }

    /**
     * 通貨箱二つから「指定の小銭を含む両替」を行う。
     * @param srcBox 両替元の箱。
     * @param dstBox 両替先の箱、。
     * @param intentionAmount 「この細かさが出せるように」という目的の金額。
     * @return 成功判定。両替成功:true。
     */
    public boolean exchange(final List<Money> srcBox, final List<Money> dstBox,
            final int intentionAmount) {
        // まず、シミュレーションを行う。
        if (!isExchangeable(srcBox, dstBox, intentionAmount)) {
            return false;
        }
        // 出来そうなら、実際に両替する。
        return realExchange(srcBox, dstBox, intentionAmount);
    }

    /**
     * 通貨箱二つから「指定の小銭を含む両替」を行う。<br/>
     * ※破壊的メソッド。引数のオブジェクトを破壊しつつ進むため、内部メソッドとする。
     * @param srcBox 両替元の箱。
     * @param dstBox 両替先の箱。
     * @param intentionAmount 「この細かさが出せるように」という目的の金額。
     * @return 成功判定。両替成功:true。
     */
    protected boolean realExchange(final List<Money> srcBox,
            final List<Money> dstBox, final int intentionAmount) {

        // まずは「両替を持ちかける側に、希望の細かさの小銭がある」か。
        if (!isGettable(dstBox, intentionAmount)) {
            // 無いならその時点で「両替不可能」
            log.debug("両替先に " + intentionAmount + " 円の小銭が無いため両替不能。");
            return false;
        }

        // 実際に取り去ってみる
        List<Money> swapBox = new ArrayList<Money>();
        realMoveMoney(dstBox, swapBox, intentionAmount);

        // 「最小公倍数な両替金額」数列を回す
        for (int minExchange : createMinExchangeSeries(intentionAmount)) {
            if (intentionAmount == minExchange) {
                continue; // 同金額なら「そもそも両替が要らない」はず、次へ。
            }
            // まずは、両替元側から取れるか否か
            if (!isGettable(srcBox, minExchange)) {
                continue; // 取れないなら両替不能。
            }
            // 次に、両替先から「残りの金」がジャストで取れるか
            int remaining = minExchange - intentionAmount;
            if (!isGettable(dstBox, remaining)) {
                continue; // こちらも取れないなら両替不能。
            }
            // ここまでこれたなら、両替可能。実際に両替する。
            realMoveMoney(dstBox, swapBox, remaining); // まず、両替先から両替分を移しきって
            realMoveMoney(srcBox, dstBox, minExchange); // 両替元から削除して
            srcBox.addAll(swapBox); // 両替元に移動。
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
    public boolean isGettable(final List<Money> moneyBox,
            final int intentionAmount) {
        // お試し用通貨箱。
        List<Money> hitTest = new ArrayList<Money>(moneyBox); // 状態が変わってもよいようにシャローコピー
        // 端数無(余り0円)で削除できるか否かを真偽値で返す。
        return sumAmount(fuzzyRemove(hitTest, intentionAmount)) == intentionAmount;
    }

    /**
     * 硬貨紙幣ごとの最小両替金額の数列を作成する。
     * @param amount 両替に含んでおきたい金額。
     * @return 数列(List).
     */
    public int[] createMinExchangeSeries(final int amount) {
        int i = 0;
        int[] series = new int[Money.values().length];
        // 紙幣・硬貨が小さなもの順に「最小公倍数な両替金額」を検討していく
        for (Money m : Money.values()) {
            // 必要金額を貨幣の額面で割る。余りを保存。
            final int surplus = amount % m.getAmount(); // 余り
            // 余りが無ければその硬貨・紙幣の倍数でドンピシャ、余るなら余り分とっぱらって１個分上へ。
            int minExAble = amount;
            if (surplus > 0) {
                minExAble += m.getAmount() - surplus;
            }
            // 最小両替金額を算出
            series[i++] = minExAble;
        }
        return series; // 数列を返す。
    }

    /**
     * 金額を指定するとお釣りが無限に沸く両替ボックスを作成する。
     * @param intentionAmount 両替に含んでおきたい金額。
     * @return 作成された貨幣ボックス。
     */
    public List<Money> createInfinityExchangeBox(final int intentionAmount) {
        List<Money> infinityExBox = new ArrayList<Money>(
                Arrays.asList(new Money[] { _500, _100, _100, _100, _100, _50,
                        _10, _10, _10, _10, _10 }));
        // 不足分の算出
        int shortage = intentionAmount - sumAmount(infinityExBox);
        final Money MAX = _1000;
        // 不足があれば、最大貨幣を足りるまで追加する。
        if (shortage > 0) {
            for (int i = 0; i < (shortage / MAX.getAmount() + 1); i++) {
                infinityExBox.add(MAX);
            }
        }
        return infinityExBox;
    }

}