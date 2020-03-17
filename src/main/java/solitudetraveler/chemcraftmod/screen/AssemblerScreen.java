package solitudetraveler.chemcraftmod.screen;

import com.mojang.blaze3d.platform.GlStateManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import solitudetraveler.chemcraftmod.container.AssemblerContainer;
import solitudetraveler.chemcraftmod.main.ChemCraftMod;
import solitudetraveler.chemcraftmod.screen.button.GenerateElementButton;
import solitudetraveler.chemcraftmod.tileentity.AssemblerTileEntity;

public class AssemblerScreen extends ContainerScreen<AssemblerContainer> {
    private ResourceLocation GUI = new ResourceLocation(ChemCraftMod.MOD_ID, "textures/gui/assembler_gui.png");
    private AssemblerTileEntity assemblerTileEntity;

    public AssemblerScreen(AssemblerContainer screenContainer, PlayerInventory inv, ITextComponent titleIn) {
        super(screenContainer, inv, titleIn);

        assemblerTileEntity = screenContainer.tileEntity;
    }

    @Override
    public void render(int mouseX, int mouseY, float partialTicks) {
        this.renderBackground();
        super.render(mouseX, mouseY, partialTicks);
        this.renderHoveredToolTip(mouseX, mouseY);
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
        drawString(Minecraft.getInstance().fontRenderer, "Atomic Assembler", 5, 5, ScreenColors.RED);
        drawString(Minecraft.getInstance().fontRenderer, "Inventory", 5, 72, ScreenColors.GREY);

        // Button press behavior
        Button.IPressable pressed = button -> assemblerTileEntity.createElement();

        addButton(new GenerateElementButton(50, 50, 50, 10, "Create", pressed));
        super.drawGuiContainerForegroundLayer(mouseX, mouseY);
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        GlStateManager.color4f(1.0f, 1.0f, 1.0f, 1.0f);
        this.minecraft.getTextureManager().bindTexture(GUI);
        int relX = (this.width - this.xSize) / 2;
        int relY = (this.height - this.ySize) / 2;
        this.blit(relX, relY, 0, 0, this.xSize, this.ySize);

        if(assemblerTileEntity.getBlockState().get(BlockStateProperties.POWERED)) {
            this.blit(relX + 67, relY + 34, 176, 17, 24, 17);
        }
    }
}
