package com.pj.handler;

import com.pj.regulation.Regulation;
import com.pj.regulation.TZRegulation;

public class RegulationHandler {

	public enum RegulationType {
		REGULATION_RIGHT, REGULATION_FLAT, REGULATION_ONE,
		REGULATION_CLIMB,REGULATION_Tz_Four,REGULATION_Tz_Five,
	}

	private int[][] regulationRight = new int[][] { { 3, 7 }, { 2, 6 },
			{ 1, 5 }, { 0, 4 } };
	private int[][] regulationFlat = new int[][] { { 2, 3 }, { 6, 7 },
			{ 0, 1 }, { 4, 5 } };
	private int[][] regulationOne = new int[][] { { 3, 1 }, { 7, 5 }, 
			{ 2, 0 },{ 6, 4 } };
	private int[][] regulationClimb = new int[][] { { 2, 7 }, { 1, 6 },
			{ 0, 5 }, { 3, 4 } };
	private int[][] regulationTzFive = new int[][] { { 4, 9 }, { 3, 8 },
			{ 2, 7 }, { 1, 6 },{ 0, 5 } };
	private static RegulationHandler regulation;
	static {
		regulation = new RegulationHandler();
	}

	private RegulationHandler() {

	}

	public static RegulationHandler getInstance() {
		return regulation;
	}

	// //////////////////////////////////////////////
	/**
	 * 获得排序排名结果
	 * 
	 * @param datas
	 *            ： 按钮对应的输入数字的数组
	 * @param regulation
	 *            ： 选择的规则类型
	 * @return
	 */
	public int[] getOrder(int[] datas, RegulationType regulation,Regulation r) {
		int[] order = new int[4];
		switch (regulation) {
		case REGULATION_RIGHT:
			order = order(datas, regulationRight,r);
			break;
		case REGULATION_FLAT:
			order = order(datas, regulationFlat,r);
			break;
		case REGULATION_ONE:
			order = order(datas, regulationOne,r);
			break;
		case REGULATION_CLIMB:
			order = order(datas, regulationClimb,r);
			break;
		}
		return order;
	}
	/**
	 * 获得排序排名结果
	 * 
	 * @param datas
	 *            ： 按钮对应的输入数字的数组
	 * @param regulation
	 *            ： 选择的规则类型
	 * @return
	 */
	public int[] getTzOrder(int[] datas, RegulationType regulation,TZRegulation r) {
		int[] order = new int[4];
		switch (regulation) {
		case REGULATION_RIGHT:
			order = TzOrder(datas, regulationRight,r);
			break;
		case REGULATION_FLAT:
			order = TzOrder(datas, regulationFlat,r);
			break;
		case REGULATION_ONE:
			order = TzOrder(datas, regulationOne,r);
			break;
		case REGULATION_CLIMB:
			order = TzOrder(datas, regulationClimb,r);
			break;
		}
		return order;			
	}
	/**
	 * 获得排序排名结果
	 * 
	 * @param datas
	 *            ： 按钮对应的输入数字的数组
	 * @param regulation
	 *            ： 选择的规则类型
	 * @return
	 */
	public int[] getTzFOrder(int[] datas, RegulationType regulation,TZRegulation r) {
		int[] Forder = new int[5];					
		Forder = TzFOrder(datas, regulationTzFive,r);
		return Forder;	
	}
	/**
	 * 获得排序排名结果
	 * 
	 * @param datas
	 *            ： 按钮对应的输入数字的数组
	 * @param regulation
	 *            ： 选择的规则类型
	 *            
	 * @return
	 *			
	 */
	private int[] order(int[] datas, int[][] regulation,Regulation r) {
		int[] order = new int[] { 1, 1, 1, 1 };
		Comparator c = Comparator.getInstance();
		for (int i = 0; i < regulation.length; i++)
			for (int k = i + 1; k < regulation.length; k++) {
				if (c.compare(new int[] { datas[regulation[i][0]],
						datas[regulation[i][1]] }, new int[] {
						datas[regulation[k][0]], datas[regulation[k][1]] }, r) > 0) {
					order[i]++;
				} else if (c.compare(new int[] { datas[regulation[i][0]],
						datas[regulation[i][1]] }, new int[] {
						datas[regulation[k][0]], datas[regulation[k][1]] }, r) < 0) {
					order[k]++;
				}
			}
		return order;
	}
/**
 * 四组牌获得排序排名结果
 * 
 * @param datas
 *            ： 按钮对应的输入数字的数组
 * @param regulation
 *            ： 选择的规则类型
 *            
 * @return
 *			
 */
private int[] TzOrder(int[] datas, int[][] regulation,TZRegulation r) {
	int[] order = new int[] { 1, 1, 1, 1 };
	TZComparator c = TZComparator.getInstance();
	for (int i = 0; i < regulation.length; i++)
		for (int k = i + 1; k < regulation.length; k++) {
			if (c.tzcompare(new int[] { datas[regulation[i][0]],
					datas[regulation[i][1]] }, new int[] {
					datas[regulation[k][0]], datas[regulation[k][1]] }, r) > 0) {
				order[i]++;
			} else if (c.tzcompare(new int[] { datas[regulation[i][0]],
					datas[regulation[i][1]] }, new int[] {
					datas[regulation[k][0]], datas[regulation[k][1]] }, r) < 0) {
				order[k]++;
			}
		}
	return order;
}

/**
 * 五组牌获得排序排名结果
 * 
 * @param datas
 *            ： 按钮对应的输入数字的数组
 * @param regulation
 *            ： 选择的规则类型
 *            
 * @return
 *			
 */
private int[] TzFOrder(int[] datas, int[][] regulation,TZRegulation r) {
	int[] order = new int[] { 1, 1, 1, 1, 1 };
	TZComparator c = TZComparator.getInstance();
	for (int i = 0; i < regulation.length; i++)
		for (int k = i + 1; k < regulation.length; k++) {
			if (c.tzcompare(new int[] { datas[regulation[i][0]],
					datas[regulation[i][1]] }, new int[] {
					datas[regulation[k][0]], datas[regulation[k][1]] }, r) > 0) {
				order[i]++;
			} else if (c.tzcompare(new int[] { datas[regulation[i][0]],
					datas[regulation[i][1]] }, new int[] {
					datas[regulation[k][0]], datas[regulation[k][1]] }, r) < 0) {
				order[k]++;
			}
		}
	return order;
}
}
