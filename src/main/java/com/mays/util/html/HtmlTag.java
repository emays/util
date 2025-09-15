package com.mays.util.html;

import java.util.Collections;
import java.util.List;

public enum HtmlTag {

	HEAD, TITLE, LINK, SCRIPT,
	//
	BODY, A, B, BR, DIV, P,
	//
	TABLE, TBODY, TH, TD, TR, CAPTION,
	//
	DEL, INS, HR, SPAN, UL, LI,
	//
	BUTTON, PRE,
	//
	IMG;

	@Override
	public String toString() {
		return super.toString().toLowerCase();
	}

	public String startElement() {
		return "<" + toString() + ">";
	}

	public String startElement(List<String> attrs) {
		String ret = "";
		ret += "<";
		ret += toString() + " ";
		int i = 0;
		for (String str : attrs) {
			if ((i % 2) == 0) {
				ret += str + "=";
			} else {
				ret += "\"" + str + "\" ";
			}
			i++;
		}
		ret += ">";
		return ret;
	}

	public String endElement() {
		return "</" + toString() + ">";
	}

	public String emptyElement() {
		return "<" + toString() + "/>";
	}

	public String valueElement(List<String> attrs, String value) {
		return startElement(attrs) + escapeValue(value) + endElement();
	}

	public String valueElement(String value) {
		return valueElement(Collections.emptyList(), value);
	}

	public static String escapeValue(String value) {
		if (value != null) {
			value = value.replace("&", "&#38;");
			value = value.replace("<", "&#60;");
			value = value.replace(">", "&#62;");
		}
		return value;
	}

}