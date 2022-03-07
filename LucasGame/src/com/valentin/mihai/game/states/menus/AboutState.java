package com.valentin.mihai.game.states.menus;

import com.valentin.mihai.game.graphics.Font;
import com.valentin.mihai.game.graphics.Sprite;
import com.valentin.mihai.game.states.GameState;
import com.valentin.mihai.game.states.GameStateManager;
import com.valentin.mihai.game.util.KeyHandler;
import com.valentin.mihai.game.util.MouseHandler;
import com.valentin.mihai.game.util.Vector2f;

import java.awt.*;
import java.awt.image.BufferedImage;

public class AboutState extends GameState {

    private Font font;
    private BufferedImage image;


    public AboutState(GameStateManager gsm) {
        super(gsm);

        image = loadImage("menu/castleGameOver.png");

        font = new Font("font/LastFont.png", 28, 36, 65);



    }

    @Override
    public void update() {

    }

    @Override
    public void input(MouseHandler mouse, KeyHandler key) {

        if(mouse.getButton() == 1)
            if(mouse.getMouseX() >= 500 && mouse.getMouseX() <= 790) {
                if(mouse.getMouseY() >= 730 && mouse.getMouseY() <= 790){// click pe Back To Menu
                  //  GameStateManager.soundtrack.get("menuTrack").stop();
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

        if(key.action.down) {
            GameStateManager.soundtrack.get("transition").play();
            GameStateManager.BTM = true;
        }

    }

    @Override
    public void render(Graphics2D g) {
        g.drawImage(image,0,0, 800, 800, null);
        Sprite.drawArray(g,font,"LUCAS",new Vector2f(210, 120), 112,144, 75,0);
        Sprite.drawArray(g,font,"WASD  MOVE THE CHARACTER", new Vector2f(125, 380), 40, 50, 25, 0 );
        Sprite.drawArray(g,font,"E      INTERACT BUTTON ", new Vector2f(125, 480), 40, 50, 20, 0 );
        Sprite.drawArray(g,font,"H      SAVE BUTTON ", new Vector2f(125, 580), 40, 50, 20, 0 );
        Sprite.drawArray(g,font,"BACK TO MENU", new Vector2f(500, 730), 25, 50, 25, 0 );
        g.setColor(Color.white);
        // g.draw(playButton);
        // g.draw(exitButton);
    }

}
