package net.treehousetech.tf2finance;

public class TF2Item {

	private String _name = "";
	private int _id = 0;
	
	public TF2Item(String name, int id){
		
		this._id = id;
		this._name = name;
		
	}
	
	public boolean shouldShow(String part){
		if(_name.contains(part)){
			return true;
		}
		if(_id == Integer.getInteger(part)){
			return true;
		}
		return false;
	}
	
	
}
