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


    private ReconstructorRecipe currentRecipe;

    public ReconstructorTileEntity() {
        super(BlockVariables.RECONSTRUCTOR_TILE_TYPE, NUMBER_RECONSTRUCTOR_SLOTS);

        this.currentRecipe = null;
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

    /**
     * Called when a player removes an item from the result slot
     **/
    public void removeInputs() {
        if(currentRecipe == null) return;

        for(int i = RECONSTRUCTOR_INPUT_1; i <= RECONSTRUCTOR_INPUT_9; i++) {
            ItemStack stackInSlot = inventory.getStackInSlot(i);
            // If inventory stack is not empty
            if(!stackInSlot.isEmpty()) {
                int countToTake = currentRecipe.getCountForIngredient(stackInSlot.getItem());

                if(countToTake > 0) {
                    inventory.extractItem(i, countToTake, false);
                } else {
                    // Otherwise input is not valid for current recipe
                    currentRecipe = null;
                }
            }
        }
    }

    @Override
    public void tick() {
        // If world is null, return
        if (world == null) return;
        // If client, return
        if (world.isRemote) return;

        // Do recipe computations
        ArrayList<ItemStack> inputArray = getCurrentInputArray();
        currentRecipe = ReconstructorRecipeHandler.getRecipeForInputs(inputArray);

        if(isActive) {
            ItemStack stackInOutput = inventory.getStackInSlot(RECONSTRUCTOR_OUTPUT).copy();
            // Current recipe exists && output stack is empty
            if (currentRecipe != null && stackInOutput.isEmpty()) {
                inventory.insertItem(RECONSTRUCTOR_OUTPUT, currentRecipe.getOutput().copy(), false);
            }
            // Current recipe exists && output stack is not empty
            if (currentRecipe != null && !stackInOutput.isEmpty()) {
                // Reset stack in output
                inventory.extractItem(RECONSTRUCTOR_OUTPUT, stackInOutput.getCount(), false);
                // Insert new output
                inventory.insertItem(RECONSTRUCTOR_OUTPUT, currentRecipe.getOutput().copy(), false);
            }
            // Current recipe does not exist && output stack is empty
            if (currentRecipe == null && stackInOutput.isEmpty()) {
                // Do nothing for this case because we want null recipes to have no output
            }
            // Current recipe does not exist && output stack is not empty
            if (currentRecipe == null && !stackInOutput.isEmpty()) {
                inventory.extractItem(RECONSTRUCTOR_OUTPUT, stackInOutput.getCount(), false);
            }
        }

        super.tick();
    }

    @Override
    public void read(CompoundNBT tag) {
        super.read(tag);
    }

    @Nonnull
    @Override
    public CompoundNBT write(CompoundNBT tag) {
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
