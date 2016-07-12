package businessLogic;

import java.util.List;

import Explain.XmlAnalyse;
import Node.ActionNode;

public class BusinessLogic {
	
	public List<ActionNode> GetAllAction(){
		XmlAnalyse analyseAction = new XmlAnalyse();
		List<ActionNode> actionNodes = analyseAction.GetAction();
		
		return analyseAction.GetAction();
	}
	
	
}
