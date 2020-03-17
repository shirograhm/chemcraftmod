package solitudetraveler.chemcraftmod.main;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScreenManager;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;
import solitudetraveler.chemcraftmod.block.BlockVariables;
import solitudetraveler.chemcraftmod.screen.*;

public class ClientProxy implements IProxy {
    @Override
    public void init() {
        ScreenManager.registerFactory(BlockVariables.GENERATOR_CONTAINER, GeneratorScreen::new);
        ScreenManager.registerFactory(BlockVariables.RECONSTRUCTOR_CONTAINER, ReconstructorScreen::new);
        ScreenManager.registerFactory(BlockVariables.DECONSTRUCTOR_CONTAINER, DeconstructorScreen::new);
        ScreenManager.registerFactory(BlockVariables.VOLCANO_CONTAINER, VolcanoScreen::new);
        ScreenManager.registerFactory(BlockVariables.ACCELERATOR_CONTAINER, AcceleratorScreen::new);
        ScreenManager.registerFactory(BlockVariables.ASSEMBLER_CONTAINER, AssemblerScreen::new);
    }

    @Override
    public World getClientWorld() {
        return Minecraft.getInstance().world;
    }

    @Override
    public PlayerEntity getClientPlayer() {
        return Minecraft.getInstance().player;
    }
}