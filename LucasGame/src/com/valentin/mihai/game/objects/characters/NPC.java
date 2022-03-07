package com.valentin.mihai.game.objects.characters;

import com.valentin.mihai.game.graphics.Font;
import com.valentin.mihai.game.graphics.Sprite;
import com.valentin.mihai.game.hub.HUD;
import com.valentin.mihai.game.objects.Entity;
import com.valentin.mihai.game.objects.EntityHandler;
import com.valentin.mihai.game.objects.ID;
import com.valentin.mihai.game.states.GameStateManager;
import com.valentin.mihai.game.util.Vector2f;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.time.DayOfWeek;

public class NPC extends Entity {

    private String name;

    private String messageL1;
    private String messageL2;

    private BufferedImage cloud;
    private BufferedImage key;

    private Entity player;

    private int visits = 0;
    private int encounters = 0;
    private boolean mission = false;

    private Font font;

    public NPC(EntityHandler handler, ID id, Sprite sprite, Vector2f origin, int width, int height, String name) {
        super(handler, id, sprite, origin, width, height);
        this.name = name;

        cloud = loadImage("res/entity/text/messageBubble2.png");

        key = loadImage("res/entity/items/key3.png");

        font = new Font("res/font/smallFont.png", 8, 12, 32);
        setAnimation(DOWN, sprite.getSpriteArray(DOWN), 10);
      //  messageL1 = "Are you Lucas?";
      //  messageL2 = "I am Jorah";

    }

    public void update() {

        if(HUD.inventory.containsKey(ID.Remains)) {
            mission = true;
        }

        if(HUD.timer > 200) {
            messageL1 = null;
            messageL2 = null;
        }

        if(HUD.timer > 400) {
            visits = 0;
        }

      if(bounds != null)
        for(Entity tempEntity : handler.entities) {
            if(tempEntity.getId() == ID.Player &&
                    tempEntity.isAction() &&
                    bounds.collides(tempEntity.getHitBounds()) &&
                    HUD.timer > 100 &&
                    !mission) {
                if(encounters == 0)
                switch (visits) {
                    case 0:
                        messageL1 = "Lucas? The Prince??";
                        messageL2 = "";
                        visits ++;
                        HUD.timer = 0;
                        break;
                    case 1:
                        messageL1 = "I was Jorah,";
                        messageL2 = "the blacksmith.";
                        visits ++;
                        HUD.timer = 0;
                        break;
                    case 2:
                        messageL1 = "I wanna escape from";
                        messageL2 = "this horrible place";
                        visits ++;
                        HUD.timer = 0;
                        break;
                    case 3:
                        messageL1 = "But first I need to";
                        messageL2 = "find something...";
                        visits ++;
                        HUD.timer = 0;
                        break;
                    case 4:
                        messageL1 = "My brother's remains,";
                        messageL2 = "hidden in this room...";
                        visits ++;
                        HUD.timer = 0;
                        break;
                    case 5:
                        messageL1 = "..I know you want";
                        messageL2 = "to find your wife.";
                        visits ++;
                        HUD.timer = 0;
                        break;
                    case 6:
                        messageL1 = "So if you help me";
                        messageL2 = "I will grant you";
                        visits ++;
                        HUD.timer = 0;
                        break;
                    case 7:
                        messageL1 = "Access to the next";
                        messageL2 = "room of the castle.";
                        visits ++;
                        HUD.timer = 0;
                        break;
                    case 8:
                        messageL1 = "Good luck! I'll wait";
                        messageL2 = "for you here.";
                        visits = 0;
                        encounters ++;
                        HUD.timer = 0;
                        break;
                } else if(encounters == 1) {
                    switch (visits) {
                        case 0:
                            messageL1 = "Go on, try to find";
                            messageL2 = "my brother's remains";
                            visits ++;
                            HUD.timer = 0;
                            break;
                        case 1:
                            messageL1 = "I will help you if";
                            messageL2 = "you succeed.";
                            visits ++;
                            HUD.timer = 0;
                            break;
                        case 2:
                            messageL1 = "So long...";
                            messageL2 = "";
                            visits = 0;
                            HUD.timer = 0;
                            break;

                    }
                }

            } else if(tempEntity.getId() == ID.Player &&
                    tempEntity.isAction() &&
                    bounds.collides(tempEntity.getHitBounds()) &&
                    HUD.timer > 100 &&
                    mission) {
                switch (visits) {
                    case 0:
                        messageL1 = "You found them";
                        messageL2 = "...";
                        visits ++;
                        GameStateManager.soundtrack.get("pickUp").play();
                        HUD.inventory.remove(ID.Remains);
                        HUD.timer = 0;
                        break;
                    case 1:
                        messageL1 = "I am so happy,";
                        messageL2 = "I can leave in peace.";
                        visits ++;
                        HUD.timer = 0;
                        break;
                    case 2:
                        messageL1 = "Here, take this";
                        messageL2 = "I wish you luck";
                        HUD.inventory.put(ID.Key3, key);
                        GameStateManager.soundtrack.get("pickUp").play();
                        visits ++;
                        HUD.timer = 0;
                        break;
                    case 3:
                        messageL1 = null;
                        messageL2 = null;
                        bounds = null;
                        break;

                }
            }

        }

    }

    @Override
    public void render(Graphics2D g) {
        if(bounds != null) {
            g.drawImage(ani.getImage(), (int) (pos.getWorldVar().x), (int) (pos.getWorldVar().y), width, height, null);
            Sprite.drawArray(g, font, name, new Vector2f(pos.getWorldVar().x - 12, pos.getWorldVar().y - 20), 16, 24, 12, 0);
        }

        if(messageL1 != null && messageL2 != null) {
            g.drawImage(cloud, (int)(pos.getWorldVar().x - 120), (int) (pos.getWorldVar().y - 150), 260, 127, null);
            Sprite.drawArray(g, font, messageL1, new Vector2f(pos.getWorldVar().x - 105, pos.getWorldVar().y - 125) , 16, 24, 12, 0);
            Sprite.drawArray(g, font, messageL2, new Vector2f(pos.getWorldVar().x - 105, pos.getWorldVar().y - 101) , 16, 24, 12, 0);
        }

    }


}
