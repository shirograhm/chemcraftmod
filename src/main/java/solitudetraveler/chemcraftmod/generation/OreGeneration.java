package solitudetraveler.chemcraftmod.generation;

import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.GenerationStage.Decoration;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.OreFeatureConfig;
import net.minecraft.world.gen.placement.CountRangeConfig;
import net.minecraft.world.gen.placement.Placement;
import net.minecraftforge.registries.ForgeRegistries;
import solitudetraveler.chemcraftmod.block.BlockList;

public class OreGeneration {

    public static void setupOreGeneration() {
        if(OreGenConfig.generate_overworld.get()) {
            for(Biome biome : ForgeRegistries.BIOMES) {
                CountRangeConfig dolomite_placement = new CountRangeConfig(100, 24, 20, 96);
                OreFeatureConfig dolomite_config = new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, BlockList.dolostone.getDefaultState(), OreGenConfig.chance.get());

                biome.addFeature(Decoration.UNDERGROUND_ORES, Biome.createDecoratedFeature(Feature.ORE, dolomite_config, Placement.COUNT_RANGE, dolomite_placement));
            }
        }
    }
}
