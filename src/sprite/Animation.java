package sprite;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Animation {
	
	private int frameCount, frameDelay, 
				currentFrame, animationDirection;
	
	private boolean stopped, loop;
	
	private ArrayList<BufferedImage> frames = new ArrayList<BufferedImage>();
	
	public Animation(BufferedImage[] frames, int frameDelay) {
		this.frameDelay = frameDelay;
		this.stopped = true;
		
		frameCount = 0;
		animationDirection = 1;
		
		for (int i = 0; i < frames.length; i++) {
			this.frames.add(frames[i]);
		}
		
		loop = false;
		
	}
	
	public Animation(BufferedImage[] frames, int frameDelay, boolean loop) {
		this.frameDelay = frameDelay;
		this.stopped = true;
		
		frameCount = 0;
		animationDirection = 1;
		
		for (int i = 0; i < frames.length; i++) {
			this.frames.add(frames[i]);
		}
		
		this.loop = loop;
		
	}
	
	public void start() {
		if (!stopped)
			return;
		if (frames.size() == 0)
			return;
		
		stopped = false;
	}
	
	public void restart() {
		if (frames.size() == 0)
			return;
		stopped = false;
		currentFrame = 0;
	}
	
	public void resetAndStop() {
		this.stopped = true;
		this.frameCount = 0;
		this.currentFrame = 0;
	}
	
	public BufferedImage getSprite() {
		System.out.println("Getting sprite at: " + currentFrame);
		return frames.get(currentFrame);
	}
	
	public void update() {
		if (!stopped) {
			frameCount++;
			
			if(frameCount > frameDelay) {
				frameCount = 0;
				currentFrame += animationDirection;
				
				if (currentFrame == frames.size() - 1)
					currentFrame = 0;
				else if (currentFrame < 0)
					currentFrame = frames.size() - 1;
			}
		} else if (loop) {
			restart();
		}
	}
	
	public BufferedImage get() {
		return frames.get(0);
	}

}
