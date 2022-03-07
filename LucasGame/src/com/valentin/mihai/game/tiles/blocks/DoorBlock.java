package com.valentin.mihai.game.tiles.blocks;

import com.valentin.mihai.game.util.AABB;
import com.valentin.mihai.game.util.Vector2f;

import java.awt.*;
import java.awt.image.BufferedImage;

public class DoorBlock extends Block {

    public DoorBlock(BufferedImage img, Vector2f pos, int w, int h) {
        super(img, pos, w, h);
    }

    @Override
    public boolean update(AABB p) {
        return true;
    }

    @Override
    public void render(Graphics2D g) {
        super.render(g);
        g.setColor(Color.green);
        g.drawRect((int) pos.getWorldVar().x, (int) pos.getWorldVar().y, w, h);
    }

}
