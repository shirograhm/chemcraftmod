package solitudetraveler.chemcraftmod.screen.button;

import net.minecraft.client.gui.widget.button.Button;

import javax.annotation.Nonnull;

public class GenerateElementButton extends Button {
    private Button.IPressable pressed = new Button.IPressable() {
        @Override
        public void onPress(@Nonnull Button button) {
            System.out.println("PRESSED THAT BUTTON");
        }
    };

    public GenerateElementButton(int xPosIn, int yPosIn, int widthIn, int heightIn, String text, IPressable pressed) {
        super(xPosIn, yPosIn, widthIn, heightIn, text, pressed);
    }

    @Override
    public void onPress() {
        super.onPress();
    }
}
