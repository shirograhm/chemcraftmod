package solitudetraveler.chemcraftmod.handler;

import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import solitudetraveler.chemcraftmod.item.ItemList;

import java.util.ArrayList;
import java.util.Collections;

public class ReconstructorRecipeHandler {

    private static ArrayList<ArrayList<ItemStack>> inputs = new ArrayList<>();
    private static ArrayList<ItemStack> outputs = new ArrayList<>();

    // SHAPED RECIPES
    static {
        addRecipe(
                new ItemStack[]{
                        new ItemStack(ItemList.getElementNumber(16)),
                        new ItemStack(ItemList.getElementNumber(8), 4)
                }, new ItemStack(ItemList.sulfate)
        );
        addRecipe(
                new ItemStack[]{
                        new ItemStack(ItemList.getElementNumber(16)),
                        new ItemStack(ItemList.getElementNumber(8), 3)
                }, new ItemStack(ItemList.sulfite)
        );
        addRecipe(
                new ItemStack[]{
                        new ItemStack(ItemList.getElementNumber(7)),
                        new ItemStack(ItemList.getElementNumber(8), 3)
                }, new ItemStack(ItemList.nitrate)
        );
        addRecipe(
                new ItemStack[]{
                        new ItemStack(ItemList.getElementNumber(7)),
                        new ItemStack(ItemList.getElementNumber(8), 2)
                }, new ItemStack(ItemList.nitrite)
        );
        addRecipe(
                new ItemStack[]{
                        new ItemStack(ItemList.getElementNumber(6)),
                        new ItemStack(ItemList.getElementNumber(8), 3)
                }, new ItemStack(ItemList.carbonate)
        );
        addRecipe(
                new ItemStack[]{
                        new ItemStack(ItemList.getElementNumber(1)),
                        new ItemStack(ItemList.getElementNumber(6)),
                        new ItemStack(ItemList.getElementNumber(8), 3)
                }, new ItemStack(ItemList.bicarbonate)
        );
        addRecipe(
                new ItemStack[]{
                        new ItemStack(ItemList.getElementNumber(8)),
                        new ItemStack(ItemList.getElementNumber(1))
                }, new ItemStack(ItemList.hydroxide)
        );
        addRecipe(
                new ItemStack[]{
                        new ItemStack(ItemList.getElementNumber(6), 2),
                        new ItemStack(ItemList.getElementNumber(1), 3),
                        new ItemStack(ItemList.getElementNumber(8), 2)
                }, new ItemStack(ItemList.acetate)
        );
        addRecipe(
                new ItemStack[]{
                        new ItemStack(ItemList.getElementNumber(6)),
                        new ItemStack(ItemList.getElementNumber(1), 3)
                }, new ItemStack(ItemList.methyl_group)
        );
        addRecipe(
                new ItemStack[]{
                        new ItemStack(ItemList.getElementNumber(6)),
                        new ItemStack(ItemList.getElementNumber(1), 2)
                }, new ItemStack(ItemList.methylene_group)
        );
        addRecipe(
                new ItemStack[]{
                        new ItemStack(ItemList.getElementNumber(6), 3),
                        new ItemStack(ItemList.getElementNumber(1), 8)
                }, new ItemStack(ItemList.propane)
        );
        addRecipe(
                new ItemStack[]{
                        new ItemStack(ItemList.methyl_group),
                        new ItemStack(ItemList.methylene_group),
                        new ItemStack(ItemList.methyl_group)
                }, new ItemStack(ItemList.propane)
        );
        addRecipe(
                new ItemStack[]{
                        new ItemStack(ItemList.getElementNumber(1), 2),
                        new ItemStack(ItemList.getElementNumber(8), 2)
                }, new ItemStack(ItemList.hydrogen_peroxide)
        );
        addRecipe(
                new ItemStack[]{
                        new ItemStack(ItemList.getElementNumber(1), 2),
                        new ItemStack(ItemList.getElementNumber(8))
                }, new ItemStack(ItemList.water)
        );
        addRecipe(
                new ItemStack[]{
                        new ItemStack(ItemList.getElementNumber(29), 9)
                }, new ItemStack(ItemList.copper_ingot)
        );
        addRecipe(
                new ItemStack[]{
                        new ItemStack(ItemList.getElementNumber(30)),
                        new ItemStack(ItemList.getElementNumber(8))
                }, new ItemStack(ItemList.zinc_oxide)
        );
        addRecipe(
                new ItemStack[]{
                        new ItemStack(ItemList.getElementNumber(11)),
                        new ItemStack(ItemList.getElementNumber(17))
                }, new ItemStack(ItemList.sodium_chloride)
        );
        addRecipe(
                new ItemStack[]{
                        new ItemStack(ItemList.getElementNumber(11)),
                        new ItemStack(ItemList.bicarbonate)
                }, new ItemStack(ItemList.sodium_bicarbonate)
        );
        addRecipe(
                new ItemStack[]{
                        new ItemStack(ItemList.getElementNumber(11)),
                        new ItemStack(ItemList.hydroxide)
                }, new ItemStack(ItemList.sodium_hydroxide)
        );
        addRecipe(
                new ItemStack[]{
                        new ItemStack(ItemList.getElementNumber(1)),
                        new ItemStack(ItemList.acetate)
                }, new ItemStack(ItemList.acetic_acid)
        );
        addRecipe(
                new ItemStack[]{
                        new ItemStack(ItemList.getElementNumber(49), 2),
                        new ItemStack(ItemList.getElementNumber(16))
                }, new ItemStack(ItemList.silver_sulfide)
        );
    }

    private static void addRecipe(ItemStack[] stacksIn, ItemStack itemStack) {
        ArrayList<ItemStack> ingredients = new ArrayList<>();
        Collections.addAll(ingredients, stacksIn);

        inputs.add(ingredients);
        outputs.add(itemStack);
    }

    public static ItemStack getResultForInputSet(ItemStack[] items) {
        // Check shaped recipes
        for (int i = 0; i < inputs.size(); i++) {
            if (ingredientsAreEqual(items, inputs.get(i))) {
                return outputs.get(i);
            }
        }
        return ItemStack.EMPTY;
    }

    private static boolean ingredientsAreEqual(ItemStack[] stacks, ArrayList<ItemStack> possible) {
        // Generate ingredients list
        ArrayList<ItemStack> ingredients = new ArrayList<>(possible);
        // Compare itemstacks
        for(ItemStack stack : stacks) {
            if(stack.getItem() != Items.AIR) {
                for(int i = 0; i < ingredients.size(); i++) {
                    if(stack.getItem() == ingredients.get(i).getItem() && stack.getCount() == ingredients.get(i).getCount()) {
                        ingredients.remove(i);
                        i--;
                    }
                }
            }
        }
        // Returns true if both contain equal itemstacks (both are subsets of each other == true)
        return (ingredients.isEmpty());
    }
}

