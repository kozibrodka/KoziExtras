package net.kozibrodka.extra.mixin;

import net.minecraft.client.render.block.BlockRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(BlockRenderer.class)
public interface RenderBlockAccessor {
    @Accessor
    void setEastFaceRotation(int i);
    @Accessor
    void setWestFaceRotation(int i);
    @Accessor
    void setSouthFaceRotation(int i);
    @Accessor
    void setNorthFaceRotation(int i);
    @Accessor
    void setTopFaceRotation(int i);
    @Accessor
    void setBottomFaceRotation(int i);
}
