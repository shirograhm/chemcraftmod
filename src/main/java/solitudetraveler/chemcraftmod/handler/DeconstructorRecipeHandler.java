package solitudetraveler.chemcraftmod.handler;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import solitudetraveler.chemcraftmod.item.ItemList;

import java.util.ArrayList;

public class DeconstructorRecipeHandler {

    private static ArrayList<Item> inputs = new ArrayList<>();
    private static ArrayList<ItemStack[]> outputs = new ArrayList<>();

    private static ItemStack[] emptyOutput = new ItemStack[] {ItemStack.EMPTY, ItemStack.EMPTY, ItemStack.EMPTY, ItemStack.EMPTY, ItemStack.EMPTY, ItemStack.EMPTY};

    static {
        addRecipe(Items.COAL, new ItemStack[] {new ItemStack(ItemList.carbon, 6), new ItemStack(ItemList.oxygen, 1), new ItemStack(ItemList.hydrogen, 1),
                                               ItemStack.EMPTY, ItemStack.EMPTY, ItemStack.EMPTY});
    }

    private static void addRecipe(Item itemIn, ItemStack[] outStacks) {
        inputs.add(itemIn);
        outputs.add(outStacks);
    }

    public static ItemStack[] getResultStacksForInput(Item in) {
        for (int i = 0; i < inputs.size(); i++) {
            if (inputs.get(i).equals(in)) {
                return outputs.get(i);
            }
        }

        return emptyOutput;
    }
}
