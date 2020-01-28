package solitudetraveler.chemcraftmod.tileentity;

import net.minecraft.block.Block;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.World;
import solitudetraveler.chemcraftmod.block.BlockVariables;
import solitudetraveler.chemcraftmod.block.ElectromagnetBlock;
import solitudetraveler.chemcraftmod.container.AcceleratorContainer;
import solitudetraveler.chemcraftmod.item.ElementItem;
import solitudetraveler.chemcraftmod.item.ItemList;
import solitudetraveler.chemcraftmod.main.ChemCraftMod;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Random;

public class AcceleratorTileEntity extends BasicTileEntity implements INamedContainerProvider {
    public static final int ACCELERATOR_INPUT_1 = 0;
    public static final int ACCELERATOR_INPUT_2 = 1;
    public static final int ACCELERATOR_OUTPUT_1 = 2;
    public static final int ACCELERATOR_OUTPUT_2 = 3;
    public static final int ACCELERATOR_OUTPUT_3 = 4;
    public static final int ACCELERATOR_OUTPUT_4 = 5;
    public static final int ACCELERATOR_OUTPUT_5 = 6;
    public static final int NUMBER_ACCELERATOR_SLOTS = 7;

    private static final double BASE_COLLISION_CHANCE = 0.081;
    private static final int ANIMATION_FRAMES = 6;

    private double multiplier;
    private int currentAnimationFrame;


    public AcceleratorTileEntity() {
        super(BlockVariables.ACCELERATOR_TILE_TYPE, NUMBER_ACCELERATOR_SLOTS);

        this.multiplier = 1.0;
        this.currentAnimationFrame = 0;
    }

    public int getCurrentAnimationFrame() {
        return currentAnimationFrame;
    }

    public boolean isActive() {
        return isActive;
    }

    public double getMultiplier() {
        return multiplier;
    }

    // Test for built particle accelerator
    private boolean acceleratorIsBuilt(World world, BlockPos blockPos) {
        Direction dir = world.getBlockState(blockPos).get(BlockStateProperties.HORIZONTAL_FACING);
        ArrayList<BlockPos> positionsToCheck = new ArrayList<>();

        int minX = 0;
        int maxX = 0;
        int minZ = 0;
        int maxZ = 0;

        switch(dir) {
            case NORTH:
                minX = -3;
                maxX = 3;
                minZ = 0;
                maxZ = 6;
                break;
            case SOUTH:
                minX = -3;
                maxX = 3;
                minZ = -6;
                maxZ = 0;
                break;
            case EAST:
                minX = -6;
                maxX = 0;
                minZ = -3;
                maxZ = 3;
                break;
            case WEST:
                minX = 0;
                maxX = 6;
                minZ = -3;
                maxZ = 3;
                break;
            default:
                ChemCraftMod.LOGGER.debug("Invalid direction on block placed at " + blockPos.toString());
                break;
        }

        for(int x = minX; x <= maxX; x++) {
            BlockPos temp1 = blockPos.add(x, 0, minZ);
            BlockPos temp2 = blockPos.add(x, 0, maxZ);

            if (temp1.compareTo(blockPos) != 0) positionsToCheck.add(temp1);
            if (temp2.compareTo(blockPos) != 0) positionsToCheck.add(temp2);
        }

        for(int z = minZ; z <= maxZ; z++) {
            BlockPos temp1 = blockPos.add(minX, 0, z);
            BlockPos temp2 = blockPos.add(maxX, 0, z);

            if (temp1.compareTo(blockPos) != 0) positionsToCheck.add(temp1);
            if (temp2.compareTo(blockPos) != 0) positionsToCheck.add(temp2);
        }

        for(BlockPos bp : positionsToCheck) {
            Block check = world.getBlockState(bp).getBlock();

            if(!(check instanceof ElectromagnetBlock)) return false;
        }

        // If all electro blocks, return true
        return true;
    }

    @Override
    public void tick() {
        // If world is null, return
        if (world == null) return;
        // If client, play sound if active
        if (world.isRemote) return;
        // Built check
        world.setBlockState(pos, this.getBlockState().with(BlockStateProperties.ENABLED, acceleratorIsBuilt(world, pos)));
        if (!this.getBlockState().get(BlockStateProperties.ENABLED)) return;

        // Get input array
        ArrayList<ElementItem> inputs = getInputElements();
        Random rand = new Random();
        // If both inputs are elements (inputs.size() = 2) and the block is active
        if(isActive && inputs.size() == 2 && outputSlotsAvailable()) {
            // Run chance collision
            if(rand.nextInt(100) < BASE_COLLISION_CHANCE) {
                runCollisionAndSetOutputStacks(rand, inputs);
            } else {
                // Increment animation frames
                currentAnimationFrame++;
                currentAnimationFrame %= ANIMATION_FRAMES;
            }
        }

        super.tick();
    }

    private boolean outputSlotsAvailable() {
        ItemStack stack1 = inventory.getStackInSlot(ACCELERATOR_OUTPUT_1);
        ItemStack stack2 = inventory.getStackInSlot(ACCELERATOR_OUTPUT_2);
        ItemStack stack3 = inventory.getStackInSlot(ACCELERATOR_OUTPUT_3);
        ItemStack stack4 = inventory.getStackInSlot(ACCELERATOR_OUTPUT_4);
        ItemStack stack5 = inventory.getStackInSlot(ACCELERATOR_OUTPUT_5);
        // If element output isn't empty
        if(!stack1.isEmpty()) return false;
        if(!stack2.isEmpty()) return false;
        if(!stack3.isEmpty()) return false;
        if(!stack4.isEmpty()) return false;
        if(!stack5.isEmpty()) return false;
        // Return true if output is empty
        return true;
    }

    private void runCollisionAndSetOutputStacks(Random rand, ArrayList<ElementItem> inputs) {
        // Variance in range [-4, 0]
        int variance = rand.nextInt(5) - 4;

        int first = inputs.get(ACCELERATOR_INPUT_1).getAtomicNumber();
        int second = inputs.get(ACCELERATOR_INPUT_2).getAtomicNumber();
        int createdAtomicNumber = first + second + variance;

        ElementItem newAtom = ItemList.getElementNumber(createdAtomicNumber);

        // Place outputs
        inventory.insertItem(ACCELERATOR_OUTPUT_3, new ItemStack(newAtom), false);
        inventory.insertItem(ACCELERATOR_OUTPUT_1, new ItemStack(ItemList.electron, -variance), false);
        inventory.insertItem(ACCELERATOR_OUTPUT_2, new ItemStack(ItemList.proton, -variance), false);
        inventory.insertItem(ACCELERATOR_OUTPUT_4, new ItemStack(ItemList.neutron, (int) (-variance * 1.35)), false);
        // Remove inputs
        inventory.extractItem(ACCELERATOR_INPUT_1, 1, false);
        inventory.extractItem(ACCELERATOR_INPUT_2, 1, false);
    }

    private ArrayList<ElementItem> getInputElements() {
        ArrayList<ElementItem> elementsIn = new ArrayList<>();
        ItemStack stack1 = inventory.getStackInSlot(ACCELERATOR_INPUT_1);
        ItemStack stack2 = inventory.getStackInSlot(ACCELERATOR_INPUT_2);

        if(stack1.getItem() instanceof ElementItem && stack1.getItem() != ItemList.unknown) elementsIn.add((ElementItem) stack1.getItem());
        if(stack2.getItem() instanceof ElementItem && stack2.getItem() != ItemList.unknown) elementsIn.add((ElementItem) stack2.getItem());

        return elementsIn;
    }

    @Override
    public void read(CompoundNBT tag) {
        this.isActive = tag.getBoolean("isActive");
        this.multiplier = tag.getDouble("multiplier");
        this.currentAnimationFrame = tag.getInt("animationFrame");

        super.read(tag);
    }

    @Nonnull
    @Override
    public CompoundNBT write(CompoundNBT tag) {
        tag.putBoolean("isActive", isActive);
        tag.putDouble("multiplier", multiplier);
        tag.putInt("animationFrame", currentAnimationFrame);

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
        return new AcceleratorContainer(i, world, pos, playerInventory, playerEntity);
    }
}