package com.valentin.mihai.game.objects;

import com.valentin.mihai.game.graphics.Animation;
import com.valentin.mihai.game.graphics.Sprite;
import com.valentin.mihai.game.hub.Observable;
import com.valentin.mihai.game.hub.Sound;
import com.valentin.mihai.game.util.AABB;
import com.valentin.mihai.game.util.KeyHandler;
import com.valentin.mihai.game.util.MouseHandler;
import com.valentin.mihai.game.util.Vector2f;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;
import java.util.Objects;

public abstract class Entity extends Observable {

    public EntityHandler handler;

    // directii de deplasare pentru animatii
    protected final int UP = 3;
    protected final int DOWN = 0;
    protected final int RIGHT = 2;
    protected final int LEFT = 1;
    protected int currentAnimation;

    protected Animation ani; // animatia
    protected Sprite sprite;
    protected Vector2f pos;
    protected ID id;

    protected int width;
    protected int height;

    // valori booleene pentru stabilirea tastelor apasate
    protected boolean up;
    protected boolean down;
    protected boolean right;
    protected boolean left;
    protected boolean action;
    protected boolean escape;
    protected boolean attack;

    protected int attackSpeed;
    protected int attackDuration;

    // viteze
    protected float dx;
    protected float dy;

    // acceleratie si viteza maxima
    protected float maxSpeed;
    protected float acc;
    protected float deacc;

    // dreptunghiurile de coliziuni
    protected AABB hitBounds;
    protected AABB bounds;


    public Entity(EntityHandler handler, ID id, Sprite sprite, Vector2f origin, int width, int height) {
        this.handler = handler;
        this.sprite = sprite;
        pos = origin;
        this.width = width;
        this.height = height;
        this.id = id;

        bounds = new AABB(pos, width, height);
        hitBounds = new AABB(pos, width, height);



        ani = new Animation();
        setAnimation(LEFT, sprite.getSpriteArray(LEFT), 10); // se initializeaza personajul cu orientare la stanga
    }

    public Entity(EntityHandler handler, String filePath, ID id, Vector2f origin, int width, int height) {
        this.handler = handler;
        pos = origin;
        this.width = width;
        this.height = height;
        this.id = id;

        bounds = new AABB(origin, width, height);
        hitBounds = new AABB(new Vector2f(origin.x + (width / 2), origin.y), width, height);
    }

    public void setAnimation(int i, BufferedImage[] frames, int delay) {
        currentAnimation = i;
        ani.setFrames(frames);
        ani.setDelay(delay);
    }

    public Animation getAnimation() {
        return ani;
    }

    // Sets the sprite to something else.
    public void setSprite(Sprite sprite) {
        this.sprite = sprite;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public void setMaxSpeed(float f) {
        maxSpeed = f;
    }

    public void setAcc(float f) {
        acc = f;
    }

    public void setDeacc(float f) {
        deacc = f;
    }

    public AABB getBounds() { return bounds; }

    public AABB getHitBounds() {
        return hitBounds;
    }

    public EntityHandler getHandler() {
        return handler;
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

    public boolean collision(ID id) {

        for(int i = 0; i < handler.entities.size(); i++) {
            if(handler.entities.get(i).getBounds() != null)
            if(handler.entities.get(i).getId() == id && handler.entities.get(i).getBounds().collides(bounds)) {
                return true;
            }
        }
        return false;
    }

    public boolean hitCollision(ID id) {

        for(int i = 0; i < handler.entities.size(); i++) {
            if(handler.entities.get(i).getHitBounds() != null)
            if(handler.entities.get(i).getId() == id && handler.entities.get(i).getHitBounds().collides(bounds)) {
                return true;
            }
        }
        return false;
    }


    public void setTouchBoxDirection() {
        if (up) {
            hitBounds.setYOffset(-height / 2);
            hitBounds.setXOffset(-width / 2);
        } else if (down) {
            hitBounds.setYOffset(height / 2);
            hitBounds.setXOffset(-width / 2);
        } else if (left) {
            hitBounds.setYOffset(0);
            hitBounds.setXOffset(-width);
        } else if (right) {
            hitBounds.setYOffset(0);
            hitBounds.setXOffset(0);
        }

    }

    public void animate() {
        if (up) {
            if (currentAnimation != UP || ani.getDelay() == -1) {
                setAnimation(UP, sprite.getSpriteArray(UP), 5);
            }
        } else if (down) {
            if (currentAnimation != DOWN || ani.getDelay() == -1) {
                setAnimation(DOWN, sprite.getSpriteArray(DOWN), 5);
            }
        } else if (left) {
            if (currentAnimation != LEFT || ani.getDelay() == -1) {
                setAnimation(LEFT, sprite.getSpriteArray(LEFT), 5);
            }
        } else if (right) {
            if (currentAnimation != RIGHT || ani.getDelay() == -1) {
                setAnimation(RIGHT, sprite.getSpriteArray(RIGHT), 5);
            }
        } else {
            setAnimation(currentAnimation, sprite.getSpriteArray(currentAnimation), -1);
        }
    }

    public void update() {
        animate();
      //  setTouchBoxDirection();
        ani.update();
    }

    public abstract void render(Graphics2D g);

    public void input(KeyHandler key, MouseHandler mouse) {

    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public Vector2f getPos() {
        return pos;
    }

    public ID getId() {
        return id;
    }

    public boolean isAction() {
        return action;
    }
}
