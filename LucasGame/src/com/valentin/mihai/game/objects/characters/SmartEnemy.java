package com.valentin.mihai.game.objects.characters;

import com.valentin.mihai.game.graphics.Sprite;
import com.valentin.mihai.game.objects.Entity;
import com.valentin.mihai.game.objects.EntityHandler;
import com.valentin.mihai.game.objects.ID;
import com.valentin.mihai.game.util.AABB;
import com.valentin.mihai.game.util.Vector2f;

import java.awt.*;

public class SmartEnemy extends Entity {


    private Entity player;

    private AABB aOE;
    private int radius;

    private Vector2f home;
    private AABB homeBox;


    public SmartEnemy(EntityHandler handler, ID id, Sprite sprite, Vector2f origin, int width, int height, Vector2f home, int radius) {
        super(handler, id, sprite, origin, width, height);
        this.home = home;
        this.radius = radius;
      //  radius = 300;

        for(Entity tempEntity : handler.entities) {
            if(tempEntity.getId() == ID.Player) this.player = tempEntity;
        }

        aOE = new AABB(new Vector2f(pos.x + width/2 - radius/2, pos.y + height/2 - radius/2), radius);
        homeBox = new AABB(home, 32, 32);


    }

    public void update() {
        super.update();

        if (!bounds.collisionTile(dx, 0)) {
            pos.x += dx;
            aOE.getPos().x += dx;
        }

        if (!bounds.collisionTile(0, dy)) {
            pos.y += dy;
            aOE.getPos().y += dy;
        }


        if(aOE.colCircleBox(player.getBounds())) { // if the player is in the area of effect
          //  moves to the player
            move();
        } else {
            moveHome();  // else, moves home
            if(bounds.collides(homeBox)) {
                dx = 0;
                dy = 0;
                up = false;
                down = false;
                left = false;
                right = false;
                setAnimation(DOWN, sprite.getSpriteArray(DOWN), 10);
            }
        }





    }


    @Override
    public void render(Graphics2D g) {
        g.drawImage(ani.getImage(), (int) (pos.getWorldVar().x), (int) (pos.getWorldVar().y), width, height, null);
        g.setColor(Color.MAGENTA);
       // g.drawOval((int)aOE.getPos().getWorldVar().x, (int)aOE.getPos().getWorldVar().y, radius, radius);
        g.setColor(Color.red);
      //  g.drawRect((int) ((pos.getWorldVar().x) + bounds.getXOffset()), (int) ((pos.getWorldVar().y) + bounds.getYOffset()), (int)bounds.getWidth(), (int)bounds.getHeight());

    }

    private void move() {

        float diffX = player.getPos().getWorldVar().x - pos.getWorldVar().x ;//+ width / 2;
        float diffY = player.getPos().getWorldVar().y - pos.getWorldVar().y ;//+ height / 2;
        float distance = (float) Math.sqrt(Math.pow(pos.getWorldVar().x - player.getPos().getWorldVar().x, 2) + Math.pow(pos.getWorldVar().y - player.getPos().getWorldVar().y, 2));

        dx = 1f/distance * diffX;
        dy = 1f/distance * diffY;

        direction();

    }

    private void moveHome() {
        float diffX = home.getWorldVar().x - pos.getWorldVar().x + width/2;
        float diffY = home.getWorldVar().y - pos.getWorldVar().y + height/2 ;
        float distance = (float) Math.sqrt(Math.pow(pos.getWorldVar().x - home.getWorldVar().x, 2) + Math.pow(pos.getWorldVar().y - home.getWorldVar().y, 2));

        dx = 1f/distance * diffX;
        dy = 1f/distance * diffY;

        direction();
    }


    private void direction() {

        if(dx >= 0 && dy >= 0 && dx > dy) {
            right = true;
            left = false;
            up = false;
            down = false;
        } else if (dx >= 0 && dy >= 0 && dx < dy) {
            right = false;
            left = false;
            up = false;
            down = true;
        } else if(dx >= 0 && dy <= 0 && dx > Math.abs(dy)) {
            right = true;
            left = false;
            up = false;
            down = false;
        } else if(dx >= 0 && dy <= 0 && dx < Math.abs(dy)) {
            right = false;
            left = false;
            up = true;
            down = false;
        } else if(dx <= 0 && dy <= 0 && dx < dy) {
            right = false;
            left = true;
            up = false;
            down = false;
        } else if(dx <= 0 && dy <= 0 && dx > dy) {
            right = false;
            left = false;
            up = true;
            down = false;
        } else if(dx <= 0 && dy >= 0 && dy > Math.abs(dx)) {
            right = false;
            left = false;
            up = false;
            down = true;
        } else if(dx <= 0 && dy >= 0 && dy < Math.abs(dx)) {
            right = false;
            left = true;
            up = false;
            down = false;
        }


    }



}
