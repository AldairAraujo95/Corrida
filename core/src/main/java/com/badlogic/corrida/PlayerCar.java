package com.badlogic.corrida;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public class PlayerCar {
    private Texture texture;
    private Rectangle bounds;
    private float speed = 200f; // pixels por segundo

    public PlayerCar(Texture texture, float x, float y) {
        this.texture = texture;
        this.bounds = new Rectangle(x, y, texture.getWidth(), texture.getHeight());
    }

    public void update(float deltaTime, int pistaEsquerda, int pistaDireita) {
        // Movimento pelas teclas
        if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
            bounds.y += speed * deltaTime;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            bounds.y -= speed * deltaTime;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            bounds.x -= speed * deltaTime;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            bounds.x += speed * deltaTime;
        }

        // 🔹 Limitar dentro da pista
        if (bounds.x < pistaEsquerda) {
            bounds.x = pistaEsquerda;
        }
        if (bounds.x + bounds.width > pistaDireita) {
            bounds.x = pistaDireita - bounds.width;
        }

        // 🔹 Limitar vertical (não sair da tela)
        if (bounds.y < 0) {
            bounds.y = 0;
        }
        if (bounds.y + bounds.height > Gdx.graphics.getHeight()) {
            bounds.y = Gdx.graphics.getHeight() - bounds.height;
        }
    }

    public void draw(SpriteBatch batch) {
        batch.draw(texture, bounds.x, bounds.y);
    }

    public Rectangle getBounds() {
        return bounds;
    }

    public void dispose() {
        texture.dispose();
    }
}
