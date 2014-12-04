package myInf133Project;


import org.mt4j.components.visibleComponents.shapes.MTEllipse;
import org.mt4j.input.gestureAction.DefaultDragAction;
import org.mt4j.input.gestureAction.DefaultZoomAction;
import org.mt4j.input.gestureAction.InertiaDragAction;
import org.mt4j.input.gestureAction.TapAndHoldVisualizer;
import org.mt4j.input.inputProcessors.IGestureEventListener;
import org.mt4j.input.inputProcessors.MTGestureEvent;
import org.mt4j.input.inputProcessors.componentProcessors.dragProcessor.DragProcessor;
import org.mt4j.input.inputProcessors.componentProcessors.tapAndHoldProcessor.TapAndHoldEvent;
import org.mt4j.input.inputProcessors.componentProcessors.tapAndHoldProcessor.TapAndHoldProcessor;
import org.mt4j.input.inputProcessors.componentProcessors.tapProcessor.TapEvent;
import org.mt4j.input.inputProcessors.componentProcessors.tapProcessor.TapProcessor;
import org.mt4j.input.inputProcessors.componentProcessors.zoomProcessor.ZoomProcessor;
import org.mt4j.sceneManagement.AbstractScene;
import org.mt4j.util.MTColor;
import org.mt4j.util.math.Vector3D;


public class Bubble {
	private int height;
	private int width;
	private int x;
	private int y;
	final MTEllipse bubble;
	private AbstractScene canvas;
	private boolean destroyed = false;
	public int holdDuration = 500;
	
	
	public Bubble(AbstractScene icanvas, int h, int w, int ix, int iy){
		height = h;
		width = w;
		x = ix;
		y = iy;
		canvas = icanvas;
		
		
		bubble = new MTEllipse(canvas.getMTApplication(), new Vector3D(x,y), height, width);
		
		float r = (float)Math.random()*255;
		float g = (float)Math.random()*255;
		float b = (float)Math.random()*255;
		
		bubble.setFillColor(new MTColor(r,g,b));
		bubble.setStrokeColor(new MTColor(r,g,b));
		
		bubble.unregisterAllInputProcessors();
		bubble.removeAllGestureEventListeners();
		
		canvas.getCanvas().addChild(bubble);
		
		this.tapHoldEvent();
		//this.tapEvent();
		//this.dragEvent();

	}
	
	private void tapHoldEvent(){
		bubble.registerInputProcessor(new TapAndHoldProcessor(canvas.getMTApplication(), holdDuration));
		bubble.addGestureListener(TapAndHoldProcessor.class,new TapAndHoldVisualizer(canvas.getMTApplication(), bubble));
		bubble.addGestureListener(TapAndHoldProcessor.class, new IGestureEventListener(){
			@Override
			public boolean processGestureEvent(MTGestureEvent ge){
				TapAndHoldEvent te = (TapAndHoldEvent)ge;
				if (te.getId()==TapAndHoldEvent.GESTURE_ENDED){
					if (te.isHoldComplete()){
						destroyed = true;
						bubble.destroy();
					}
				}
				return false;
			}
		});
	}
	
	private void tapEvent(){
		bubble.registerInputProcessor(new TapProcessor(canvas.getMTApplication()));
		bubble.addGestureListener(TapProcessor.class, new IGestureEventListener(){
			@Override
			public boolean processGestureEvent(MTGestureEvent ge){
				TapEvent te = (TapEvent)ge;
				if (te.getId()==TapEvent.GESTURE_ENDED){
					float r = (float)Math.random()*255;
					float g = (float)Math.random()*255;
					float b = (float)Math.random()*255;
					
					bubble.setFillColor(new MTColor(r,g,b,100));
					bubble.setStrokeColor(new MTColor(r,g,b));
				}
				return false;
			}
		});
	}
	/*
	private void dragEvent(){
		bubble.registerInputProcessor(new DragProcessor(canvas.getMTApplication()));
		bubble.addGestureListener(DragProcessor.class, new InertiaDragAction(500,.95f,25));
	}*/
	
	private void dragEvent(){
		bubble.registerInputProcessor(new DragProcessor(canvas.getMTApplication()));
		bubble.addGestureListener(DragProcessor.class, new DefaultDragAction());
	}
	
	public boolean isDestroyed(){
		return destroyed;
	}

}
