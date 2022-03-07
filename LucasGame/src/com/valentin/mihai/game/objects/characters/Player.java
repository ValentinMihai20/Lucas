package com.valentin.mihai.game.objects.characters;

import com.valentin.mihai.game.graphics.Sprite;
import com.valentin.mihai.game.hub.HUD;
import com.valentin.mihai.game.objects.Entity;
import com.valentin.mihai.game.objects.EntityHandler;
import com.valentin.mihai.game.objects.ID;
import com.valentin.mihai.game.states.GameStateManager;
import com.valentin.mihai.game.states.levels.Level1;
import com.valentin.mihai.game.states.levels.Level2;
import com.valentin.mihai.game.states.levels.Level3;
import com.valentin.mihai.game.states.orchestration.PracticeToolState;
import com.valentin.mihai.game.util.KeyHandler;
import com.valentin.mihai.game.util.MouseHandler;
import com.valentin.mihai.game.util.Vector2f;

import java.awt.*;

public class Player extends Entity {

    private int state;

    private Vector2f spawn;

    public Player(EntityHandler handler, ID id, Sprite sprite, Vector2f origin, int width, int height, int state) {
        super(handler,id, sprite, origin, width, height);
        this.state = state;

        switch (state) {
            case GameStateManager.LEVEL1:
                spawn = new Vector2f(140, 700);
                break;
            case GameStateManager.LEVEL2:
                spawn = new Vector2f(380, 700);
                break;
            case GameStateManager.LEVEL3:
                spawn = new Vector2f(100, 700);
                break;
        }


         maxSpeed = 2.3f ;
         acc = 1f;
         deacc = 0.8f;

         // setam marginile sa fie la picioare
         bounds.setWidth(25);
         bounds.setHeight(20);
         bounds.setXOffset(4);
         bounds.setYOffset(30);

         // marim limitele de atingere
         hitBounds.setHeight(55);
         hitBounds.setWidth(40);
         hitBounds.setXOffset(-5);

    }

/*
    public void move() {
        if(up) {
            dy = -2;
        } else if (down){
            dy = 2;
        } else {
            dy = 0;
        }

        if(left) {
            dx = -2;
        } else if(right){
            dx = 2;
        } else {
            dx = 0;
        }
    }
 */

    public void update() {
        super.update();
        move();

        if (!bounds.collisionTile(dx, 0)) {
            pos.x += dx;
        }

        if (!bounds.collisionTile(0, dy)) {
            pos.y += dy;

            switch(state) {
                case GameStateManager.LEVEL1:
                    //Level1.map.y += dy;
                    break;
                    case GameStateManager.PRACTICE:
                        //PracticeToolState.map.y += dy;
                        break;
                case GameStateManager.LEVEL2:
                    //Level2.map.y += dy;
                    break;
            }
        }
        if( hitCollision(ID.DumbEnemy)  || hitCollision(ID.SmartEnemy) ) {

            GameStateManager.soundtrack.get("hit").play();
            HUD.HEALTH--;
            pos.x = spawn.x;
            pos.y = spawn.y;

            switch(state) {
                case GameStateManager.LEVEL1:
                    //Level1.map.y = 100;
                    break;
                    case GameStateManager.PRACTICE:
                       // PracticeToolState.map.y = 0;
                        break;
                case GameStateManager.LEVEL2:
                    //Level2.map.y = 100;
                    break;

            }

        }

        if(collision(ID.Chest) || collision(ID.JorahNPC)
            || collision(ID.Fountain) || collision(ID.MoraxNPC) || collision(ID.WifeNPC)) {

              pos.x -= dx;
              pos.y -= dy;
            switch(state) {
                case GameStateManager.LEVEL1:
                    //Level1.map.y -= dy;
                    break;
                case GameStateManager.PRACTICE:
                    //PracticeToolState.map.y -= dy;
                    break;
                case GameStateManager.LEVEL2:
                    //Level2.map.y -= dy;
                    break;
            }

        }



    }


    @Override
    public void render(Graphics2D g) {
        g.setColor(Color.blue);
        //g.drawRect((int) ((pos.getWorldVar().x) + bounds.getXOffset()), (int) ((pos.getWorldVar().y) + bounds.getYOffset()), (int)bounds.getWidth(), (int)bounds.getHeight());
        g.setColor(Color.MAGENTA);
       // g.drawRect((int) ((pos.getWorldVar().x) + hitBounds.getXOffset()), (int) ((pos.getWorldVar().y) + hitBounds.getYOffset()), (int)hitBounds.getWidth(), (int)hitBounds.getHeight());
        g.drawImage(ani.getImage(), (int) (pos.getWorldVar().x), (int) (pos.getWorldVar().y), width, height, null);
    }

    public void input(KeyHandler key, MouseHandler mouse) {

        if (mouse.getButton() == 1) {
            System.out.println("Player: " + pos.x + ", " + pos.y);
        }

        if(escape) System.exit(0);

        up = key.up.down;
        down = key.down.down;
        left = key.left.down;
        right = key.right.down;

        action = key.action.down;

        escape = key.escape.down;

    }

    // miscarea personajuli
    public void move() {
        if(up) {
            dy -= acc;
            if(dy < -maxSpeed) {
                dy = -maxSpeed;
            }
            down = false;
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
            up = false;
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
            right = false;
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
            left = false;
        } else {
            if(dx > 0) {
                dx -= deacc;
                if(dx < 0) {
                    dx = 0;
                }
            }
        }
    }

    public void setSpawn(Vector2f spawn) {
        this.spawn = spawn;
    }

}
