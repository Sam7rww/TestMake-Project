package Explain;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class XmlAnalyse {
	/*
	 * 操作列表
	 */
	private List<ActionNode> AllPath = new ArrayList<ActionNode>();

	public List<ActionNode> GetAction() {
		// 创建一个documentBuildFactory的对象
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		
		try {
			// 创建documentBuilder对象
			DocumentBuilder db = dbf.newDocumentBuilder();
			// 通过document的parse方法加载bookstore.xml文件到当前项目下
			Document document = db.parse("/Users/sam/TestMake-Project/Test-Make/AllXml/File.xml");
			//获取所有book节点的集合
			NodeList booklist = document.getElementsByTagName("path");
			//通过NodeList的getLength方法可以获取bookList的长度
			System.out.println("一共有"+booklist.getLength()+"本书");
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;
	}

	
	public static void main(String[] args) {
		XmlAnalyse analyse = new XmlAnalyse();
		analyse.GetAction();
	}
}
