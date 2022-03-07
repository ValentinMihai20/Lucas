package tests;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;
import java.util.Locale;
import java.util.Scanner;

public class Sounds {

    public static void main(String[] args) {
        File file = new File("res/sounds/wav/bonk.wav") ;
        Scanner scanner = new Scanner(System.in);

        try {

            AudioInputStream audioStream = AudioSystem.getAudioInputStream(file);
            Clip clip = AudioSystem.getClip();
            clip.open(audioStream);

            String response;
            response = "";

            while(!response.equals("Q")) {
                System.out.println("P - play, S - Stop, R - reset, Q - quit");
                System.out.println("Enter your choice:");

                response = scanner.next();
                response = response.toUpperCase();

                switch (response) {
                    case "P":
                        clip.start();
                        break;
                    case "S":
                        clip.stop();
                        break;
                    case "R":
                        clip.setMicrosecondPosition(0);
                        break;
                    case "Q":
                        clip.close();
                        break;
                    default:
                        System.out.println("Not a valid response");
                        break;
                }

            }

            System.out.println("Salut!");


        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }



    }
}
