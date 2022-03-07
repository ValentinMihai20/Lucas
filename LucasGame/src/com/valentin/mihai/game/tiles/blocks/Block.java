package com.valentin.mihai.game.tiles.blocks;

import com.valentin.mihai.game.util.AABB;
import com.valentin.mihai.game.util.Vector2f;

import java.awt.*;
import java.awt.image.BufferedImage;

public abstract class Block {

    protected int w; // latime block
    protected int h; // inaltime block

    protected BufferedImage img;
    protected Vector2f pos;

    public Block(BufferedImage img, Vector2f pos, int w, int h) {
        this.img = img;
        this.w = w;
        this.h = h;
        this.pos = pos;

    }

    public abstract boolean update(AABB p);

    public void render(Graphics2D g) {
        g.drawImage(img, (int) pos.getWorldVar().x, (int) pos.getWorldVar().y , w, h, null );
    }


}
