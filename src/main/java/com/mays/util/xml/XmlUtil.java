package com.mays.util.xml;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Source;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;

import org.dom4j.Document;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.SAXException;

public class XmlUtil {

	private static final Logger logger = LoggerFactory.getLogger(XmlUtil.class);

	private XmlUtil() {
	}

	public static void write(Document document, Path file) throws IOException {
		OutputFormat format = OutputFormat.createPrettyPrint();
		format.setTrimText(false);
		XMLWriter writer = new XMLWriter(Files.newBufferedWriter(file), format);
		writer.write(document);
		writer.close();
	}

	// https://stackoverflow.com/questions/55571046/eclipse-is-confused-by-imports-accessible-from-more-than-one-module
	
	public static void validate(String file, String xsd, String schema_dir)
			throws ParserConfigurationException, SAXException, IOException {
		XmlUtil.validate(new FileInputStream(file), xsd, schema_dir);
	}

	public static void validate(InputStream in, String xsd, String schema_dir)
			throws ParserConfigurationException, SAXException, IOException {
		// See javax.xml.validation
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		dbf.setNamespaceAware(true);
		DocumentBuilder parser = dbf.newDocumentBuilder();
		org.w3c.dom.Document document = parser.parse(in);
		SchemaFactory factory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
		factory.setResourceResolver(new XmlResourceResolver(schema_dir));
		ArrayList<Source> sources = new ArrayList<>();
		logger.info("Schema: " + xsd);
		Source schema_file = new StreamSource(XmlUtil.class.getClassLoader().getResourceAsStream(xsd));
		sources.add(schema_file);
		javax.xml.validation.Schema schema = factory.newSchema(sources.toArray(new Source[sources.size()]));
		Validator validator = schema.newValidator();
		validator.validate(new DOMSource(document));
	}

}
