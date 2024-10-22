package io.github.eng1_group2;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.viewport.Viewport;

public class UI {
    private float startX;
    private final Viewport viewport;
    public static final float UI_RATIO = 0.8f;


    public UI(Viewport viewport) {
        this.viewport = viewport;
    }

    // in the constructor the viewport has no width but resize always gets called at init
    public void resize() {
        this.startX = viewport.getWorldWidth() * UI_RATIO;
    }

    public void render() {
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
