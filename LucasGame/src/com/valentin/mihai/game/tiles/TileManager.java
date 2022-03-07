package com.valentin.mihai.game.tiles;

import com.valentin.mihai.game.graphics.Sprite;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;

public class TileManager { // gestionarea mapelor

    public static ArrayList<TileMap> tm;

    public TileManager() {
        tm = new ArrayList<TileMap>();
    }

    public TileManager(String path) {
        tm = new ArrayList<TileMap>();
        addTileMap(path, 16, 16); // transmitere marime tile-uri
    }

    // functia de adaugare a unei mape de tile-uri dintr-un fisier xml
    private void addTileMap(String path, int blockWidth, int blockHeight) {

        String imagePath;

        int width = 0;
        int height = 0;
        int tileWidth;
        int tileHeight;
        int tileCount;
        int tileColumns;
        int layer = 0;
        int layers;
        Sprite sprite;

        String[] data = new String[10]; // vector in care stocam layerele ca Strings

        try {
            DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = builderFactory.newDocumentBuilder();
            Document doc = builder.parse(new File(getClass().getClassLoader().getResource(path).toURI()));
            doc.getDocumentElement().normalize();

            // citirea caracteristicilor tilemap-ului dupa tag-uri
            NodeList list = doc.getElementsByTagName("tileset"); // tag-ul tileset
            Node node = list.item(0);
            Element eElement = (Element) node;
            imagePath = eElement.getAttribute("name");
            tileWidth = Integer.parseInt(eElement.getAttribute("tilewidth"));
            tileHeight = Integer.parseInt(eElement.getAttribute("tileheight"));
            tileCount = Integer.parseInt(eElement.getAttribute("tilecount"));
            tileColumns = Integer.parseInt(eElement.getAttribute("columns"));

            // citirea imaginii tileset
            sprite = new Sprite("tiles/" + imagePath + ".png", tileWidth, tileHeight);

            list = doc.getElementsByTagName("layer"); // tag-ul layer
            layers = list.getLength();

            for (int i = 0; i < layers; i++) {
                node = list.item(i);
                eElement = (Element) node;
                if (i <= 0) {
                    width = Integer.parseInt(eElement.getAttribute("width"));
                    height = Integer.parseInt(eElement.getAttribute("height"));
                }
                data[i] = eElement.getElementsByTagName("data").item(0).getTextContent();


                switch(i) {
                    // pentru layerele BaseLayer si InteractLayer desenam TileMapNorm - adica blocuri normale
                    case 0:
                    case 2:
                        tm.add(new TileMapNorm(data[i], sprite, width, height, blockWidth, blockHeight, tileColumns));
                        break;
                        // pentru layerul TopLayer desenam TileMapObject - adica tile-urile de care ne vom lovi
                    case 1:
                        tm.add(new TileMapObject(data[i], sprite, width, height, blockWidth, blockHeight, tileColumns));
                        break;
                }

            }


        } catch( ParserConfigurationException | URISyntaxException | IOException | SAXException e) {
                e.printStackTrace();
        }

    }

    public void render(Graphics2D g) {
        for(int i = 0; i < tm.size(); i++) {
            tm.get(i).render(g);
        }
    }

}
