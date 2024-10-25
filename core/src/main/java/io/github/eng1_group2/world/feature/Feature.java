package io.github.eng1_group2.world.feature;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import io.github.eng1_group2.Main;
import io.github.eng1_group2.utils.Vec2;
import io.github.eng1_group2.utils.hasBoundingBox;

public class Feature extends Actor implements hasBoundingBox {
    private final FeatureType type;
    private final TextureRegion textureRegion;
    private final Rectangle boundingBox;
    private final Main main;
    private final Vec2 size;
    private Vec2 origin;

    public Feature(FeatureType type, Vec2 origin, Vec2 size, Main main) {
        this.type = type;
        this.origin = origin;
        this.main = main;
        this.size = size;
        Vector2 screenPos = main.getWorld().gridSquareToScreenPos(origin);
        this.setBounds(screenPos.x, screenPos.y, main.getWorld().getGridUnit() * size.x(), main.getWorld().getGridUnit() * size.y());
        this.textureRegion = new TextureRegion(main.getAssetManager().get(this.type.texturePath(), Texture.class), this.type.textureOrigin().x(), this.type.textureOrigin().y(), 16, 16);
        this.boundingBox = new Rectangle(origin.x(), origin.y(), size.x(), size.y());
        this.setTouchable(Touchable.disabled);
    }

    public void resize() {
        Vector2 screenPos = main.getWorld().gridSquareToScreenPos(origin);
        this.setBounds(screenPos.x, screenPos.y, main.getWorld().getGridUnit() * size.x(), main.getWorld().getGridUnit() * size.y());
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        int gridUnit = main.getWorld().getGridUnit();
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
