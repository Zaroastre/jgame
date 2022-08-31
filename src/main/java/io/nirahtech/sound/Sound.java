package io.nirahtech.sound;

import java.io.IOException;
import java.net.URL;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class Sound {
    Clip clip;
    URL soundURL[] = new URL[1];

    public Sound() {
        final URL url = Sound.class.getClassLoader().getResource("main-ost.wav");
        soundURL[0] = url;
        this.setFile();
        
    }

    public void setFile() {
        AudioInputStream audioInputStream = null;
        try {
            audioInputStream = AudioSystem.getAudioInputStream(this.soundURL[0]);
        } catch (UnsupportedAudioFileException | IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        try {
            this.clip = AudioSystem.getClip();
        } catch (LineUnavailableException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        if (this.clip != null && audioInputStream != null) {
            try {
                this.clip.open(audioInputStream);
            } catch (LineUnavailableException | IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

    public void play() {
        this.clip.start();
    }

    public void loop() {
        this.clip.loop(Clip.LOOP_CONTINUOUSLY);
    }

    public void stop() {
        this.clip.stop();
    }
}
