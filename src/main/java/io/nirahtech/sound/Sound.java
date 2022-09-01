package io.nirahtech.sound;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

import io.nirahtech.sound.apis.AudioPlayer;

public final class Sound implements AudioPlayer {
    private Clip clip;
    private URL audioFileURL = null;
    private long resumeTime = 0;
    private boolean isPaused = false;

    public Sound(final Path audioFileURL) {
        if (Files.exists(audioFileURL) && Files.isRegularFile(audioFileURL)) {
            final URL url = Sound.class.getClassLoader().getResource(audioFileURL.toString());
            this.audioFileURL = url;
            this.loadAudioFile();
        }

    }

    private final void loadAudioFile() {
        AudioInputStream audioInputStream = null;
        try {
            audioInputStream = AudioSystem.getAudioInputStream(this.audioFileURL);
        } catch (UnsupportedAudioFileException | IOException e) {
            e.printStackTrace();
        }
        try {
            this.clip = AudioSystem.getClip();
        } catch (LineUnavailableException e) {
            e.printStackTrace();
        }
        if (this.clip != null && audioInputStream != null) {
            try {
                this.clip.open(audioInputStream);
            } catch (LineUnavailableException | IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void play() {
        if (this.isPaused) {
            this.clip.setMicrosecondPosition(this.resumeTime);
            this.resumeTime = 0;
        }
        this.clip.start();
    }

    @Override
    public void loop() {
        this.clip.loop(Clip.LOOP_CONTINUOUSLY);
    }

    @Override
    public void stop() {
        this.clip.stop();
    }

    @Override
    public void pause() {
        this.isPaused = true;
        this.resumeTime = this.clip.getMicrosecondPosition();
        this.stop();
    }
}
