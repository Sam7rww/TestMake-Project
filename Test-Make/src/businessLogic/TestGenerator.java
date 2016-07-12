package businessLogic;

import Explain.Type;
import Node.ActionNode;
import Node.ClickNode;



public class TestGenerator {
	public static String testCasePre = "public class ";
	public static String testCaseExtend = " extends ActivityInstrumentationTestCase2";
	public static String soloCreator = "private Solo solo";
	public static String overrideState = "@Override\n";
	public static String testResultName = "test_result";
	public static String setupFunction = 
			"public void setUp() throws Exception { \n solo = new Solo(getInstrumentation(), getActivity()); \n }\n\n";
	public static String teardownFunction = 
			"public void tearDown() throws Exception { \n solo.finishOpenedActivities(); \n }\n\n";
	
	public String testApplicationName; // = "SimpleGUI"
	public String testApplicationPackageName;
	public String MainActivityName = "MainActivity";
	public String TestCaseClassName;
	
	String ans = "";
	
	ActionNode actionNode;
	
	public TestGenerator(ActionNode an){
		actionNode.action = an.action;
		actionNode.Next = an.Next;
		actionNode.Componentid = an.Componentid;
		actionNode.position = an.position;
	}
	
	public String generatorTestCore(){
		
		
		
		
		return ans;
	}
	
}
