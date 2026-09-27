package net.kozibrodka.extra.mixin.stapi;

import net.minecraft.block.Block;
import net.minecraft.client.particle.BlockParticle;
import net.minecraft.util.math.BlockPos;
import net.modificationstation.stationapi.api.block.BlockState;
import net.modificationstation.stationapi.api.client.StationRenderAPI;
import net.modificationstation.stationapi.api.client.model.block.BlockWorldModelProvider;
import net.modificationstation.stationapi.api.client.render.model.BakedModel;
import net.modificationstation.stationapi.impl.client.arsenic.renderer.render.particle.ArsenicDiggingParticle;
import net.modificationstation.stationapi.mixin.arsenic.client.BlockParticleAccessor;
import net.modificationstation.stationapi.api.client.texture.Sprite;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

import java.util.Objects;

@Mixin(value = ArsenicDiggingParticle.class, remap = false)
public class ArsenicDiggingParticleMixin {

    @Shadow @Final
    private BlockParticle digging;
    @Shadow private Sprite texture;

    /**
     * @author kozibrodka
     * @reason take into account "model.getOverrides" when selecting particles
     */
    @Overwrite
    public void checkBlockCoords(int x, int y, int z) {
        Block block = ((BlockParticleAccessor)this.digging).getBlock();
        if (block instanceof BlockWorldModelProvider provider) {
            this.texture = provider.getCustomWorldModel(this.digging.world, x, y, z).getBaked().getSprite();
        } else {
            BlockState state = this.digging.world.getBlockState(x, y, z);
            BakedModel model = StationRenderAPI.getBakedModelManager().getBlockModels().getModel(state);
            if (!model.isBuiltin()) {
//                this.texture = model.getSprite();
//                this.texture = StationRenderAPI.getBakedModelManager().getAtlas(Atlases.GAME_ATLAS_TEXTURE).getSprite(Identifier.of("minecraft:block/tnt_side"));
                BlockPos pos = new BlockPos(x, y, z);
                long seed = state.getRenderingSeed(pos);
                model = Objects.requireNonNull(model.getOverrides().apply(model, state, this.digging.world, pos, (int) seed));
                this.texture = model.getSprite();

            }
        }
    }

}
