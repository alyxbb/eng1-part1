package io.github.eng1_group2.world.building;

import io.github.eng1_group2.Main;
import io.github.eng1_group2.utils.Vec2;

public class IncompleteBuilding extends AbstractBuilding {
    private float buildTimeRemaining;

    public IncompleteBuilding(BuildingType type, Vec2 origin, Main main) {
        super(type,
            origin,
            main,
            main.getWorld().getConfig().incompleteBuilding().getTextureRegion(main.getAssetManager(), new Vec2(16, 16)));
        buildTimeRemaining = type.buildTime();
    }

    public void update(float delta) {
        buildTimeRemaining -= delta;
        if (buildTimeRemaining <= 0) {
            main.getWorld().completeBuilding(this);
        }
    }

    public BuildingType getType() {
        return this.type;
    }
}
