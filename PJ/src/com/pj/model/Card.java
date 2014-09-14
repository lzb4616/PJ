package com.pj.model;

public class Card {

	/** 排九的名字*/
	private String name;
	/** 点数*/
	private int dotNum;
	/** porkNum数*/
	private int porkNum;
	/** 从键盘输入的数*/
	private int inputNum;
	/**每张牌的级别	 */
	private int levelNum;
	
	public Card(){}
	public Card(String name,int dotNum,int porkNum,int inputNum,int levelNum){
		this.name = name;
		this.dotNum = dotNum;
		this.porkNum = porkNum;
		this.inputNum = inputNum;
		this.levelNum = levelNum;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getDotNum() {
		return dotNum;
	}
	public void setDotNum(int dotNum) {
		this.dotNum = dotNum;
	}
	public int getPorkNum() {
		return porkNum;
	}
	public void setPorkNum(int porkNum) {
		this.porkNum = porkNum;
	}
	public int getInputNum() {
		return inputNum;
	}
	public void setInputNum(int inputNum) {
		this.inputNum = inputNum;
	}
	public int getLevelNum() {
		return levelNum;
	}
	public void setLevelNum(int levelNum) {
		this.levelNum = levelNum;
	}
}
