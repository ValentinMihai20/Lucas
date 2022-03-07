package com.valentin.mihai.game.hub;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

public class Sound { // clasa pentru sunete

    private Clip clip;
    private FloatControl gainControl; // variabila pentru setarea volumului

    public Sound(String path) {

        File file = new File(path);

        try {

            AudioInputStream ais = AudioSystem.getAudioInputStream(file); // extragerea audio-ului din fisier
         //   System.out.println("Loading: " + file + ". . .");

            // schimbare format din wav in mp3
            AudioFormat baseFormat = ais.getFormat();
            AudioFormat decodeFormat = new AudioFormat(
              AudioFormat.Encoding.PCM_SIGNED,
              baseFormat.getSampleRate(),
              16,
              baseFormat.getChannels(),
              baseFormat.getChannels() * 2,
                    baseFormat.getSampleRate(),
                    false
            );

            AudioInputStream dais = AudioSystem.getAudioInputStream(decodeFormat, ais);


            clip = AudioSystem.getClip();
            clip.open(dais); // deschidere clip

            gainControl = (FloatControl)clip.getControl(FloatControl.Type.MASTER_GAIN); // control volum pentru clip

        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }

    }

    public void play() {
        if(clip == null) return;
        stop();
        clip.setFramePosition(0);
        clip.start();
    }

    public void stop() {
        if(clip.isRunning()) clip.stop();
    }

    public void reset() {
        clip.setMicrosecondPosition(0);
    }

    public void setTime(long ms) {
        clip.setMicrosecondPosition(ms);
    }

    public void close() {
        stop();
        clip.close();
    }

    public void setVolume(double gain) {
        float dB = (float) (Math.log(gain) / Math.log(10.0) * 20.0);
        gainControl.setValue(dB);
    }


    public Clip getClip() {
        return clip;
    }


    public void setClip(Clip clip) {
        this.clip = clip;
    }

    @Override
    public String toString() {
        return "Sound{" +
                "clip=" + clip +
                '}';
    }
}
