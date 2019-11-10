package solitudetraveler.chemcraftmod.proxy;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScreenManager;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;
import solitudetraveler.chemcraftmod.block.BlockList;
import solitudetraveler.chemcraftmod.container.DeconstructorScreen;
import solitudetraveler.chemcraftmod.container.ReconstructorScreen;
import solitudetraveler.chemcraftmod.container.VolcanoScreen;

public class ClientProxy implements IProxy {
    @Override
    public void init() {
        ScreenManager.registerFactory(BlockList.RECONSTRUCTOR_CONTAINER, ReconstructorScreen::new);
        ScreenManager.registerFactory(BlockList.DECONSTRUCTOR_CONTAINER, DeconstructorScreen::new);
        ScreenManager.registerFactory(BlockList.VOLCANO_CONTAINER, VolcanoScreen::new);
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