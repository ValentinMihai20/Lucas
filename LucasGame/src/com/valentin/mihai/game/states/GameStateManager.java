package com.valentin.mihai.game.states;

import com.valentin.mihai.game.DataBase;
import com.valentin.mihai.game.GamePanel;
import com.valentin.mihai.game.hub.HUD;
import com.valentin.mihai.game.hub.Sound;
import com.valentin.mihai.game.states.levels.Level1;
import com.valentin.mihai.game.states.levels.Level2;
import com.valentin.mihai.game.states.levels.Level3;
import com.valentin.mihai.game.states.menus.AboutState;
import com.valentin.mihai.game.states.menus.EndState;
import com.valentin.mihai.game.states.menus.GameOverState;
import com.valentin.mihai.game.states.menus.MenuState;
import com.valentin.mihai.game.states.orchestration.PracticeToolState;
import com.valentin.mihai.game.util.KeyHandler;
import com.valentin.mihai.game.util.MouseHandler;
import com.valentin.mihai.game.util.Vector2f;

import java.awt.*;
import java.sql.SQLException;
import java.util.HashMap;

public class GameStateManager { // gestionarea fazelor jocului

    private static GameStateManager gsm_Instance;
    private DataBase db;

    private GameState[] states;
    private int currentState = 0;

    public static HashMap<String, Sound> soundtrack; // linia melodica a jocului

    public static Vector2f map;

    public static final int numOfGameStates = 10;



    // identifiers for levels
    public static final int LEVEL1 = 0;
    public static final int LEVEL2 = 1;
    public static final int LEVEL3 = 2;

    public static final int[] levels = {LEVEL1, LEVEL2, LEVEL3};
    public static int currentLevel = -1;

    // for other states
    public static final int MENU = 3;
    public static final int PAUSE = 4;
    public static final int GAME_OVER = 5;
    public static final int PRACTICE = 6;
    public static final int ABOUT = 7;
    public static final int STORY = 8;
    public static final int END = 9;


    public static boolean next = false;
    public static boolean over = false;
    public static boolean about = false;
    public static boolean BTM = false;
    public static boolean end = false;

    private GameStateManager() {

        map = new Vector2f(GamePanel.width, GamePanel.height);
        Vector2f.setWorldXY(map.x, map.y);

        db = new DataBase();

        states = new GameState[numOfGameStates];
        soundtrack = new HashMap<>();

        // states soundtrack

        // level 1
        soundtrack.put("playTrack", new Sound("res/sounds/mp3/yeah.mp3"));
        soundtrack.get("playTrack").setVolume(0.05);

        // level 2
        soundtrack.put("level2track", new Sound("res/sounds/mp3/candyShop.mp3"));
        soundtrack.get("level2track").setVolume(0.05);

        // level 3
        soundtrack.put("level3track", new Sound("res/sounds/mp3/InDaClub.mp3"));
        soundtrack.get("level3track").setVolume(0.05);

        soundtrack.put("menuTrack", new Sound("res/sounds/mp3/lightOfTheSeven.mp3"));

        soundtrack.put("transition", new Sound("res/sounds/mp3/new/glassy.mp3"));
        soundtrack.get("transition").setVolume(0.25);
        soundtrack.put("gameOver", new Sound("res/sounds/mp3/new/gameOver.mp3"));

        // sound effects

        // player
        soundtrack.put("hit", new Sound("res/sounds/mp3/hurt.mp3"));
        soundtrack.put("bonk", new Sound("res/sounds/mp3/bonk.mp3"));

        //chest
        soundtrack.put("open", new Sound("res/sounds/mp3/chestOpen.mp3"));
        soundtrack.put("pickUp", new Sound("res/sounds/mp3/collect.mp3"));
        soundtrack.put("locked", new Sound("res/sounds/mp3/new/door locked.mp3"));

        //door
         soundtrack.put("lockedDoor", new Sound("res/sounds/mp3/new/doorSmash.mp3"));
         soundtrack.put("levelUp", new Sound("res/sounds/mp3/new/levelUp.mp3"));





        // adding menu state first
        currentState = MENU;
        add(currentState);
        GameStateManager.soundtrack.get("menuTrack").play();

    }

    public void unload(int state) {
        states[state] = null;
    }

    public void add(int state) {
        switch (state) {
            case LEVEL1:
                states[state] = (new Level1(this));
                break;
            case LEVEL2:
                states[state] = (new Level2(this));
                break;
            case LEVEL3:
                states[state] = (new Level3(this));
                break;
            case MENU:
                states[state] = (new MenuState(this));
                break;
            case PAUSE:
                states[state] = (new PauseState(this));
                break;
            case GAME_OVER:
                states[state] = (new GameOverState(this));
                break;
            case PRACTICE:
                states[state] = (new PracticeToolState(this));
                break;
            case ABOUT:
                states[state] = (new AboutState(this));
                break;
            case END:
                states[state] = (new EndState(this));
                break;
        }
    }

    public void addAndPop(int state) {
        unload(currentState);
        currentState = state;
        add(currentState);
    }

    public void update() {
        try{
            GameStateManager.soundtrack.get("menuTrack").setVolume(GameStateManager.getInstance().getDataBase().getMenuVolume()/10.);
        }
        catch(SQLException e){
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }

        //Vector2f.setWorldXY(map.x, map.y); // schimbarea view-ului

            states[currentState].update();

            // next este true daca am apasat pe New Story
        if(next) {
            soundtrack.get("menuTrack").reset();
            soundtrack.get("menuTrack").stop();
            currentLevel++;
            addAndPop(currentLevel);
            next = false;
        }

        // daca ai pierdut toate vietile, intervine faza de Game Over
        if(HUD.HEALTH == 0) {
          //  soundtrack.get("playTrack").reset();
           // soundtrack.get("playTrack").stop();
            addAndPop(GAME_OVER);
            HUD.reset();
        }

        // over e true la final de nivel
        if(over) {
            soundtrack.get("playTrack").reset();
            soundtrack.get("playTrack").stop();
            soundtrack.get("menuTrack").play();
            addAndPop(MENU);
            HUD.reset();
            over = false;
        }
        if(about){
            addAndPop(ABOUT);
            about = false;
        }
        if(BTM){
            addAndPop(MENU);
            BTM=false;
        }
        if(end){
            addAndPop(END);
            end=false;
        }
    }


    public void input(MouseHandler mouse, KeyHandler key) {
        states[currentState].input(mouse, key);
    }

    public void render(Graphics2D g) {
        try {
            states[currentState].render(g);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static GameStateManager getInstance() {
        if(gsm_Instance == null)
            gsm_Instance = new GameStateManager();
        return gsm_Instance;
    }

    public DataBase getDataBase(){
        return db;
    }

}
