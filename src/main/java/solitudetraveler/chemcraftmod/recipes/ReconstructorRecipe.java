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

    public boolean ingredientsEqual(ArrayList<ItemStack> input) {
        // If arraylist sizes are not equal, return false
        if(input.size() != ingredients.size()) return false;
        // Sort input to test against
        Collections.sort(input, sorter);
        // Iterate through and test at each index
        for(int i = 0; i < input.size(); i++) {
            if(input.get(i).getCount() != ingredients.get(i).getCount()) {
                return false;
            }
            if(input.get(i).getItem() != ingredients.get(i).getItem()) {
                return false;
            }
        }
        return true;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append("Ingredients: ");
        sb.append(ingredients.toString());
        sb.append("\n");
        sb.append("Result: ");
        sb.append(result.toString());

        return sb.toString();
    }
}
