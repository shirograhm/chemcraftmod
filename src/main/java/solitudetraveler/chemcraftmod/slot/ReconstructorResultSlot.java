package solitudetraveler.chemcraftmod.slot;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import solitudetraveler.chemcraftmod.container.ReconstructorContainer;

import javax.annotation.Nonnull;

public class ReconstructorResultSlot extends Slot {
    ReconstructorContainer containerReference;

    public ReconstructorResultSlot(ReconstructorContainer containerIn, IInventory inventoryIn, int index, int xPosition, int yPosition) {
        super(inventoryIn, index, xPosition, yPosition);

        containerReference = containerIn;
    }

    @Override
    public boolean isItemValid(ItemStack stack) {
        return super.isItemValid(stack);
    }

    @Nonnull
    @Override
    public ItemStack onTake(PlayerEntity thePlayer, @Nonnull ItemStack stack) {
        containerReference.removeInputs();
        return stack;
    }
}
