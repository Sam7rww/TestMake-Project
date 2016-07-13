package businessLogic;

import Explain.Action;
import Explain.Type;
import Node.ActionNode;
import Node.ClickNode;

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
	public String MainActivityName = "MainActivity";
	public String TestCaseClassName;

	String ans = "";

	ActionNode actionNode;

	public TestGenerator(ActionNode an) {
		actionNode.setAction(an.getAction());
		actionNode.setNext(an.getNext());
		actionNode.setComponentid(an.getComponentid());
		actionNode.setPosition(an.getPosition());
	}

	public String generatorTestCore() {
		ans += "/*------ Test Core Function ------*/\n";
		ans += "public void testOnClick()" + "\n";
		ans += "{\n";

		while (actionNode.getAction() != Action.ORACLE) {
			switch (actionNode.getAction()) {
			case CLICK:
				ans += "// Click-TestAction-In-TestState\n";

				if (actionNode.getComponentid().equals("NULL")) { // 代表点击的位置处没有控件，需要根据坐标点击
					String[] pos = actionNode.getPosition().split("\\|");
					ans += "solo.clickOnScreen((float)" + pos[0] + ", (float)"
							+ pos[1] + ");\n\n";
				} else {

					// 根据组件id和类型对组件进行操作
					// if(actionNode.getComponentid().contains("EditText")){
					//
					// }

				}
				break;
			case DCLICK:
				ans += "// Double-Click-TestAction-In-TestState\n";
				// 双击怎么写？
				// if (actionNode.getComponentid().equals("NULL")) { //
				// 代表点击的位置处没有控件，需要根据坐标点击
				// String[] pos = actionNode.getPosition().split("\\|");
				// ans += "solo.clickOnScreen((float)" + pos[0] + ", (float)"
				// + pos[1] + ");\n\n";
				// } else {
				// //——————————————————————————————————————————————————————————
				// }

				break;
			case LCLICK:
				ans += "// Long-Click-TestAction-In-TestState\n";
				if (actionNode.getComponentid().equals("NULL")) { // 代表点击的位置处没有控件，需要根据坐标点击
					String[] pos = actionNode.getPosition().split("\\|");
					ans += "solo.clickLongOnScreen((float)" + pos[0]
							+ ", (float)" + pos[1] + ");\n\n";
				} else {

				}
				break;
			case DRAG:
				ans += "// Drag-TestAction-In-TestState\n";
				
				String stepCount = "5";
				
				if (actionNode.getComponentid().equals("NULL")) { // 代表点击的位置处没有控件，需要根据坐标点击
					String[] pos = actionNode.getPosition().split("#");
					String[] pos1 = pos[0].split("\\|");
					String[] pos2 = pos[1].split("\\|");
					ans += "solo.drag((float)" + pos1[0] + ", (float)"
							+ pos2[0] + ",(float)" + pos1[1] + ",(float)"
							+ pos2[1] + stepCount+");\n\n";
				} else {

				}
				break;
			}
		}

		return ans;
	}

}
