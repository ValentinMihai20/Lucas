package com.valentin.mihai.game.hub;

import com.valentin.mihai.game.graphics.Sprite;
import com.valentin.mihai.game.objects.Entity;
import com.valentin.mihai.game.objects.ID;
import com.valentin.mihai.game.states.GameState;
import com.valentin.mihai.game.states.GameStateManager;
import com.valentin.mihai.game.util.Vector2f;
import com.valentin.mihai.game.graphics.Font;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

public class HUD implements Observer{

    public static int scor = 0;
    public static int HEALTH = 3;
    public static BufferedImage[] hearts = new BufferedImage[3];
    public static HashMap<ID, BufferedImage> inventory;
    private BufferedImage border;  // rama pentru obiecte inventar
    public static int timer = 0;


    Font font;

    Vector2f heartPosition; // pozitia health-ului
    Vector2f invPosition;   // pozitia inventarului
    Vector2f scorPosition;
    public HUD() {

        heartPosition = new Vector2f(60, 30);
        invPosition = new Vector2f(170, 680);
        scorPosition = new Vector2f(60,80);

        for(int i = 0; i < 3; i++) {
            hearts[i] = loadImage("hub/heart.png");
        }

        border = loadImage("hub/frame2.png");
        inventory = new HashMap<ID, BufferedImage>();

        font = new Font("res/font/smallFont.png", 8, 12, 32);

    }
    public void update() {
        HEALTH = GameState.clamp(HEALTH, 0, 3);
        HUD.timer++;
        if(HUD.timer >= 1000) HUD.timer = 0;
    }

    public void render(Graphics2D g) {

        float hx = heartPosition.x;
        float invX = invPosition.x;
        float hy = heartPosition.y;
        float invY = invPosition.y;

        for(int i = 0; i < HEALTH; i++) {
            if(hearts[i] != null) {
                g.drawImage(hearts[i], (int) hx, (int) hy, 32, 32, null);
            }
            hx += 40; // deplasarea la dreapta pentru desenarea urmatoarei inimi
        }

        g.setColor(Color.BLACK);
        g.drawString(String.valueOf(timer), 120, 30);

        if(inventory.size() > 0)
        for(BufferedImage tempImage : inventory.values()) {
            if(tempImage != null) {
                g.drawImage(border,(int) invX - 7, (int) invY - 7, 64, 64, null);
                g.drawImage(tempImage, (int) invX, (int) invY, 50, 50, null);
            }
            invX += 64;
        }

        Sprite.drawArray(g,font,"SCOR: "+scor+"",scorPosition,16,16,16,0 );
    }


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

    public int getHEALTH() {
        return HEALTH;
    }

    public static void reset() {
        HEALTH = 3;
        inventory.clear();
    }

    @Override
    public void call(Object event) {
        ID id = (ID)event;
        switch (id)  {
            case Key1:
                inventory.put(id , loadImage("entity/items/key1.png"));
                break;
            case Key2:
                inventory.put(id, loadImage("entity/items/key2.png"));
                break;
            case Key3:
                inventory.put(id, loadImage("entity/items/key3.png"));
                break;
            case Key4:
                inventory.put(id, loadImage("entity/items/key4.png"));
                break;
            case Remains:
                inventory.put(id, loadImage("entity/items/scheletu.png"));
                break;
            case Cloak:
                inventory.put(id, loadImage("entity/items/Cloak.png"));
                break;
            case Mirror:
                inventory.put(id, loadImage("entity/items/MirrorLastLevel.png"));
                break;
        }

    }
}
