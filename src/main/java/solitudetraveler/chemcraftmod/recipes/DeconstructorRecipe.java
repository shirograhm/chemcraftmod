package solitudetraveler.chemcraftmod.recipes;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import java.util.ArrayList;

public class DeconstructorRecipe {
    private Item input;
    private ArrayList<ItemStack> results;

    public DeconstructorRecipe(Item itemIn, ArrayList<ItemStack> outputsIn) {
        this.results = outputsIn;
        this.input = itemIn;

        if(results.size() > 6) throw new IllegalArgumentException("Invalid amount of outputs for deconstructor recipe.");
    }

    public ArrayList<ItemStack> getResults() {
        return results;
    }

    public Item getInput() {
        return input;
    }

    public boolean inputEqual(Item itemIn) {
        return this.input == itemIn;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append("Input: ");
        sb.append(input.toString());
        sb.append("\n");
        sb.append("Result: ");
        sb.append(results.toString());

        return sb.toString();
    }
}
