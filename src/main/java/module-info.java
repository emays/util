module com.mays.util {
	
	requires transitive java.xml;
	requires transitive org.dom4j;
	requires transitive org.slf4j;
    
	exports com.mays.util;
	exports com.mays.util.html;
	exports com.mays.util.xml;

}
