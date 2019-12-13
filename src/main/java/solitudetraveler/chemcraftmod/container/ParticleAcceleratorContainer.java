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
import solitudetraveler.chemcraftmod.tileentity.ParticleAcceleratorTileEntity;

import javax.annotation.Nonnull;

public class ParticleAcceleratorContainer extends Container {
    ParticleAcceleratorTileEntity tileEntity;
    private PlayerEntity playerEntity;
    private IItemHandler playerInventory;

    public ParticleAcceleratorContainer(int id, World world, BlockPos pos, PlayerInventory playerInv, PlayerEntity player) {
        super(BlockList.PARTICLE_CONTAINER, id);

        tileEntity = (ParticleAcceleratorTileEntity) world.getTileEntity(pos);
        playerEntity = player;
        playerInventory = new InvWrapper(playerInv);

        addSlot(new Slot(tileEntity, ParticleAcceleratorTileEntity.PARTICLE_INPUT_1, 0, 0));
        addSlot(new Slot(tileEntity, ParticleAcceleratorTileEntity.PARTICLE_INPUT_2, 18, 0));
        addSlot(new Slot(tileEntity, ParticleAcceleratorTileEntity.PARTICLE_OUTPUT_1, 36, 0));
        addSlot(new Slot(tileEntity, ParticleAcceleratorTileEntity.PARTICLE_OUTPUT_2, 54, 0));
        addSlot(new Slot(tileEntity, ParticleAcceleratorTileEntity.PARTICLE_OUTPUT_3, 72, 0));
        addSlot(new Slot(tileEntity, ParticleAcceleratorTileEntity.PARTICLE_OUTPUT_4, 90, 0));
        addSlot(new Slot(tileEntity, ParticleAcceleratorTileEntity.PARTICLE_OUTPUT_5, 108, 0));
        layoutPlayerInventorySlots(8, 84);
    }

    @Override
    public boolean canInteractWith(@Nonnull PlayerEntity playerIn) {
        return isWithinUsableDistance(IWorldPosCallable.of(tileEntity.getWorld(), tileEntity.getPos()), playerEntity, BlockList.particle_accelerator);
    }

    @Nonnull
    @Override
    public ItemStack transferStackInSlot(PlayerEntity playerIn, int index) {
        ItemStack itemStack = ItemStack.EMPTY;
        Slot slot = this.inventorySlots.get(index);

        if(slot != null && slot.getHasStack()) {
            ItemStack stack = slot.getStack();
            itemStack = stack.copy();

            if(index < ParticleAcceleratorTileEntity.NUMBER_PARTICLE_SLOTS) {
                if (!this.mergeItemStack(stack, ParticleAcceleratorTileEntity.NUMBER_PARTICLE_SLOTS, ParticleAcceleratorTileEntity.NUMBER_PARTICLE_SLOTS + 36, false)) {
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
