package io.github.eng1_group2.world.building;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import io.github.eng1_group2.Main;
import io.github.eng1_group2.utils.Vec2;

public class Building extends Actor {
    private final BuildingType type;
    private final Main main;
    private final TextureRegion textureRegion;
    private final Rectangle boundingBox;
    private Vec2 origin;

    public Building(BuildingType type, Vec2 origin, Main main) {
        this.type = type;
        this.origin = origin;
        this.main = main;
        Vector2 screenPos = main.getWorld().gridSquareToScreenPos(origin);
        this.setBounds(screenPos.x, screenPos.y, main.getWorld().getGridUnit() * this.type.size().x(), main.getWorld().getGridUnit() * this.type.size().y());
        this.textureRegion = new TextureRegion(main.getAssetManager().get(this.type.texturePath(), Texture.class), this.type.textureOrigin().x(), this.type.textureOrigin().y(), this.type.size().x() * 16, this.type.size().y() * 16);
        this.boundingBox = new Rectangle(origin.x(), origin.y(), this.type.size().x(), this.type.size().y());
        this.setTouchable(Touchable.disabled);
    }

    public void resize() {
        Vector2 screenPos = main.getWorld().gridSquareToScreenPos(origin);
        this.setBounds(screenPos.x, screenPos.y, main.getWorld().getGridUnit() * this.type.size().x(), main.getWorld().getGridUnit() * this.type.size().y());
    }


    public BuildingType getType() {
        return type;
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.draw(this.textureRegion, this.getX(), this.getY(), this.getWidth(), this.getHeight());
    }

    public boolean overlaps(Building other) {
        if (this.boundingBox.overlaps(other.boundingBox)) {
            return true;
        }
        return false;
    }
}
