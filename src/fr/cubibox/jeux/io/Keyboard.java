package fr.cubibox.jeux.io;

import fr.cubibox.jeux.main;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Keyboard implements KeyListener {
    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e){
        // avancer
        if (e.getKeyChar() == 'z' ){
            System.out.println("avancer");
            main.getEngine().getPlayer().Avancer(0.1f);
        }

        //gauche
        else if (e.getKeyCode() == 81){

        }

        //reculer
        else if (e.getKeyCode() == 83){

        }

        //droite
        else if (e.getKeyCode() == 68 ){

        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }
}
