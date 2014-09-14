package com.pj.regulation;

import java.util.HashMap;
import java.util.Map;

public class Regulation {

	private Map<String, Integer> co = new HashMap<String, Integer>();

	public Regulation(int[][] c){
		for(int i = 0; i < c.length; i++)
			this.setWeight(c[i][0],c[i][1], c[i][2]);
	}
	/**
	 * 获得组合的权值
	 * 
	 * @param key
	 *            ：大值_小值，如：“10_8”
	 * @return
	 */
	public int getWeight(String key) {
		return co.get(key);
	}

	/**
	 * 获得组合的权值
	 * 
	 * @param cardNum1
	 * @param cardNum2
	 * @return：返回值若为-1，则组合规则中没有指定，需要比较点数关系
	 */
	public int getWeight(int cardNum1, int cardNum2) {
		Integer result = co.get((cardNum1 > cardNum2 ? cardNum1 + "_" + cardNum2
				: cardNum2 + "_" + cardNum1));
		if(result == null)
			result = -1;
		return result;
	}

	private void setWeight(int cardNum1, int cardNum2,int weight) {
		String key = cardNum1 > cardNum2 ? cardNum1 + "_" + cardNum2
				: cardNum2 + "_" + cardNum1;
		co.put(key,weight);
	}
}
