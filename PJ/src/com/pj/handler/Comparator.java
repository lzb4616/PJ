package com.pj.handler;

import com.pj.regulation.Regulation;
import com.pj.source.StatusDatas;

public class Comparator {

	private static Comparator comparator;
	static {
		comparator = new Comparator();
	}

	private Comparator() {

	}

	public static Comparator getInstance() {
		return comparator;
	}

	/**
	 * 比较两组牌的大小
	 * 
	 * @param c1
	 * @param c2
	 * @param r
	 * @return: 若c1大于c2则返回1，若等于则返回0，否则返回-1
	 */
	public int compare(int[] e1, int[] e2, Regulation r) {
		StatusDatas sd = StatusDatas.getInstance();
		int w1 = r.getWeight(sd.getWeightByInputNum(e1[0]),
				sd.getWeightByInputNum(e1[1]));
		int w2 = r.getWeight(sd.getWeightByInputNum(e2[0]),
				sd.getWeightByInputNum(e2[1]));
		int result = 0;
		if (w1 == w2) {
			int[] c1 = new int[] { sd.getLevelByInputNum(e1[0]),
					sd.getLevelByInputNum(e1[1]) };
			int[] c2 = new int[] { sd.getLevelByInputNum(e2[0]),
					sd.getLevelByInputNum(e2[0]) };
			if (w1 == -1) {
				if ((sd.getCardByInputNum(e1[0]).getDotNum() + sd
						.getCardByInputNum(e1[1]).getDotNum()) % 10 > (sd
						.getCardByInputNum(e2[0]).getDotNum() + sd
						.getCardByInputNum(e2[1]).getDotNum()) % 10)
					result = 1;
				else if ((sd.getCardByInputNum(e1[0]).getDotNum() + sd
						.getCardByInputNum(e1[1]).getDotNum()) % 10 < (sd
						.getCardByInputNum(e2[0]).getDotNum() + sd
						.getCardByInputNum(e2[1]).getDotNum()) % 10)
					result = -1;
				else {
					if ((c1[0] > c2[0] && c1[0] > c2[1]) || c1[1] > c2[0]
							&& c1[1] > c2[1])
						result = 1;
					else if ((c2[0] > c1[0] && c2[0] > c1[1]) || c2[1] > c1[0]
							&& c2[1] > c1[1])
						result = -1;
					else
						result = 0;
				}
			}
		} else if (w1 > w2) {
			result = 1;
		} else {
			result = -1;
		}
		return result;
	}
}
