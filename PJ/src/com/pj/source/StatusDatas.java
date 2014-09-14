package com.pj.source;

import java.util.HashMap;
import java.util.Map;

import com.pj.model.Card;
import com.pj.regulation.Regulation;
import com.pj.regulation.impl.RegulationA;
import com.pj.regulation.impl.RegulationB;
import com.pj.regulation.impl.RegulationC;
import com.pj.regulation.impl.RegulationD;

/**
 * 采用单例模式
 * 
 * @author Administrator
 * 
 */
public final class StatusDatas {

	private static StatusDatas data = null;
	private Map<String, Card> cards;
	private int regulationIndex = 0;
	private Regulation[] regulations;
	static {
		data = new StatusDatas();
	}
	/**
	 * 标记当前正在编辑的按钮的索引
	 */
	private int currentIndex = 0;

	private StatusDatas() {
		cards = new HashMap<String, Card>();
		String[] name = new String[] { "三", "六", "天", "地", "人", "和", "梅", "长三",
				"板凳", "虎头", "四红", "幺六", "幺五", "杂九", "杂八", "杂七", "杂五" };
		int[] dotNum = new int[] { 3, 6, 12, 2, 8, 4, 10, 6, 4, 11, 10, 7, 6,
				9, 8, 7, 5 };
		int[] porkNum = new int[] { 16, 15, 14, 13, 12, 11, 10, 9, 8, 7, 6, 5,
				4, 3, 2, 1, 0 };
		int[] inputNum = new int[] { 3, 9, 12, 2, 8, 4, 0, 6, 14, 11, 10, 7,
				16, 19, 18, 17, 5 };
		int[] levelNum = new int[]{1,1,11,10,9,8,7,7,7,6,6,6,6,5,4,3,2};
		for (int i = 0; i < name.length; i++)
			cards.put("" + inputNum[i], new Card(name[i], dotNum[i],
					porkNum[i], inputNum[i],levelNum[i]));
		regulations = new Regulation[] { new RegulationA(), new RegulationB(),
				new RegulationC(), new RegulationD()};
	}

	public static StatusDatas getInstance() {
		return data;
	}

	public int getCurrentIndex() {
		return currentIndex;
	}

	public void setCurrentIndex(int currentIndex) {
		this.currentIndex = currentIndex;
	}

	public Card getCardByInputNum(int inputNum) {
		return cards.get("" + inputNum);
	}

	public int getWeightByInputNum(int inputNum) {
		if (cards.get("" + inputNum) == null)
			return -1;
		return cards.get("" + inputNum).getPorkNum();
	}

	public int getLevelByInputNum(int inputNum) {
		if (cards.get("" + inputNum) == null)
			return -1;
		return cards.get("" + inputNum).getLevelNum();
	}
	
	public int getRegulationIndex() {
		return regulationIndex;
	}

	public void setRegulationIndex(int regulationIndex) {
		this.regulationIndex = regulationIndex;
	}
	public Regulation getCurrentRegulation(){
		return regulations[regulationIndex];
	}
}
