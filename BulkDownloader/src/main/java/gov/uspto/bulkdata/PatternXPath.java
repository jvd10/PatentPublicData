package gov.uspto.bulkdata;

import java.util.ArrayList;
import java.util.List;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;

import com.google.common.base.Preconditions;

public class PatternXPath implements XPathMatch {

	private List<XPathExpression> xPaths = new ArrayList<XPathExpression>();

	public PatternXPath(String... xPathExpressions) throws XPathExpressionException {
		Preconditions.checkNotNull(xPathExpressions, "xPathExpression can not be null");

		XPathFactory fact = XPathFactory.newInstance();
		XPath xpath = fact.newXPath();

		for(String xpathStr: xPathExpressions){
			XPathExpression xpathExp = xpath.compile(xpathStr);
			xPaths.add(xpathExp);
		}
	}

	@Override
	public boolean match(Document document) throws XPathExpressionException {
		for(XPathExpression xPath: xPaths){
			String value = xPath.evaluate(document);
			if (value != null && value.length() > 0){
				return true;
			}
		}

		return false;
	}

	@Override
	public String toString() {
		return "PatternXPath [xPaths=" + xPaths + "]";
	}
}