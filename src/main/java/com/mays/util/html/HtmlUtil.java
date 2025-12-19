package com.mays.util.html;

import java.io.IOException;
import java.io.StringWriter;
import java.nio.file.Files;
import java.nio.file.Path;

import org.dom4j.Document;
import org.dom4j.io.HTMLWriter;
import org.dom4j.io.OutputFormat;

public class HtmlUtil {

	private HtmlUtil() {
	}

	public static void write(Document document, Path file) throws IOException {
		OutputFormat format = OutputFormat.createPrettyPrint();
		format.setTrimText(false);
		format.setXHTML(false);
		format.setExpandEmptyElements(true);
		HTMLWriter writer = new HTMLWriter(Files.newBufferedWriter(file), format);
		writer.write(document);
		writer.close();
	}

	public static String write(Document document) throws IOException {
		OutputFormat format = OutputFormat.createPrettyPrint();
		format.setTrimText(false);
		format.setXHTML(false);
		format.setExpandEmptyElements(true);
		StringWriter sw = new StringWriter();
		HTMLWriter writer = new HTMLWriter(sw, format);
		writer.write(document);
		writer.close();
		return sw.toString();
	}

}
