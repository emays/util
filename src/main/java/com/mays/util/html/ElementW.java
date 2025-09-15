package com.mays.util.html;

import java.util.List;

import org.dom4j.Attribute;
import org.dom4j.Element;
import org.dom4j.Node;

public class ElementW {

	private Element element;

	public ElementW(Element element) {
		super();
		this.element = element;
	}

	public Element getElement() {
		return element;
	}

	public ElementW addElement(String name) {
		return new ElementW(element.addElement(name));
	}

	public ElementW addAttribute(String name, String value) {
		element.addAttribute(name, value);
		return this;
	}

	public ElementW addClass(String value) {
		String attr = element.attributeValue("class");
		if (attr != null) {
			attr += " ";
		} else {
			attr = "";
		}
		attr += value;
		return this.addAttribute("class", attr);
	}

	public ElementW addCDATA(String cdata) {
		element.addCDATA(cdata);
		return this;
	}

	public ElementW addText(String text) {
		element.addText(text);
		return this;
	}

	public void setText(String text) {
		removeChildren();
		element.setText(text);
	}

	public void removeChildren() {
		List<Node> nodes = element.selectNodes("child::node()");
		nodes.forEach(node -> element.remove(node));
	}

	public ElementW addElement(HtmlTag tag, String... attributes) {
		return addElement(tag.toString(), attributes);
	}

	public ElementW element(int i) {
		List<Element> els = element.elements();
		return new ElementW(els.get(i));
	}

	public ElementW elementById(String id) {
		for (Element el : element.elements()) {
			for (Attribute attr : el.attributes()) {
				if (attr.getName().equalsIgnoreCase("id") && attr.getValue().equalsIgnoreCase(id))
					return new ElementW(el);
			}
			ElementW el_el = new ElementW(el).elementById(id);
			if (el_el != null)
				return el_el;
		}
		return null;
	}

	public void removeId() {
		if (element.attribute("id") != null)
			element.remove(element.attribute("id"));
	}

	public ElementW addElement(String tag, String... attributes) {
		if (attributes.length % 2 != 0)
			throw new RuntimeException("Attributes length " + attributes.length);
		Element tag_elem = element.addElement(tag);
		for (int i = 0; i < attributes.length; i = i + 2) {
			tag_elem.addAttribute(attributes[i], attributes[i + 1]);
		}
		return new ElementW(tag_elem);
	}

	public ElementW addDiv(String div_class) {
		return addElement(HtmlTag.DIV, "class", div_class);
	}

	public ElementW addDiv(String div_class, String text) {
		return addDiv(div_class).addText(text);
	}

	public ElementW createCopy() {
		return new ElementW(element.createCopy());
	}

	public void add(ElementW el) {
		element.add(el.getElement());
	}

}
