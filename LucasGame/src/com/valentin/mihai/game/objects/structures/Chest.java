package com.valentin.mihai.game.objects.structures;

import com.valentin.mihai.game.hub.HUD;
import com.valentin.mihai.game.objects.Entity;
import com.valentin.mihai.game.objects.EntityHandler;
import com.valentin.mihai.game.objects.ID;
import com.valentin.mihai.game.states.GameStateManager;
import com.valentin.mihai.game.util.AABB;
import com.valentin.mihai.game.util.Vector2f;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Chest extends Entity {

    protected BufferedImage image; // imagine chest

    // date pentru continut
    protected BufferedImage content = null;
    protected AABB contentBounds;
    protected ID contentId;

    //deplarea fata de cufar a continutului
    protected float defX;
    protected float defY;

    private boolean empty = false;
    private boolean open = false;

    public Chest(EntityHandler handler, String filePath, ID id, Vector2f origin, int width, int height, float defX, float defY, int code) {
        // code 1 -- Key1
        // code 2 -- Key2
        // code 3 -- Key3
        // code 4 -- Key4
        super(handler, filePath, id, origin, width, height);
        this.defX = defX;
        this.defY = defY;

        image = loadImage(filePath);

        switch (code) {

            case 1:
                content = loadImage("entity/items/key1.png");
                contentId = ID.Key1;
                break;
            case 2:
                content = loadImage("entity/items/key2.png");
                contentId = ID.Key2;
                break;
            case 3:
                content = loadImage("entity/items/key3.png");
                contentId = ID.Key3;
                break;
            case 4:
                content = loadImage("entity/items/key4.png");
                contentId = ID.Key4;
                break;
            case 5:
                content = loadImage("entity/items/scheletu.png");
                contentId = ID.Remains;
                break;
            case 6:
                content = loadImage("entity/items/Cloak.png");
                contentId = ID.Cloak;
                break;
            case 7:
                content = loadImage("res/entity/items/MirrorLastLevel.png");
                contentId = ID.Mirror;
                break;

        }
    }

    @Override
    public void update() {

        if(content != null)
        for(Entity tempEntity : handler.entities) {
            if(tempEntity.getId() == ID.Player &&
            !empty && tempEntity.isAction() &&
            bounds.collides(tempEntity.getHitBounds())) { // interactiunea cu chestul

                try {

                    GameStateManager.soundtrack.get("open").play();
                    Thread.sleep(100);
                    GameStateManager.soundtrack.get("bonk").play();
                    open = true;
                    empty = true;
                    contentBounds = new AABB(new Vector2f((int) (pos.getWorldVar().x + defX*width), (int) (pos.getWorldVar().y + defY*height)), 32, 32);
                    HUD.timer = 0; // timer pentru a nu putea interactiona din nou imediat cu chestul

                } catch(Exception e) {
                    e.printStackTrace();
                }

            }else if(tempEntity.getId() == ID.Player &&
                    empty && tempEntity.isAction() &&
                    bounds.collides(tempEntity.getHitBounds()) && HUD.timer >= 250) {
                HUD.timer = 0;
                GameStateManager.soundtrack.get("locked").play();
            }
        }

        if(content == null)
        {
            for(Entity tempEntity : handler.entities) {
                if(tempEntity.getId() == ID.Player &&
                        !empty && tempEntity.isAction() &&
                        bounds.collides(tempEntity.getHitBounds()) && HUD.timer >= 250) {
                    HUD.timer = 0;
                    GameStateManager.soundtrack.get("locked").play();
                }

            }

        }


        if(contentBounds != null) {
            for (Entity entity : handler.entities) {
                if (entity.getId() == ID.Player &&
                        contentBounds.collides(entity.getHitBounds()) &&
                        entity.isAction()) { // interactiunea cu continutul (pick up)
                    //HUD.inventory.put(contentId, content);
                    changeState(contentId);
                    HUD.scor++;
                    content = null;
                    contentBounds = null;
                    GameStateManager.soundtrack.get("pickUp").play();
                }
            }
        }

    }

    public void setContent(BufferedImage content) {
        this.content = content;
    }

    public BufferedImage getContent() {
        return content;
    }

    public AABB getContentBounds() {
        return contentBounds;
    }

    public boolean isEmpty() {
        return empty;
    }

    @Override
    public void render(Graphics2D g) {
        g.setColor(Color.green);
        g.drawImage(image,(int) (pos.getWorldVar().x), (int) (pos.getWorldVar().y), width, height, null );
      //  g.drawRect((int) ((pos.getWorldVar().x) + bounds.getXOffset()), (int) ((pos.getWorldVar().y) + bounds.getYOffset()), (int)bounds.getWidth(), (int)bounds.getHeight());

        if(content != null && open) {
            g.drawImage(content, (int) (pos.getWorldVar().x + defX*width), (int) (pos.getWorldVar().y + defY*height), 32, 32, null);
            g.setColor(Color.MAGENTA);
         // g.drawRect((int) (pos.getWorldVar().x + defX*width), (int) (pos.getWorldVar().y + defY*height), 32, 32);
        }

    }
}
