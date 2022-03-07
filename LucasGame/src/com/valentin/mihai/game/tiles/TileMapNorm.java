package com.valentin.mihai.game.tiles;

import com.valentin.mihai.game.graphics.Sprite;
import com.valentin.mihai.game.tiles.blocks.Block;
import com.valentin.mihai.game.tiles.blocks.NormBlock;
import com.valentin.mihai.game.util.Vector2f;

import java.awt.*;
import java.util.ArrayList;

public class TileMapNorm extends TileMap{

    private ArrayList<Block> blocks;

    public TileMapNorm(String data, Sprite sprite, int width, int height, int tileWidth, int tileHeight, int tileColumns) {
        blocks = new ArrayList<Block>();

        String[] block = data.split(",");
        for(int i = 0; i < (width * height); i++) {
            int temp = Integer.parseInt(block[i].replaceAll("\\s+",""));
                if(temp != 0) {
                    // adaugam blocurile la pozitiile potrivite pe ecran
                    blocks.add(new NormBlock(sprite.getSprite((int) (temp - 1) % tileColumns, (int)(temp - 1) / tileColumns), new Vector2f((int)(i % width) * tileWidth, (int)(i / height) * tileHeight), tileWidth, tileHeight));
                }

        }
    }

    @Override
    public void render(Graphics2D g) {
        for(int i =0; i < blocks.size(); i++) {
            blocks.get(i).render(g);
        }
    }
}
