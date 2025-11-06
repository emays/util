package com.mays.util.html;

import java.util.Collections;
import java.util.List;

public enum HtmlTag {

	HEAD, TITLE, LINK, SCRIPT, STYLE,
	//
	BODY, A, P, B, BR, DIV, SPAN,
	//
	H1, H2, H3, H4, H5, H6,
	//
	TABLE, TBODY, TH, TD, TR, CAPTION,
	//
	DEL, INS, HR, UL, LI,
	//
	PRE,
	//
	FORM, INPUT, TEXTAREA, BUTTON, SELECT, OPTION, OPTGROUP, FIELDSET, LABEL, OUTPUT,
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