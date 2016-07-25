package businessLogic;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import Explain.Action;
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
	public String testApplicationPackageName = "";
	public String MainActivityName = "MainMenu";
	public String TestCaseClassName = "ScriptTest";

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
	 * @return TODO 根据一个节点生成一条测试语句
	 */
	public String generatorTestCore(ActionNode actionNode) {
		String ans = "";
		
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
					+ ",(float)" + pos1[1] + ",(float)" + pos2[1] + ","
					+ stepCount + ");\n\n";

			is_drag = true;

			break;
		default:

			break;
		}

		return ans;
	}

	/**
	 * @return TODO 生成导包等辅助语句
	 */
	public String generatorCompleteTest() {
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

		ans += "/*------ Test Core Function ------*/\n";
		ans += "public void testOnClick()" + "\n";
		ans += "{\n";

		return ans;
	}

	/**
	 * @param actionNode
	 * @return TODO 生成Oracle测试语句
	 */
	public String testOracleSequence(ActionNode actionNode) {
		// Test Oracle Sequence
		System.out.println("Test Oracle Sequence");

		String ans = "";

		ans += "solo.sleep(1000);\n\n";

		int i = 1;
		while (actionNode != null) {

			String indexAndType[] = actionNode.getType().split("\\|");
			if (indexAndType[1].equals("IMAGE")) {
				ans += "// Assert-Image\n";
				ans += "solo.takeScreenshot(\"\");\n";
				ans += "Bitmap bitmap = BitmapFactory.decodeStream();";
				int color = bitmap.getPixel(1, 2);
				int a = 10;
				int b = a<<24;
			} else if (indexAndType[1].equals("TEXT")) {
				ans += "// Assert-Text\n";
				ans += "boolean test" + i + " = solo.searchText(\""
						+ actionNode.getPosition() + "\");\n\n";
			}
			if (i == 1) {
				ans += "boolean " + testResultName + ";\n";
				ans += testResultName + " = test1;\n";
			}
			if (actionNode.getAction() == Action.OR) {
				ans += testResultName + " = " + testResultName + "||" + "test"
						+ i + ";\n";
			} else if (actionNode.getAction() == Action.AND) {
				ans += testResultName + " = " + testResultName + "&&" + "test"
						+ i + ";\n";
			}
			i++;
			actionNode = actionNode.getNext();
		}

		ans += "assertTrue(\"" + "Test: Failed." + "\", " + testResultName
				+ ");\n";

		ans += "}\n";
		ans += "/*--------------------------------*/\n";
		return ans;
	}

}
