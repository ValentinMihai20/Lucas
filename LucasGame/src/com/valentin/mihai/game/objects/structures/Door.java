package com.valentin.mihai.game.objects.structures;

import com.valentin.mihai.game.hub.HUD;
import com.valentin.mihai.game.hub.Sound;
import com.valentin.mihai.game.objects.Entity;
import com.valentin.mihai.game.objects.EntityHandler;
import com.valentin.mihai.game.objects.ID;
import com.valentin.mihai.game.states.GameStateManager;
import com.valentin.mihai.game.util.AABB;
import com.valentin.mihai.game.util.Vector2f;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.HashMap;

public class Door extends Entity {

    private BufferedImage image;
    private ID[] rightKeysId;
    private boolean open = false;

    public Door(EntityHandler handler, String filePath, ID id, Vector2f origin, int width, int height, ID[] code) {
        super(handler, filePath, id, origin, width, height);
        image = loadImage(filePath);

        rightKeysId = code;

    }


    public void update() {

        if(HUD.inventory.containsKey(rightKeysId[0]) && HUD.inventory.containsKey(rightKeysId[1])) {
            open = true;
        }

        for(Entity tempEntity : handler.entities) {
            if(tempEntity.getId() == ID.Player &&
                    open && tempEntity.isAction() &&
                    bounds.collides(tempEntity.getHitBounds())) {

                try {

                    GameStateManager.soundtrack.get("levelUp").play();
                    Thread.sleep(1000);
                    if(GameStateManager.currentLevel == GameStateManager.LEVEL1){
                        GameStateManager.next = true;
                        GameStateManager.soundtrack.get("playTrack").stop();
                    }
                    else if(GameStateManager.currentLevel == GameStateManager.LEVEL2){
                        GameStateManager.next = true;
                        GameStateManager.soundtrack.get("level2track").stop();
                    }
                    else if(GameStateManager.currentLevel == GameStateManager.LEVEL3){
                        GameStateManager.end = true;
                        GameStateManager.soundtrack.get("level3track").stop();
                    }


                } catch(Exception e) {
                    e.printStackTrace();
                }

            }else if(tempEntity.getId() == ID.Player &&
                    !open && tempEntity.isAction() &&
                    bounds.collides(tempEntity.getHitBounds()) && HUD.timer >= 100) {
                HUD.timer = 0;
                GameStateManager.soundtrack.get("lockedDoor").play();
            }
        }

    }

    @Override
    public void render(Graphics2D g) {
        g.drawImage(image,(int) (pos.getWorldVar().x), (int) (pos.getWorldVar().y), width, height, null );
    }
}
