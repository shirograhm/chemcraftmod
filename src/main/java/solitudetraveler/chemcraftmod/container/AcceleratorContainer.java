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
import solitudetraveler.chemcraftmod.tileentity.AcceleratorTileEntity;

import javax.annotation.Nonnull;

public class AcceleratorContainer extends Container {
    AcceleratorTileEntity tileEntity;
    private PlayerEntity playerEntity;
    private IItemHandler playerInventory;

    public AcceleratorContainer(int id, World world, BlockPos pos, PlayerInventory playerInv, PlayerEntity player) {
        super(BlockList.ACCELERATOR_CONTAINER, id);

        tileEntity = (AcceleratorTileEntity) world.getTileEntity(pos);
        playerInventory = new InvWrapper(playerInv);
        playerEntity = player;

        addSlot(new Slot(tileEntity, AcceleratorTileEntity.ACCELERATOR_INPUT_1, 17, 22));
        addSlot(new Slot(tileEntity, AcceleratorTileEntity.ACCELERATOR_INPUT_2, 17, 40));
        addSlot(new Slot(tileEntity, AcceleratorTileEntity.ACCELERATOR_OUTPUT_1, 125, 13));
        addSlot(new Slot(tileEntity, AcceleratorTileEntity.ACCELERATOR_OUTPUT_2, 107, 31));
        addSlot(new Slot(tileEntity, AcceleratorTileEntity.ACCELERATOR_OUTPUT_3, 125, 31));
        addSlot(new Slot(tileEntity, AcceleratorTileEntity.ACCELERATOR_OUTPUT_4, 143, 31));
        addSlot(new Slot(tileEntity, AcceleratorTileEntity.ACCELERATOR_OUTPUT_5, 125, 49));
        layoutPlayerInventorySlots(8, 76);
    }

    @Override
    public boolean canInteractWith(@Nonnull PlayerEntity playerIn) {
        return isWithinUsableDistance(IWorldPosCallable.of(tileEntity.getWorld(), tileEntity.getPos()), playerEntity, BlockList.accelerator);
    }

    @Nonnull
    @Override
    public ItemStack transferStackInSlot(PlayerEntity playerIn, int index) {
        ItemStack itemStack = ItemStack.EMPTY;
        Slot slot = this.inventorySlots.get(index);

        if(slot != null && slot.getHasStack()) {
            ItemStack stack = slot.getStack();
            itemStack = stack.copy();

            if(index < AcceleratorTileEntity.NUMBER_ACCELERATOR_SLOTS) {
                if (!this.mergeItemStack(stack, AcceleratorTileEntity.NUMBER_ACCELERATOR_SLOTS, AcceleratorTileEntity.NUMBER_ACCELERATOR_SLOTS + 36, false)) {
                    return ItemStack.EMPTY;
                }
            } else {
                return ItemStack.EMPTY;
            }
        }
        return itemStack;
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
