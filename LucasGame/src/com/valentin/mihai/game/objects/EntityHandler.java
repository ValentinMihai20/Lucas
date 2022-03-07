package com.valentin.mihai.game.objects;

import com.valentin.mihai.game.graphics.Sprite;
import com.valentin.mihai.game.objects.characters.DumbEnemy;
import com.valentin.mihai.game.objects.characters.Player;
import com.valentin.mihai.game.objects.characters.SmartEnemy;
import com.valentin.mihai.game.objects.items.Key;
import com.valentin.mihai.game.objects.structures.Chest;
import com.valentin.mihai.game.objects.structures.Door;
import com.valentin.mihai.game.states.GameState;
import com.valentin.mihai.game.util.KeyHandler;
import com.valentin.mihai.game.util.MouseHandler;
import com.valentin.mihai.game.util.Vector2f;

import java.awt.*;
import java.util.ArrayList;

public class EntityHandler { // gestionarea entitatilor

    public ArrayList<Entity> entities;

    public EntityHandler() {
        entities = new ArrayList<Entity>();
    }

    public boolean isEmpty() {
      return entities.isEmpty();
    }

    public void pop(Entity entity) {
        entities.remove(entity);
    } // inlatura o entitate

    public void pop() {
        entities.remove(entities.size() - 1);
    } // inlatura ultima entitate

    public void removeAllEntities() {
        for(Entity tempEntity : entities) {
            entities.remove(tempEntity);
        }
    }

    public void update() {
        for(Entity tempEntity : entities) {
            tempEntity.update();
        }
    }

    public void addPlayer(Sprite sprite, Vector2f pos, int width, int height, int state) {
            entities.add(new Player(this, ID.Player, sprite, pos, width, height, state));
    }

    public void add(ID id, Vector2f pos, int width, int height, String file) {
        switch (id) {
            case Chest:
                entities.add(new Chest(this, file, id, pos, width, height,1.5f,0, 1));
                break;
            case Key1:
                entities.add(new Key(this, file, id, pos, width, height));
                break;
        }
    }

    // functii de adaugare entitati specifice

    public void addDumbEnemy(Sprite sprite, Vector2f pos, int width, int height, int mode) {
        entities.add(new DumbEnemy(this, ID.DumbEnemy, sprite, pos, width, height, mode));
    }

    public void addDoor(Vector2f pos, ID[] code) {
        entities.add(new Door(this, "entity/structures/Door.png", ID.Door, pos, 64, 64, code));
    }

    public void addChest(Vector2f pos, int code, float defX, float defY) {
        entities.add(new Chest(this,"entity/structures/Chest.png", ID.Chest, pos, 44, 32, defX, defY, code));
    }

    public void addSmartEnemy(Sprite sprite, Vector2f pos, int width, int height, Vector2f home, int radius) {
        entities.add(new SmartEnemy(this, ID.SmartEnemy, sprite, pos, width, height, home, radius));
    }

    public void add(Entity entity) {
        entities.add(entity);
    }


    public void input(MouseHandler mouse, KeyHandler key) {
        for (int i = 0; i < entities.size(); i++) {
            entities.get(i).input(key, mouse);
        }
    }

    public void render(Graphics2D g) {
        for (int i = 0; i < entities.size(); i++) {
            entities.get(i).render(g);
        }
    }

    public Entity getEntity(int index) {
        return entities.get(index);
    }

    public void remove(Entity entity) {
        entities.remove(entity);
    }

}
