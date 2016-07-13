package businessLogic;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import Explain.Action;
import Node.ActionNode;

public class TestGenerator {
	public String storePath;
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

//	ActionNode actionNode;

//	public TestGenerator(ActionNode an) {
//		actionNode.setAction(an.getAction());
//		actionNode.setNext(an.getNext());
//		actionNode.setComponentid(an.getComponentid());
//		actionNode.setPosition(an.getPosition());
//	}

	public String generatorTestCore(ActionNode actionNode) {
		ans += "/*------ Test Core Function ------*/\n";
		ans += "public void testOnClick()" + "\n";
		ans += "{\n";

		boolean is_drag = false;
		
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
					//————————————————————————————————————————————————
				}
				
				ans += "solo.sleep(10000)\n\n";
				ans += "ScreenShot ss = new ScreenShot(\""+ TestCaseClassName +"_sc\");\n\n";
				ans += "Bitmap bitmap = ss.getScreenShot();\n\n";
				
				is_drag = true;
				
				break;
			default:
				
				break;
			}
			actionNode = actionNode.getNext();
		}
		
		// Test Oracle Sequence
		if(is_drag){
			ans += "boolean " + testResultName + " = (bitmap.getPixel(307, 436) == -1);\n\n";
		}else{
			
		}
		ans += "assertTrue(\"" + "Test: Failed." + "\", " + testResultName +");\n";
		
		ans += "}\n";
		ans += "/*--------------------------------*/\n";
		return ans;
	}

	public String generatorCompleteTest(ActionNode actionNode){
		String ans = "";
		
		if (!testApplicationPackageName.equals("")) {
			ans += "package "+testApplicationPackageName+".test;\n\n";
			ans += "import "+testApplicationPackageName+"."+MainActivityName+";\n";
		}
		ans += "import com.robotium.solo.Solo;\n";
		ans += "import android.annotation.SuppressLint;\n";
		ans += "import android.test.ActivityInstrumentationTestCase2;\n\n";
		
		ans += testCasePre + TestCaseClassName + testCaseExtend + "<" + MainActivityName + ">" +"\n";
		ans += "{\n";
		ans += soloCreator + ";\n\n";
		
		ans += "@SuppressLint(\"NewApi\")\n";
		ans += "public " + TestCaseClassName + "()";
		ans += "{\n";
		ans += "super("+ MainActivityName +".class)" +";\n";
		ans += "}\n\n";
		
		ans += overrideState;
		ans += setupFunction;
		ans += overrideState;
		ans += teardownFunction;
		
		//加入针对该链表的测试
		ans += generatorTestCore(actionNode);
		ans += "}\n";
		return ans;
	}
	
	public void StoreTestCase(String mid){
		if (!storePath.equals(""))
		{
			File fi = new File(storePath+TestCaseClassName+".java");
			if (!(fi.exists())) {
				try {
					fi.createNewFile();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					System.out.println(storePath+TestCaseClassName+".java"+": "+e.toString());
					e.printStackTrace();
				}
			}
			FileWriter fw;
			try {
				//fw = new FileWriter(fi, true);[不覆盖原文件]
				fw = new FileWriter(fi); //[覆盖原文件]
				fw.write(mid);
				fw.flush();
				fw.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
