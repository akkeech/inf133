package myInf133Project;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import javax.swing.Timer;

import org.mt4j.AbstractMTApplication;
import org.mt4j.components.visibleComponents.shapes.MTEllipse;
import org.mt4j.components.visibleComponents.shapes.MTRectangle;
import org.mt4j.components.visibleComponents.widgets.MTTextArea;
import org.mt4j.components.visibleComponents.widgets.MTTextField;
import org.mt4j.input.gestureAction.DefaultDragAction;
import org.mt4j.input.gestureAction.InertiaDragAction;
import org.mt4j.input.inputData.InputCursor;
import org.mt4j.input.inputProcessors.IGestureEventListener;
import org.mt4j.input.inputProcessors.MTGestureEvent;
import org.mt4j.input.inputProcessors.componentProcessors.dragProcessor.DragProcessor;
import org.mt4j.input.inputProcessors.componentProcessors.flickProcessor.FlickEvent;
import org.mt4j.input.inputProcessors.componentProcessors.flickProcessor.FlickProcessor;
import org.mt4j.input.inputProcessors.componentProcessors.tapProcessor.TapEvent;
import org.mt4j.input.inputProcessors.componentProcessors.tapProcessor.TapProcessor;
import org.mt4j.input.inputProcessors.componentProcessors.unistrokeProcessor.UnistrokeEvent;
import org.mt4j.input.inputProcessors.componentProcessors.unistrokeProcessor.UnistrokeProcessor;
import org.mt4j.input.inputProcessors.componentProcessors.unistrokeProcessor.UnistrokeUtils.Direction;
import org.mt4j.input.inputProcessors.componentProcessors.unistrokeProcessor.UnistrokeUtils.UnistrokeGesture;
import org.mt4j.input.inputProcessors.globalProcessors.CursorTracer;
import org.mt4j.sceneManagement.AbstractScene;
import org.mt4j.util.MTColor;
import org.mt4j.util.animation.Animation;
import org.mt4j.util.animation.MultiPurposeInterpolator;
import org.mt4j.util.font.FontManager;
import org.mt4j.util.font.IFont;
import org.mt4j.util.math.Vector3D;
import org.mt4j.util.math.Vertex;

public class myInf133ProjectScene extends AbstractScene {
	private Set<Bubble> bubbleSet = new HashSet<Bubble>();
	private int score = 0;

	public myInf133ProjectScene(AbstractMTApplication mtApplication) {
		super(mtApplication, "Inf133 Project");
		
		this.registerGlobalInputProcessor(new CursorTracer(mtApplication, this));
		
		this.setClearColor(MTColor.WHITE);
		
		
		/*
		
		UnistrokeProcessor up = new UnistrokeProcessor(getMTApplication());
		up.addTemplate(UnistrokeGesture.CIRCLE, Direction.CLOCKWISE);
		up.addTemplate(UnistrokeGesture.CIRCLE, Direction.COUNTERCLOCKWISE);
		
		getCanvas().registerInputProcessor(up);
		getCanvas().addGestureListener(UnistrokeProcessor.class, new IGestureEventListener(){
			public boolean processGestureEvent(MTGestureEvent ge){
				UnistrokeEvent ue = (UnistrokeEvent)ge;
				
				switch(ue.getId()){
				case UnistrokeEvent.GESTURE_STARTED:
					//getCanvas().addChild(ue.getVisualization());
					break;
				case UnistrokeEvent.GESTURE_UPDATED:
					break;
				case UnistrokeEvent.GESTURE_ENDED:
					UnistrokeGesture g = ue.getGesture();
					if(g == UnistrokeGesture.CIRCLE){
						Bubble bubble = new Bubble((AbstractScene)(getMTApplication().getCurrentScene()), (int)(ue.getVisualization().getBounds().getHeightXYVectLocal().length()/2),  (int)(ue.getVisualization().getBounds().getWidthXYVectLocal().length()/2), (int)(ue.getVisualization().getBounds().getCenterPointLocal().x), (int)(ue.getVisualization().getBounds().getCenterPointLocal().y));
					}
					break;
				}
				
				
				return false;
			}
		});*/
		
		/*
		for (int i=0; i<20; i++){
			Bubble bubble = new Bubble(this, 125, 125,(int)(Math.random()*this.getMTApplication().height), (int)(Math.random()*this.getMTApplication().width));
		}*/

		IFont fontArial = FontManager.getInstance().createFont(mtApplication, "arial.ttf", 
				50, 	//Font size
				MTColor.BLACK);	//Font color
		//Create a textfield
		final MTTextArea textField = new MTTextArea(mtApplication, fontArial); 
		final MTTextArea textField2 = new MTTextArea(mtApplication, fontArial); 
		
		textField.setNoStroke(true);
		textField.setNoFill(true);
		
		textField.setText("Score:");
		textField2.setText("on screen:");
		//Center the textfield on the screen
		textField.setPositionGlobal(new Vector3D(120, 100));
		textField2.setPositionGlobal(new Vector3D(120, 20));
		//Add the textfield to our canvas
		this.getCanvas().addChild(textField);
		this.getCanvas().addChild(textField2);
		textField.unregisterAllInputProcessors();
		textField.removeAllGestureEventListeners();
		textField2.unregisterAllInputProcessors();
		textField2.removeAllGestureEventListeners();
		
	  
	  final Timer timer = new Timer(100, null);
	
	  
	  timer.addActionListener(new ActionListener(){
		  private int speed = 10;
		  private int counter = 0;
		  private int speedReduceCounter = 0;
		  public void actionPerformed(ActionEvent evt) {
			  textField.setText("Score:" + score);
			  textField2.setText("on screen: " + bubbleSet.size());
	    	  if (bubbleSet.size() >= 10){
	    		  timer.stop();
	    	  }
			  if (counter >= speed){
		    	  bubbleSet.add(new Bubble((AbstractScene)(getMTApplication().getCurrentScene()), 110, 110,(int)((Math.random()*(getMTApplication().width - 250)) + 125), (int)((Math.random()*(getMTApplication().height - 250)) + 125)));
		    	  speedReduceCounter++;
		    	  counter = 0;
			  }
			  if (speedReduceCounter == 10 && speed > 1){
				  counter = 0;
				  speedReduceCounter = 0;
				  speed--;
			  }
	    	  counter++;
	    	  if (bubbleSet.size()>0){
		    	  Set<Bubble> tempSet = new HashSet<Bubble>();
		    	  for(Bubble b: bubbleSet){
		    		   if (b.isDestroyed()){
		    			   tempSet.add(b);
		    			   score++;
		    		   }
		    	  }
		    	  for(Bubble r: tempSet){
		    		  bubbleSet.remove(r);
		    	  }
	    	  }
		  }
	  });
	  
	  timer.start();
		
		//Bubble bubble = new Bubble(this, 200, 200,(int)(Math.random()*this.getMTApplication().height), (int)(Math.random()*this.getMTApplication().width));
		
	}
	
	@Override
	public void init(){
	
	}
	
	@Override
	public void shutDown(){
		
	}
}
