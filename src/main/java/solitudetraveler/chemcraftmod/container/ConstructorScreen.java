package solitudetraveler.chemcraftmod.container;

import com.mojang.blaze3d.platform.GlStateManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import solitudetraveler.chemcraftmod.ChemCraftMod;
import solitudetraveler.chemcraftmod.tileentity.ConstructorTileEntity;

public class ConstructorScreen extends ContainerScreen<ConstructorContainer> {

    private ResourceLocation GUI = new ResourceLocation(ChemCraftMod.MOD_ID, "textures/gui/constructor_gui.png");

    private ConstructorTileEntity constructorTE;

    public ConstructorScreen(ConstructorContainer screenContainer, PlayerInventory inv, ITextComponent titleIn) {
        super(screenContainer, inv, titleIn);

        constructorTE = (ConstructorTileEntity) screenContainer.tileEntity;
    }

    @Override
    public void render(int mouseX, int mouseY, float partialTicks) {
        this.renderBackground();
        super.render(mouseX, mouseY, partialTicks);
        this.renderHoveredToolTip(mouseX, mouseY);
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
        drawString(Minecraft.getInstance().fontRenderer, "Molecular Constructor", 5, 5, 0x4dff5b);
        drawString(Minecraft.getInstance().fontRenderer, "Inventory", 5, 73, 0x969696);
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        GlStateManager.color4f(1.0f, 1.0f, 1.0f, 1.0f);
        this.minecraft.getTextureManager().bindTexture(GUI);
        int relX = (this.width - this.xSize) / 2;
        int relY = (this.height - this.ySize) / 2;
        this.blit(relX, relY, 0, 0, this.xSize, this.ySize);

        if(constructorTE.isConstructing()) {
            int k = (int) (constructorTE.getConstructionTimeScaled() * 23);
            this.blit(relX + 89, relY + 33, 176, 0, k + 1, 16);
        }
    }
}
