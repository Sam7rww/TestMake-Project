package businessLogic;

import Explain.Action;
import Node.ActionNode;

public class TestScript {
	public static void main(String[] args){
		ActionNode a1 = new ActionNode(null, Action.CLICK, "MenuItem", "Item1", "23.4|11.1");
		ActionNode a2 = new ActionNode(null, Action.CLICK, null, null, "231.9|9");
		ActionNode a3 = new ActionNode(null, Action.CLICK, "TextView", "text1", "2.3|4.3");
		ActionNode a4 = new ActionNode(null, Action.DRAG, null, null, "1.1|2.2#3.3|4.4#123|456");
		ActionNode a5 = new ActionNode(null, Action.DRAG, null, null, "11.1|12.2#13.3|14.4#23.5|26.6");
		ActionNode a6 = new ActionNode(null, Action.ORACLE, "3|TEXT", "EditText", "Note 1 test");
//		a1.setNext(a2);
		a2.setNext(a3);
		a3.setNext(a4);
		a4.setNext(a5);
		a5.setNext(a1);
		a1.setNext(a6);
		
		String result = new TestGenerator().generatorCompleteTest();
		new TestAssist().generator(a2, result,"");
		new TestAssist().generator(a2, result, "fileName");
		System.out.println("heheda");
	}
}
