package solitudetraveler.chemcraftmod.tileentity;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import solitudetraveler.chemcraftmod.block.BlockList;
import solitudetraveler.chemcraftmod.container.DeconstructorContainer;
import solitudetraveler.chemcraftmod.handler.DeconstructorRecipeHandler;
import solitudetraveler.chemcraftmod.recipes.DeconstructorRecipe;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;

public class DeconstructorTileEntity extends BasicTileEntity implements INamedContainerProvider {
    public static final int DECONSTRUCTOR_INPUT = 0;
    public static final int DECONSTRUCTOR_OUTPUT_1 = 1;
    public static final int DECONSTRUCTOR_OUTPUT_2 = 2;
    public static final int DECONSTRUCTOR_OUTPUT_3 = 3;
    public static final int DECONSTRUCTOR_OUTPUT_4 = 4;
    public static final int DECONSTRUCTOR_OUTPUT_5 = 5;
    public static final int DECONSTRUCTOR_OUTPUT_6 = 6;
    public static final int NUMBER_DECONSTRUCTOR_SLOTS = 7;

    private static final int DECONSTRUCTION_TIME = 160;

    private boolean isDeconstructing;
    private int deconstructionTimeLeft;

    public DeconstructorTileEntity() {
        super(BlockList.DECONSTRUCTOR_TILE_TYPE, NUMBER_DECONSTRUCTOR_SLOTS);

        this.deconstructionTimeLeft = 0;
        this.isDeconstructing = false;
    }

    private boolean isOutputEmpty() {
        ArrayList<ItemStack> outs = new ArrayList<>();
        for(int i = DECONSTRUCTOR_OUTPUT_1; i <= DECONSTRUCTOR_OUTPUT_6; i++) {
            ItemStack is = inventory.getStackInSlot(i);
            if(!is.isEmpty() && is.getItem() != Items.AIR) {
                outs.add(is);
            }
        }

        return outs.size() == 0;
    }

    public double getDeconstructionTimeScaled() {
        return (DECONSTRUCTION_TIME - deconstructionTimeLeft) * 1.0 / DECONSTRUCTION_TIME;
    }

    public boolean isDeconstructing() {
        return isDeconstructing;
    }

    @Override
    public void tick() {
        // If world is null, return
        if(world == null) return;
        // If on client, return
        if(world.isRemote) return;

        // Do recipe computations
        ItemStack input = inventory.getStackInSlot(DECONSTRUCTOR_INPUT);
        DeconstructorRecipe recipe = DeconstructorRecipeHandler.getRecipeForInputs(input);

        // If outputs are not empty
        if(!isOutputEmpty()) {
            isDeconstructing = false;
            deconstructionTimeLeft = 0;
        }
        else {
            // If input is valid
            if(isActive && recipe != null) {
                // If we are already deconstructing
                if(isDeconstructing) {
                    deconstructionTimeLeft--;
                }
                // Otherwise, begin deconstruction
                else {
                    isDeconstructing = true;
                    deconstructionTimeLeft = DECONSTRUCTION_TIME;
                }
            }
            // Otherwise, if input is invalid
            else {
                // Stop deconstruction
                isDeconstructing = false;
                deconstructionTimeLeft = 0;
            }
        }
        if (isDeconstructing && deconstructionTimeLeft == 0) {
            // Clear input stack
            inventory.setStackInSlot(DECONSTRUCTOR_INPUT, ItemStack.EMPTY);
            // Set output stacks
            for (int i = 0; i < recipe.getResults().size(); i++) {
                inventory.setStackInSlot(i + 1, recipe.getResults().get(i));
            }
            // Reset machine
            isDeconstructing = false;
        }

        super.tick();
    }

    @Override
    public void read(CompoundNBT tag) {
        this.deconstructionTimeLeft = tag.getInt("timeLeft");
        this.isDeconstructing = tag.getBoolean("isDeconstructing");

        super.read(tag);
    }

    @Nonnull
    @Override
    public CompoundNBT write(CompoundNBT tag) {
        tag.putInt("timeLeft", deconstructionTimeLeft);
        tag.putBoolean("isDeconstructing", isDeconstructing);

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
        if (world != null) {
            return new DeconstructorContainer(i, world, pos, playerInventory, playerEntity);
        }
        return null;
    }
}
