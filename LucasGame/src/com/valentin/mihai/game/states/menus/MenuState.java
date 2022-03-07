package com.valentin.mihai.game.states.menus;

import com.valentin.mihai.game.graphics.Sprite;
import com.valentin.mihai.game.hub.HUD;
import com.valentin.mihai.game.hub.Sound;
import com.valentin.mihai.game.states.GameState;
import com.valentin.mihai.game.states.GameStateManager;
import com.valentin.mihai.game.util.KeyHandler;
import com.valentin.mihai.game.util.MouseHandler;
import com.valentin.mihai.game.graphics.Font;
import com.valentin.mihai.game.util.Vector2f;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Objects;

public class MenuState extends GameState {

    private Font font;
    private Font font2;
    private BufferedImage image;
    private Rectangle playButton = new Rectangle(210, 380, 400, 60);
    private Rectangle exitButton = new Rectangle(300, 500, 200, 60);


    public MenuState(GameStateManager gsm) {
        super(gsm);

        image = loadImage("menu/castleGameOver.png");

        font = new Font("font/LastFont.png", 28, 36, 65);

        font2 = new Font("res/font/smallFont.png", 8, 12, 32);



    }

    @Override
    public void update() {

    }

    @Override
    public void input(MouseHandler mouse, KeyHandler key) {
        if(mouse.getButton() == 1) // cand apasam click stanga
        if(mouse.getMouseX() >= 210 && mouse.getMouseX() <= 370) {
            if(mouse.getMouseY() >= 240 && mouse.getMouseY() <= 330){ // click pe New Story
                GameStateManager.soundtrack.get("menuTrack").stop();
                GameStateManager.soundtrack.get("transition").play();
                try {
                    Thread.sleep(100);       // asteapta 100 ms
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                GameStateManager.next = true;
            }
        }

        if(mouse.getButton() == 1)
            if(mouse.getMouseX() >= 290 && mouse.getMouseX() <= 400) {
                if(mouse.getMouseY() >= 440 && mouse.getMouseY() <= 520){ // click pe Exit
                    System.exit(0);
                }
            }


        if(mouse.getButton() == 1)
            if(mouse.getMouseX() >= 290 && mouse.getMouseX() <= 390){
                if(mouse.getMouseY() >=350 && mouse.getMouseY() <= 422){ // click pe Load
                    //  GameStateManager.soundtrack.get("menuTrack").stop();
                   // GameStateManager.soundtrack.get("transition").play();
                    try {
                        Thread.sleep(200);       // asteapta 200 ms
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    try {
                        int level = GameStateManager.getInstance().getDataBase().getCurrentLevel();
                        HUD.HEALTH = GameStateManager.getInstance().getDataBase().getHeroLife();
                        HUD.scor= GameStateManager.getInstance().getDataBase().getHeroScore();
                        if(level == 1) {
                            GameStateManager.currentLevel = -1;
                            GameStateManager.next = true;
                        }
                        if(level == 2) {
                            GameStateManager.currentLevel = 0;
                            GameStateManager.next = true;
                        }
                        if(level == 3) {
                            GameStateManager.currentLevel = 1;
                            GameStateManager.next = true;
                        }
                    }
                    catch(SQLException e){
                        System.err.println(e.getClass().getName() + ": " + e.getMessage());
                    }
                }
            }
        if(mouse.getButton() == 1)
            if(mouse.getMouseX() >= 80 && mouse.getMouseX() <= 200){
                if(mouse.getMouseY() >=730 && mouse.getMouseY() <= 790){ // click pe About
                    //  GameStateManager.soundtrack.get("menuTrack").stop();
                    GameStateManager.soundtrack.get("transition").play();
                    try {
                        Thread.sleep(200);       // asteapta 200 ms
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    GameStateManager.about = true;
                }
            }


        if(key.escape.down) System.exit(0);

        if(key.action.down) {
            GameStateManager.soundtrack.get("transition").play();
            GameStateManager.about = true;
        }

        if(mouse.getButton() == 1)
            if(mouse.getMouseX() >= 590 && mouse.getMouseX() <= 690) {
                if (mouse.getMouseY() >= 570 && mouse.getMouseY() <= 630) { // click pe Change1
                    //  GameStateManager.soundtrack.get("menuTrack").stop();
                    //GameStateManager.soundtrack.get("transition").play();
                    try {
                        Thread.sleep(200);       // asteapta 200 ms
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    try {
                        if (GameStateManager.getInstance().getDataBase().getMenuVolume() == 10) {
                            GameStateManager.getInstance().getDataBase().updateMenuMusicVolume(1F);
                        }
                        else {
                            GameStateManager.getInstance().getDataBase().updateMenuMusicVolume(GameStateManager.getInstance().getDataBase().getMenuVolume() + 1F);
                        }
                        GameStateManager.soundtrack.get("menuTrack").setVolume(GameStateManager.getInstance().getDataBase().getMenuVolume()/10.);

                    } catch (SQLException e) {
                        System.err.println(e.getClass().getName() + ": " + e.getMessage());

                    }
                }
            }

        if(mouse.getButton() == 1)
            if(mouse.getMouseX() >= 590 && mouse.getMouseX() <= 690) {
                if (mouse.getMouseY() >= 630 && mouse.getMouseY() <= 690) { // click pe Change2
                    //  GameStateManager.soundtrack.get("menuTrack").stop();
                    //GameStateManager.soundtrack.get("transition").play();
                    try {
                        Thread.sleep(200);       // asteapta 200 ms
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    try {
                        if (GameStateManager.getInstance().getDataBase().getGameVolume() == 50) {
                            GameStateManager.getInstance().getDataBase().updateGameMusicVolume(5F);
                        }
                        else {
                            GameStateManager.getInstance().getDataBase().updateGameMusicVolume(GameStateManager.getInstance().getDataBase().getGameVolume() + 5F);
                        }
                        GameStateManager.soundtrack.get("playTrack").setVolume(GameStateManager.getInstance().getDataBase().getGameVolume()/100.);
                        GameStateManager.soundtrack.get("level2track").setVolume(GameStateManager.getInstance().getDataBase().getGameVolume()/100.);
                        GameStateManager.soundtrack.get("level3track").setVolume(GameStateManager.getInstance().getDataBase().getGameVolume()/100.);
                    } catch (SQLException e) {
                        System.err.println(e.getClass().getName() + ": " + e.getMessage());

                    }
                }
            }

    }

    @Override
    public void render(Graphics2D g) {
        g.drawImage(image,0,0, 800, 800, null);
        Sprite.drawArray(g,font,"LUCAS",new Vector2f(210, 30), 112,144, 75,0);
        Sprite.drawArray(g,font,"NEW STORY", new Vector2f(225, 240), 56, 72, 40, 0 );
        Sprite.drawArray(g,font,"EXIT", new Vector2f(320, 450), 56, 72, 35, 0 );
        Sprite.drawArray(g,font,"LOAD", new Vector2f(320, 350), 56, 72, 35, 0 );

        Sprite.drawArray(g,font,"ABOUT", new Vector2f(80, 730), 40, 60, 35, 0 );

        Sprite.drawArray(g,font2,"Menu Volume:", new Vector2f(250, 580), 24, 36, 20, 0 );
        Sprite.drawArray(g,font2,"Game Volume:", new Vector2f(250, 630), 24, 36, 20, 0 );

        Sprite.drawArray(g,font2,"Change", new Vector2f(600, 580), 24, 36, 20, 0 );
        Sprite.drawArray(g,font2,"Change", new Vector2f(600, 630), 24, 36, 20, 0 );

        try {
            float volumeGame = GameStateManager.getInstance().getDataBase().getGameVolume();
            float volumeMenu = GameStateManager.getInstance().getDataBase().getMenuVolume();
            Sprite.drawArray(g,font2,Float.toString(volumeMenu*10), new Vector2f(500, 580), 24, 36, 15, 0 );
            Sprite.drawArray(g,font2,Float.toString(volumeGame), new Vector2f(500, 630), 24, 36, 15, 0 );


        }
        catch(SQLException e){
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }



        g.setColor(Color.white);
       // g.draw(playButton);
       // g.draw(exitButton);
    }

}
