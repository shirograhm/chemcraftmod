package solitudetraveler.chemcraftmod.handler;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import solitudetraveler.chemcraftmod.item.ItemList;

import java.util.ArrayList;
import java.util.Arrays;

public class ConstructorRecipeHandler {

    private static ArrayList<Item[]> inputs = new ArrayList<>();
    private static ArrayList<ItemStack> outputs = new ArrayList<>();

    static {
        addRecipe(
                new Item[]{
                        Items.AIR, ItemList.hydrogen, ItemList.oxygen,
                        ItemList.carbon, ItemList.carbon, ItemList.carbon,
                        ItemList.carbon, ItemList.carbon, ItemList.carbon
                }, new ItemStack(Items.COAL, 1));
    }

    private static void addRecipe(Item[] items, ItemStack itemStack) {
        inputs.add(items);
        outputs.add(itemStack);
    }

    public static ItemStack getResultForInputSet(Item[] items) {
        for (int i = 0; i < inputs.size(); i++) {
            if (Arrays.equals(inputs.get(i), items)) {
                return outputs.get(i);
            }
        }

        return ItemStack.EMPTY;
    }
}

