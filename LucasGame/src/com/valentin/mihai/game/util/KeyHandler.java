package com.valentin.mihai.game.util;

import com.valentin.mihai.game.GamePanel;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.List;

public class KeyHandler implements KeyListener {

    public static List<Key> keys = new ArrayList<Key>();

    // clasa pentru taste
    public class Key {

        public int presses, absorbs;
        public boolean down, clicked;

        public Key() {
            keys.add(this);
        }

        public void toggle(boolean pressed) {
            if(pressed != down) {
                down = pressed;
            }
            if(pressed) {
                presses++;
            }
        }

        public void tick() {
            if(absorbs < presses) {
                absorbs++;
                clicked = true;
            } else {
                clicked = false;
            }
        }

    }

    public KeyHandler(GamePanel game) {
        game.addKeyListener(this);
    }

    public Key up = new Key();
    public Key down = new Key();
    public Key left = new Key();
    public Key right = new Key();
    public Key action = new Key();
    public Key menu = new Key();
    public Key enter = new Key();
    public Key escape = new Key();
    public Key save = new Key();

    // dezactivarea tuturor tastelor
    public void releaseAll() {
        for (int i = 0; i < keys.size(); i++) {
            keys.get(i).down = false;
        }
    }

    public void tick() {
        for  (Key tempKey : keys) {
            tempKey.down = false;
        }
    }

    public void toggle(KeyEvent e, boolean pressed) {
        if(e.getKeyCode() == KeyEvent.VK_W) up.toggle(pressed);
        if(e.getKeyCode() == KeyEvent.VK_S) down.toggle(pressed);
        if(e.getKeyCode() == KeyEvent.VK_A) left.toggle(pressed);
        if(e.getKeyCode() == KeyEvent.VK_D) right.toggle(pressed);
        if(e.getKeyCode() == KeyEvent.VK_M) menu.toggle(pressed);
        if(e.getKeyCode() == KeyEvent.VK_E) action.toggle(pressed);
        if(e.getKeyCode() == KeyEvent.VK_ESCAPE) escape.toggle(pressed);
        if(e.getKeyCode() == KeyEvent.VK_H) save.toggle(pressed);

    }


    public void keyTyped(KeyEvent e) {
        // do nothing
    }

    public void keyPressed(KeyEvent e) {
        toggle(e,  true);
    }

    public void keyReleased(KeyEvent e) {
        toggle(e, false);
    }

}
