package com.valentin.mihai.game.states.levels;

import com.valentin.mihai.game.graphics.Sprite;
import com.valentin.mihai.game.hub.HUD;
import com.valentin.mihai.game.hub.Sound;
import com.valentin.mihai.game.objects.Entity;
import com.valentin.mihai.game.objects.EntityHandler;
import com.valentin.mihai.game.objects.ID;
import com.valentin.mihai.game.objects.characters.NPC;
import com.valentin.mihai.game.objects.characters.NPC2;
import com.valentin.mihai.game.states.GameState;
import com.valentin.mihai.game.states.GameStateManager;
import com.valentin.mihai.game.tiles.TileManager;
import com.valentin.mihai.game.util.KeyHandler;
import com.valentin.mihai.game.util.MouseHandler;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.sql.SQLException;

import com.valentin.mihai.game.graphics.Font;
import com.valentin.mihai.game.util.Vector2f;

public class Level1 extends GameState {

    private Font font;
    private Font font2;
    private TileManager tm;

    public static Vector2f map;

    private final String[][] JorahText = {
            {"Lucas? The Prince??", ""},
            {"I was Jorah,", "the blacksmith."},
            {"I wanna escape from", "this horrible place"},
            {"But first I need to", "find something..."},
            {"My brother's remains,", "hidden in this room..."},
            {"..I know you want", "to find your wife."},
            {"So if you help me", "I will grant you"},
            {"Access to the next", "room of the castle."},
            {"Good luck! I'll wait", "for you here."},
            {"Go on, try to find", "my brother's remains"},
            {"I will help you if", "you succeed."},
            {"So long...", ""},
            {"You found them", "..."},
            {"I am so happy,", "I can leave in peace."},
            {"Here, take this", "I wish you luck"}
    };


    public Level1(GameStateManager gsm) {
        super(gsm);

        GameStateManager.soundtrack.get("playTrack").play();

        map = new Vector2f(0, 0);
        Vector2f.setWorldXY(map.x, map.y);


        handler = new EntityHandler();
        hud = new HUD();
        tm = new TileManager("tiles/Map1.xml");
        font = new Font("font/RealFont.png", 20, 20, 32);
        font2 = new Font("font/LastFont.png", 28, 36, 65);


        //HUD.inventory.put(ID.Key3, loadImage("entity/items/key3.png"));
        //HUD.inventory.put(ID.Key2, loadImage("entity/items/key3.png"));


        //adding chests and door

        handler.addDoor(new Vector2f(592, 80), new ID[]{ID.Key3, ID.Key2});
        handler.addChest(new Vector2f(675, 410), 5, 0.2f, 1.2f);
        handler.addChest(new Vector2f(530, 625), 2, 0, -1.5f);

        handler.addPlayer(new Sprite("entity/Costel.png", 32, 48), new Vector2f(140, 700), 32, 48, GameStateManager.LEVEL1);


        //adding enemies
        handler.addDumbEnemy(new Sprite("entity/characters/DumbEnemy2.png", 32, 48), new Vector2f(440, 570), 32, 48, 2);
        handler.addDumbEnemy(new Sprite("entity/characters/DumbEnemy2.png", 32, 48), new Vector2f(380, 530), 32, 48, 1);
        handler.addDumbEnemy(new Sprite("entity/DumbEnemy.png", 32, 48), new Vector2f(600, 410), 32, 48, 3);
        handler.addDumbEnemy(new Sprite("entity/DumbEnemy.png", 32, 48), new Vector2f(500, 410), 32, 48, 4);
        handler.addSmartEnemy(new Sprite("entity/characters/skeletonWithCap.png", 32, 48), new Vector2f(600, 160), 32, 48, new Vector2f(600, 160), 300);

        // adding npc's
        //  handler.add(new NPC(this.handler, ID.JorahNPC, new Sprite("entity/characters/BeardGuy.png", 32, 48), new Vector2f(150, 350), 32, 48, "Jorah"));
        handler.add(new NPC2(this.handler, ID.JorahNPC, new Sprite("entity/characters/BeardGuy.png", 32, 48), new Vector2f(150, 350), 32, 48, "Jorah", JorahText, ID.Remains, ID.Key3));


        for (Entity entity : handler.entities) {
            if (entity.getId() == ID.Chest) {
                entity.register(hud);
            }
        }
    }


    public void update() {

        Vector2f.setWorldXY(map.x, map.y);
        if (map.y > 90) map.y = 90;
        if (map.y < 0) map.y = 0;

        handler.update();
        hud.update();

    }

    public void input(MouseHandler mouse, KeyHandler key) {
        handler.input(mouse, key);

        if (key.save.down) {
            System.out.println("S-a salvat jocul!");
            save();
        }
    }

    public void render(Graphics2D g) {
        tm.render(g);
        handler.render(g);
        hud.render(g);
    }

    public void save() {
        try {
            GameStateManager.getInstance().getDataBase().updateCurrentLevel(1);
            GameStateManager.getInstance().getDataBase().updateHeroLife(HUD.HEALTH);
            GameStateManager.getInstance().getDataBase().updateHeroScore(HUD.scor);

        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }
    }
}