package com.slyak.core.util;

import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Set;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.HanyuPinyinVCharType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

public class PinyinUtils {

	private static final HanyuPinyinOutputFormat FORMAT = new HanyuPinyinOutputFormat();

	static {
		FORMAT.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
		FORMAT.setCaseType(HanyuPinyinCaseType.LOWERCASE);
		FORMAT.setVCharType(HanyuPinyinVCharType.WITH_V);
	}

	public static String generatePinyin(String chinese) {
		int len = chinese.length();
		String[][] pys = new String[len][];
		for (int i = 0; i < len; i++) {
			char c = chinese.charAt(i);
			if (isChinese(c)) {
				int j = 0;
				Set<String> s = getCharPinyin(c);
				pys[i] = new String[s.size()];
				for (String py : getCharPinyin(c)) {
					pys[i][j] = py;
					j++;
				}
			} else {
				pys[i][0] = String.valueOf(c);
			}
		}
		String[] result;
		if (pys.length > 1) {
			result = multiply(pys[0], 1, pys);
		} else {
			result = pys[0];
		}
		StringBuffer sb = new StringBuffer("|");
		for (String t : result) {
			sb.append(t).append('|');
		}
		return sb.toString();
	}

	private static String[] multiply(String[] tempResult, int nextIndex,
			String[][] pys) {
		String[] next = pys[nextIndex];
		int pl = tempResult.length;
		int npl = next.length;
		String[] result = new String[pl * npl];
		int count = 0;
		for (int i = 0; i < pl; i++) {
			for (int j = 0; j < npl; j++) {
				result[count] = tempResult[i] + next[j];
				count++;
			}
		}
		++nextIndex;
		if (nextIndex < pys.length) {
			return multiply(result, nextIndex, pys);
		} else {
			return result;
		}
	}

	class PinyinCollection {
		String[] shortPinyins;
		String[] fullPinyins;

		public String[] getShortPinyins() {
			return shortPinyins;
		}

		public void setShortPinyins(String[] shortPinyins) {
			this.shortPinyins = shortPinyins;
		}

		public String[] getFullPinyins() {
			return fullPinyins;
		}

		public void setFullPinyins(String[] fullPinyins) {
			this.fullPinyins = fullPinyins;
		}
	}

	private static Set<String> getCharPinyin(char c) {
		try {
			String[] result = PinyinHelper.toHanyuPinyinStringArray(c, FORMAT);
			return new LinkedHashSet<String>(Arrays.asList(result));
		} catch (BadHanyuPinyinOutputFormatCombination e) {
			return Collections.emptySet();
		}
	}

	public static boolean isChinese(char c) {
		return String.valueOf(c).matches("[\\u4E00-\\u9FA5]+");
	}

	public static void main(String[] args) {
		System.out.println(generatePinyin("曾阿的"));
	}
}
