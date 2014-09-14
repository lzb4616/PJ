package com.pj.handler;

import com.pj.regulation.TZRegulation;
import com.pj.source.TzStatusDatas;

public class TZComparator {

	private static TZComparator tzcomparator;
	static {
		tzcomparator = new TZComparator();
	}

	private TZComparator() {

	}

	public static TZComparator getInstance() {
		return tzcomparator;
	}

	/**
	 * 比较两组牌的大小
	 * 
	 * @param c1
	 * @param c2
	 * @param r
	 * @return: 若c1大于c2则返回1，若等于则返回0，否则返回-1
	 */
	public int tzcompare(int[] e1, int[] e2, TZRegulation r) {
		TzStatusDatas sd = TzStatusDatas.getInstance();
		int w1 = r.getWeight(sd.getCardByInputNum(e1[0]),
				sd.getCardByInputNum(e1[1]));
		int w2 = r.getWeight(sd.getCardByInputNum(e2[0]),
				sd.getCardByInputNum(e2[1]));
		int result = 0;
	    if (w1 == w2) {
	    	return 0;
		} 
	    else if(w1 > w2){
			result = 1;
		}else {
			result = -1;
		}
		return result;
	}
}
