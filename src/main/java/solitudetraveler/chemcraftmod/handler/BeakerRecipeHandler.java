package solitudetraveler.chemcraftmod.handler;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import solitudetraveler.chemcraftmod.item.ItemList;

import java.util.ArrayList;
import java.util.Collections;

public class BeakerRecipeHandler {

    private static ArrayList<ArrayList<Item>> inputs = new ArrayList<>();
    private static ArrayList<ItemStack[]> outputs = new ArrayList<>();

    private static final ItemStack[] EMPTY_OUTPUT = new ItemStack[]{ItemStack.EMPTY, ItemStack.EMPTY, ItemStack.EMPTY, ItemStack.EMPTY};

    static {
        addRecipe(
                new Item[]{ItemList.soap, ItemList.water},
                new ItemStack[]{new ItemStack(ItemList.liquid_soap, 1)}
        );
    }

    private static void addRecipe(Item[] items, ItemStack[] outStacks) {
        ArrayList<Item> ingredients = new ArrayList<>();
        ItemStack[] stacks = new ItemStack[6];
        // Generate inputs arraylist using collections library
        Collections.addAll(ingredients, items);
        // Generate out stacks array
        System.arraycopy(outStacks, 0, stacks, 0, outStacks.length);
        if(outStacks.length < 4) {
            System.arraycopy(EMPTY_OUTPUT, 0, stacks, outStacks.length, 4 - outStacks.length);
        }

        inputs.add(ingredients);
        outputs.add(stacks);
    }

    public static ItemStack[] getResultStacksForInputArray(ArrayList<Item> ingredients) {
        for(int i = 0; i < inputs.size(); i++) {
            if(ingredients.containsAll(inputs.get(i)) && inputs.get(i).containsAll(ingredients)) {
                return outputs.get(i);
            }
        }

        return EMPTY_OUTPUT;
    }

    public static boolean outputNotEmpty(ItemStack[] out) {
        for(ItemStack stack : out) {
            if(!stack.isEmpty()) return true;
        }
        return false;
    }
}
