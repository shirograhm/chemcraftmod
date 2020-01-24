package solitudetraveler.chemcraftmod.container;

import com.mojang.blaze3d.platform.GlStateManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import solitudetraveler.chemcraftmod.main.ChemCraftMod;
import solitudetraveler.chemcraftmod.tileentity.GeneratorTileEntity;

public class GeneratorScreen extends ContainerScreen<GeneratorContainer> {
    private ResourceLocation GUI = new ResourceLocation(ChemCraftMod.MOD_ID, "textures/gui/generator_gui.png");
    private GeneratorTileEntity tileEntity;

    public GeneratorScreen(GeneratorContainer screenContainer, PlayerInventory inventory, ITextComponent title) {
        super(screenContainer, inventory, title);

        tileEntity = screenContainer.tileEntity;
    }

    @Override
    public void render(int mouseX, int mouseY, float partialTicks) {
        this.renderBackground();
        super.render(mouseX, mouseY, partialTicks);
        this.renderHoveredToolTip(mouseX, mouseY);
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
        drawString(Minecraft.getInstance().fontRenderer, "Generator", 6, 6, 0x445566);
        drawString(Minecraft.getInstance().fontRenderer, "Inventory", 6, 64, 0x969696);
        super.drawGuiContainerForegroundLayer(mouseX, mouseY);
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        GlStateManager.color4f(1.0f, 1.0f, 1.0f, 1.0f);
        this.minecraft.getTextureManager().bindTexture(GUI);
        int relX = (this.width - this.xSize) / 2;
        int relY = (this.height - this.ySize) / 2;
        this.blit(relX, relY, 0, 0, this.xSize, this.ySize);

        if(tileEntity.isPowered()) {
            int powerSize = (int) (tileEntity.getPowerLevelScaled() * 34);

            this.blit(relX + 48, relY + 22, 176, 34 - powerSize, 8, powerSize);
        }
    }
}
