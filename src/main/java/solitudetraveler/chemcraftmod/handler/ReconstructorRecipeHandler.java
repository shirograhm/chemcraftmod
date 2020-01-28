package solitudetraveler.chemcraftmod.handler;

import net.minecraft.item.ItemStack;
import solitudetraveler.chemcraftmod.item.ItemList;
import solitudetraveler.chemcraftmod.recipes.ReconstructorRecipe;

import java.util.ArrayList;
import java.util.Arrays;

public class ReconstructorRecipeHandler {
    private static ArrayList<ReconstructorRecipe> recipes = new ArrayList<>();

    static {
        // Covalent compounds
        addRecipe(new ArrayList<>(Arrays.asList(
                new ItemStack(ItemList.getElementNumber(1), 2),
                new ItemStack(ItemList.getElementNumber(8)))),

                new ItemStack(ItemList.water)
        );
        addRecipe(new ArrayList<>(Arrays.asList(
                new ItemStack(ItemList.getElementNumber(1), 2),
                new ItemStack(ItemList.getElementNumber(8), 2))),

                new ItemStack(ItemList.hydrogen_peroxide)
        );
        addRecipe(new ArrayList<>(Arrays.asList(
                new ItemStack(ItemList.getElementNumber(6), 2),
                new ItemStack(ItemList.getElementNumber(1), 3),
                new ItemStack(ItemList.getElementNumber(8), 2))),

                new ItemStack(ItemList.acetate)
        );
        addRecipe(new ArrayList<>(Arrays.asList(
                new ItemStack(ItemList.getElementNumber(16)),
                new ItemStack(ItemList.getElementNumber(8), 4))),

                new ItemStack(ItemList.sulfate)
        );
        addRecipe(new ArrayList<>(Arrays.asList(
                new ItemStack(ItemList.getElementNumber(16)),
                new ItemStack(ItemList.getElementNumber(8), 3))),

                new ItemStack(ItemList.sulfite)
        );
        addRecipe(new ArrayList<>(Arrays.asList(
                new ItemStack(ItemList.getElementNumber(7)),
                new ItemStack(ItemList.getElementNumber(8), 3))),

                new ItemStack(ItemList.nitrate)
        );
        addRecipe(new ArrayList<>(Arrays.asList(
                new ItemStack(ItemList.getElementNumber(7)),
                new ItemStack(ItemList.getElementNumber(8), 2))),

                new ItemStack(ItemList.nitrite)
        );
        addRecipe(new ArrayList<>(Arrays.asList(
                new ItemStack(ItemList.getElementNumber(6)),
                new ItemStack(ItemList.getElementNumber(8), 3))),

                new ItemStack(ItemList.carbonate)
        );
        addRecipe(new ArrayList<>(Arrays.asList(
                new ItemStack(ItemList.getElementNumber(1)),
                new ItemStack(ItemList.getElementNumber(6)),
                new ItemStack(ItemList.getElementNumber(8), 3))),

                new ItemStack(ItemList.bicarbonate)
        );
        addRecipe(new ArrayList<>(Arrays.asList(
                new ItemStack(ItemList.getElementNumber(8)),
                new ItemStack(ItemList.getElementNumber(1)))),

                new ItemStack(ItemList.hydroxide)
        );
        addRecipe(new ArrayList<>(Arrays.asList(
                new ItemStack(ItemList.getElementNumber(6)),
                new ItemStack(ItemList.getElementNumber(1), 3))),

                new ItemStack(ItemList.methyl_group)
        );
        addRecipe(new ArrayList<>(Arrays.asList(
                new ItemStack(ItemList.getElementNumber(6)),
                new ItemStack(ItemList.getElementNumber(1), 2))),

                new ItemStack(ItemList.methylene_group)
        );
        addRecipe(new ArrayList<>(Arrays.asList(
                new ItemStack(ItemList.getElementNumber(6), 3),
                new ItemStack(ItemList.getElementNumber(1), 8))),

                new ItemStack(ItemList.propane)
        );
        // Ionic compounds
        addRecipe(new ArrayList<>(Arrays.asList(
                new ItemStack(ItemList.getElementNumber(30)),
                new ItemStack(ItemList.getElementNumber(8)))),

                new ItemStack(ItemList.zinc_oxide)
        );
        addRecipe(new ArrayList<>(Arrays.asList(
                new ItemStack(ItemList.getElementNumber(11)),
                new ItemStack(ItemList.getElementNumber(17)))),

                new ItemStack(ItemList.sodium_chloride)
        );
        addRecipe(new ArrayList<>(Arrays.asList(
                new ItemStack(ItemList.getElementNumber(11)),
                new ItemStack(ItemList.getElementNumber(1)),
                new ItemStack(ItemList.getElementNumber(6)),
                new ItemStack(ItemList.getElementNumber(8), 3))),

                new ItemStack(ItemList.sodium_bicarbonate)
        );
        addRecipe(new ArrayList<>(Arrays.asList(
                new ItemStack(ItemList.getElementNumber(11)),
                new ItemStack(ItemList.getElementNumber(8)),
                new ItemStack(ItemList.getElementNumber(1)))),

                new ItemStack(ItemList.sodium_hydroxide)
        );
        addRecipe(new ArrayList<>(Arrays.asList(
                new ItemStack(ItemList.getElementNumber(6), 2),
                new ItemStack(ItemList.getElementNumber(1), 4),
                new ItemStack(ItemList.getElementNumber(8), 2))),

                new ItemStack(ItemList.acetic_acid)
        );
        addRecipe(new ArrayList<>(Arrays.asList(
                new ItemStack(ItemList.getElementNumber(47), 2),
                new ItemStack(ItemList.getElementNumber(16)))),

                new ItemStack(ItemList.silver_sulfide)
        );
        // Extra recipes
        addRecipe(new ArrayList<>(Arrays.asList(
                new ItemStack(ItemList.getElementNumber(11)),
                new ItemStack(ItemList.bicarbonate))),

                new ItemStack(ItemList.sodium_bicarbonate)
        );
        addRecipe(new ArrayList<>(Arrays.asList(
                new ItemStack(ItemList.getElementNumber(11)),
                new ItemStack(ItemList.hydroxide))),

                new ItemStack(ItemList.sodium_hydroxide)
        );
        addRecipe(new ArrayList<>(Arrays.asList(
                new ItemStack(ItemList.getElementNumber(1)),
                new ItemStack(ItemList.acetate))),

                new ItemStack(ItemList.acetic_acid)
        );
        addRecipe(new ArrayList<>(Arrays.asList(
                new ItemStack(ItemList.methyl_group),
                new ItemStack(ItemList.methylene_group),
                new ItemStack(ItemList.methyl_group))),

                new ItemStack(ItemList.propane)
        );
    }

    private static void addRecipe(ArrayList<ItemStack> stacks, ItemStack result) {
        recipes.add(new ReconstructorRecipe(stacks, result));
    }

    // Returns recipe with matching input
    // Otherwise returns null
    public static ReconstructorRecipe getRecipeForInputs(ArrayList<ItemStack> input) {
        for (ReconstructorRecipe rr : recipes) {
            if (rr.ingredientsEqual(input)) {
                return rr;
            }
        }

        return null;
    }
}