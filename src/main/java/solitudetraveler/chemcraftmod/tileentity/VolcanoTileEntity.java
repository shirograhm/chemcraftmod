package solitudetraveler.chemcraftmod.tileentity;

import com.sun.javafx.geom.Vec3d;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.Item;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import solitudetraveler.chemcraftmod.block.BlockVariables;
import solitudetraveler.chemcraftmod.container.VolcanoContainer;
import solitudetraveler.chemcraftmod.item.ItemList;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Random;

public class VolcanoTileEntity extends BasicTileEntity implements INamedContainerProvider {
    public static final int VOLCANO_SLOT_1 = 0;
    public static final int VOLCANO_SLOT_2 = 1;
    public static final int NUMBER_VOLCANO_SLOTS = 2;

    private static final int VOLCANO_RUN_TIME = 60;

    private boolean isRunning;
    private int currentTimeLeft;

    public VolcanoTileEntity() {
        super(BlockVariables.VOLCANO_TILE_TYPE, NUMBER_VOLCANO_SLOTS);

        isRunning = false;
        currentTimeLeft = -1;
    }

    private boolean checkRequirements() {
        Item item1 = inventory.getStackInSlot(VOLCANO_SLOT_1).getItem();
        Item item2 = inventory.getStackInSlot(VOLCANO_SLOT_2).getItem();

        return item1.equals(ItemList.vinegar) && item2.equals(ItemList.baking_soda);
    }

    public float getCurrentTimeLeftScaled() {
        return (VOLCANO_RUN_TIME - currentTimeLeft) * 1.0f / VOLCANO_RUN_TIME;
    }

    public boolean isRunning() {
        return isRunning;
    }

    @Override
    public void tick() {
        // If world is null, return
        if(world == null) return;
        // If on client, spawn particles when requirements are met
        if(world.isRemote) {
            if(checkRequirements()) {
                // generate particle position and speed vectors
                Random rand = new Random();
                Vec3d particleSpeed = new Vec3d((rand.nextDouble() - 0.5D) / 2.0D, 0.5D, (rand.nextDouble() - 0.5D) / 2.0D);
                Vec3d particlePosition = new Vec3d(pos.getX() + 0.5D, pos.getY() + 1.25D, pos.getZ() + 0.5D);
                // Generate particles for the volcano when reaction completes
                for (int i = 0; i < 10; i++) {
                    world.addParticle(ParticleTypes.POOF, true, particlePosition.x, particlePosition.y, particlePosition.z, particleSpeed.x, particleSpeed.y, particleSpeed.z);
                }
            }
            // Return from client block
            return;
        }

        // Server side code
        if(checkRequirements()) {
            // Begin running volcano ticks if not running right now
            if(!isRunning) {
                isRunning = true;
                currentTimeLeft = VOLCANO_RUN_TIME;
            }
            else {
                currentTimeLeft--;
            }

            // Remove items inside volcano if time is up
            if(currentTimeLeft == 0) {
                inventory.extractItem(VOLCANO_SLOT_1, 1, false);
                inventory.extractItem(VOLCANO_SLOT_2, 1, false);
                // Reset current time left
                currentTimeLeft = VOLCANO_RUN_TIME;
            }
        }
        else {
            isRunning = false;
            currentTimeLeft = 0;
        }

        super.tick();
    }

    @Override
    public void read(CompoundNBT tag) {
        isRunning = tag.getBoolean("isRunning");
        currentTimeLeft = tag.getInt("timeLeft");

        super.read(tag);
    }

    @Nonnull
    @Override
    public CompoundNBT write(CompoundNBT tag) {
        tag.putBoolean("isRunning", isRunning);
        tag.putInt("timeLeft", currentTimeLeft);

        return super.write(tag);
    }

    @Nonnull
    @Override
    public ITextComponent getDisplayName() {
        ResourceLocation location = getType().getRegistryName();
        return new StringTextComponent(location != null ? location.getPath() : "");
    }

    @Nullable
    @Override
    public Container createMenu(int i, @Nonnull PlayerInventory playerInventory, @Nonnull PlayerEntity playerEntity) {
        return new VolcanoContainer(i, world, pos, playerInventory, playerEntity);
    }
}
