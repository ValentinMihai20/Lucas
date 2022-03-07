package com.valentin.mihai.game.states.menus;

import com.valentin.mihai.game.graphics.Font;
import com.valentin.mihai.game.graphics.Sprite;
import com.valentin.mihai.game.hub.Sound;
import com.valentin.mihai.game.states.GameState;
import com.valentin.mihai.game.states.GameStateManager;
import com.valentin.mihai.game.util.KeyHandler;
import com.valentin.mihai.game.util.MouseHandler;
import com.valentin.mihai.game.util.Vector2f;

import java.awt.*;
import java.awt.image.BufferedImage;

public class  GameOverState extends GameState {

    private Font font;
    private BufferedImage image;
    private final Rectangle retryButton = new Rectangle(210, 380, 400, 60);
    private final Rectangle exitButton = new Rectangle(300, 500, 200, 60);

    public GameOverState(GameStateManager gsm) {
        super(gsm);
        image = loadImage("menu/MenuBackground.gif");
        font = new Font("font/LastFont.png", 28, 36, 65);

        GameStateManager.soundtrack.get("playTrack").stop();
        GameStateManager.soundtrack.get("level2track").stop();
        GameStateManager.soundtrack.get("level3track").stop();

        GameStateManager.soundtrack.get("gameOver").play();
        GameStateManager.soundtrack.get("gameOver").setVolume(0.5);

        GameStateManager.soundtrack.get("menuTrack").play();

    }

    @Override
    public void update() {

    }

    @Override
    public void input(MouseHandler mouse, KeyHandler key) {
        if(mouse.getButton() == 1)
            if(mouse.getMouseX() >= 210 && mouse.getMouseX() <= 610) {
                if(mouse.getMouseY() >= 380 && mouse.getMouseY() <= 440){ // click pe Try Again
                    GameStateManager.soundtrack.get("menuTrack").stop();
                    GameStateManager.soundtrack.get("transition").play();
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    GameStateManager.currentLevel = -1;
                    GameStateManager.next = true;
                }
            }

        if(mouse.getButton() == 1)
            if(mouse.getMouseX() >= 200 && mouse.getMouseX() <= 500) {
                if(mouse.getMouseY() >= 500 && mouse.getMouseY() <= 560){
                    GameStateManager.soundtrack.get("transition").play();
                    try {
                        Thread.sleep(200);       // asteapta 200 ms
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    GameStateManager.BTM = true;
                }
            }

        if(key.escape.down) System.exit(0);


    }

    @Override
    public void render(Graphics2D g) {
        g.drawImage(image,0,0, 800, 800, null);
        Sprite.drawArray(g,font,"GAME OVER",new Vector2f(180, 180), 67,86, 50,0);
        Sprite.drawArray(g,font,"TRY AGAIN", new Vector2f(225, 380), 56, 72, 40, 0 );
        Sprite.drawArray(g,font,"EXIT TO MENU", new Vector2f(190, 500), 56, 72, 35, 0 );
    }
}
