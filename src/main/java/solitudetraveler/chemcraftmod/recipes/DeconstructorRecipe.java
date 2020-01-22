package solitudetraveler.chemcraftmod.recipes;

import net.minecraft.item.ItemStack;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class DeconstructorRecipe {
    private Comparator<ItemStack> sorter = new Comparator<ItemStack>() {
        @Override
        public int compare(ItemStack o1, ItemStack o2) {
            int nameCompare = o1.getItem().toString().compareTo(o2.getItem().toString());

            if(nameCompare != 0) return nameCompare;
            else return o1.getCount() - o2.getCount();
        }
    };
    private ItemStack input;
    private ArrayList<ItemStack> results;

    public DeconstructorRecipe(ItemStack stackIn, ArrayList<ItemStack> outputsIn) {
        Collections.sort(outputsIn, sorter);
        this.results = outputsIn;
        this.input = stackIn;
    }

    public ArrayList<ItemStack> getResults() {
        return results;
    }

    public ItemStack getInput() {
        return input;
    }

    public boolean inputEqual(ItemStack stackIn) {
        return sorter.compare(this.input, stackIn) == 0;
    }
}
