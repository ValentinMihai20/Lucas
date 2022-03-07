package com.valentin.mihai.game.objects.structures;

import com.valentin.mihai.game.graphics.Sprite;
import com.valentin.mihai.game.objects.Entity;
import com.valentin.mihai.game.objects.EntityHandler;
import com.valentin.mihai.game.objects.ID;
import com.valentin.mihai.game.util.Vector2f;

import java.awt.*;
import java.awt.image.BufferedImage;

public class lockedChest extends Entity {

    private BufferedImage content;
    private ID contentId;
    private ID key;
    private int colour;

    public lockedChest(EntityHandler handler, ID id, Sprite sprite, Vector2f origin, int width, int height, float defX, float defY, ID contentId, ID key, int colour) {
        super(handler, id, sprite, origin, width, height);
        this.contentId = contentId;
        this.key = key;
        this.colour = colour;

        switch (contentId) {

            case Key1:
                content = loadImage("entity/items/key1.png");
                break;
            case Key2:
                content = loadImage("entity/items/key2.png");
                break;
            case Key3:
                content = loadImage("entity/items/key3.png");
                break;
            case Key4:
                content = loadImage("entity/items/key4.png");
                break;

        }


    }

    @Override
    public void render(Graphics2D g) {

    }

}
