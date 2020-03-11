package solitudetraveler.chemcraftmod.item;

import net.minecraft.util.text.Style;
import net.minecraft.util.text.TextFormatting;

import javax.annotation.Nullable;

public class ColoredTextComponent extends Style {
    @Nullable
    @Override
    public TextFormatting getColor() {
        return TextFormatting.BLUE;
    }

    public ColoredTextComponent() {
        super();
    }
}
