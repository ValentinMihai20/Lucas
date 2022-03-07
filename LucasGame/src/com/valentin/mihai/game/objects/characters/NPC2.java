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

public class NPC2 extends Entity {

    private String name;

    private String messageL1;
    private String messageL2;
    
    private String[][] text;

    private BufferedImage cloud;
    private BufferedImage key;
    private ID prize;

    private int visits = 0;
    private int encounters = 0;

    private ID missionItem;
    private boolean mission = false;


    private Font font;

    public NPC2(EntityHandler handler, ID id, Sprite sprite, Vector2f origin, int width, int height, String name, String[][] text, ID missionItem, ID prize) {
        super(handler, id, sprite, origin, width, height);
        this.name = name;
        this.text = text;
        this.missionItem = missionItem;
        this.prize = prize;


        cloud = loadImage("res/entity/text/messageBubble2.png");

        switch (prize) {
            case Key1:
                key = loadImage("entity/items/key1.png");
                break;
            case Key2:
                key = loadImage("entity/items/key2.png");
                break;
            case Key3:
                key = loadImage("entity/items/key3.png");
                break;
            case Key4:
                key = loadImage("entity/items/key4.png");
                break;
            case WifeHeart:
                key = loadImage("res/entity/items/InimaWife.png");
                break;


        }


        font = new Font("res/font/smallFont.png", 8, 12, 32);

        if(name == "Jorah") {
            setAnimation(DOWN, sprite.getSpriteArray(DOWN), 10);
        }
        else if(name == "Morax"){
            setAnimation(RIGHT, sprite.getSpriteArray(RIGHT), 10);
        }
        else if(name == "WIFE") {
            setAnimation(DOWN, sprite.getSpriteArray(DOWN), 10);
        }




    }

    public void update() {

        if(HUD.inventory.containsKey(missionItem)) {
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
                        messageL1 = text[0][0];    // "Lucas? The Prince??";
                        messageL2 = text[0][1];
                        visits ++;
                        HUD.timer = 0;
                        break;
                    case 1:
                        messageL1 =  text[1][0];
                        messageL2 =  text[1][1];
                        visits ++;
                        HUD.timer = 0;
                        break;
                    case 2:
                        messageL1 =  text[2][0];
                        messageL2 =  text[2][1];
                        visits ++;
                        HUD.timer = 0;
                        break;
                    case 3:
                        messageL1 = text[3][0];
                        messageL2 = text[3][1];
                        visits ++;
                        HUD.timer = 0;
                        break;
                    case 4:
                        messageL1 = text[4][0];
                        messageL2 = text[4][1];
                        visits ++;
                        HUD.timer = 0;
                        break;
                    case 5:
                        messageL1 = text[5][0];
                        messageL2 = text[5][1];
                        visits ++;
                        HUD.timer = 0;
                        break;
                    case 6:
                        messageL1 = text[6][0];
                        messageL2 = text[6][1];
                        visits ++;
                        HUD.timer = 0;
                        break;
                    case 7:
                        messageL1 = text[7][0];
                        messageL2 = text[7][1];
                        visits ++;
                        HUD.timer = 0;
                        break;
                    case 8:
                        messageL1 = text[8][0];
                        messageL2 = text[8][1];
                        visits = 0;
                        encounters ++;
                        HUD.timer = 0;
                        break;
                } else if(encounters == 1) {
                    switch (visits) {
                        case 0:
                            messageL1 = text[9][0];
                            messageL2 = text[9][1];
                            visits ++;
                            HUD.timer = 0;
                            break;
                        case 1:
                            messageL1 = text[10][0];
                            messageL2 = text[10][1];
                            visits ++;
                            HUD.timer = 0;
                            break;
                        case 2:
                            messageL1 = text[11][0];
                            messageL2 = text[11][1];
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
                        messageL1 = text[12][0];
                        messageL2 = text[12][1];
                        visits ++;
                        GameStateManager.soundtrack.get("pickUp").play();
                        HUD.inventory.remove(missionItem);
                        HUD.timer = 0;
                        break;
                    case 1:
                        messageL1 = text[13][0];
                        messageL2 = text[13][1];
                        visits ++;
                        HUD.timer = 0;
                        break;
                    case 2:
                        messageL1 = text[14][0];
                        messageL2 = text[14][1];
                        HUD.inventory.put(prize, key);
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
            if (name == "Jorah") {
                g.drawImage(cloud, (int) (pos.getWorldVar().x - 120), (int) (pos.getWorldVar().y - 150), 260, 127, null);
                Sprite.drawArray(g, font, messageL1, new Vector2f(pos.getWorldVar().x - 105, pos.getWorldVar().y - 125), 16, 24, 12, 0);
                Sprite.drawArray(g, font, messageL2, new Vector2f(pos.getWorldVar().x - 105, pos.getWorldVar().y - 101), 16, 24, 12, 0);
            }
            else if (name == "Morax"){
                g.drawImage(cloud, (int) (pos.getWorldVar().x - 40), (int) (pos.getWorldVar().y - 150), 260, 127, null);
                Sprite.drawArray(g, font, messageL1, new Vector2f(pos.getWorldVar().x - 25, pos.getWorldVar().y - 125), 16, 24, 12, 0);
                Sprite.drawArray(g, font, messageL2, new Vector2f(pos.getWorldVar().x - 25, pos.getWorldVar().y - 101), 16, 24, 12, 0);
            }
            if (name == "WIFE") {
                g.drawImage(cloud, (int) (pos.getWorldVar().x - 190), (int) (pos.getWorldVar().y - 150), 260, 127, null);
                Sprite.drawArray(g, font, messageL1, new Vector2f(pos.getWorldVar().x - 175, pos.getWorldVar().y - 125), 16, 24, 12, 0);
                Sprite.drawArray(g, font, messageL2, new Vector2f(pos.getWorldVar().x - 175, pos.getWorldVar().y - 101), 16, 24, 12, 0);
            }
        }

    }


}
