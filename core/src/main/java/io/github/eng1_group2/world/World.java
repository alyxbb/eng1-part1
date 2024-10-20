package io.github.eng1_group2.world;


import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import io.github.eng1_group2.world.building.Building;
import io.github.eng1_group2.world.building.BuildingType;
import io.github.eng1_group2.utils.Vec2;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class World {
    private final Vec2 gridSize = new Vec2(10, 10);
    private final List<Building> buildings;


    public World() {
        this.buildings = new ArrayList<>();
        this.buildings.add(new Building(new BuildingType("house", Color.RED,new Vec2(2,2)),new Vec2(3, 4)));
        this.buildings.add(new Building(new BuildingType("lecture_theatre", Color.BLUE,new Vec2(1,1)),new Vec2(0, 1)));
        this.buildings.add(new Building(new BuildingType("cafe", Color.GREEN,new Vec2(3,1)),new Vec2(0, 2)));
    }

    public void renderBuildings(Camera camera) {
        int gridUnit = Math.round(Math.min(camera.viewportWidth / gridSize.x(), camera.viewportHeight / gridSize.y()));

        ShapeRenderer shapeRenderer = new ShapeRenderer();
        shapeRenderer.setProjectionMatrix(camera.combined);

        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        shapeRenderer.setColor(0.7f, 0.7f, 0.7f, 1f);
        // draw grid lines
        for (int i = 0; i < gridSize.x(); i++) {
            shapeRenderer.line(i * gridUnit, 0, i * gridUnit, gridSize.y() * gridUnit);
        }
        for (int i = 0; i < gridSize.y(); i++) {
            shapeRenderer.line(0, i * gridUnit, gridSize.x() * gridUnit, i * gridUnit);
        }
        shapeRenderer.end();

        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        for (var building: this.buildings) {
            shapeRenderer.setColor(building.getType().color());
            shapeRenderer.rect(building.getOrigin().x() * gridUnit, building.getOrigin().y() * gridUnit, gridUnit * building.getType().size().x(), gridUnit * building.getType().size().y());
        }
        shapeRenderer.end();
    }
}
