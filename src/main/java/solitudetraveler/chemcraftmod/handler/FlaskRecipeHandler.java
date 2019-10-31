package solitudetraveler.chemcraftmod.handler;

import net.minecraft.item.ItemStack;
import solitudetraveler.chemcraftmod.item.ItemList;

import java.util.ArrayList;

public class FlaskRecipeHandler {
    private static ArrayList<ItemStack[]> inputs = new ArrayList<>();
    private static ArrayList<ItemStack[]> outputs = new ArrayList<>();

    public static ItemStack[] emptyOutput = new ItemStack[]{ItemStack.EMPTY, ItemStack.EMPTY};

    static {
        addRecipe(new ItemStack(ItemList.getElementNumber(11)), new ItemStack(ItemList.bicarbonate), ItemStack.EMPTY, ItemStack.EMPTY, ItemStack.EMPTY,
                new ItemStack(ItemList.sodium_bicarbonate), ItemStack.EMPTY);
        addRecipe(new ItemStack(ItemList.getElementNumber(11)), new ItemStack(ItemList.getElementNumber(17)), ItemStack.EMPTY, ItemStack.EMPTY, ItemStack.EMPTY,
                new ItemStack(ItemList.sodium_chloride), ItemStack.EMPTY);
        addRecipe(new ItemStack(ItemList.sodium_bicarbonate), new ItemStack(ItemList.acetic_acid), ItemStack.EMPTY, ItemStack.EMPTY, ItemStack.EMPTY,
                ItemStack.EMPTY, ItemStack.EMPTY);
    }

    private static void addRecipe(ItemStack in1, ItemStack in2, ItemStack in3, ItemStack in4, ItemStack in5, ItemStack out1, ItemStack out2) {
        inputs.add(new ItemStack[]{in1, in2, in3, in4, in5});
        outputs.add(new ItemStack[]{out1, out2});
    }

    public static boolean areOutputsEqual(ItemStack[] out1, ItemStack[] out2) {
        for(int i = 0; i < out1.length; i++) {
            if(out1[i] != out2[i]) return false;
        }
        return true;
    }

    public static ItemStack[] getOutputForInputArray(ItemStack[] in) {
        for(int i = 0; i < inputs.size(); i++) {
            if(areOutputsEqual(in, inputs.get(i))) {
                return outputs.get(i);
            }
        }
        return emptyOutput;
    }
}
