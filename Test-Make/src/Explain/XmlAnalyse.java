package Explain;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.text.Position;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.soap.Detail;
import javax.xml.soap.Node;

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

	private ActionNode NowNode = null;

	private boolean isFirst = true;

	public List<ActionNode> GetAction() {
		// 创建一个documentBuildFactory的对象
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();

		// DragNode dragNode = new DragNode(Type.pointToArea, Action.DRAG, 0, 0,
		// 0, 0, 0, 0, null);
		// AllPath.add(dragNode);

		try {
			// 创建documentBuilder对象
			DocumentBuilder db = dbf.newDocumentBuilder();
			// 通过document的parse方法加载bookstore.xml文件到当前项目下
			Document document = db.parse("/Users/sam/TestMake-Project/Test-Make/AllXml/File.xml");
			// 获取所有book节点的集合
			NodeList pathlist = document.getElementsByTagName("path");
			// 通过NodeList的getLength方法可以获取bookList的长度
			System.out.println("一共有" + pathlist.getLength() + "条路");

			for (int i = 0; i < pathlist.getLength(); i++) {
				System.out.println("=====================下面开始遍历第" + (i + 1) + "条路的内容=====================");
				// 通过Item(index)方法获取一个operation节点，nodelist的索引值从0开始
				// 将book节点进行强制类型转换，转换为Element类型
				Element path = (Element) pathlist.item(i); // 第i条路

				// 解析path节点(即当前路)的子节点
				NodeList OperationList = path.getChildNodes();
				// 通过NodeList的getLength方法可以获取bookList的长度
				System.out.println("当前一共有" + OperationList.getLength() + "个操作");
				int ope = 0;
				for (int j = 0; j < OperationList.getLength(); j++) {
					// 区分出text类型的node以及element类型的node 节点
					if (OperationList.item(j).getNodeType() == org.w3c.dom.Node.ELEMENT_NODE) {
						System.out.println("================下面开始遍历第" + (ope + 1) + "个操作的内容================");
						ope++;
						// 解析operation节点
						Element operation = (Element) OperationList.item(j);
						// 当前标签名字
						String Opename = operation.getNodeName();
						// 通过type，Action取得属性
						String type = operation.getAttribute("type");
						String action = operation.getAttribute("action");
						System.out.println(Opename);
						System.out.println("第" + (ope) + "个操作的类型为：" + type);
						System.out.println("第" + ope + "个操作的动作为：" + action);

						NodeList childNodes = operation.getChildNodes();
						System.out.println("一共有" + childNodes.getLength() + "个子节点");

						if (Opename.equalsIgnoreCase("oracle")) {
							System.out.println("这是一个Oracle哦！！");
							if (type.contains("SingleComponentResult")) {
								String theType = null;
								String theComponent = null;
								String thePos = null;
								for(int l=0;l<childNodes.getLength();l++){
									if (childNodes.item(l).getNodeType() == Node.ELEMENT_NODE) {
										System.out.println(childNodes.item(l).getNodeName());
										NodeList singleElement = childNodes.item(l).getChildNodes();
										for(int m=0;m<singleElement.getLength();m++){
											if (singleElement.item(m).getNodeType() == Node.ELEMENT_NODE) {
												String NowString = singleElement.item(m).getNodeName();
												System.out.println("现在的子节点名字：" + NowString);
												if (NowString.equalsIgnoreCase("index")) {
													theType = singleElement.item(m).getTextContent();
													System.out.println(theType);
												}
												else if (NowString.equalsIgnoreCase("resultType")) {
													theType += "|"+singleElement.item(m).getTextContent();
													System.out.println(theType);
												}
												else if (NowString.equalsIgnoreCase("componentType")) {
													theComponent = singleElement.item(m).getTextContent();
													System.out.println(theComponent);
												}
												else if (NowString.equalsIgnoreCase("expect")) {
													thePos = singleElement.item(m).getTextContent();
													System.out.println(thePos);
												}
											}
										}
									}
								}
								ActionNode anActionNode = new ActionNode(null, Action.ORACLE, theType, theComponent, thePos);
								this.setNode(anActionNode);
							}
							
							
						} else {//路径中的Operation
							/*
							 * 针对不同的type属性进行不同的解析过程
							 */
							if (type.equalsIgnoreCase("component")) {
								// NodeList detailOpe =
								// operation.getChildNodes();
								String type2 = null;
								String Text = null;
								for (int k = 0; k < childNodes.getLength(); k++) {
									if (childNodes.item(k).getNodeType() == org.w3c.dom.Node.ELEMENT_NODE) {
										// System.out.println("操作的组件类型："+childNodes.item(k).getNodeName());
										// System.out.println("操作的组件的text内容："+childNodes.item(k).getTextContent());
										if (childNodes.item(k).getNodeName().equalsIgnoreCase("type")) {
											type2 = childNodes.item(k).getTextContent();
											System.out.println(type2);
										}
										if (childNodes.item(k).getNodeName().equalsIgnoreCase("resourceId")) {
											Text = childNodes.item(k).getTextContent();
											System.out.println(Text);
										}
									}
								}
								ActionNode anActionNode = null;
								if (action.equalsIgnoreCase("click")) {
									anActionNode = new ActionNode(null, Action.CLICK, type2, Text, null);
								} else if (action.equalsIgnoreCase("longClick")) {
									anActionNode = new ActionNode(null, Action.LCLICK, type2, Text, null);
								}

								this.setNode(anActionNode);
							} else if (type.equalsIgnoreCase("singlePoint")) {
								// Type type2 = null;
								String position = null;
								for (int k = 0; k < childNodes.getLength(); k++) {
									if (childNodes.item(k).getNodeType() == org.w3c.dom.Node.ELEMENT_NODE) {
										System.out.println(childNodes.item(k).getNodeName());
										NodeList singleNodes = childNodes.item(k).getChildNodes();
										for (int l = 0; l < singleNodes.getLength(); l++) {
											if (singleNodes.item(l).getNodeType() == org.w3c.dom.Node.ELEMENT_NODE) {
												String pos = singleNodes.item(l).getTextContent();
												System.out.println("点坐标为：" + pos);
												if (position == null) {
													position = pos;
													position += "|";
												} else {
													position += pos;
												}
											}
										}
										System.out.println(position);
									}
								}
								ActionNode anActionNode = null;
								if (action.equalsIgnoreCase("click")) {
									anActionNode = new ActionNode(null, Action.CLICK, null, null, position);
								} else if (action.equalsIgnoreCase("longClick")) {
									anActionNode = new ActionNode(null, Action.LCLICK, null, null, position);
								}
								this.setNode(anActionNode);
							} else if (type.equalsIgnoreCase("doublePoint")) {
								// 位置
								String position = null;
								for (int k = 0; k < childNodes.getLength(); k++) {
									if (childNodes.item(k).getNodeType() == org.w3c.dom.Node.ELEMENT_NODE) {// doubleNode节点
										System.out.println(childNodes.item(k).getNodeName());
										NodeList doubleNodes = childNodes.item(k).getChildNodes();
										for (int l = 0; l < doubleNodes.getLength(); l++) {
											// boolean IfFirstNode = true;
											if (doubleNodes.item(l).getNodeType() == org.w3c.dom.Node.ELEMENT_NODE) {// singleNode节点
												System.out.println(doubleNodes.item(l).getNodeName());
												NodeList singleNodes = doubleNodes.item(l).getChildNodes();
												String tempPosition = null;
												for (int m = 0; m < singleNodes.getLength(); m++) {
													if (singleNodes.item(m).getNodeType() == Node.ELEMENT_NODE) {// pointX/Y节点
														String pos = singleNodes.item(m).getTextContent();
														System.out.println("点坐标为：" + pos);
														if (tempPosition == null) {
															tempPosition = pos;
															tempPosition += "|";
														} else {
															tempPosition += pos;
															// tempPosition +=
															// "#";
														}
													}
												}
												if (position == null) {
													position = tempPosition;
													position += "#";
												} else {
													position += tempPosition;
												}
												tempPosition = null;
											}
										}
									}
								}
								System.out.println("doublePoint的结果为：" + position);
								ActionNode anActionNode = new ActionNode(null, Action.DRAG, null, null, position);
								this.setNode(anActionNode);
							} else if (type.equalsIgnoreCase("pointToArea")) {
								// 位置
								String position1 = null;
								String position2 = null;
								for (int k = 0; k < childNodes.getLength(); k++) {
									if (childNodes.item(k).getNodeType() == Node.ELEMENT_NODE) {// singlePoint和doublePoint子节点
										String ChildNodeName = childNodes.item(k).getNodeName();
										System.out.println("当前子节点名字为（第一层子节点）：" + ChildNodeName);

										if (ChildNodeName.equalsIgnoreCase("singlePoint")) {// singlePoint子节点
											System.out.println("----当前处理拖动的singlePoint----");
											NodeList singleNode = childNodes.item(k).getChildNodes();
											for (int l = 0; l < singleNode.getLength(); l++) {
												if (singleNode.item(l).getNodeType() == Node.ELEMENT_NODE) {
													String pos = singleNode.item(l).getTextContent();
													System.out.println("点坐标为：" + pos);
													if (position1 == null) {
														position1 = pos;
														position1 += "|";
													} else {
														position1 += pos;
													}
												}
											}
											System.out.println("SinglePoint的值为：" + position1);
										} else { // doublePoint子节点
											System.out.println("----当前处理拖动的area----");
											NodeList singleNodes = childNodes.item(k).getChildNodes();
											for (int l = 0; l < singleNodes.getLength(); l++) { // 遍历doublePoint子节点
												if (singleNodes.item(l).getNodeType() == Node.ELEMENT_NODE) {// doublePoint子节点中的element(singlePoint)
													System.out.println(
															"area中的第一层子节点" + singleNodes.item(l).getNodeName());
													NodeList pointNodes = singleNodes.item(l).getChildNodes();
													String tempPosition = null;
													for (int m = 0; m < pointNodes.getLength(); m++) {
														if (pointNodes.item(m).getNodeType() == Node.ELEMENT_NODE) {// pointX/Y节点
															System.out.println(
																	"area中的第二层子节点" + pointNodes.item(m).getNodeName());
															String pos = pointNodes.item(m).getTextContent();
															System.out.println("点坐标为：" + pos);
															if (tempPosition == null) {
																tempPosition = pos;
																tempPosition += "|";
															} else {
																tempPosition += pos;
																// tempPosition
																// += "#";
															}
														}
													}
													if (position2 == null) {
														position2 = tempPosition;
														position2 += "#";
													} else {
														position2 += tempPosition;
													}
													tempPosition = null;
												}
											}
										}
									}
								}
								position1 = position1 + "#" + position2;
								System.out.println("拖动操作的最后坐标为：" + position1);
								ActionNode anActionNode = new ActionNode(null, Action.DRAG, null, null, position1);
								this.setNode(anActionNode);
							}

						} // element型子节点（operation）
					}
				} // 不同操作
				NowNode = null;

				isFirst = true;

			} // 不同路径
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return AllPath;
	}

	// public String ComponentClick(NodeList DetailList){
	// for(int i=0;i<DetailList.getLength();i++){
	// if (DetailList.item(i).getNodeType() == Node.ELEMENT_NODE) {
	// String NodeValue = DetailList.item(i).
	// }
	//
	// }
	//
	// return null;
	// }

	public void setNode(ActionNode actionNode) {
		// 路径链表 的第一个
		if (isFirst) {
			isFirst = false;
			AllPath.add(actionNode);
			NowNode = actionNode;
		}
		// 不是第一个，把上一个node连接到当前的node
		else {
			NowNode.setNext(actionNode);
			NowNode = actionNode;
		}
	}

	public static void main(String[] args) {
		XmlAnalyse analyse = new XmlAnalyse();
		analyse.GetAction();
	}
}
