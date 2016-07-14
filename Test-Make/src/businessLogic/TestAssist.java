package businessLogic;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import Explain.Action;
import Node.ActionNode;

public class TestAssist {
	public String storePath = ".\\Script\\";
	public String TestCaseClassName = "Script";

	// num表示生成脚本的个数
	int num = 3;
	TestGenerator testGenerator = new TestGenerator();

	int scriptNum = 0;

	boolean is_drag;

	// String res = "";
	// res += testGenerator.generatorCompleteTest(actionNode);

	/**
	 * @param actionNode
	 * @param res
	 *            TODO 遍历一条路径
	 */
	public void generator(ActionNode actionNode, String res) {

		while (actionNode.getAction() != Action.ORACLE) {
//			System.out.println(actionNode.getAction());
			if (actionNode.getAction() == Action.DRAG) {
				String path[] = actionNode.getPosition().split("#");
				if (path.length == 3) {

//					System.out.println("————————————————进了");
					float x1 = Float.parseFloat(path[1].split("\\|")[0]);
					float y1 = Float.parseFloat(path[1].split("\\|")[1]);
					float x2 = Float.parseFloat(path[2].split("\\|")[0]);
					float y2 = Float.parseFloat(path[2].split("\\|")[1]);

					for (int i = 0; i < num; i++) {
						is_drag = false;
//						if (actionNode.getNext().getAction() == Action.ORACLE) {
//							System.out.println("第" + (i + 1) + "次小的for循环");
//						} else {
//							System.out.println("~~~~~~~~~~~~~~~~~第" + (i + 1)
//									+ "次da的for循环");
//						}
						float x = (float) (Math.random() * (x2 - x1) + x1);
						float y = (float) (Math.random() * (y2 - y1) + y1);
						String temp = path[0] + "#" + x + "|" + y + "|";
						ActionNode tempNode = new ActionNode(actionNode.getNext(),
								actionNode.getAction(), actionNode.getType(),
								actionNode.getComponentid(), temp);
						// actionNode.setPosition(path[0] + "#" + x + "|" + y
						// + "|");
						String ans = res
								+ testGenerator.generatorTestCore(tempNode);
						generator(actionNode.getNext(), ans);
						// StoreTestCase(ans);
					}
					// actionNode = actionNode.getNext();
					is_drag = true;
					break;
				} else {
					res += testGenerator.generatorTestCore(actionNode);
					actionNode = actionNode.getNext();
				}
			} else {
				res += testGenerator.generatorTestCore(actionNode);
				actionNode = actionNode.getNext();
			}
		}

		if (!is_drag) {
			res += testGenerator.testOracleSequence(actionNode);
			res += "}\n";
			scriptNum++;
			StoreTestCase(res, scriptNum);
		}
	}

	/**
	 * @param mid
	 *            TODO 存储文件
	 */
	public void StoreTestCase(String mid, int k) {
		if (!storePath.equals("")) {
			File fi = new File(storePath + TestCaseClassName + k + ".java");
			if (!(fi.exists())) {
				try {
					fi.createNewFile();
				} catch (IOException e) {
					System.out.println(storePath + TestCaseClassName + k
							+ ".java" + ": " + e.toString());
					e.printStackTrace();
				}
			}
			FileWriter fw;
			try {
				fw = new FileWriter(fi); // [覆盖原文件]
				// fw = new FileWriter(fi,true); // [不覆盖原文件]
				fw.write(mid);
				fw.flush();
				fw.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
