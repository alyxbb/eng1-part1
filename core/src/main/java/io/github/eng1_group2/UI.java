package io.github.eng1_group2;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.viewport.Viewport;

public class UI {
    private float startX;

    public UI(float uiRatio, Viewport viewport) {
    }

    //in the constructor the viewport has no width but resize always gets called at init
    public void resize(float uiRatio, Viewport viewport) {
        this.startX = viewport.getWorldWidth() * uiRatio;

    }

    public void render(Viewport viewport) {
        ShapeRenderer shapeRenderer = new ShapeRenderer();
        shapeRenderer.setProjectionMatrix(viewport.getCamera().combined);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        shapeRenderer.setColor(1f, 1f, 1f, 1f);
        // start of ui line
        shapeRenderer.line(startX, 0, startX, viewport.getWorldHeight());

        // button or smthn
        shapeRenderer.rect(startX + 10, 50, 50, 50);
        shapeRenderer.end();
    }
}
