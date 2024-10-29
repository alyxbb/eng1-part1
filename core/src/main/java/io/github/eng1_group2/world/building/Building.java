package io.github.eng1_group2.world.building;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import io.github.eng1_group2.Main;
import io.github.eng1_group2.utils.Vec2;

public class Building extends AbstractBuilding {


    public Building(BuildingType type, Vec2 origin, Main main) {
        super(
            type,
            origin,
            main,
            new TextureRegion(
                main.getAssetManager().get(type.texture().path(), Texture.class),
                type.texture().origin().x(),
                type.texture().origin().y(),
                type.size().x() * 16,
                type.size().y() * 16
            )
        );
    }
}
