package com.valentin.mihai.game.states;

import com.valentin.mihai.game.hub.HUD;
import com.valentin.mihai.game.hub.Sound;
import com.valentin.mihai.game.objects.EntityHandler;
import com.valentin.mihai.game.util.KeyHandler;
import com.valentin.mihai.game.util.MouseHandler;
import com.valentin.mihai.game.util.Vector2f;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;
import java.util.Objects;

public abstract class GameState {

    protected GameStateManager gsm;
    protected EntityHandler handler;
    protected HUD hud;

    public GameState(GameStateManager gsm) {
        this.gsm = gsm;
    }

    public abstract void update();
    public abstract void input(MouseHandler mouse, KeyHandler key);
    public abstract void render(Graphics2D g);

    public BufferedImage loadImage(String file) {
        BufferedImage image;
        try {
            image = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream(file)));
            return image;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static float clamp(float val, float min, float max) {
        return Math.max(min, Math.min(max, val));
    }
    public static int clamp(int val, int min, int max) {
        return ((int)Math.max(min, Math.min(max, val)));
    }


}
