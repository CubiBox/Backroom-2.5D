package fr.cubibox.backroom2_5d.game;

// TODO: Faire la classe client, qui contiendra la plupart des fonctions de jeu, etc.

public class Client {
    private final String name;


    // TODO
    private String ip;
    private int port;

    //

    public Client(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
