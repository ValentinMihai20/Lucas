package com.valentin.mihai.game.objects.items;

import com.valentin.mihai.game.objects.Entity;
import com.valentin.mihai.game.objects.EntityHandler;
import com.valentin.mihai.game.objects.ID;
import com.valentin.mihai.game.util.Vector2f;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Key extends Entity {

    private BufferedImage image;
    private int code;

    public Key(EntityHandler handler,  String filePath, ID id, Vector2f origin, int width, int height) {
        super(handler, filePath, id, origin, width, height);
        image = loadImage(filePath);

        switch(filePath) {
            case "entity/items/key1.png":
                code = 0;
                break;
            case "entity/items/key2.png":
                code = 1;
                break;
            case "entity/items/key3.png":
                code = 2;
                break;
            case "entity/items/key4.png":
                code = 3;
                break;
            default:
                System.out.println("No such item.");
                break;
        }


    }

    public int getCode() {
        return code;
    }


    @Override
    public void update() {

    }

    @Override
    public void render(Graphics2D g) {
        g.setColor(Color.green);
        g.drawImage(image,(int) (pos.getWorldVar().x), (int) (pos.getWorldVar().y), width, height, null );
        g.drawRect((int) ((pos.getWorldVar().x) + bounds.getXOffset()), (int) ((pos.getWorldVar().y) + bounds.getYOffset()), (int)bounds.getWidth(), (int)bounds.getHeight());

    }
}


