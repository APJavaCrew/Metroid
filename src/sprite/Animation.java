package sprite;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Animation {
	
	private int frameCount, frameDelay, 
				currentFrame, animationDirection, 
				totalFrames;
	
	private boolean stopped, loop;
	
	private ArrayList<Frame> frames = new ArrayList<Frame>();
	
	public Animation(BufferedImage[] frames, int frameDelay) {
		this.frameDelay = frameDelay;
		this.stopped = true;
		
		for (int i = 0; i < frames.length; i++) {
			addFrame(frames[i], frameDelay);
		}
		
		loop = false;
		
	}
	
	public Animation(BufferedImage[] frames, int frameDelay, boolean loop) {
		this.frameDelay = frameDelay;
		this.stopped = true;
		
		for (int i = 0; i < frames.length; i++) {
			addFrame(frames[i], frameDelay);
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
	
	private void addFrame(BufferedImage frame, int duration) {
        if (duration <= 0) {
            System.err.println("Invalid duration: " + duration);
            throw new RuntimeException("Invalid duration: " + duration);
        }

        frames.add(new Frame(frame, duration));
        currentFrame = 0;
    }
	
	public BufferedImage getSprite() {
		return frames.get(currentFrame).getFrame();
	}
	
	public void update() {
		if (!stopped) {
			frameCount++;
			
			if(frameCount > frameDelay) {
				frameCount = 0;
				currentFrame += animationDirection;
				
				if (currentFrame > totalFrames - 1)
					currentFrame = 0;
				else if (currentFrame < 0)
					currentFrame = totalFrames - 1;
			}
		} else if (loop) {
			restart();
		}
	}

}
