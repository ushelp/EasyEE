package cn.easyproject.easyee.sm.base.util;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

/**
 * 通过XStream进行xml与java Bean 的转换
 */
public class XmlUtil {

	private static final String header = "<?xml version=\"1.0\" encoding=\"UTF-8\"?> ";

	/**
	 * java bean 转化为xml
	 * 
	 * @param object
	 * @param objClass
	 * @return
	 */
	public static String parseBeanToXml(Object object, Class<?> objClass) {
		String xmlString = "";
		XStream xStream = new XStream(new DomDriver());
		xStream.processAnnotations(objClass);
		xmlString = xStream.toXML(object);
		xmlString = header + xmlString;
		return xmlString;
	}

	public static Object fromXml(String xml, Class<?> objClass) {
		XStream xStream = new XStream();
		xStream.processAnnotations(objClass);
		return xStream.fromXML(xml);
	}

}
