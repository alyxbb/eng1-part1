package io.github.eng1_group2.world;


import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import io.github.eng1_group2.Main;
import io.github.eng1_group2.UI;
import io.github.eng1_group2.utils.Vec2;
import io.github.eng1_group2.world.building.Building;
import io.github.eng1_group2.world.building.BuildingType;

import java.util.ArrayList;
import java.util.List;


public class World extends InputAdapter {
    private final Vec2 gridSize = new Vec2(10, 10);
    private final List<Building> buildings;
    private int gridUnit;
    private final Main main;

    public World(Main main) {
        this.buildings = new ArrayList<>();
        var buildingTypes = main.getRegistries().getBuildingTypes();
        this.buildings.add(new Building(buildingTypes.get("house"), new Vec2(3, 4)));
        this.buildings.add(new Building(buildingTypes.get("lecture_theatre"), new Vec2(0, 1)));
        this.buildings.add(new Building(buildingTypes.get("cafe"), new Vec2(0, 2)));
        this.main = main;
    }


    public void render() {
        ShapeRenderer shapeRenderer = new ShapeRenderer();
        shapeRenderer.setProjectionMatrix(main.getViewport().getCamera().combined);

        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        shapeRenderer.setColor(0.7f, 0.7f, 0.7f, 1f);
        // draw grid lines
        // skip the first one, so we only do internal lines
        for (int i = 1; i < gridSize.x(); i++) {
            shapeRenderer.line(i * gridUnit, 0, i * gridUnit, gridSize.y() * gridUnit);
        }
        for (int i = 1; i < gridSize.y(); i++) {
            shapeRenderer.line(0, i * gridUnit, gridSize.x() * gridUnit, i * gridUnit);
        }
        shapeRenderer.end();

        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        for (var building : this.buildings) {
            shapeRenderer.setColor(building.getType().color());
            shapeRenderer.rect(building.getBoundingBox().getOrigin().x() * gridUnit, building.getBoundingBox().getOrigin().y() * gridUnit, gridUnit * building.getBoundingBox().getSize().x(), gridUnit * building.getBoundingBox().getSize().y());
        }
        shapeRenderer.end();
    }

    public void resize() {
        gridUnit = Math.round(Math.min((main.getViewport().getWorldWidth()) / gridSize.x(), main.getViewport().getWorldHeight() / gridSize.y()));
    }

    public void addBuilding(BuildingType buildingType, Vec2 location) {
        if (location.x() < 0 || location.y() < 0) {
            throw new IllegalArgumentException("location must be positive");
        }
        if (location.x() + buildingType.size().x() > gridSize.x() || location.y() + buildingType.size().y() > gridSize.y()) {
            throw new IllegalArgumentException("building would extend outside grid");
        }
        Building building = new Building(buildingType, location);
        for (Building testBuilding : buildings) {
            if (testBuilding.getBoundingBox().intersects(building.getBoundingBox())) {
                throw new IllegalArgumentException("building would intersect with a building");
            }
        }
        buildings.add(building);
    }


    public Vec2 screenPosToGridSquare(Vector2 screenPos) {
        if (screenPos.x < 0 || screenPos.y < 0) {
            throw new IllegalArgumentException("screenpos must be positive");
        }
        Vec2 gridPos = new Vec2((int) screenPos.x / gridUnit, (int) screenPos.y / gridUnit);
        if (gridPos.x() >= gridSize.x() || gridPos.y() >= gridSize.y()) {
            throw new IllegalArgumentException("position is not in the grid");
        }
        return gridPos;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {

        Vector2 touchPos = new Vector2(screenX, screenY);
        main.getViewport().unproject(touchPos);
        if (main.getViewport().getWorldWidth() * UI.UI_RATIO < touchPos.x) {
            return false;
        }
        try {
            Vec2 gridSquare = screenPosToGridSquare(touchPos);
            addBuilding(main.getUi().getSelectedBuilding(), gridSquare);
        } catch (IllegalArgumentException e) {
            System.out.println(e);
        }
        return true;
    }
}
