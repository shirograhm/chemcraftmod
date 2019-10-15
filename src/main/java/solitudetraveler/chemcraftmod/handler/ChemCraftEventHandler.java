package solitudetraveler.chemcraftmod.handler;

import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.particles.ItemParticleData;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.item.ItemTossEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import solitudetraveler.chemcraftmod.item.ItemList;

public class ChemCraftEventHandler {
//    @SubscribeEvent
//    public static void itemTossedEvent(ItemTossEvent event) {
//        ItemEntity itemEntity = event.getEntityItem();
//        World worldIn = itemEntity.world;
//
//        if(itemEntity.getItem().getItem() == ItemList.sodium_chloride) {
//            BlockPos pos = new BlockPos(itemEntity.lastTickPosX, itemEntity.lastTickPosY, itemEntity.lastTickPosZ);
//            ItemParticleData data = new ItemParticleData(ParticleTypes.ITEM, itemEntity.getItem());
//
//            for(int i = 0; i < 25; i++) {
//                worldIn.addParticle(data, pos.getX(), pos.getY(), pos.getZ(), 0.5, 0.5, 0.5);
//            }
//        }
//    }

    @SubscribeEvent
    public static void entityItemTickEvent(ItemTossEvent event) {
        ItemEntity itemEntity = event.getEntityItem();
        PlayerEntity tosser = event.getPlayer();
        World worldIn = itemEntity.getEntityWorld();

        if(worldIn.isRemote) {
            if(itemEntity.getItem().getItem() == ItemList.sodium_chloride) {
                Direction facing = tosser.getHorizontalFacing();
                ItemParticleData data = new ItemParticleData(ParticleTypes.ITEM, itemEntity.getItem());
                BlockPos itemPos = new BlockPos(itemEntity.posX, itemEntity.posY - 1, itemEntity.posZ);

                switch(facing) {
                    case NORTH:
                        itemPos.north();
                        break;
                    case SOUTH:
                        itemPos.south();
                        break;
                    case EAST:
                        itemPos.east();
                        break;
                    case WEST:
                        itemPos.west();
                        break;
                    case UP:
                        itemPos.up();
                        break;
                    case DOWN:
                        itemPos.down();
                        break;
                    default:
                }

                for(int i = 0; i < 25; i++) {
                    System.out.println("Generate particle at " + itemPos.toString());
                    worldIn.addParticle(data, itemPos.getX(), itemPos.getY(), itemPos.getZ(), 0.5, 0.5, 0.5);
                }
            }
        }
    }
}
