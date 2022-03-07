package com.valentin.mihai.game.states.levels;

import com.valentin.mihai.game.graphics.Sprite;
import com.valentin.mihai.game.hub.HUD;
import com.valentin.mihai.game.objects.Entity;
import com.valentin.mihai.game.objects.EntityHandler;
import com.valentin.mihai.game.objects.ID;
import com.valentin.mihai.game.objects.characters.NPC2;
import com.valentin.mihai.game.objects.characters.Player;
import com.valentin.mihai.game.objects.structures.Door;
import com.valentin.mihai.game.states.GameState;
import com.valentin.mihai.game.states.GameStateManager;
import com.valentin.mihai.game.tiles.TileManager;
import com.valentin.mihai.game.util.KeyHandler;
import com.valentin.mihai.game.util.MouseHandler;
import com.valentin.mihai.game.util.Vector2f;

import java.awt.*;

public class Level3 extends GameState {

    private TileManager tm;
    public static Vector2f map;
    private final String[][] WifeText = {
            {"Lucas? My Love??", ""},
            {"I'm so happy to see you,", "alive and well."},
            {"We need to escape from", "this horrible place"} ,
            {"But first I need you to", "bring me something."},
            {"Our wedding gift,", "It's hidden on this floor."},
            {"I know you want", "to leave now"},
            {"But i don't know how", "I can live without it."},
            {"You know how much", "I care about it."},
            {"Good luck! I'll wait", "for you here."},
            {"Go on, try to find", "our wedding gift"},
            {"We can leave after", "you succeed."},
            {"I'll wait here...", ""},
            {"You found it!", "..."},
            {"I am so happy,", "we can leave in peace now."},
            {"Let's go,", "My love!"}
    };



    public Level3(GameStateManager gsm) {
        super(gsm);

        GameStateManager.soundtrack.get("level3track").play();

        map = new Vector2f(0, 0);
        Vector2f.setWorldXY(map.x, map.y);

        handler = new EntityHandler();
        hud = new HUD();
        tm = new TileManager("tiles/maps/Level3333.xml");


        // adding the player
        handler.add(new Player(handler, ID.Player, new Sprite("entity/Costel.png",32,48), new Vector2f(100, 700), 32, 48, GameStateManager.LEVEL3));
        HUD.inventory.put(ID.LucasHeart, loadImage("res/entity/items/InimaLucas.png"));
        // enemies
        handler.addDumbEnemy(new Sprite("entity/DumbEnemy.png",32, 48),new Vector2f(140,200 ) , 32, 48, 5);
        handler.addDumbEnemy(new Sprite("entity/DumbEnemy.png",32, 48),new Vector2f(140,400 ) , 32, 48, 6);
        handler.addDumbEnemy(new Sprite("entity/DumbEnemy.png",32, 48),new Vector2f(340,400 ) , 32, 48, 5);
        handler.addDumbEnemy(new Sprite("entity/DumbEnemy.png",32, 48),new Vector2f(340,210 ) , 32, 48, 6);
        handler.addDumbEnemy(new Sprite("entity/DumbEnemy.png",32, 48),new Vector2f(340,560 ) , 32, 48, 6);
        handler.addDumbEnemy(new Sprite("entity/DumbEnemy.png",32, 48),new Vector2f(340,700 ) , 32, 48, 5);
        handler.addDumbEnemy(new Sprite("entity/DumbEnemy.png",32, 48),new Vector2f(540,400 ) , 32, 48, 6);
        handler.addDumbEnemy(new Sprite("entity/DumbEnemy.png",32, 48),new Vector2f(540,600 ) , 32, 48, 5);

        //NPC
        handler.add(new NPC2(this.handler, ID.WifeNPC, new Sprite("res/entity/characters/wifey.png", 32, 48), new Vector2f(580, 220),32, 48, "WIFE", WifeText, ID.Mirror, ID.WifeHeart));

        handler.addChest(new Vector2f(725, 735), 7, -0.2f, -1.2f);

        handler.add(new Door(this.handler,"res/entity/structures/PortalLastLevel.png",ID.Door,new Vector2f(690,100),64,64,new ID[]{ID.LucasHeart,ID.WifeHeart}));


        for (Entity entity : handler.entities) {
            if (entity.getId() == ID.Chest) {
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
            GameStateManager.getInstance().getDataBase().updateCurrentLevel(3);
            GameStateManager.getInstance().getDataBase().updateHeroLife(HUD.HEALTH);
            GameStateManager.getInstance().getDataBase().updateHeroScore(HUD.scor);

        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }
    }
}
