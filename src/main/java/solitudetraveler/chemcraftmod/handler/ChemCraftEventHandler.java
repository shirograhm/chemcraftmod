package solitudetraveler.chemcraftmod.handler;

import net.minecraft.entity.item.ItemEntity;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.item.ItemEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import solitudetraveler.chemcraftmod.item.ItemList;

import java.util.Random;

public class ChemCraftEventHandler {
    @SubscribeEvent
    public static void entityItemTickEvent(ItemEvent event) {
        ItemEntity itemEntity = event.getEntityItem();
        World worldIn = itemEntity.getEntityWorld();

        if(worldIn.isRemote) {
            System.out.println(itemEntity.getItem().getItem());

            if(itemEntity.getItem().getItem() == ItemList.carbonate) {
                Random rand = new Random();

                BlockPos itemPos = new BlockPos(itemEntity.posX, itemEntity.posY - 1, itemEntity.posZ);

                for(int i = 0; i < 25; i++) {
                    BlockPos pos = new BlockPos(itemEntity.posX - rand.nextDouble(), itemEntity.posY - rand.nextDouble(), itemEntity.posZ - rand.nextDouble());

                    System.out.println("Generate particle at " + itemPos.toString());
                    worldIn.addParticle(ParticleTypes.POOF, true, itemPos.getX(), itemPos.getY(), itemPos.getZ(), 0.5, 0.5, 0.5);
                }
            }
        }
    }
}
