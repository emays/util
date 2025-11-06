package com.mays.util.html;

import java.util.List;

import org.dom4j.Attribute;
import org.dom4j.Element;
import org.dom4j.Node;

/**
 * {@code ElementW} adds methods to {@code ElementW} via delegation. Does not
 * implement all methods, so use getElement() to access the wrapped Element.
 */
public class ElementW {

	private Element element;

	/**
	 * Creates a new {@code ElementW} wrapping the given element
	 * 
	 * @param element is the {@code Element} to wrap
	 * @return the new {@code ElementW}
	 */
	public static ElementW of(Element element) {
		return new ElementW(element);
	}

	/**
	 * Constructs a new {@code ElementW} wrapping the given element
	 * 
	 * @param element is the {@code Element} to wrap
	 */
	public ElementW(Element element) {
		super();
		this.element = element;
	}

	/**
	 * Access the wrapped {@code Element}
	 * 
	 * @return the wrapped {@code Element}
	 */
	public Element getElement() {
		return element;
	}

	/**
	 * Adds the given {@code ElementW} to this element. If the given element already
	 * has a parent defined then an {@code IllegalAddException} will be thrown.
	 *
	 * @param el is the element to be added
	 */
	public void add(ElementW el) {
		element.add(el.getElement());
	}

	/**
	 * Adds a new {@code ElementW} node with the given name to this element and
	 * returns the new node.
	 *
	 * @param name is the name for the {@code ElementW} node.
	 * @return the newly added {@code ElementW} node.
	 */
	public ElementW addElement(String name) {
		return new ElementW(element.addElement(name));
	}

	/**
	 * Adds a new {@code ElementW} node with a name of the given HtmlTag tag to this
	 * element and returns the new node. Adds an attribute to the new node for each
	 * name and value in attributes.
	 * 
	 * <p>
	 * <blockquote> addElement(HtmlTag.DIV, "id", "div1", "style", "font: 10pt
	 * sans-serif;") </blockquote>
	 * 
	 * @param tag        is the HtmlTag name for the {@code ElementW} node
	 * @param attributes is a sequence of attribute name and value
	 * @return the newly added {@code ElementW} node
	 * @throws IllegalArgumentException if the length of attributes is not a
	 *                                  multiple of 2
	 */
	public ElementW addElement(HtmlTag tag, String... attributes) {
		return addElement(tag.toString(), attributes);
	}

	/**
	 * Adds a new {@code ElementW} node with the given name to this element and
	 * returns the new node. Adds an attribute to the new node for each name and
	 * value in attributes.
	 * 
	 * <p>
	 * <blockquote> addElement("div", "id", "div1", "style", "font: 10pt
	 * sans-serif;") </blockquote>
	 *
	 * @param name       is the name for the {@code ElementW} node
	 * @param attributes is a sequence of attribute name and value
	 * @return the newly added {@code ElementW} node
	 * @throws IllegalArgumentException if the length of attributes is not a
	 *                                  multiple of 2
	 */
	public ElementW addElement(String name, String... attributes) {
		if (attributes.length % 2 != 0)
			throw new IllegalArgumentException("Attributes length " + attributes.length);
		Element tag_elem = element.addElement(name);
		for (int i = 0; i < attributes.length; i = i + 2) {
			tag_elem.addAttribute(attributes[i], attributes[i + 1]);
		}
		return new ElementW(tag_elem);
	}

	/**
	 * This returns the attribute value for the attribute with the given name and
	 * any namespace or null if there is no such attribute or the empty string if
	 * the attribute value is empty.
	 *
	 * @param name is the name of the attribute value to be returned
	 * @return the value of the attribute, null if the attribute does not exist or
	 *         the empty string
	 */
	public String attributeValue(String name) {
		return element.attributeValue(name);
	}

	/**
	 * Adds the attribute value of the given local name. If an attribute already
	 * exists for the given name it will be replaced. Attributes with null values
	 * are silently ignored. If the value of the attribute is null then this method
	 * call will remove any attributes with the given name.
	 *
	 * @param name  is the name of the attribute whose value is to be added or
	 *              updated
	 * @param value is the attribute's value
	 * @return this {@code ElementW}
	 */
	public ElementW addAttribute(String name, String value) {
		element.addAttribute(name, value);
		return this;
	}

	/**
	 * Appends the value to the class attribute of this {@code ElementW} creating a
	 * new attribute if one doesn't already exist
	 * 
	 * @param value is the value of the class attribute to append or set
	 * @return this {@code ElementW}
	 */
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

	/**
	 * Adds a new {@code CDATA} node with the given text to this element.
	 *
	 * @param cdata is the text for the {@code CDATA} node
	 * @return this {@code ElementW}
	 */
	public ElementW addCDATA(String cdata) {
		element.addCDATA(cdata);
		return this;
	}

	/**
	 * Adds a new {@code Text} node with the given text to this element.
	 *
	 * @param text is the text for the {@code Text} node
	 * @return this {@code ElementW}
	 */
	public ElementW addText(String text) {
		element.addText(text);
		return this;
	}

	/**
	 * Removes the child nodes of this element and adds a new {@code Text} node with
	 * the given text to this element.
	 */
	public void setText(String text) {
		removeChildren();
		element.setText(text);
	}

	/**
	 * Adds a new {@code Comment} node with the given text to this element.
	 *
	 * @param comment is the text for the {@code Comment} node
	 * @return this {@code ElementW} instance
	 */
	public ElementW addComment(String comment) {
		element.addComment(comment);
		return this;
	}

	/**
	 * Removes the child nodes of this element.
	 */
	public void removeChildren() {
		List<Node> nodes = element.selectNodes("child::node()");
		nodes.forEach(node -> element.remove(node));
	}

	/**
	 * Returns the elements contained in this element.
	 *
	 * @return a list of all the elements in this element.
	 */
	public List<ElementW> elements() {
		return element.elements().stream().map(el -> new ElementW(el)).toList();
	}

	/**
	 * Returns the element at the specified position in the elements contained in
	 * this element.
	 *
	 * @param index index of the element to return
	 * @return the element at the specified position in the elements
	 * @throws IndexOutOfBoundsException if the index is out of range
	 */
	public ElementW element(int index) {
		List<Element> els = element.elements();
		return new ElementW(els.get(index));
	}

	/**
	 * Returns the first element for the given local name and any namespace.
	 *
	 * @param name the name to search for
	 * @return the first element with the given local name
	 */
	public ElementW element(String name) {
		return new ElementW(element.element(name));
	}

	/**
	 * Returns the first element in this element with an id attribute equal to
	 * idValue (ignoring case). Searches recursively.
	 *
	 * @param idValue the id value to search for
	 * @return the first element with the given local name
	 */
	public ElementW elementById(String idValue) {
		for (Element el : element.elements()) {
			for (Attribute attr : el.attributes()) {
				if (attr.getName().equalsIgnoreCase("id") && attr.getValue().equalsIgnoreCase(idValue))
					return new ElementW(el);
			}
			ElementW el_el = new ElementW(el).elementById(idValue);
			if (el_el != null)
				return el_el;
		}
		return null;
	}

	@Deprecated
	public ElementW addDiv(String div_class) {
		return addElement(HtmlTag.DIV, "class", div_class);
	}

	@Deprecated
	public ElementW addDiv(String div_class, String text) {
		return addDiv(div_class).addText(text);
	}

	/**
	 * Creates a deep copy of this element. The new element is detached from its
	 * parent, and getParent() on the clone will return null.
	 *
	 * @return a new deep copy ElementW
	 */
	public ElementW createCopy() {
		return new ElementW(element.createCopy());
	}

}
