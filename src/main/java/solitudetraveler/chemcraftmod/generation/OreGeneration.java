package solitudetraveler.chemcraftmod.generation;

import net.minecraft.block.BlockState;
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
                // Dolostone generation
                addOreGenerationToBiome(biome, 20, 24, 96, 100, BlockList.dolostone.getDefaultState());
                // Copper ore generation
                addOreGenerationToBiome(biome, 0, 6, 70, 65, BlockList.copper_ore.getDefaultState());
                // Nickel ore generation
                addOreGenerationToBiome(biome, 12, 24, 52, 40, BlockList.nickel_ore.getDefaultState());
            }
        }
    }

    private static void addOreGenerationToBiome(Biome biome, int topOffset, int bottomOffset, int maximum, int count, BlockState blockState) {
        CountRangeConfig placement = new CountRangeConfig(count, bottomOffset, topOffset, maximum);
        OreFeatureConfig config = new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, blockState, OreGenConfig.chance.get());

        biome.addFeature(Decoration.UNDERGROUND_ORES, Biome.createDecoratedFeature(Feature.ORE, config, Placement.COUNT_RANGE, placement));
    }
}
