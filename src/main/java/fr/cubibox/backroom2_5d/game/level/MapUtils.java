package fr.cubibox.backroom2_5d.game.level;

import fr.cubibox.backroom2_5d.engine.maths.Line2F;
import fr.cubibox.backroom2_5d.engine.maths.Vector2F;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;

public class MapUtils {
    public static Map importMap(File f) {
        //initialize Level Id
        String idLevel = "";
        boolean idLevelDone = false;

        //initialize Map Size
        int mapSize = 0;
        String mapS = "";
        boolean mapSizeDone = false;

        //Initialize Chunks
        String chunk = "";
        Chunk[][] chunks = new Chunk[0][0];
        int tempCX = 0;
        int tempCY = 0;

        //Initialize Polygons
        String poly = "";
        ArrayList<Line2F> currentEdges = new ArrayList<>();
        ArrayList<MapObject> currentPolys = new ArrayList<>();
        float height;

        //Initialize Edges
        int tempX = 0;
        int tempY = 0;
        Vector2F tempP;
        Vector2F tempP2;

        String temp = "";


        try {
            FileInputStream fis = new FileInputStream(f);
            int r;
            while ((r = fis.read()) != -1) {
                if (r != 32 && r != 9) {
                    temp = "";

                    //id Level
                    while (!idLevelDone) {
                        if ((char) r == '\n' || (char) r == '\r') {
                            idLevelDone = true;
                            r = fis.read();
                            if ((char) r == '\n') r = fis.read();
                            break;
                        } else {
                            if ((char) r == '\n') r = fis.read();
                            idLevel += (char) r;
                        }
                        r = fis.read();
                    }

                    //map Size
                    while (!mapSizeDone) {
                        if ((char) r == '\n' || (char) r == '\r') {
                            if ((char) r == '\n') r = fis.read();
                            mapSizeDone = true;
                            mapSize = Integer.parseInt(mapS);
                            chunks = new Chunk[mapSize / 16][mapSize / 16];
                            break;
                        } else {
                            if ((char) r == '\n') r = fis.read();
                            mapS += (char) r;
                        }
                        r = fis.read();
                    }


                    //Chunk cord
                    if ((char) r == '#') {
                        r = fis.read();
                        temp = "";
                        while ((char) r != ';') {
                            temp += (char) r;
                            r = fis.read();
                        }
                        tempCX = Integer.parseInt(temp);
                        r = fis.read();
                        temp = "";

                        while (!((char) r == '\n' || (char) r == '\r')) {
                            temp += (char) r;
                            r = fis.read();
                        }
                        if ((char) r == '\n') r = fis.read();
                        tempCY = Integer.parseInt(temp);
                        r = fis.read();
                        temp = "";
                    }


                    //Polygone id
                    else if ((char) r == '$') {
                        r = fis.read();
                        while (!((char) r == '\n' || (char) r == '\r')) {
                            poly += (char) r;
                            r = fis.read();
                        }
                        if ((char) r == '\n') r = fis.read();
                    }

                    //Edge
                    else if ((char) r == '@') {
                        temp = "";
                        r = fis.read();
                        r = fis.read();
                        while ((char) r != '-') {
                            while ((char) r != ';') {
                                temp += (char) r;
                                r = fis.read();
                            }
                            tempX = Integer.parseInt(temp);
                            temp = "";
                            r = fis.read();
                            while ((char) r != ']') {
                                temp += (char) r;
                                r = fis.read();
                            }
                            tempY = Integer.parseInt(temp);
                            temp = "";
                            r = fis.read();
                        }
                        tempP = new Vector2F(tempX, tempY);
                        r = fis.read();
                        r = fis.read();

                        while (!((char) r == '\n' || (char) r == '\r')) {
                            while ((char) r != ';') {
                                temp += (char) r;
                                r = fis.read();
                            }
                            tempX = Integer.parseInt(temp);
                            temp = "";
                            r = fis.read();
                            while ((char) r != ']') {
                                temp += (char) r;
                                r = fis.read();
                            }
                            tempY = Integer.parseInt(temp);
                            temp = "";
                            r = fis.read();
                        }
                        if ((char) r == '\n') r = fis.read();
                        tempP2 = new Vector2F(tempX, tempY);
                        currentEdges.add(new Line2F(tempP, tempP2));
                    }

                    //Height polygon / finish it
                    if ((char) r == '%') {
                        temp = "";
                        r = fis.read();
                        while (!((char) r == '\n' || (char) r == '\r')) {
                            temp += (char) r;
                            r = fis.read();
                        }
                        if ((char) r == '\n') r = fis.read();
                        height = Float.parseFloat(temp);
                        ArrayList<Vector2F> tempPointsArray = new ArrayList<>();
                        for (Line2F e : currentEdges) {
                            tempPointsArray.add(e.getA());
                        }
                        boolean isLine = false;
                        float tmpX1 = currentEdges.get(0).getA().getX();
                        float tmpY1 = currentEdges.get(0).getA().getY();
                        float tmpX2 = currentEdges.get(currentEdges.size() - 1).getB().getX();
                        float tmpY2 = currentEdges.get(currentEdges.size() - 1).getB().getY();

                        if (tmpX1 == tmpX2 && tmpY1 == tmpY2)
                            tempPointsArray.add(currentEdges.get(currentEdges.size() - 1).getB());
                        else {
                            isLine = true;
                            tempPointsArray.add(currentEdges.get(currentEdges.size() - 1).getB());
                        }

                        currentPolys.add(new MapObject(poly, tempPointsArray, height, isLine));
                        poly = "";
                        currentEdges = new ArrayList<>();
                    }

                    //Finish the currentChunk
                    if (r == '!') {
                        chunks[tempCY][tempCX] = new Chunk(currentPolys, tempCX, tempCY);
                        currentPolys = new ArrayList<>();
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("failed");
        }
        return new Map(chunks, idLevel, mapSize);
    }
}
