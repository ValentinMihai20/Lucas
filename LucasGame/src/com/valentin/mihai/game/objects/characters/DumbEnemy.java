package com.valentin.mihai.game.objects.characters;

import com.valentin.mihai.game.graphics.Sprite;
import com.valentin.mihai.game.objects.Entity;
import com.valentin.mihai.game.objects.EntityHandler;
import com.valentin.mihai.game.objects.ID;
import com.valentin.mihai.game.util.Vector2f;

import java.awt.*;

public class DumbEnemy extends Entity {

    private int mode;

    public DumbEnemy(EntityHandler handler, ID id, Sprite sprite, Vector2f origin, int width, int height, int mode) {
        super(handler, id, sprite, origin, width, height);
        maxSpeed = 3f ;
        acc = 1f;
        deacc = 0.8f;

        this.mode = mode;

        bounds.setWidth(55);
        bounds.setHeight(45);
        bounds.setXOffset(-10);
        bounds.setYOffset(20);

        // moduri de initializare a inamicului
        switch (mode) {
            case 1:
                down = true;
                break;
            case 2:
                up = true;
                bounds.setHeight(135);
                bounds.setWidth(175);
                bounds.setXOffset(-70);
                bounds.setYOffset(-35);
                break;
            case 3:
                up = true;
                bounds.setWidth(25);
                bounds.setHeight(20);
                bounds.setXOffset(4);
                bounds.setYOffset(30);
                break;
            case 4:
                down = true;
                bounds.setWidth(25);
                bounds.setHeight(20);
                bounds.setXOffset(4);
                bounds.setYOffset(30);
                break;
            case 5:
                left = true;
                break;
            case 6:
                right = true;
                break;
            default:
                break;

        }


    }


    public void update() {
        super.update();

        move();
        if (!bounds.collisionTile(dx, 0)) {
            pos.x += dx;
        }

        if (!bounds.collisionTile(0, dy)) {
            pos.y += dy;
        }

        // moduri de miscare a inamicului
        switch (mode) {
            case 1:
            if (bounds.collisionTile(dx, 0) && left) {
                up = false;
                down = true;
                right = false;
                left = false;

            } else if (bounds.collisionTile(dx, 0) && right) {
                up = true;
                down = false;
                right = false;
                left = false;
            }

            if (bounds.collisionTile(0, dy) && down) {

                up = false;
                down = false;
                right = true;
                left = false;

            } else if (bounds.collisionTile(0, dy) && up) {
                up = false;
                down = false;
                right = false;
                left = true;
            }
            break;
            case 2:
                if (bounds.collisionTile(dx, 0) && left) {
                    up = true;
                    down = false;
                    right = false;
                    left = false;

                } else if (bounds.collisionTile(dx, 0) && right) {
                    up = false;
                    down = true;
                    right = false;
                    left = false;
                }
                if (bounds.collisionTile(0, dy) && down) {

                    up = false;
                    down = false;
                    right = false;
                    left = true;

                } else if (bounds.collisionTile(0, dy) && up) {
                    up = false;
                    down = false;
                    right = true;
                    left = false;
                }
                break;
            case 3:
            case 4:
                if (bounds.collisionTile(0, dy) && down) {
                    up = true;
                    down = false;

                } else if (bounds.collisionTile(0, dy) && up) {
                    down = true;
                    up = false;
                }
                break;
            case 5:
            case 6:
                if (bounds.collisionTile(dx, 0) && left) {
                   right = true;
                   left = false;

                } else if (bounds.collisionTile(dx, 0) && right) {
                    right = false;
                    left = true;
                }
                break;


        }


    }



    @Override
    public void render(Graphics2D g) {
        g.setColor(Color.red);
      //  g.drawRect((int) ((pos.getWorldVar().x) + bounds.getXOffset()), (int) ((pos.getWorldVar().y) + bounds.getYOffset()), (int)bounds.getWidth(), (int)bounds.getHeight());
       // g.drawRect((int) ((pos.getWorldVar().x) + hitBounds.getXOffset()), (int) ((pos.getWorldVar().y) + hitBounds.getYOffset()), (int)hitBounds.getWidth(), (int)hitBounds.getHeight());
        g.drawImage(ani.getImage(), (int) (pos.getWorldVar().x), (int) (pos.getWorldVar().y), width, height, null);
    }


    public int getMode() {
        return mode;
    }

    public void setMode(int mode) {
        this.mode = mode;
    }

    public void move() {
        if(up) {
            dy -= acc;
            if(dy < -maxSpeed) {
                dy = -maxSpeed;
            }
        } else {
            if(dy < 0) {
                dy += deacc;
                if(dy > 0) {
                    dy = 0;
                }
            }
        }
        if(down) {
            dy += acc;
            if(dy > maxSpeed) {
                dy = maxSpeed;
            }
        } else {
            if(dy > 0) {
                dy -= deacc;
                if(dy < 0) {
                    dy = 0;
                }
            }
        }
        if(left) {
            dx -= acc;
            if(dx < -maxSpeed) {
                dx = -maxSpeed;
            }
        } else {
            if(dx < 0) {
                dx += deacc;
                if(dx > 0) {
                    dx = 0;
                }
            }
        }
        if(right) {
            dx += acc;
            if(dx > maxSpeed) {
                dx = maxSpeed;
            }
        } else {
            if(dx > 0) {
                dx -= deacc;
                if(dx < 0) {
                    dx = 0;
                }
            }
        }
    }


}
