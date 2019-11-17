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
import solitudetraveler.chemcraftmod.item.CompoundItem;
import solitudetraveler.chemcraftmod.item.ElementItem;
import solitudetraveler.chemcraftmod.tileentity.BeakerTileEntity;

import javax.annotation.Nonnull;

public class BeakerContainer extends Container {
    BeakerTileEntity tileEntity;
    private PlayerEntity playerEntity;
    private IItemHandler playerInventory;

    public BeakerContainer(int id, World world, BlockPos pos, PlayerInventory playerInv, PlayerEntity player) {
        super(BlockList.BEAKER_CONTAINER, id);

        tileEntity = (BeakerTileEntity) world.getTileEntity(pos);
        playerEntity = player;
        playerInventory = new InvWrapper(playerInv);

        addSlot(new Slot(tileEntity, BeakerTileEntity.BEAKER_INPUT_1, 26, 22));
        addSlot(new Slot(tileEntity, BeakerTileEntity.BEAKER_INPUT_2, 26, 40));
        addSlot(new Slot(tileEntity, BeakerTileEntity.BEAKER_INPUT_3, 44, 22));
        addSlot(new Slot(tileEntity, BeakerTileEntity.BEAKER_INPUT_4, 44, 40));

        addSlot(new Slot(tileEntity, BeakerTileEntity.BEAKER_OUTPUT_1, 107, 31));
        addSlot(new Slot(tileEntity, BeakerTileEntity.BEAKER_OUTPUT_2, 134, 13));
        addSlot(new Slot(tileEntity, BeakerTileEntity.BEAKER_OUTPUT_3, 134, 31));
        addSlot(new Slot(tileEntity, BeakerTileEntity.BEAKER_OUTPUT_4, 134, 49));
        layoutPlayerInventorySlots(8, 76);
    }

    @Override
    public boolean canInteractWith(@Nonnull PlayerEntity playerIn) {
        return isWithinUsableDistance(IWorldPosCallable.of(tileEntity.getWorld(), tileEntity.getPos()), playerEntity, BlockList.beaker);
    }

    @Nonnull
    @Override
    public ItemStack transferStackInSlot(PlayerEntity playerIn, int index) {
        ItemStack itemStack = ItemStack.EMPTY;
        Slot slot = this.inventorySlots.get(index);

        if(slot != null && slot.getHasStack()) {
            ItemStack stack = slot.getStack();
            itemStack = stack.copy();

            // If the slot clicked is one of the volcano slots
            if(index < BeakerTileEntity.NUMBER_BEAKER_SLOTS) {
                if(!this.mergeItemStack(stack, BeakerTileEntity.NUMBER_BEAKER_SLOTS, BeakerTileEntity.NUMBER_BEAKER_SLOTS + 36, false)) {
                    return ItemStack.EMPTY;
                }
            } else {
                if (stack.getItem() instanceof CompoundItem || stack.getItem() instanceof ElementItem) {
                    if (!this.mergeItemStack(stack, BeakerTileEntity.BEAKER_INPUT_1, BeakerTileEntity.BEAKER_OUTPUT_1, true)) {
                        return ItemStack.EMPTY;
                    }
                }
                else {
                    return ItemStack.EMPTY;
                }
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
