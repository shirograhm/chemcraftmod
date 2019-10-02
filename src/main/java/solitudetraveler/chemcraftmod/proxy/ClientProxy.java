package solitudetraveler.chemcraftmod.proxy;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScreenManager;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;
import solitudetraveler.chemcraftmod.block.BlockList;
import solitudetraveler.chemcraftmod.container.ConstructorScreen;

public class ClientProxy implements IProxy {

    @Override
    public void init() {
        ScreenManager.registerFactory(BlockList.CONSTRUCTOR_CONTAINER, ConstructorScreen::new);
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