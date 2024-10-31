package io.github.eng1_group2.world.feature;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import io.github.eng1_group2.utils.HasBoundingBox;
import io.github.eng1_group2.utils.Vec2;
import io.github.eng1_group2.world.World;

public class Feature extends Actor implements HasBoundingBox {
    private final FeatureType type;
    private final TextureRegion textureRegion;
    private final Rectangle boundingBox;
    private final World world;
    private final Vec2 size;
    private final Vec2 origin;

    public Feature(FeatureConfig config, World world) {
        this.type = config.type();
        this.origin = config.position();
        this.size = config.size();
        this.world = world;
        Vector2 screenPos = world.gridSquareToScreenPos(origin);
        this.setBounds(screenPos.x, screenPos.y, world.getGridUnit() * size.x(), world.getGridUnit() * size.y());
        this.textureRegion = this.type.texture().getTextureRegion(world.getMain().getAssetManager(), new Vec2(16, 16));
        this.boundingBox = new Rectangle(origin.x(), origin.y(), size.x(), size.y());
        this.setTouchable(Touchable.disabled);
    }

    public void resize() {
        Vector2 screenPos = world.gridSquareToScreenPos(origin);
        this.setBounds(screenPos.x, screenPos.y, world.getGridUnit() * size.x(), world.getGridUnit() * size.y());
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        int gridUnit = world.getGridUnit();
        for (int i = 0; i < size.x(); i++) {
            for (int j = 0; j < size.y(); j++) {
                batch.draw(this.textureRegion, this.getX() + gridUnit * i, this.getY() + gridUnit * j, gridUnit, gridUnit);
            }
        }
    }

    @Override
    public Rectangle getBoundingBox() {
        return this.boundingBox;
    }
}
