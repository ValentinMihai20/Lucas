package com.valentin.mihai.game.graphics;

import com.valentin.mihai.game.util.Vector2f;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Objects;

public class Sprite {

    private BufferedImage SPRITE_SHEET = null; // imaginea pentru sprite

    private BufferedImage[][] spriteArray; // tabloul de subImagini al sprite-ului

    private final int TILE_SIZE = 32;

    public int w;
    public int h;

    private int wSprite; // numar coloane SpriteSheet
    private int hSprite; // numar linii SpriteSheet

    public Sprite(String file) {
        w = TILE_SIZE;
        h = 48;

        System.out.println("Loading: " + file + ". . .");
        SPRITE_SHEET = loadSprite(file);

        wSprite = SPRITE_SHEET.getWidth() / w;
        hSprite = SPRITE_SHEET.getHeight() / h;
        loadSpriteArray();
    }


    public Sprite(String file, int w, int h) {
        this.w = w;
        this.h = h;
        System.out.println("Loading: " + file + ". . .");
        SPRITE_SHEET = loadSprite(file);

        wSprite = SPRITE_SHEET.getWidth() / w;
        hSprite = SPRITE_SHEET.getHeight() / h;

        loadSpriteArray();
    }

    public Sprite(String file, int w, int h, boolean stat) {
        this.w = w;
        this.h = h;
        System.out.println("Loading: " + file + ". . .");
        SPRITE_SHEET = loadSprite(file);

        wSprite = SPRITE_SHEET.getWidth() / w;
        hSprite = SPRITE_SHEET.getHeight() / h;

        if(stat) {
            loadSpriteArray();
        }

    }

    public void setSize(int width, int height) {
        setWidth(width);
        setHeight(height);
    }

    public void setWidth(int width) {
        w = width;
        wSprite = SPRITE_SHEET.getWidth() / w;
    }

    public void setHeight(int height) {
        h = height;
        hSprite = SPRITE_SHEET.getHeight() / h;
    }

    public int getW() {
        return w;
    }

    public int getH() {
        return h;
    }

    private BufferedImage loadSprite(String file) {
        BufferedImage sprite = null;
        try {
            sprite = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream(file)));
        } catch (Exception e) {
            System.out.println("ERROR: could not load file: " + file);
        }
        return sprite;
    }

    // imi imparte imaginea in wSprite * hSprite subimagini
    public void loadSpriteArray() {
        spriteArray = new BufferedImage[hSprite][wSprite];
        for(int y = 0; y < hSprite; y++)
            for(int x = 0; x < wSprite; x++)
                spriteArray[y][x] = getSprite(x, y);
    }

    public BufferedImage getSPRITE_SHEET() { return SPRITE_SHEET; }

    public BufferedImage getSprite(int x, int y) {
        return SPRITE_SHEET.getSubimage(x*w, y*h, w, h);
    }

    public BufferedImage[] getSpriteArray(int i) {
        return  spriteArray[i];
    }

    public BufferedImage[][] getSpriteArray2() {
        return spriteArray;
    }

    public static void drawArray(Graphics2D g, ArrayList<BufferedImage> img, Vector2f pos, int width, int height, int xOffset, int yOffset) {
        float x = pos.x;
        float y = pos.y;

        for(int i = 0; i < img.size(); i++) {
            if(img.get(i) != null) {
                g.drawImage(img.get(i), (int) x, (int) y, width, height, null);
            }
            x += xOffset;
            y += yOffset;
        }
    }

    // functie desen pentru tablou de imagini
    public static void drawArray(Graphics2D g, ArrayList<BufferedImage> img, int bound, Vector2f pos, int width, int height, int xOffset, int yOffset) {
        float x = pos.x;
        float y = pos.y;

        for(int i = 0; i < img.size(); i++) {
            if(img.get(i) != null) {
                g.drawImage(img.get(i), (int) x, (int) y, width, height, null);
            }
            x += xOffset;
            y += yOffset;
        }
    }

    // functie desen pentru text
    public static void drawArray(Graphics2D g, Font f, String word, Vector2f pos, int width, int height, int xOffset, int yOffset) {
        float x = pos.x;
        float y = pos.y;
        for(int i = 0; i < word.length(); i++) {
            if(word.charAt(i) != 32)
                 g.drawImage(f.getFont(word.charAt(i)), (int) x, (int) y, width, height, null);
            x += xOffset;
            y += yOffset;
        }
    }

}
