package com.valentin.mihai.game.states.levels;

import com.valentin.mihai.game.graphics.Sprite;
import com.valentin.mihai.game.hub.HUD;
import com.valentin.mihai.game.objects.Entity;
import com.valentin.mihai.game.objects.EntityHandler;
import com.valentin.mihai.game.objects.ID;
import com.valentin.mihai.game.objects.characters.NPC2;
import com.valentin.mihai.game.objects.characters.Player;
import com.valentin.mihai.game.objects.characters.SmartEnemy;
import com.valentin.mihai.game.objects.structures.Chest;
import com.valentin.mihai.game.states.GameState;
import com.valentin.mihai.game.states.GameStateManager;
import com.valentin.mihai.game.tiles.TileManager;
import com.valentin.mihai.game.util.KeyHandler;
import com.valentin.mihai.game.util.MouseHandler;
import com.valentin.mihai.game.util.Vector2f;

import java.awt.*;

public class Level2 extends GameState {

    private TileManager tm;
    public static Vector2f map;
    private final String[][] MoraxText = {
            {"Lucas? The Prince??", ""},
            {"I was Morax,", "the tailor."},
            {"I want to escape from", "this horrible place"} ,
            {"But first I need to", "find something..."},
            {"Your father's royal cloak,", "hidden in this room..."},
            {"..I know you want", "to find your wife."},
            {"So if you help me", "I will grant you"},
            {"Access to the next", "room of the castle."},
            {"Good luck! I'll wait", "for you here."},
            {"Go on, try to find", "the royal cloak"},
            {"I will help you if", "you succeed."},
            {"So long...", ""},
            {"You found it!", "..."},
            {"I am so happy,", "I can leave in peace."},
            {"Here, take this", "I wish you luck"}
    };

    public Level2(GameStateManager gsm) {
        super(gsm);

        GameStateManager.soundtrack.get("level2track").play();

        map = new Vector2f(0, 0);
        Vector2f.setWorldXY(map.x, map.y);

        handler = new EntityHandler();
        hud = new HUD();
        tm = new TileManager("tiles/Map2.xml");

        // adding structures
        //NPC - Morax
        handler.add(new NPC2(this.handler, ID.MoraxNPC, new Sprite("res/entity/characters/BlondHandsomeGuy.png", 32, 48), new Vector2f(70, 665),32, 48, "Morax", MoraxText, ID.Cloak, ID.Key3));

        // fountains
        handler.add(new Chest(handler,"res/entity/structures/FountainStatue.png" , ID.Fountain, new Vector2f(736, 400), 32, 48, 0, 1.25f, 6));
        handler.add(new Chest(handler,"res/entity/structures/FountainStatue.png" , ID.Fountain, new Vector2f(736, 200), 32, 48, 0, 0, 0 ));
        handler.add(new Chest(handler,"res/entity/structures/FountainStatue.png" , ID.Fountain, new Vector2f(736, 624), 32, 48, 0, 0, 0 ));
        handler.add(new Chest(handler,"res/entity/structures/FountainStatue.png" , ID.Fountain, new Vector2f(32, 200), 32, 48, 0, 0, 0 ));
        handler.add(new Chest(handler,"res/entity/structures/FountainStatue.png" , ID.Fountain, new Vector2f(32, 400), 32, 48, 0, 0, 0 ));
        handler.add(new Chest(handler,"res/entity/structures/FountainStatue.png" , ID.Fountain, new Vector2f(32, 600), 32, 48, 0, 0, 0 ));


        // door and chests
        handler.addDoor(new Vector2f(576, 80), new ID[]{ID.Key4, ID.Key3});
        handler.addChest(new Vector2f(725, 735), 4, -0.2f, -1.2f);


        // adding the player
       // handler.addPlayer(new Sprite("entity/Costel.png",32,48),new Vector2f(380, 700),32, 48, GameStateManager.LEVEL2);

        handler.add(new Player(handler, ID.Player, new Sprite("entity/Costel.png",32,48), new Vector2f(380, 700), 32, 48, GameStateManager.LEVEL2));

        // enemies
        handler.add(new SmartEnemy(handler, ID.SmartEnemy, new Sprite("res/entity/characters/skeletonAnim.png"), new Vector2f(380, 380), 32, 48, new Vector2f(400, 380), 150));
        handler.addDumbEnemy(new Sprite("res/entity/characters/DumbEnemy3.png",32, 48),new Vector2f(600,610 ) , 32, 48, 4);
        handler.addDumbEnemy(new Sprite("entity/DumbEnemy.png",32, 48),new Vector2f(600,410 ) , 32, 48, 4);
        handler.addDumbEnemy(new Sprite("res/entity/characters/DumbEnemy3.png",32, 48),new Vector2f(700,210 ) , 32, 48, 4);
        handler.addDumbEnemy(new Sprite("entity/DumbEnemy.png",32, 48),new Vector2f(200,410 ) , 32, 48, 4);
        handler.addDumbEnemy(new Sprite("res/entity/characters/DumbEnemy3.png",32, 48),new Vector2f(120,410 ) , 32, 48, 3);
        handler.addDumbEnemy(new Sprite("entity/DumbEnemy.png",32, 48),new Vector2f(200,210 ) , 32, 48, 3);
        handler.add(new SmartEnemy(handler, ID.SmartEnemy, new Sprite("res/entity/characters/skeletonAnim.png"), new Vector2f(440, 180), 32, 48, new Vector2f(440, 180), 180));


        for (Entity entity : handler.entities) {
            if (entity.getId() == ID.Chest || entity.getId() == ID.Fountain) {
                entity.register(hud);
            }
        }

    }

    @Override
    public void update() {
        Vector2f.setWorldXY(map.x, map.y);
        if(map.y > 0) map.y = 0;
        if(map.y < 0) map.y = 0;

        if(!handler.entities.isEmpty()) {
            handler.update();
        }
        hud.update();
    }

    @Override
    public void input(MouseHandler mouse, KeyHandler key) {

        if(!handler.entities.isEmpty()) handler.input(mouse, key);
        if (key.save.down) {
            System.out.println("S-a salvat jocul!");
            save();
        }
    }

    @Override
    public void render(Graphics2D g) {
        tm.render(g);
        if(!handler.entities.isEmpty()) handler.render(g);
        hud.render(g);
    }
    public void save() {
        try {
            GameStateManager.getInstance().getDataBase().updateCurrentLevel(2);
            GameStateManager.getInstance().getDataBase().updateHeroLife(HUD.HEALTH);
            GameStateManager.getInstance().getDataBase().updateHeroScore(HUD.scor);

        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }
    }
}
