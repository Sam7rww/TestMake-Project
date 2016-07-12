package Explain;


public class ActionNode {
	/*
	 * 用户要求操作
	 */
	private Action action;
	/*
	 * 被操作的id
	 */
	private String id;
	/*
	 * 被操作的坐标
	 */
	private double FstX;
	private double FstY;
	private double SecX;
	private double SecY;
	
	public ActionNode(Action act,String ID,double fstx,double fsty,double secx,double secy) {
		// TODO Auto-generated constructor stub
		this.action = act;
		this.id = ID;
		this.FstX = fstx;
		this.FstY = fsty;
		this.SecX = secx;
		this.SecY = secy;
	}

	public Action getAction() {
		return action;
	}

	public void setAction(Action action) {
		this.action = action;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public double getFstX() {
		return FstX;
	}

	public void setFstX(double fstX) {
		FstX = fstX;
	}

	public double getFstY() {
		return FstY;
	}

	public void setFstY(double fstY) {
		FstY = fstY;
	}

	public double getSecX() {
		return SecX;
	}

	public void setSecX(double secX) {
		SecX = secX;
	}

	public double getSecY() {
		return SecY;
	}

	public void setSecY(double secY) {
		SecY = secY;
	}
	
}
