package businessLogic;

import Node.ActionNode;

public class TestGenerator {
	public static String testCasePre = "public class ";
	public static String testCaseExtend = " extends ActivityInstrumentationTestCase2";
	public static String soloCreator = "private Solo solo";
	public static String overrideState = "@Override\n";
	public static String testResultName = "test_result";
	public static String setupFunction = "public void setUp() throws Exception { \n solo = new Solo(getInstrumentation(), getActivity()); \n }\n\n";
	public static String teardownFunction = "public void tearDown() throws Exception { \n solo.finishOpenedActivities(); \n }\n\n";

	public String testApplicationName; // = "SimpleGUI"
	public String testApplicationPackageName;
	public String MainActivityName;
	public String TestCaseClassName;

	String ans = "";

	boolean is_drag = false;
	
	// ActionNode actionNode;

	// public TestGenerator(ActionNode an) {
	// actionNode.setAction(an.getAction());
	// actionNode.setNext(an.getNext());
	// actionNode.setComponentid(an.getComponentid());
	// actionNode.setPosition(an.getPosition());
	// }

	/**
	 * @param actionNode
	 * @return
	 * TODO 根据一个节点生成一条测试语句
	 */
	public String generatorTestCore(ActionNode actionNode) {
//		ans += "/*------ Test Core Function ------*/\n";
//		ans += "public void testOnClick()" + "\n";
//		ans += "{\n";


		// while (actionNode.getAction() != Action.ORACLE) {
		switch (actionNode.getAction()) {
		case CLICK:
			ans += "// Click-TestAction-In-TestState\n";

			if (actionNode.getType() == null) { // 代表点击的位置处没有控件，需要根据坐标点击
				String[] pos = actionNode.getPosition().split("\\|");
				ans += "solo.clickOnScreen((float)" + pos[0] + ", (float)"
						+ pos[1] + ");\n\n";
			} else {
				// 根据组件id和类型对组件进行操作
				ans += "solo.clickOn" + actionNode.getType() + "(\""
						+ actionNode.getComponentid() + "\");\n\n";
			}
			break;
		case DCLICK:
			ans += "// Double-Click-TestAction-In-TestState\n";
			// 暂时没有双击

			break;
		case LCLICK:
			ans += "// Long-Click-TestAction-In-TestState\n";
			if (actionNode.getType() == null) { // 代表点击的位置处没有控件，需要根据坐标点击
				String[] pos = actionNode.getPosition().split("\\|");
				ans += "solo.clickLongOnScreen((float)" + pos[0] + ", (float)"
						+ pos[1] + ");\n\n";
			} else {
				ans += "solo.clickLongOn" + actionNode.getType() + "(\""
						+ actionNode.getComponentid() + "\");\n\n";
			}
			break;
		case DRAG:
			ans += "// Drag-TestAction-In-TestState\n";

			String stepCount = "5";

			String[] pos = actionNode.getPosition().split("#");
			String[] pos1 = pos[0].split("\\|");
			String[] pos2 = pos[1].split("\\|");
			ans += "solo.drag((float)" + pos1[0] + ", (float)" + pos2[0]
					+ ",(float)" + pos1[1] + ",(float)" + pos2[1] + stepCount
					+ ");\n\n";

			is_drag = true;

			break;
		default:

			break;
		}
		// actionNode = actionNode.getNext();
		// }

		return ans;
	}

	/**
	 * @param actionNode
	 * @return
	 * TODO 生成导包等辅助语句
	 */
	public String generatorCompleteTest(ActionNode actionNode) {
		String ans = "";

		if (!testApplicationPackageName.equals("")) {
			ans += "package " + testApplicationPackageName + ".test;\n\n";
			ans += "import " + testApplicationPackageName + "."
					+ MainActivityName + ";\n";
		}
		ans += "import com.robotium.solo.Solo;\n";
		ans += "import android.annotation.SuppressLint;\n";
		ans += "import android.test.ActivityInstrumentationTestCase2;\n\n";

		ans += testCasePre + TestCaseClassName + testCaseExtend + "<"
				+ MainActivityName + ">" + "\n";
		ans += "{\n";
		ans += soloCreator + ";\n\n";

		ans += "@SuppressLint(\"NewApi\")\n";
		ans += "public " + TestCaseClassName + "()";
		ans += "{\n";
		ans += "super(" + MainActivityName + ".class)" + ";\n";
		ans += "}\n\n";

		ans += overrideState;
		ans += setupFunction;
		ans += overrideState;
		ans += teardownFunction;

		// 加入针对该链表的测试
		// ans += generatorTestCore(actionNode);
		// ans += "}\n";
		ans += "/*------ Test Core Function ------*/\n";
		ans += "public void testOnClick()" + "\n";
		ans += "{\n";
		
		return ans;
	}

	/**
	 * @param actionNode
	 * @return
	 * TODO 生成Oracle测试语句
	 */
	public String testOracleSequence(ActionNode actionNode) {
		// Test Oracle Sequence

		ans += "solo.sleep(10000)\n\n";
		ans += "ScreenShot ss = new ScreenShot(\"" + TestCaseClassName
				+ "_sc\");\n\n";
		ans += "Bitmap bitmap = ss.getScreenShot();\n\n";

		if (is_drag) {
			ans += "boolean " + testResultName
					+ " = (bitmap.getPixel(307, 436) == -1);\n\n";
		} else {

		}
		ans += "assertTrue(\"" + "Test: Failed." + "\", " + testResultName
				+ ");\n";

		ans += "}\n";
		ans += "/*--------------------------------*/\n";
		return null;
	}

}
