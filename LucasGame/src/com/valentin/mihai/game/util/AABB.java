package com.valentin.mihai.game.util;

import com.valentin.mihai.game.objects.Entity;
import com.valentin.mihai.game.tiles.TileMapObject;

public class AABB { // coliziuni

    private Vector2f pos;

    private float xOffset = 0; // deplasare pe Ox
    private float yOffset = 0; // deplasare pe Oy

    private float w; // latime
    private float h; // inaltime

    private float r; // raza

    private int size;

    private Entity entity;

    // constructor pentru box de tip rectangle
    public AABB(Vector2f pos, int w, int h) {
        this.pos = pos;
        this.w = w;
        this.h = h;

        size = Math.max(w,h);
    }

    // constructor pentru box de tip circle
    public AABB(Vector2f pos, int r) {
        this.pos = pos;
        this.r = r;
        size = r;
    }

    public Vector2f getPos() {
        return pos;
    }

    public float getRadius() {
        return r;
    }

    public float getWidth() {
        return w;
    }

    public float getHeight() {
        return h;
    }

    public void setBox(Vector2f pos, int w, int h) {
        this.pos = pos;
        this.w = w;
        this.h = h;
        size = Math.max(w, h);
    }

    public void setCircle(Vector2f pos, int r) {
        this.pos = pos;
        this.r = r;
        size = r;
    }

    public void setWidth(float f) {
        w = f;
    }

    public void setHeight(float f) { h = f; }

    public void setXOffset(float f) { xOffset = f; }

    public void setYOffset(float f) { yOffset = f; }

    public boolean collides2(AABB bBox) {
        float ax = ((pos.getWorldVar().x + (xOffset)) + (w / 2));
        float ay = ((pos.getWorldVar().y + (yOffset)) + (h / 2));
        float bx = ((bBox.pos.getWorldVar().x + (bBox.xOffset)) + (bBox.w / 2));
        float by = ((bBox.pos.getWorldVar().y + (bBox.yOffset)) + (bBox.h / 2));

        if(Math.abs(ax-bx) < (this.w / 2) + (bBox.w / 2)) {
            if(Math.abs(ay-by) < (this.h / 2) + (bBox.h / 2)) {
                return true;
            }
        }
        return false;
    }

    // functii care verifica daca doua dreptunghiuri se intersecteaza

    public boolean collides(AABB bBox) {

        float ax = ((pos.getWorldVar().x + (xOffset)));
        float ay = ((pos.getWorldVar().y + (yOffset)));
        float bx = ((bBox.pos.getWorldVar().x + (bBox.xOffset)));
        float by = ((bBox.pos.getWorldVar().y + (bBox.yOffset)));

        if(ax < bx + bBox.w &&
                ax + w > bx &&
                ay < by + bBox.h &&
                ay + h > by)
            return true;

        return false;
    }


    // verificare coliziune pentru cercuri
    public boolean colCircleBox(AABB aBox) {

        float dx = Math.max(aBox.getPos().getWorldVar().x + aBox.getXOffset(), Math.min(pos.getWorldVar().x + (r/2), aBox.getPos().getWorldVar().x + aBox.getXOffset() + aBox.getWidth()));
        float dy = Math.max(aBox.getPos().getWorldVar().y + aBox.getYOffset(), Math.min(pos.getWorldVar().y + (r/2), aBox.getPos().getWorldVar().y + aBox.getYOffset() + aBox.getHeight()));

        dx = pos.getWorldVar().x + r/2 - dx;
        dy = pos.getWorldVar().y + r/2 - dy;

        if(Math.sqrt(dx*dx + dy*dy) < r / 2) {
            return true;
        }

        return false;

    }

    //functie booleana care verifica orice coliziune cu tile-urile din TopLayer

    public boolean collisionTile(float ax, float ay) {
        for(int c = 0; c < 4; c++) {

            int xt = (int) ( (pos.x + ax) + (c % 2) * w + xOffset) / 16;
            int yt = (int) ( (pos.y + ay) + ((c / 2)) * h + yOffset) / 16;

            String key = String.valueOf(xt) + "," + String.valueOf(yt);
            if(TileMapObject.tmo_blocks.containsKey(key)) {
                return TileMapObject.tmo_blocks.get(key).update(this);
            }

        }

        return false;

    }

    public float getXOffset() {
        return xOffset;
    }

    public float getYOffset() {
        return yOffset;
    }

}
