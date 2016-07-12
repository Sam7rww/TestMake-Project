package businessLogic;

import java.util.List;

import Explain.ActionNode;
import Explain.XmlAnalyse;

public class BusinessLogic {
	
	public List<ActionNode> GetAllAction(){
		XmlAnalyse analyseAction = new XmlAnalyse();
		
		return analyseAction.GetAction();
	}
	
	
}
