package solitudetraveler.chemcraftmod.handler;

import net.minecraft.item.ItemStack;
import solitudetraveler.chemcraftmod.item.ItemList;
import solitudetraveler.chemcraftmod.recipes.DeconstructorRecipe;

import java.util.ArrayList;
import java.util.Arrays;

public class DeconstructorRecipeHandler {

    private static ArrayList<DeconstructorRecipe> recipes = new ArrayList<>();

    static {
        addRecipe(
                new ItemStack(ItemList.copper_ingot),
                new ArrayList<>(Arrays.asList(
                        new ItemStack(ItemList.getElementNumber(29), 9)))
        );
        addRecipe(
                new ItemStack(ItemList.bleach),
                new ArrayList<>(Arrays.asList(
                        new ItemStack(ItemList.sodium_hydroxide, 9)))
        );
        addRecipe(
                new ItemStack(ItemList.vinegar),
                new ArrayList<>(Arrays.asList(
                        new ItemStack(ItemList.acetic_acid, 9)))
        );
        addRecipe(
                new ItemStack(ItemList.baking_soda),
                new ArrayList<>(Arrays.asList(
                        new ItemStack(ItemList.sodium_bicarbonate, 9)))
        );
        addRecipe(
                new ItemStack(ItemList.salt),
                new ArrayList<>(Arrays.asList(
                        new ItemStack(ItemList.sodium_chloride, 9)))
        );
    }

    private static void addRecipe(ItemStack stackIn, ArrayList<ItemStack> resultStacks) {
        recipes.add(new DeconstructorRecipe(stackIn, resultStacks));
    }

    // Returns recipe with matching input
    // Otherwise returns null
    public static DeconstructorRecipe getRecipeForInputs(ItemStack input) {
        for(DeconstructorRecipe dr : recipes) {
            if(dr.inputEqual(input)) {
                return dr;
            }
        }

        return null;
    }
}
