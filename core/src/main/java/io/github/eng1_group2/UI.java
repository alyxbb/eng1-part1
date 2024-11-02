package io.github.eng1_group2;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.ui.Button.ButtonStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Timer;
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
    private final VerticalGroup buildingSelector;
    private float startX;
    private BuildingType selectedBuilding;
    private Timer.Task hideWarningTask;

    public UI(Main main) {
        this.main = main;
        this.stage = new Stage(this.main.getViewport());

        // styles
        // Do we want to add our own font?
        BitmapFont font = new BitmapFont();
        // label styles
        LabelStyle whiteLabelStyle = new LabelStyle(font, Color.WHITE);
        LabelStyle greenLabelStyle = new LabelStyle(font, Color.GREEN);
        LabelStyle redLabelStyle = new LabelStyle(new BitmapFont(), Color.RED);
        // buttons styles
        TextButtonStyle textButtonStyle = new TextButtonStyle();
        textButtonStyle.font = font;
        ButtonStyle buildingTypeStyle = new ButtonStyle();

        //create the ui layout table
        this.table = new Table();
        this.table.setDebug(DEBUG, true);
        this.stage.addActor(this.table);

        //balance
        balanceIndicator = new Label("£", whiteLabelStyle);
        this.table.add(balanceIndicator).top().left();

        // pause button
        TextButton pauseButton = new TextButton("pause", textButtonStyle);
        pauseButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                main.getTimer().togglePause();
                String text = main.getTimer().isPaused() ? "unpause" : "pause";
                pauseButton.setText(text);
            }
        });
        this.table.add(pauseButton).top().right();
        this.table.row();

        //timer
        this.timer = new Label("00:00", whiteLabelStyle);
        this.table.add(timer);
        this.table.row();

        //building Selector
        buildingSelector = new VerticalGroup();
        ScrollPane buildingSelectorScroller = new ScrollPane(buildingSelector);
        this.table.add(buildingSelectorScroller).colspan(2).expand();
        this.table.row();


        //add buildings to building selector
        for (BuildingType buildingType : main.getRegistries().getDynamic().getBuildingTypes()) {

            //the button
            Button button = new Button(buildingTypeStyle);
            button.setDebug(DEBUG, true);
            buildingSelector.addActor(button);


            //add image to button
            button.add(new Image(new TextureRegionDrawable(buildingType.texture().getTextureRegion(main.getAssetManager(), buildingType.size().mul(16))))).width(75).height(75);

            //add text to button
            //new line at start is to fix a weird bug where the first line of the first item gets cut off.
            Label label = new Label(String.format("\nName: %s\nCost: £%,d\nBuildTime: %02d:%02d\nCategory: %s", buildingType.name(), buildingType.cost(), (int) (buildingType.buildTime() / 60), (int) buildingType.buildTime() % 60, buildingType.category().getName()), redLabelStyle);
            button.add(label);

            //add listener
            button.addListener(new ChangeListener() {
                @Override
                public void changed(ChangeEvent event, Actor actor) {
                    for (Actor child : buildingSelector.getChildren()) {
                        ((Label) ((Button) child).getCells().get(1).getActor()).setStyle(redLabelStyle);
                    }
                    label.setStyle(greenLabelStyle);
                    selectedBuilding = buildingType;
                }
            });


        }
        //set default building
        ((Button) buildingSelector.getChildren().get(0)).toggle();


        //warning box
        this.warningBox = new Label("", redLabelStyle);
        this.warningBox.setAlignment(Align.bottom);
        this.warningBox.setWrap(true);
        this.table.add(warningBox).bottom().colspan(2).height(75);
    }

    public Stage getStage() {
        return stage;
    }

    // in the constructor the viewport has no width but resize always gets called at startup so we do all the sizing logic here
    public void resize() {
        Viewport viewport = main.getViewport();
        this.startX = viewport.getWorldWidth() * UI_RATIO;
        this.table.setPosition(startX, 0);
        this.table.setWidth(viewport.getScreenWidth() - startX);
        this.table.setHeight(viewport.getWorldHeight());
        this.table.getCell(this.warningBox).width(table.getWidth());
    }

    public void render() {
        this.balanceIndicator.setText(String.format("£%,d", main.getWorld().getBalance()));
        int timeRemaining = (int) main.getTimer().getTimeRemaining();
        this.timer.setText(String.format("%02d:%02d", timeRemaining / 60, timeRemaining % 60));

        //draw a line separating the ui from the map
        ShapeRenderer shapeRenderer = new ShapeRenderer();
        shapeRenderer.setProjectionMatrix(main.getViewport().getCamera().combined);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        shapeRenderer.setColor(1f, 1f, 1f, 1f);
        shapeRenderer.line(startX, 0, startX, main.getViewport().getWorldHeight());
        shapeRenderer.end();

        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();
    }

    public BuildingType getSelectedBuilding() {
        return selectedBuilding;
    }

    public void setWarningMessage(String message) {
        this.warningBox.setText(message);
        if (this.hideWarningTask != null){
            this.hideWarningTask.cancel();
        }
        this.hideWarningTask = new Timer.Task() {
            @Override
            public void run() {
                warningBox.setText("");
            }
        };
        Timer.schedule(this.hideWarningTask,5);
    }
}
