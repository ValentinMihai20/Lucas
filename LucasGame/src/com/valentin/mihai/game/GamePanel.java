package com.valentin.mihai.game;


import com.valentin.mihai.game.states.GameStateManager;
import com.valentin.mihai.game.util.KeyHandler;
import com.valentin.mihai.game.util.MouseHandler;

import javax.swing.JPanel;
import java.awt.*;
import java.awt.image.BufferedImage;

public class GamePanel extends JPanel implements Runnable {

    public static int width;
    public static int height;

    public static int oldFrameCount; // rata de improspatare


    private Thread thread;
    private boolean running = false;

    private BufferedImage image;
    private Graphics2D g;

    private MouseHandler mouse;
    private KeyHandler key;
    private GameStateManager gsm;

    public GamePanel(int width, int height) {

        this.width = width;
        this.height = height;

        setPreferredSize(new Dimension(width, height));
        setFocusable(true);
        requestFocus();

    }


    // initializare

    public void init() {
        running = true;

        // grafici de baza
        image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        g = (Graphics2D) image.getGraphics();

        // intrarile -- mouse -- tastatura --
        mouse = new MouseHandler(this);
        key = new KeyHandler(this);

        // Managerul de faze ale jocului.
        gsm = GameStateManager.getInstance();

    }

    // functia run care ruleaza baza jocului

   public void addNotify() {
       super.addNotify();
       if (thread == null) {
           start();
       }
   }

    public synchronized void start(){
        if(running)
            return;

        running = true;

        thread = new Thread(this, "GameThread");
        thread.start();
    }



    public void run(){

        init();

        int fps = 60;
        double TPT = 1000000000 / fps;
        double delta = 0;
        long now;
        long lastTime = System.nanoTime();
        long timer = 0;
        int ticks = 0;

        while(running){
            now = System.nanoTime();
            delta += (now-lastTime) / TPT;
            timer += now - lastTime;
            lastTime = now;

            if(delta >= 1) {
                update();
                render();
                draw();
                input(mouse, key);
                ticks++;
                delta--;
            }

            if(timer >= 1000000000){
                System.out.println("FPS : " + ticks);
                ticks = 0;
                timer = 0;
            }

        }

    }


    // pentru reimprospatarea jocului in permanenta
    public void update() {
        gsm.update();
    }

    // pentru intrari
    public void input(MouseHandler mouse, KeyHandler key) {
        gsm.input(mouse, key);
    }

    // pentru afisarea imaginilor pe ecran
    public void render() {
        if (g != null) {
            g.setColor(new Color(66, 134, 244));
            g.fillRect(0, 0, width, height);
            gsm.render(g);
        }

    }

    public void draw() {
        Graphics g2 = (Graphics) this.getGraphics();
        g2.drawImage(image, 0, 0, width, height, null);
        g2.dispose();
    }

}
