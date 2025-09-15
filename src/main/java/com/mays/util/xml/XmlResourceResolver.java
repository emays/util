package com.mays.util.xml;

import java.io.InputStream;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.ls.LSInput;
import org.w3c.dom.ls.LSResourceResolver;

public class XmlResourceResolver implements LSResourceResolver {

	private static final Logger logger = LoggerFactory.getLogger(XmlResourceResolver.class);

	private String basePath;

	public XmlResourceResolver(String basePath) {
		this.basePath = basePath;
	}

	@Override
	public LSInput resolveResource(String type, String namespaceURI, String publicId, String systemId, String baseURI) {
		Path path = Paths.get(basePath, systemId);
		logger.info("Resolve resource: " + path.toString());
		InputStream resource = this.getClass().getClassLoader().getResourceAsStream(path.toString());
		if (resource == null)
			throw new RuntimeException("Could not find the specified xsd file: " + basePath + " " + systemId);
		return new XmlLSInputImpl(publicId, systemId, baseURI, resource, "UTF-8");
	}

}