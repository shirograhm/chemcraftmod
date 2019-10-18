package solitudetraveler.chemcraftmod.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Hand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;
import solitudetraveler.chemcraftmod.item.CompoundItem;
import solitudetraveler.chemcraftmod.item.ElementItem;
import solitudetraveler.chemcraftmod.tileentity.FlaskTileEntity;

import javax.annotation.Nullable;

public class FlaskBlock extends Block {

    public FlaskBlock(ResourceLocation name, Block.Properties props) {
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
        return new FlaskTileEntity();
    }

    @Override
    public boolean onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit) {
        if(!worldIn.isRemote) {
            FlaskTileEntity flask = ((FlaskTileEntity) worldIn.getTileEntity(pos));
            ItemStack itemUsed = player.getHeldItem(handIn);
            boolean showGUI = true;

            if(itemUsed.getItem() instanceof CompoundItem || itemUsed.getItem() instanceof ElementItem) {
                // Add data to flask
                ItemStack remaining = flask.addItemToFlask(itemUsed);
                // Remove item from hand
                player.setHeldItem(handIn, remaining);
                // Play animation

                showGUI = false;
            }

            if(showGUI) {
                NetworkHooks.openGui((ServerPlayerEntity) player, flask, flask.getPos());
            }
        }
        return super.onBlockActivated(state, worldIn, pos, player, handIn, hit);
    }
}
