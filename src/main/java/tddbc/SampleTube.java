package tddbc;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * とりあえず、本日使うであろうサンプルを列挙。
 * 
 * @author kazuhit_m
 */
public class SampleTube {

	/** ロガー */
	private Log log = LogFactory.getLog(this.getClass());

	public static void main(String[] args) {
		SampleTube ins = new SampleTube();
		ins.outSampleMessage(args);
	}

	/**
	 * 引数全部空白でつなげて、コンソールにプリントする。
	 * 
	 * @param words 単語配列。
	 * @return 成功判定。成功:true。
	 */
	public boolean outSampleMessage(String[] words) {
		StringBuilder sb = new StringBuilder();
		for (String s : words) {
			sb.append(s);
			sb.append(" ");
		}
		log.info(sb.toString());
		return true;
	}
}
