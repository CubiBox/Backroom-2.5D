package fr.cubibox.backroom.game;

import java.util.ArrayList;

public class Polygon {
    private ArrayList<Edge> edges;
    private String id;
    private float height;

    public Polygon(ArrayList<Edge> edges, float height, String id){
        this.height = height;
        this.edges = edges;
        this.id = id;
    }

    public ArrayList<Edge> getEdges() {
        return edges;
    }

    public void setEdges(ArrayList<Edge> edges) {
        this.edges = edges;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public float getHeight() {
        return height;
    }

    public void setHeight(float height) {
        this.height = height;
    }

    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append("$").append(id).append("\n");
        for (Edge e : edges){
            sb.append(e.toString());
        }
        sb.append("\t\t%").append(height).append("\n");
        return sb.toString();
    }
}