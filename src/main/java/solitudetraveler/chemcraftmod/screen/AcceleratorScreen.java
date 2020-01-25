package solitudetraveler.chemcraftmod.screen;

import com.mojang.blaze3d.platform.GlStateManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import solitudetraveler.chemcraftmod.container.AcceleratorContainer;
import solitudetraveler.chemcraftmod.main.ChemCraftMod;
import solitudetraveler.chemcraftmod.tileentity.AcceleratorTileEntity;

import java.util.Random;

public class AcceleratorScreen extends ContainerScreen<AcceleratorContainer> {
    private ResourceLocation GUI = new ResourceLocation(ChemCraftMod.MOD_ID, "textures/gui/accelerator_gui.png");
    private AcceleratorTileEntity tileEntity;
    private int animationScreen;

    public AcceleratorScreen(AcceleratorContainer screenContainer, PlayerInventory inventory, ITextComponent title) {
        super(screenContainer, inventory, title);

        this.xSize = 176;
        this.ySize = 158;

        animationScreen = new Random().nextInt(2);
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
        drawString(Minecraft.getInstance().fontRenderer, "Particle Accelerator", 6, 6, ScreenColors.ORANGE);
        drawString(Minecraft.getInstance().fontRenderer, "Inventory", 6, 64, ScreenColors.GREY);
        super.drawGuiContainerForegroundLayer(mouseX, mouseY);
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        GlStateManager.color4f(1.0f, 1.0f, 1.0f, 1.0f);
        this.minecraft.getTextureManager().bindTexture(GUI);
        int relX = (this.width - this.xSize) / 2;
        int relY = (this.height - this.ySize) / 2;
        this.blit(relX, relY, 0, 0, this.xSize, this.ySize);

        if(tileEntity.isActive()) {
            int animationFrame = tileEntity.getCurrentAnimationFrame();
            this.blit(relX + 52, relY + 21, 180 + 36 * animationScreen, animationFrame * 36, 36, 36);
        }
    }
}
