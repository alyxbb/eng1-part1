package io.github.eng1_group2;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
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
    private final Label balanceIndicator;
    private final Label warningBox;
    private final Label timer;
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
                main.getTimer().togglePause();
                String text = "pause";
                if (main.getTimer().isPaused()){
                    text = "unpause";
                }
                ((TextButton) actor).setText(text);

            }
        });
        LabelStyle labelStyle = new LabelStyle();
        labelStyle.font = new BitmapFont();
        Label balanceIndicator = new Label("£", labelStyle);
        this.balanceIndicator = balanceIndicator;
        VerticalGroup buildingSelector = new VerticalGroup();

        this.timer = new Label("00:00", labelStyle);

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
        selectedBuilding = main.getRegistries().getBuildingTypes().iterator().next();

        labelStyle.font = new BitmapFont();
        this.warningBox = new Label("", labelStyle);

        ScrollPane buildingSelectorScroller = new ScrollPane(buildingSelector);

        this.table = new Table();

        this.stage = new Stage(this.main.getViewport());
        this.stage.addActor(this.table);
        this.table.setDebug(DEBUG, true);
        this.table.add(balanceIndicator).top().left();
        this.table.add(pauseButton).top().right();
        this.table.row();
        this.table.add(timer);
        this.table.row();
        this.table.add(buildingSelectorScroller).colspan(2).expand();
        this.table.row();
        this.table.add(warningBox).bottom().colspan(2);
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
        this.balanceIndicator.setText(String.format("£%,d", main.getWorld().getBalance()));
        int timeRemaining = (int) main.getTimer().getTimeRemaining();
        this.timer.setText(String.format("%d:%d", timeRemaining / 60, timeRemaining % 60));
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

    public void setWarningMessage(String message) {
        this.warningBox.setText(message);
    }
}
