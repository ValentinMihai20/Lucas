package com.valentin.mihai.game.states.orchestration;

import com.valentin.mihai.game.graphics.Font;
import com.valentin.mihai.game.graphics.Sprite;
import com.valentin.mihai.game.hub.HUD;
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
import com.valentin.mihai.game.util.Vector2f;

import java.awt.*;

public class PracticeToolState extends GameState {


    private TileManager tm;
    public static Vector2f map;
    private Font font;

    private final String[][] JorahText = {
            //first encounter
            {"Lucas? The Prince??", ""},
            {"I was Jorah,", "the blacksmith."},
            {"I wanna escape from", "this horrible place"} ,
            {"But first I need to", "find something..."},
            {"My brother's remains,", "hidden in this room..."},
            {"..I know you want", "to find your wife."},
            {"So if you help me", "I will grant you"},
            {"Access to the next", "room of the castle."},
            {"Good luck! I'll wait", "for you here."},
            // intermediate encounter
            {"Go on, try to find", "my brother's remains"},
            {"I will help you if", "you succeed."},
            {"So long...", ""},
            // last encounter
            {"You found them", "..."},
            {"I am so happy,", "I can leave in peace."},
            {"Here, take this", "I wish you luck"}
    };


    public PracticeToolState(GameStateManager gsm) {
        super(gsm);

        map = new Vector2f(0, 100);
        Vector2f.setWorldXY(map.x, map.y);

        handler = new EntityHandler();
        hud = new HUD();
        tm = new TileManager("tiles/PracticeMap.xml");
        font = new Font("font/LastFont.png", 28 , 36, 65);

      //  handler.add(new NPC(this.handler, ID.JorahNPC, new Sprite("entity/characters/BeardGuy.png", 32, 48), new Vector2f(200, 500), 32, 48, "Jorah"));
        handler.addPlayer(new Sprite("entity/Costel.png",32,48),new Vector2f(140, 700),32, 48, GameStateManager.PRACTICE);
       // handler.addSmartEnemy(new Sprite("entity/characters/BlondHandsomeGuy.png", 32, 48), new Vector2f(600, 160), 32, 48, new Vector2f(600, 160));

        handler.add(new NPC2(this.handler, ID.JorahNPC, new Sprite("entity/characters/BeardGuy.png", 32, 48), new Vector2f(200, 500),32, 48, "Jorah", JorahText, ID.Remains, ID.Key3));


    }

    @Override
    public void update() {

        Vector2f.setWorldXY(map.x, map.y);
        if(map.y >= 90) map.y = 90;
        if(map.y <= 0) map.y = 0;

        handler.update();
        hud.update();

    }

    @Override
    public void input(MouseHandler mouse, KeyHandler key) {
            handler.input(mouse, key);
    }

    @Override
    public void render(Graphics2D g) {

        tm.render(g);
        handler.render(g);
        hud.render(g);

        Sprite.drawArray(g,font,"PRACTICE TOOL",new Vector2f(200, 30), 28,36, 20,0);


    }
}

