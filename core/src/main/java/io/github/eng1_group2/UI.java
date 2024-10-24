package io.github.eng1_group2;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.Viewport;
import io.github.eng1_group2.world.building.BuildingType;

public class UI {
    public static final float UI_RATIO = 0.8f;
    public static final boolean DEBUG = Boolean.getBoolean("unisim.ui_debug");
    private final Main main;
    private final Stage stage;
    private final Table table;
    private float startX;
    private BuildingType selectedBuilding;


    public UI(Main main) {
        this.main = main;

        TextButtonStyle style = new TextButtonStyle();
        style.font = new BitmapFont();
        TextButton pauseButton = new TextButton("pause", style);
        pauseButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                System.out.println("clicky clicky click click");
            }
        });

        VerticalGroup buildingSelector = new VerticalGroup();

        for (BuildingType buildingType : main.getRegistries().getBuildingTypes()) {
            Button button = new TextButton(buildingType.name(), style);
            buildingSelector.addActor(button);
            button.addListener(new ChangeListener() {
                @Override
                public void changed(ChangeEvent event, Actor actor) {
                    selectedBuilding = buildingType;
                }
            });
        }

        ScrollPane buildingSelectorScroller = new ScrollPane(buildingSelector);

        this.table = new Table();

        this.stage = new Stage(this.main.getViewport());
        this.stage.addActor(this.table);
        this.table.setDebug(DEBUG, true);
        this.table.add(pauseButton).top().right();
        this.table.row();
        this.table.add(buildingSelectorScroller).expand();
    }

    public Stage getStage() {
        return stage;
    }

    // in the constructor the viewport has no width but resize always gets called at init
    public void resize() {
        Viewport viewport = main.getViewport();
        this.startX = viewport.getWorldWidth() * UI_RATIO;
        this.table.setPosition(startX, 0);
        this.table.setWidth(viewport.getScreenWidth() - startX);
        this.table.setHeight(viewport.getWorldHeight());
    }

    public void render() {
        Viewport viewport = main.getViewport();
        ShapeRenderer shapeRenderer = new ShapeRenderer();
        shapeRenderer.setProjectionMatrix(viewport.getCamera().combined);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        shapeRenderer.setColor(1f, 1f, 1f, 1f);
        // start of ui line
        shapeRenderer.line(startX, 0, startX, viewport.getWorldHeight());
        shapeRenderer.end();
        float delta = Gdx.graphics.getDeltaTime();
        stage.act(delta);
        stage.draw();
    }

    public BuildingType getSelectedBuilding() {
        return selectedBuilding;
    }
}
