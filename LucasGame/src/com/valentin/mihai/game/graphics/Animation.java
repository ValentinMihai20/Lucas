package com.valentin.mihai.game.graphics;

import java.awt.image.BufferedImage;
import java.nio.Buffer;

public class Animation {

    private BufferedImage[] frames; // tablou pentru frames

    private int currentFrame;
    private int numFrames;

    private int count;
    private int delay;

    private int timesPlayed;

    public Animation(BufferedImage[] frames) {
        timesPlayed = 0;
        setFrames(frames);
    }

    public Animation() {
        timesPlayed = 0;
    }

    public void setFrames(BufferedImage[] frames) {
        this.frames = frames;
        currentFrame = 0;
        count = 0;
        timesPlayed = 0;
        delay = 2;
        numFrames = frames.length;
    }

    public void setDelay(int i) {
        delay = i;
    }

    public void setFrame(int i) { currentFrame = i; }
    public void setNumFrames(int i) { numFrames = i; }

    public void update() {
        if (delay == -1) return;

        count++;
        if (count == delay) {
            currentFrame++;
            count = 0;
        }

        if(currentFrame == numFrames) {
            currentFrame = 0;
            timesPlayed++;
        }
    }

    public int getDelay() {
        return delay;
    }

    public int getCurrentFrame() {
        return currentFrame;
    }

    public int getCount() {
        return count;
    }

    public BufferedImage getImage() {return frames[currentFrame]; }

    public boolean hasPlayedOnce(){
            return timesPlayed > 0;
    }

    public boolean hasPlayed(int i) { return timesPlayed == i;}

}
