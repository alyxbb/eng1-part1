package io.github.eng1_group2;

import com.badlogic.gdx.Gdx;
import io.github.eng1_group2.world.building.IncompleteBuilding;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Timer {
    private static final float TOTAL_TIME = 5 * 60;
    private float timeRemaining;
    private final List<IncompleteBuilding> incompleteBuildings;

    public Timer() {
        timeRemaining = TOTAL_TIME;
        incompleteBuildings = new ArrayList<>();
    }

    public void render() {
        float delta = Gdx.graphics.getDeltaTime();
        timeRemaining -= delta;

        // copy buildings array, so we can remove items from it if they have finished building.
        for (IncompleteBuilding building : new ArrayList<>(incompleteBuildings)) {
            building.update(delta);
        }
    }

    public float getTimeRemaining() {
        return timeRemaining;
    }

    public void registerBuilding(IncompleteBuilding building){
        incompleteBuildings.add(building);
    }

    public void unregisterBuilding(IncompleteBuilding incompleteBuilding) {
        incompleteBuildings.remove(incompleteBuilding);
    }
}
