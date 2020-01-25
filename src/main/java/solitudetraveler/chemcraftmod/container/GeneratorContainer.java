package solitudetraveler.chemcraftmod.container;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IWorldPosCallable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;
import net.minecraftforge.items.wrapper.InvWrapper;
import solitudetraveler.chemcraftmod.block.BlockList;
import solitudetraveler.chemcraftmod.block.BlockVariables;
import solitudetraveler.chemcraftmod.tileentity.GeneratorTileEntity;

import javax.annotation.Nonnull;

public class GeneratorContainer extends Container {
    public GeneratorTileEntity tileEntity;

    private IItemHandler playerInventory;

    public GeneratorContainer(int id, World world, BlockPos pos, PlayerInventory playerInv, PlayerEntity player) {
        super(BlockVariables.GENERATOR_CONTAINER, id);

        tileEntity = (GeneratorTileEntity) world.getTileEntity(pos);
        playerInventory = new InvWrapper(playerInv);

        addSlot(new Slot(tileEntity, GeneratorTileEntity.GENERATOR_INPUT, 98, 31));
        layoutPlayerInventorySlots(8, 76);
    }

    @Override
    public boolean canInteractWith(@Nonnull PlayerEntity playerIn) {
        return isWithinUsableDistance(IWorldPosCallable.of(tileEntity.getWorld(), tileEntity.getPos()), playerIn, BlockList.generator);
    }

    @Nonnull
    @Override
    public ItemStack transferStackInSlot(PlayerEntity playerIn, int index) {
        ItemStack previousStack = ItemStack.EMPTY;
        Slot slot = this.inventorySlots.get(index);

        // If slot contains some itemstack
        if(slot != null && slot.getHasStack()) {
            ItemStack stackInSlot = slot.getStack();
            previousStack = stackInSlot.copy();

            // If clicked slot is in generator
            if(index < GeneratorTileEntity.NUMBER_GENERATOR_SLOTS) {
                // Container to inventory
                if (!this.mergeItemStack(stackInSlot, GeneratorTileEntity.NUMBER_GENERATOR_SLOTS, GeneratorTileEntity.NUMBER_GENERATOR_SLOTS + 36, true)) {
                    return ItemStack.EMPTY;
                }
            } else {
                // Inventory to container
                if(!this.mergeItemStack(stackInSlot, GeneratorTileEntity.GENERATOR_INPUT, GeneratorTileEntity.GENERATOR_INPUT + 1, false)) {
                    return ItemStack.EMPTY;
                }
            }

            if(stackInSlot.getCount() == 0) {
                slot.putStack(ItemStack.EMPTY);
            } else {
                slot.onSlotChanged();
            }

            if(stackInSlot.getCount() == previousStack.getCount()) return ItemStack.EMPTY;
        }

        return previousStack;
    }

    private void layoutPlayerInventorySlots(int leftCol, int topRow) {
        // Player inventory
        addSlotBox(playerInventory, 9, leftCol, topRow, 9, 18, 3, 18);

        // Hot bar
        topRow += 58;
        addSlotRange(playerInventory, 0, leftCol, topRow, 9, 18);
    }

    private int addSlotRange(IItemHandler handler, int index, int x, int y, int amount, int dx) {
        for (int i = 0 ; i < amount ; i++) {
            addSlot(new SlotItemHandler(handler, index, x, y));
            x += dx;
            index++;
        }
        return index;
    }

    private int addSlotBox(IItemHandler handler, int index, int x, int y, int horAmount, int dx, int verAmount, int dy) {
        for (int j = 0 ; j < verAmount ; j++) {
            index = addSlotRange(handler, index, x, y, horAmount, dx);
            y += dy;
        }
        return index;
    }
}
