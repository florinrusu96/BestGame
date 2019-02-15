package com.mygdx.game.Block_Bunny.handlers;

import java.util.HashMap;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;

public class Contents {

    private HashMap<String, Texture> textures;
    private HashMap<String, Sound> sounds;
    private HashMap<String, Music> music;

    public Contents() {
        textures = new HashMap<String, Texture>();
        sounds = new HashMap<String, Sound>();
        music = new HashMap<String, Music>();
    }

    public void loadTexture(String key, String path) {
        textures.put(key, new Texture(Gdx.files.internal(path)));
    }

    public Texture getTexture(String key) {
        return textures.get(key);
    }

    public void removeTexture(String key) {
        Texture texture = textures.get(key);
        textures.remove(key);
        texture.dispose();
    }

    //// Sounds /////////////////////////////////////////

    public void loadSound(String key, String path) {
        sounds.put(key, Gdx.audio.newSound(Gdx.files.internal(path)));
    }

    public Sound getSound(String key) {
        return sounds.get(key);
    }

    public void removeSound(String key) {
        Sound sound = sounds.get(key);
        sounds.remove(key);
        sound.dispose();
    }

    //// Music ////////////////////////////////////////////

    public void loadMusic(String key, String path) {
        music.put(key, Gdx.audio.newMusic(Gdx.files.internal(path)));
    }

    public Music getMusic(String key) {
        return music.get(key);
    }

    public void removeMusic(String key) {
        Music m = music.get(key);
        music.remove(key);
        m.dispose();
    }
}

