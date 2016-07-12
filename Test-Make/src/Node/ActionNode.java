package Node;

import Explain.Action;
import Explain.Type;

public class ActionNode {
	/*
	 * 下一个节点
	 */
	public ActionNode Next;
	/*
	 * 用户要求操作(click,dclick,lclick,drag)
	 */
	public Action action;
	/*
	 * 操作类型
	 */
//	public Type type
	/*
	 * 被操作的id(被操作的组件ID)，看type类型，若是component调用这个String
	 */
	public String Componentid;
	/*
	 * 被操作的坐标(被操作的组件坐标)，看type类型，若是singlePoint调用这个String
	 */
	public String position;
//	private double StartX;
//	private double StartY;
	/*
	 * 被操作的坐标
	 */
//	private double FstX;
//	private double FstY;
//	private double SecX;
//	private double SecY;
	
	public ActionNode(Type typ,Action act,ActionNode nextnode,String ID,String pos) {
		// TODO Auto-generated constructor stub
//		this.type = typ;
		this.action = act;
		this.Next = nextnode;
		this.Componentid = ID;
		this.position = pos;
//		this.FstX = fstx;
//		this.FstY = fsty;
//		this.SecX = secx;
//		this.SecY = secy;
//		this.Oracle = Ora;
	}

	
	
}
