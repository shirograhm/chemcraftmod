package solitudetraveler.chemcraftmod.generation;

import net.minecraftforge.common.ForgeConfigSpec;

class OreGenConfig {
    static ForgeConfigSpec.IntValue large_vein;
    static ForgeConfigSpec.IntValue medium_vein;
    static ForgeConfigSpec.IntValue small_vein;
    static ForgeConfigSpec.BooleanValue generate_overworld;

    static void init(ForgeConfigSpec.Builder server, ForgeConfigSpec.Builder client) {
        server.comment("Ore generation config...");

        small_vein = server
                .comment("Ore blocks per vein - small edition.")
                .defineInRange("oregen.chance", 1, 1, 2);
        medium_vein = server
                .comment("Ore blocks per vein - medium edition.")
                .defineInRange("oregen.chance", 4, 3, 5);
        large_vein = server
                .comment("Ore blocks per vein - large edition.")
                .defineInRange("oregen.chance", 8, 6, 10);
        generate_overworld = server
                .comment("Whether or not you want chemcraft ore generation.")
                .define("oregen.generate_overworld", true);
    }
}
