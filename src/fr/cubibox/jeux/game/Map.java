package fr.cubibox.jeux.game;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;

public class Map {
    private File mapFile;
    private int[][] mapContent;

    private int size;

    public Map(File mapFile) {
        this.mapFile = mapFile;
        this.mapContent = ExtractMap(mapFile);
    }

    private int[][] ExtractMap(File f) {
        int[][] map = new int[8][8];
        try{
            FileInputStream fis=new FileInputStream(f);
            int r;
            int x=0;
            int y=0;
            while((r=fis.read())!= -1){
//                if (Character.isDigit(r)) {
//                    map[y][x] = r-48;
//                    x++;
//                }
                if ((char)r == '1') {
                    map[y][x] = 1;
                    x++;
                }
                else if ((char)r == '0') {
                    map[y][x] = 0;
                    x++;
                }
                else if (r == 13){
                    y++;
                    x=0;
                }
            }
            System.out.println();
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return map;
    }

    public File getMapFile() {
        return mapFile;
    }

    public void setMapFile(File mapFile) {
        this.mapFile = mapFile;
    }

    public int[][] getMapContent() {
        return mapContent;
    }

    public void setMapContent(int[][] mapContent) {
        this.mapContent = mapContent;
    }
}
