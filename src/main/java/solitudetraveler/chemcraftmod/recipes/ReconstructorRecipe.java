package solitudetraveler.chemcraftmod.recipes;

import net.minecraft.item.ItemStack;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class ReconstructorRecipe {
    private Comparator<ItemStack> sorter = new Comparator<ItemStack>() {
        @Override
        public int compare(ItemStack o1, ItemStack o2) {
            int nameCompare = o1.getItem().toString().compareTo(o2.getItem().toString());

            if(nameCompare != 0) return nameCompare;
            else return o1.getCount() - o2.getCount();
        }
    };
    private ArrayList<ItemStack> ingredients;
    private ItemStack result;

    public ReconstructorRecipe(ArrayList<ItemStack> ingredientsIn, ItemStack resultIn) {
        Collections.sort(ingredientsIn, sorter);
        this.ingredients = ingredientsIn;
        this.result = resultIn;
    }

    public ItemStack getOutput() {
        return result;
    }

    public ArrayList<ItemStack> getIngredients() {
        return ingredients;
    }

    public boolean inputEqual(ArrayList<ItemStack> input) {
        if(input.size() != ingredients.size()) {
            return false;
        }

        Collections.sort(input, sorter);

        for(int i = 0; i < input.size(); i++) {
            if(input.get(i).getCount() != ingredients.get(i).getCount()) {
                return false;
            }
            if(!input.get(i).getItem().toString().equals(ingredients.get(i).getItem().toString())) {
                return false;
            }
        }
        // Test equality if lengths are equivalent
        return true;
    }
}
