package com.valentin.mihai.game;

import javax.swing.JFrame;

public class Window extends JFrame {

    //setting up the window

    public Window() {
        setTitle("Lucas"); // titlu
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // exit on close button
        setContentPane(new GamePanel(800,800));  // setting size of the Game Panel
        pack();
        setLocationRelativeTo(null); // fereastra apare in centrul ecranului
        setVisible(true);
    }

}
