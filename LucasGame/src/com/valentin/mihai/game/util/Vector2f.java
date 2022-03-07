package com.valentin.mihai.game.util;

public class Vector2f { // puncte din planul jocului

    public float x;
    public float y;

    // coordonatele lumii
    public static float worldX;
    public static float worldY;

    public Vector2f() {
        x = 0;
        y = 0;
    }

    public Vector2f(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public Vector2f(Vector2f vec) {
        new Vector2f(vec.x, vec.y);
    }

    public void addX(float f) { x += f; }
    public void addY(float f) { y += f; }

    public void setX(float f) { x = f; }
    public void setY(float f) { y = f; }

    public void setVector(Vector2f vec) {
        this.x = vec.x;
        this.y = vec.y;
    }

    public void setVector(float x, float y) {
        this.x = x;
        this.y = y;
    }

    // seteaza pozitia curenta a view-ului
    public static void setWorldXY(float x, float y) {
        worldX = x;
        worldY = y;
    }

    public Vector2f getWorldVar() {
        return new Vector2f(x - worldX, y - worldY);
    }

    @Override
    public String toString() {
        return x + ", " + y ;
    }

}
