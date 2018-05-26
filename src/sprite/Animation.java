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
	
	public Animation(BufferedImage[] frames, int frameDelay, int animationSpeed, boolean loop) {
		this.frameDelay = frameDelay;
		this.stopped = true;
		
		frameCount = 0;
		this.animationDirection = animationSpeed;
		
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
		if (currentFrame >= frames.size() - 1 && !loop)
			stopped = true;
		
		return frames.get(currentFrame);
	}
	
	public void update() {
		if (!stopped) {
			frameCount++;
			
			if(frameCount > frameDelay) {
				frameCount = 0;
				currentFrame += animationDirection;
				
				if (currentFrame == frames.size() && loop)
					currentFrame = 0;
				else if (currentFrame == frames.size())
					stopped = true;
			}
		}
	}
	
	public BufferedImage get() {
		return frames.get(0);
	}

	public boolean hasStopped() {
		return stopped;
	}
	
}
