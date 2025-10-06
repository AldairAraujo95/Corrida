package com.badlogic.corrida;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.math.MathUtils;

public class GameCorrida extends ApplicationAdapter {
    private SpriteBatch batch;
    private ShapeRenderer shapeRenderer; // permite desenhar formas geométricas
    private PlayerCar playerCar;
    private Texture inimigoTexture;
    private Array<InimigoCar> inimigos;

    private float spawnTimer = 0f;
    private boolean gameOver = false;

    // limites da pista
    private int pistaEsquerda = 100;
    private int pistaDireita = 500;

    @Override
    public void create() {
        batch = new SpriteBatch();
        shapeRenderer = new ShapeRenderer();

        Texture carTexture = new Texture("carro.png");
        playerCar = new PlayerCar(carTexture, 200, 50); // posição inicial dentro da pista

        inimigoTexture = new Texture("inimigo.png");
        inimigos = new Array<>();
    }

    @Override
    public void render() {
        float deltaTime = Gdx.graphics.getDeltaTime();
        ScreenUtils.clear(1, 1, 1, 1);

        if (!gameOver) {
            playerCar.update(deltaTime, pistaEsquerda, pistaDireita);

            // Spawn automático a cada 2 segundos
            spawnTimer += deltaTime;
            if (spawnTimer > 2f) {
                int quantidade = MathUtils.random(2, 4); // vários inimigos por vez
                for (int i = 0; i < quantidade; i++) {
                    float x = MathUtils.random(pistaEsquerda,
                        pistaDireita - inimigoTexture.getWidth());
                    inimigos.add(new InimigoCar(inimigoTexture, x, Gdx.graphics.getHeight() + (i * 80)));
                }
                spawnTimer = 0f;
            }

            // Atualizar inimigos
            for (int i = 0; i < inimigos.size; i++) {
                InimigoCar inimigo = inimigos.get(i);
                inimigo.update(deltaTime);

                if (inimigo.isOutOfScreen()) {
                    inimigos.removeIndex(i);
                    i--;
                    continue;
                }

                // Colisão 🚨
                if (playerCar.getBounds().overlaps(inimigo.getBounds())) {
                    System.out.println("💥 Game Over!");
                    gameOver = true;
                }
            }
        }

        // 🔹 Desenhar pista (linhas brancas)
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        shapeRenderer.setColor(0, 0, 0, 1);
        shapeRenderer.line(pistaEsquerda, 0, pistaEsquerda, Gdx.graphics.getHeight());
        shapeRenderer.line(pistaDireita, 0, pistaDireita, Gdx.graphics.getHeight());
        shapeRenderer.end();

        // 🔹 Desenhar carros
        batch.begin();
        playerCar.draw(batch);
        for (InimigoCar inimigo : inimigos) {
            inimigo.draw(batch);
        }
        batch.end();
    }

    @Override
    public void dispose() {
        batch.dispose();
        shapeRenderer.dispose();
        playerCar.dispose();
        inimigoTexture.dispose();
    }
}
