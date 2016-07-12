package Explain;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import Node.ActionNode;

public class XmlAnalyse {
	/*
	 * 操作列表
	 */
	private List<ActionNode> AllPath = new ArrayList<ActionNode>();

	public List<ActionNode> GetAction() {
		// 创建一个documentBuildFactory的对象
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		
//		DragNode dragNode = new DragNode(Type.pointToArea, Action.DRAG, 0, 0, 0, 0, 0, 0, null);
//		AllPath.add(dragNode);
		
		try {
			// 创建documentBuilder对象
			DocumentBuilder db = dbf.newDocumentBuilder();
			// 通过document的parse方法加载bookstore.xml文件到当前项目下
			Document document = db.parse("/Users/sam/TestMake-Project/Test-Make/AllXml/File.xml");
			//获取所有book节点的集合
			NodeList pathlist = document.getElementsByTagName("path");
			//通过NodeList的getLength方法可以获取bookList的长度
			System.out.println("一共有"+pathlist.getLength()+"条路");
			
			for(int i=0;i<pathlist.getLength();i++){	
				System.out.println("=====================下面开始遍历第"+(i+1)+"条路的内容=====================");
				//通过Item(index)方法获取一个operation节点，nodelist的索引值从0开始
				//将book节点进行强制类型转换，转换为Element类型
				Element path = (Element) pathlist.item(i);		//第i条路
				
				//解析path节点(即当前路)的子节点
				NodeList OperationList = path.getChildNodes();
				//通过NodeList的getLength方法可以获取bookList的长度
				System.out.println("当前一共有"+OperationList.getLength()+"个操作");
				int ope = 0;
				for(int j=0;j<OperationList.getLength();j++){
					//区分出text类型的node以及element类型的node 节点
					if (OperationList.item(j).getNodeType() == org.w3c.dom.Node.ELEMENT_NODE){
						System.out.println("================下面开始遍历第"+(ope+1)+"个操作的内容================");
						ope++;
						//解析operation节点
						Element operation = (Element) OperationList.item(j);
						//通过type，Action取得属性
						String type = operation.getAttribute("type");
						String action = operation.getAttribute("action");
						System.out.println("第i个操作的类型为："+type);
						System.out.println("第i个操作的动作为："+action);
						
					}
					
				}
				
				
				
				
				
				
				
				
				
				
				
			}
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

		return AllPath;
	}

	
	public static void main(String[] args) {
		XmlAnalyse analyse = new XmlAnalyse();
		analyse.GetAction();
	}
}
