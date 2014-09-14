package com.pj.source;


import com.pj.regulation.TZRegulation;
import com.pj.regulation.impl.TZRegulationA;
import com.pj.regulation.impl.TZRegulationB;
import com.pj.regulation.impl.TZRegulationC;
import com.pj.regulation.impl.TZRegulationD;

/**
 * 采用单例模式
 * 
 * @author Administrator
 * 
 */
public final class TzStatusDatas {

	private static TzStatusDatas data = null;
	private int regulationIndex = 0;
	private TZRegulation[] tzregulations;
	static {
		data = new TzStatusDatas();
	}
	/**
	 * 标记当前正在编辑的按钮的索引
	 */
	private int currentIndex = 0;

	private TzStatusDatas() {		
		tzregulations = new TZRegulation[] {new TZRegulationA(),new TZRegulationB(),
				new TZRegulationC(),new TZRegulationD()};
	}

	public static TzStatusDatas getInstance() {
		return data;
	}

	public int getCurrentIndex() {
		return currentIndex;
	}

	public void setCurrentIndex(int currentIndex) {
		this.currentIndex = currentIndex;
	}

	public int getCardByInputNum(int inputNum) {
		return inputNum;
	}

	public int getRegulationIndex() {
		return regulationIndex;
	}

	public void setRegulationIndex(int regulationIndex) {
		this.regulationIndex = regulationIndex;
	}

	public TZRegulation getCurrentTZRegulation(){
		return tzregulations[regulationIndex];
	}
}
