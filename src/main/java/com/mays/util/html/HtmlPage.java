package com.mays.util.html;

import java.io.InputStream;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

public class HtmlPage {

	private String name;

	private Document document;

	private ElementW element;

	public HtmlPage(String name) {
		this.name = name;
		document = DocumentHelper.createDocument();
		element = addBody(null, null);
	}

	public HtmlPage(String name, String stylesheet, String script) {
		this.name = name;
		document = DocumentHelper.createDocument();
		element = addBody(stylesheet, script);
	}

	public HtmlPage(Path path) throws Exception {
		SAXReader reader = new SAXReader();
		document = reader.read(path.toString());
		element = new ElementW(document.getRootElement());
	}

	public HtmlPage(InputStream stream) throws Exception {
		SAXReader reader = new SAXReader();
		document = reader.read(stream);
		element = new ElementW(document.getRootElement());
	}

	public String getName() {
		return name;
	}

	public ElementW getElement() {
		return element;
	}

	public ElementW getElement(String id) {
		return getElement(id, false);
	}

	public ElementW getElement(String id, boolean detach) {
		Element el = (Element) getDocument().selectSingleNode("//*[@id='" + id + "']");
		if (el == null)
			return null;
		if (detach)
			el.detach();
		return new ElementW(el);
	}

	public Document getDocument() {
		return document;
	}

	public void setTitle(String title) {
		Element el = (Element) document.selectSingleNode("//title");
		el.setText(title);
	}

	private ElementW addBody(String stylesheet, String script) {
		document.addDocType("html", "", "");
		Element html = document.addElement("html").addAttribute("lang", "en");
		Element head = html.addElement("head");
		head.addElement("title").addText(name);
		head.addElement("meta").addAttribute("charset", "UTF-8");
		if (stylesheet != null)
			head.addElement("link").addAttribute("rel", "stylesheet") //
					.addAttribute("type", "text/css") //
					.addAttribute("href", stylesheet);
		if (script != null)
			head.addElement("script").addAttribute("src", script);
		return new ElementW(html.addElement("body"));
	}

	public void write(String outdir) throws Exception {
		HtmlUtil.write(document, Paths.get(outdir, name + ".html"));
	}

}