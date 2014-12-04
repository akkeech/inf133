package myInf133Project;

import org.mt4j.MTApplication;

public class myInf133ProjectStart extends MTApplication{
 
	public static void main(String[] args){
		initialize();
	 }
	 
	 @Override
	 public void startUp(){
		addScene(new myInf133ProjectScene(this));
	 }
}