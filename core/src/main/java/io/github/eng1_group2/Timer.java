package io.github.eng1_group2;

import com.badlogic.gdx.Gdx;
import io.github.eng1_group2.world.World;
import io.github.eng1_group2.world.building.IncompleteBuilding;

import java.util.ArrayList;
import java.util.List;

public class Timer {
    private final List<IncompleteBuilding> incompleteBuildings;
    private float timeRemaining;
    private boolean paused;

    public Timer(World world) {
        timeRemaining = world.getConfig().gameDuration();
        incompleteBuildings = new ArrayList<>();
        paused = false;
    }

    public void update() {
        // this does all the logic related to time passing
        if (paused) {
            return;
        }
        float delta = Gdx.graphics.getDeltaTime();
        timeRemaining -= delta;

        // copy buildings array, so we can remove items from it if they have finished building.
        for (IncompleteBuilding building : new ArrayList<>(incompleteBuildings)) {
            building.update(delta);
        }
    }


    public void registerBuilding(IncompleteBuilding building) {
        incompleteBuildings.add(building);
    }

    public void unregisterBuilding(IncompleteBuilding incompleteBuilding) {
        incompleteBuildings.remove(incompleteBuilding);
    }

    public float getTimeRemaining() {
        return timeRemaining;
    }

    public boolean isPaused() {
        return paused;
    }

    public void togglePause() {
        this.paused = !this.paused;
    }
}
