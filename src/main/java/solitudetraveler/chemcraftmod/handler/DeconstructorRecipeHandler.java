package solitudetraveler.chemcraftmod.handler;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import solitudetraveler.chemcraftmod.item.ItemList;

import java.util.ArrayList;
import java.util.Collections;

public class DeconstructorRecipeHandler {

    private static ArrayList<Item> inputs = new ArrayList<>();
    private static ArrayList<ArrayList<ItemStack>> outputs = new ArrayList<>();

    static {
        addRecipe(ItemList.copper_ingot, new ItemStack[]{
                new ItemStack(ItemList.getElementNumber(29), 9)
        });
        addRecipe(ItemList.bleach, new ItemStack[]{
                new ItemStack(ItemList.sodium_hydroxide, 9)
        });
        addRecipe(ItemList.vinegar, new ItemStack[]{
                new ItemStack(ItemList.acetic_acid, 9)
        });
        addRecipe(ItemList.baking_soda, new ItemStack[]{
                new ItemStack(ItemList.sodium_bicarbonate, 9)
        });
        addRecipe(ItemList.salt, new ItemStack[]{
                new ItemStack(ItemList.sodium_chloride, 9)
        });
    }

    private static void addRecipe(Item itemIn, ItemStack[] stacksOut) {
        ArrayList<ItemStack> results = new ArrayList<>();
        Collections.addAll(results, stacksOut);

        inputs.add(itemIn);
        outputs.add(results);
    }

    public static boolean isDeconstructible(Item in) {
        return !getResultStacksForInput(in).isEmpty();
    }

    public static ArrayList<ItemStack> getResultStacksForInput(Item in) {
        for (int i = 0; i < inputs.size(); i++) {
            if (inputs.get(i).equals(in)) {
                return outputs.get(i);
            }
        }

        return new ArrayList<>();
    }
}
