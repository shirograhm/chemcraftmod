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
import solitudetraveler.chemcraftmod.block.BlockVariables;
import solitudetraveler.chemcraftmod.container.ReconstructorContainer;
import solitudetraveler.chemcraftmod.handler.ReconstructorRecipeHandler;
import solitudetraveler.chemcraftmod.recipes.ReconstructorRecipe;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;

public class ReconstructorTileEntity extends BasicTileEntity implements INamedContainerProvider {
    public static final int RECONSTRUCTOR_INPUT_1 = 0;
    public static final int RECONSTRUCTOR_INPUT_2 = 1;
    public static final int RECONSTRUCTOR_INPUT_3 = 2;
    public static final int RECONSTRUCTOR_INPUT_4 = 3;
    public static final int RECONSTRUCTOR_INPUT_5 = 4;
    public static final int RECONSTRUCTOR_INPUT_6 = 5;
    public static final int RECONSTRUCTOR_INPUT_7 = 6;
    public static final int RECONSTRUCTOR_INPUT_8 = 7;
    public static final int RECONSTRUCTOR_INPUT_9 = 8;
    public static final int RECONSTRUCTOR_OUTPUT = 9;
    public static final int NUMBER_RECONSTRUCTOR_SLOTS = 10;

    private static final int RECONSTRUCTION_TIME = 60;

    private boolean isReconstructing;
    private int reconstructionTimeLeft;

    public ReconstructorTileEntity() {
        super(BlockVariables.RECONSTRUCTOR_TILE_TYPE, NUMBER_RECONSTRUCTOR_SLOTS);

        this.reconstructionTimeLeft = 0;
        this.isReconstructing = false;
    }

    private ArrayList<ItemStack> getCurrentInputArray() {
        ArrayList<ItemStack> input = new ArrayList<>();
        for (int i = RECONSTRUCTOR_INPUT_1; i <= RECONSTRUCTOR_INPUT_9; i++) {
            ItemStack is = inventory.getStackInSlot(i);
            if (!is.isEmpty() && is.getItem() != Items.AIR) {
                input.add(is);
            }
        }

        return input;
    }

    public double getReconstructionTimeScaled() {
        return (RECONSTRUCTION_TIME - reconstructionTimeLeft) * 1.0 / RECONSTRUCTION_TIME;
    }

    public boolean isReconstructing() {
        return isReconstructing;
    }

    @Override
    public void tick() {
        // If world is null, return
        if (world == null) return;
        // If client, return
        if (world.isRemote) return;

        // Do recipe computations
        ArrayList<ItemStack> inputArray = getCurrentInputArray();
        ReconstructorRecipe recipe = ReconstructorRecipeHandler.getRecipeForInputs(inputArray);

        // If out stack not empty
        if (inventory.getStackInSlot(RECONSTRUCTOR_OUTPUT) != ItemStack.EMPTY) {
            isReconstructing = false;
            reconstructionTimeLeft = 0;
        } else {
            // If input is valid
            if (isActive && recipe != null) {
                // If we are already reconstructing
                if (isReconstructing) {
                    reconstructionTimeLeft--;
                }
                // Otherwise, begin reconstruction
                else {
                    isReconstructing = true;
                    reconstructionTimeLeft = RECONSTRUCTION_TIME;
                }
            }
            // Otherwise if the input is invalid
            else {
                // Stop reconstruction
                isReconstructing = false;
                reconstructionTimeLeft = 0;
            }
        }
        if (isReconstructing && reconstructionTimeLeft == 0) {
            // Clear input stacks
            for (int i = RECONSTRUCTOR_INPUT_1; i <= RECONSTRUCTOR_INPUT_9; i++) {
                // Reset input stacks
                inventory.extractItem(i, inventory.getStackInSlot(i).getCount(), false);
            }
            // Set output stack
            inventory.insertItem(RECONSTRUCTOR_OUTPUT, recipe.getOutput().copy(), false);
            // Reset machine
            isReconstructing = false;
            reconstructionTimeLeft = 0;
        }

        super.tick();
    }

    @Override
    public void read(CompoundNBT tag) {
        this.reconstructionTimeLeft = tag.getInt("timeLeft");
        this.isReconstructing = tag.getBoolean("isReconstructing");

        super.read(tag);
    }

    @Nonnull
    @Override
    public CompoundNBT write(CompoundNBT tag) {
        tag.putInt("timeLeft", reconstructionTimeLeft);
        tag.putBoolean("isReconstructing", isReconstructing);

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
            return new ReconstructorContainer(i, world, pos, playerInventory, playerEntity);
        }
        return null;
    }

}
