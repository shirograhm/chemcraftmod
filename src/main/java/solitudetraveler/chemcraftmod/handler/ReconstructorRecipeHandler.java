package solitudetraveler.chemcraftmod.handler;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import solitudetraveler.chemcraftmod.item.ItemList;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class ReconstructorRecipeHandler {

    private static ArrayList<Item[]> shapedInputs = new ArrayList<>();
    private static ArrayList<ItemStack> shapedOutputs = new ArrayList<>();

    private static ArrayList<ArrayList<Item>> shapelessInputs = new ArrayList<>();
    private static ArrayList<ItemStack> shapelessOutputs = new ArrayList<>();

    // SHAPED RECIPES
    static {
        addShapedRecipe(
                new Item[]{
                        Items.AIR, ItemList.getElementNumber(1), ItemList.getElementNumber(8),
                        ItemList.getElementNumber(6), ItemList.getElementNumber(6), ItemList.getElementNumber(6),
                        ItemList.getElementNumber(6), ItemList.getElementNumber(6), ItemList.getElementNumber(6)
                }, new ItemStack(Items.COAL, 1)
        );
        addShapedRecipe(
                new Item[]{
                        ItemList.getElementNumber(8), Items.AIR, ItemList.getElementNumber(8),
                        ItemList.getElementNumber(20), ItemList.getElementNumber(6), ItemList.getElementNumber(20),
                        ItemList.getElementNumber(20), ItemList.getElementNumber(20), ItemList.getElementNumber(20)
                }, new ItemStack(ItemList.calcite, 1)
        );
        addShapedRecipe(
                new Item[]{
                        Items.AIR, Items.AIR, Items.AIR,
                        ItemList.getElementNumber(6), ItemList.getElementNumber(30), ItemList.getElementNumber(6),
                        ItemList.getElementNumber(20), ItemList.getElementNumber(20), ItemList.getElementNumber(20)
                }, new ItemStack(ItemList.aragonite, 1)
        );
        addShapedRecipe(
                new Item[]{
                        Items.AIR, ItemList.getElementNumber(9), Items.AIR,
                        ItemList.getElementNumber(9), ItemList.getElementNumber(20), ItemList.getElementNumber(9),
                        Items.AIR, ItemList.getElementNumber(9), Items.AIR,
                }, new ItemStack(ItemList.fluorite, 1)
        );
        addShapedRecipe(
                new Item[]{
                        ItemList.getElementNumber(8), ItemList.getElementNumber(14), ItemList.getElementNumber(8),
                        ItemList.getElementNumber(26), ItemList.getElementNumber(26), ItemList.getElementNumber(26),
                        ItemList.getElementNumber(20), ItemList.getElementNumber(20), ItemList.getElementNumber(20)
                }, new ItemStack(ItemList.andradite, 1)
        );
        addShapedRecipe(
                new Item[]{
                        Items.AIR, Items.AIR, Items.AIR,
                        ItemList.getElementNumber(8), Items.AIR, ItemList.getElementNumber(8),
                        ItemList.getElementNumber(14), ItemList.getElementNumber(40), ItemList.getElementNumber(14)
                }, new ItemStack(ItemList.zircon, 1)
        );
        addShapedRecipe(
                new Item[]{
                        Items.AIR, ItemList.getElementNumber(22), Items.AIR,
                        ItemList.getElementNumber(8), ItemList.getElementNumber(22), ItemList.getElementNumber(8),
                        ItemList.getElementNumber(26), ItemList.getElementNumber(26), ItemList.getElementNumber(26)
                }, new ItemStack(ItemList.ilmenite, 1)
        );
        addShapedRecipe(
                new Item[]{
                        Items.AIR, ItemList.getElementNumber(1), Items.AIR,
                        Items.AIR, ItemList.getElementNumber(6), Items.AIR,
                        ItemList.getElementNumber(1), Items.AIR, ItemList.getElementNumber(1)
                }, new ItemStack(ItemList.methyl_group, 1)
        );
        addShapedRecipe(
                new Item[]{
                        Items.AIR, Items.AIR, Items.AIR,
                        Items.AIR, ItemList.getElementNumber(6), Items.AIR,
                        ItemList.getElementNumber(1), Items.AIR, ItemList.getElementNumber(1)
                }, new ItemStack(ItemList.methylene_group, 1)
        );
        addShapedRecipe(
                new Item[]{
                        Items.AIR, ItemList.getElementNumber(8), Items.AIR,
                        Items.AIR, ItemList.getElementNumber(6), Items.AIR,
                        ItemList.getElementNumber(8), Items.AIR, ItemList.getElementNumber(8)
                }, new ItemStack(ItemList.carbonate, 1)
        );
        addShapedRecipe(
                new Item[]{
                        Items.AIR, Items.AIR, Items.AIR,
                        Items.AIR, ItemList.getElementNumber(6), Items.AIR,
                        ItemList.getElementNumber(8), Items.AIR, ItemList.getElementNumber(8)
                }, new ItemStack(ItemList.carbonite, 1)
        );
        addShapedRecipe(
                new Item[]{
                        Items.AIR, ItemList.getElementNumber(8), Items.AIR,
                        ItemList.methyl_group, ItemList.getElementNumber(6), ItemList.getElementNumber(8),
                        Items.AIR, Items.AIR, Items.AIR
                }, new ItemStack(ItemList.acetate, 1)
        );
        addShapelessRecipe(
                new Item[]{
                        ItemList.alkane_group, ItemList.carbonite, ItemList.getElementNumber(11),
                        ItemList.alkane_group, ItemList.carbonite, ItemList.getElementNumber(11),
                        ItemList.alkane_group, ItemList.carbonite, ItemList.getElementNumber(11)
                }, new ItemStack(ItemList.soap, 2)
        );
    }

    // SHAPELESS RECIPES
    static {
        addShapelessRecipe(
                new Item[]{
                        ItemList.getElementNumber(1), ItemList.getElementNumber(8)
                }, new ItemStack(ItemList.hydroxide, 1)
        );
        addShapelessRecipe(
                new Item[]{
                        ItemList.getElementNumber(1), ItemList.getElementNumber(1), ItemList.getElementNumber(8)
                }, new ItemStack(ItemList.water, 1)
        );
        addShapelessRecipe(
                new Item[]{
                        ItemList.hydroxide, ItemList.getElementNumber(1)
                }, new ItemStack(ItemList.water, 1)
        );
        addShapelessRecipe(
                new Item[]{
                        ItemList.water, ItemList.getElementNumber(1)
                }, new ItemStack(ItemList.hydrogen_peroxide, 1)
        );
        addShapelessRecipe(
                new Item[]{
                        ItemList.getElementNumber(8), ItemList.getElementNumber(8), ItemList.getElementNumber(1), ItemList.getElementNumber(1)
                }, new ItemStack(ItemList.hydrogen_peroxide, 1)
        );
        addShapelessRecipe(
                new Item[]{
                        ItemList.methyl_group, ItemList.methylene_group, ItemList.methylene_group
                }, new ItemStack(ItemList.alkane_group, 1)
        );
        addShapelessRecipe(
                new Item[]{
                        ItemList.getElementNumber(1), ItemList.carbonate
                }, new ItemStack(ItemList.bicarbonate, 1)
        );
        addShapelessRecipe(
                new Item[]{
                        ItemList.getElementNumber(1), ItemList.acetate,
                }, new ItemStack(ItemList.acetic_acid, 1)
        );
        addShapelessRecipe(
                new Item[]{
                        ItemList.getElementNumber(11), ItemList.bicarbonate
                }, new ItemStack(ItemList.sodium_bicarbonate, 1)
        );
        addShapelessRecipe(
                new Item[]{
                        ItemList.getElementNumber(11), ItemList.getElementNumber(17)
                }, new ItemStack(ItemList.sodium_chloride, 1)
        );
    }

    private static void addShapedRecipe(Item[] items, ItemStack itemStack) {
        shapedInputs.add(items);
        shapedOutputs.add(itemStack);
    }

    private static void addShapelessRecipe(Item[] items, ItemStack itemStack) {
        ArrayList<Item> ingredients = new ArrayList<>();
        Collections.addAll(ingredients, items);

        shapelessInputs.add(ingredients);
        shapelessOutputs.add(itemStack);
    }

    public static ItemStack getResultForInputSet(Item[] items) {
        // Check shaped recipes
        for (int i = 0; i < shapedInputs.size(); i++) {
            if (Arrays.equals(shapedInputs.get(i), items)) {
                return shapedOutputs.get(i);
            }
        }
        // Check shapeless recipes
        for (int i = 0; i < shapelessInputs.size(); i++) {
            if (ingredientsAreEqual(items, shapelessInputs.get(i))) {
                return shapelessOutputs.get(i);
            }
        }

        return ItemStack.EMPTY;
    }

    private static boolean ingredientsAreEqual(Item[] items, ArrayList<Item> testIngredients) {
        // Generate ingredients list
        ArrayList<Item> ingredients = new ArrayList<>();
        for(Item i : items) {
            if(i != Items.AIR) ingredients.add(i);
        }

        // Test ingredient list
        for(Item i : testIngredients) {
            if(ingredients.contains(i)) ingredients.remove(i);
            else return false;
        }

        // Return true only if all ingredients used
        return ingredients.isEmpty();
    }
}

