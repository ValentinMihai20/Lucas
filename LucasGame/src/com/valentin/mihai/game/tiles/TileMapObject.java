package com.valentin.mihai.game.tiles;

import com.valentin.mihai.game.graphics.Sprite;
import com.valentin.mihai.game.tiles.blocks.Block;
import com.valentin.mihai.game.tiles.blocks.ObjBlock;
import com.valentin.mihai.game.util.Vector2f;

import java.awt.*;
import java.util.HashMap;

public class TileMapObject extends TileMap {

    public static HashMap<String, Block> tmo_blocks;

    public TileMapObject(String data, Sprite sprite, int width, int height, int tileWidth, int tileHeight, int tileColumns) {
        Block tempBlock;

        // pentru blocuri obiect, stocarea o facem cu un hashmap
        tmo_blocks = new HashMap<String, Block>();

        String[] block = data.split(",");
        for(int i = 0; i < (width * height); i++) {
            int temp = Integer.parseInt(block[i].replaceAll("\\s+",""));
            if(temp != 0) {
               tempBlock = new ObjBlock(sprite.getSprite( (int) ((temp - 1) % tileColumns), (int) ((temp - 1) / tileColumns)), new Vector2f((int)(i % width) * tileWidth, (int) ((i / height) * tileHeight)), tileWidth, tileHeight);
               // asociaza fiecarui bloc un string de tip (x,y), care ne va ajuta la coliziuni
               tmo_blocks.put(String.valueOf((int) (i % width)) + "," + String.valueOf((int) (i / height)), tempBlock);
            }
        }
    }

    @Override
    public void render(Graphics2D g) {
       for(Block block : tmo_blocks.values()) {
           block.render(g);
       }
    }

}
