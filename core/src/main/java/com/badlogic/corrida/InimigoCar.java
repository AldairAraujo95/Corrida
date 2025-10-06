package com.badlogic.corrida;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public class InimigoCar {
    private Texture texture;
    private Rectangle bounds;
    private float speed;

    public InimigoCar(Texture texture, float x, float y) {
        this.texture = texture;
        this.bounds = new Rectangle(x, y, texture.getWidth(), texture.getHeight());
        this.speed = 350; // velocidade do inimigo
    }

    public void update(float deltaTime) {
        // movimenta o carro inimigo para baixo
        bounds.y -= speed * deltaTime;
    }

    public void draw(SpriteBatch batch) {
        batch.draw(texture, bounds.x, bounds.y);
    }

    public boolean isOutOfScreen() {
        // se o carro inimigo passou da parte de baixo da tela
        return bounds.y + bounds.height < 0;
    }

    public Rectangle getBounds() {
        return bounds;
    }

    public void dispose() {
        texture.dispose();
    }
}
