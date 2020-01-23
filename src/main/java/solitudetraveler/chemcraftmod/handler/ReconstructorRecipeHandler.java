package solitudetraveler.chemcraftmod.handler;

import net.minecraft.item.ItemStack;
import solitudetraveler.chemcraftmod.item.ItemList;
import solitudetraveler.chemcraftmod.recipes.ReconstructorRecipe;

import java.util.ArrayList;
import java.util.Arrays;

public class ReconstructorRecipeHandler {

    private static ArrayList<ReconstructorRecipe> recipes = new ArrayList<>();

    // SHAPED RECIPES
    static {
        // SULFATE
        addRecipe(
                new ArrayList<>(Arrays.asList(
                        new ItemStack(ItemList.getElementNumber(16)),
                        new ItemStack(ItemList.getElementNumber(8), 4))),
                new ItemStack(ItemList.sulfate)
        );
        // SULFITE
        addRecipe(
                new ArrayList<>(Arrays.asList(
                        new ItemStack(ItemList.getElementNumber(16)),
                        new ItemStack(ItemList.getElementNumber(8), 3))),
                new ItemStack(ItemList.sulfite)
        );
        // NITRATE
        addRecipe(
                new ArrayList<>(Arrays.asList(
                        new ItemStack(ItemList.getElementNumber(7)),
                        new ItemStack(ItemList.getElementNumber(8), 3))),
                new ItemStack(ItemList.nitrate)
        );
        // NITRITE
        addRecipe(
                new ArrayList<>(Arrays.asList(
                        new ItemStack(ItemList.getElementNumber(7)),
                        new ItemStack(ItemList.getElementNumber(8), 2))),
                new ItemStack(ItemList.nitrite)
        );
        // CARBONATE
        addRecipe(
                new ArrayList<>(Arrays.asList(
                        new ItemStack(ItemList.getElementNumber(6)),
                        new ItemStack(ItemList.getElementNumber(8), 3))),
                new ItemStack(ItemList.carbonate)
        );
        // BICARBONATE
        addRecipe(
                new ArrayList<>(Arrays.asList(
                        new ItemStack(ItemList.getElementNumber(1)),
                        new ItemStack(ItemList.getElementNumber(6)),
                        new ItemStack(ItemList.getElementNumber(8), 3))),
                new ItemStack(ItemList.bicarbonate)
        );
        // HYDROXIDE
        addRecipe(
                new ArrayList<>(Arrays.asList(
                        new ItemStack(ItemList.getElementNumber(8)),
                        new ItemStack(ItemList.getElementNumber(1)))),
                new ItemStack(ItemList.hydroxide)
        );
        // ACETATE
        addRecipe(
                new ArrayList<>(Arrays.asList(
                        new ItemStack(ItemList.getElementNumber(6), 2),
                        new ItemStack(ItemList.getElementNumber(1), 3),
                        new ItemStack(ItemList.getElementNumber(8), 2))),
                new ItemStack(ItemList.acetate)
        );
        // METHYL GROUP
        addRecipe(
                new ArrayList<>(Arrays.asList(
                        new ItemStack(ItemList.getElementNumber(6)),
                        new ItemStack(ItemList.getElementNumber(1), 3))),
                new ItemStack(ItemList.methyl_group)
        );
        // METHYLENE GROUP
        addRecipe(
                new ArrayList<>(Arrays.asList(
                        new ItemStack(ItemList.getElementNumber(6)),
                        new ItemStack(ItemList.getElementNumber(1), 2))),
                new ItemStack(ItemList.methylene_group)
        );
        // PROPANE
        addRecipe(
                new ArrayList<>(Arrays.asList(
                        new ItemStack(ItemList.getElementNumber(6), 3),
                        new ItemStack(ItemList.getElementNumber(1), 8))),
                new ItemStack(ItemList.propane)
        );
        // PROPANE USING CARBON GROUPS
        addRecipe(
                new ArrayList<>(Arrays.asList(
                        new ItemStack(ItemList.methyl_group),
                        new ItemStack(ItemList.methylene_group),
                        new ItemStack(ItemList.methyl_group))),
                new ItemStack(ItemList.propane)
        );
        // HYDROGEN PEROXIDE
        addRecipe(
                new ArrayList<>(Arrays.asList(
                        new ItemStack(ItemList.getElementNumber(1), 2),
                        new ItemStack(ItemList.getElementNumber(8), 2))),
                new ItemStack(ItemList.hydrogen_peroxide)
        );
        // WATER
        addRecipe(
                new ArrayList<>(Arrays.asList(
                        new ItemStack(ItemList.getElementNumber(1), 2),
                        new ItemStack(ItemList.getElementNumber(8)))),
                new ItemStack(ItemList.water)
        );
        // COPPER INGOT
        addRecipe(
                new ArrayList<>(Arrays.asList(
                        new ItemStack(ItemList.getElementNumber(29), 9))),
                new ItemStack(ItemList.copper_ingot)
        );
        // SILICON INGOT
        addRecipe(
                new ArrayList<>(Arrays.asList(
                        new ItemStack(ItemList.getElementNumber(14), 9))),
                new ItemStack(ItemList.silicon_ingot)
        );
        // ALUMINIUM INGOT
        addRecipe(
                new ArrayList<>(Arrays.asList(
                        new ItemStack(ItemList.getElementNumber(13), 9))),
                new ItemStack(ItemList.aluminium_ingot)
        );
        // TIN INGOT
        addRecipe(
                new ArrayList<>(Arrays.asList(
                        new ItemStack(ItemList.getElementNumber(50), 9))),
                new ItemStack(ItemList.tin_ingot)
        );
        // ZINC OXIDE
        addRecipe(
                new ArrayList<>(Arrays.asList(
                        new ItemStack(ItemList.getElementNumber(30)),
                        new ItemStack(ItemList.getElementNumber(8)))),
                new ItemStack(ItemList.zinc_oxide)
        );
        // SODIUM CHLORIDE
        addRecipe(
                new ArrayList<>(Arrays.asList(
                        new ItemStack(ItemList.getElementNumber(11)),
                        new ItemStack(ItemList.getElementNumber(17)))),
                new ItemStack(ItemList.sodium_chloride)
        );
        addRecipe(
                new ArrayList<>(Arrays.asList(
                        new ItemStack(ItemList.getElementNumber(11)),
                        new ItemStack(ItemList.bicarbonate))),
                new ItemStack(ItemList.sodium_bicarbonate)
        );
        addRecipe(
                new ArrayList<>(Arrays.asList(
                        new ItemStack(ItemList.getElementNumber(11)),
                        new ItemStack(ItemList.hydroxide))),
                new ItemStack(ItemList.sodium_hydroxide)
        );
        addRecipe(
                new ArrayList<>(Arrays.asList(
                        new ItemStack(ItemList.getElementNumber(1)),
                        new ItemStack(ItemList.acetate))),
                new ItemStack(ItemList.acetic_acid)
        );
        addRecipe(
                new ArrayList<>(Arrays.asList(
                        new ItemStack(ItemList.getElementNumber(47), 2),
                        new ItemStack(ItemList.getElementNumber(16)))),
                new ItemStack(ItemList.silver_sulfide)
        );
    }

    private static void addRecipe(ArrayList<ItemStack> stacks, ItemStack result) {
        recipes.add(new ReconstructorRecipe(stacks, result));
    }

    // Returns recipe with matching input
    // Otherwise returns null
    public static ReconstructorRecipe getRecipeForInputs(ArrayList<ItemStack> input) {
        for (ReconstructorRecipe rr : recipes) {
            if (rr.inputEqual(input)) {
                return rr;
            }
        }

        return null;
    }
}