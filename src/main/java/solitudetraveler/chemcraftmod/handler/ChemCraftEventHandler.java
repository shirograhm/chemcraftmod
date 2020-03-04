package solitudetraveler.chemcraftmod.handler;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class ChemCraftEventHandler {
    // TODO: Somehow use this file to create cool animation on event
    @SubscribeEvent
    public static void entityJoinWorldEvent(EntityJoinWorldEvent event) {
        if(event.getEntity() instanceof PlayerEntity) {
            PlayerEntity player = (PlayerEntity) event.getEntity();
            CompoundNBT playerNBT = player.serializeNBT();

            if(!playerNBT.getBoolean("already_joined")) {
                // Add item if not tagged
                player.inventory.addItemStackToInventory(new ItemStack(Items.ENCHANTED_BOOK));
                // Add nbt tag for first join
                CompoundNBT newNBT = new CompoundNBT();
                newNBT.putBoolean("already_joined", true);
                player.writeAdditional(newNBT);
            }
        }
    }
}
