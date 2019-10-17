package solitudetraveler.chemcraftmod.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Hand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import solitudetraveler.chemcraftmod.item.CompoundItem;
import solitudetraveler.chemcraftmod.tileentity.ErlenmeyerTileEntity;

import javax.annotation.Nullable;

public class ErlenmeyerBlock extends Block {

    public ErlenmeyerBlock(ResourceLocation name, Block.Properties props) {
        super(props);

        setRegistryName(name);
    }

    @Override
    public boolean hasTileEntity(BlockState state) {
        return true;
    }

    @Nullable
    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world) {
        return new ErlenmeyerTileEntity();
    }

    @Override
    public boolean onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit) {
        if(!worldIn.isRemote) {
            TileEntity tileEntity = worldIn.getTileEntity(pos);
            if(player.getHeldItem(handIn).getItem() instanceof CompoundItem) {
                // Remove item from hand
                // Add data to flask
                // Play animation
            }
        }
        return super.onBlockActivated(state, worldIn, pos, player, handIn, hit);
    }
}
