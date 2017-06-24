package www.springmvcplus.com.util.system;

import java.io.File;
import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;

public class JsoupUtil {
	
	public static Element getHtml(File filexml) {
		try {
			org.jsoup.nodes.Document parse = Jsoup.parse(filexml, "UTF-8");
			Element element = parse.getElementsByTag("sqls").get(0);
			return element;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
}
