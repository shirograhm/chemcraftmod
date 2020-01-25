package solitudetraveler.chemcraftmod.handler;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.Tag;
import solitudetraveler.chemcraftmod.item.ItemList;
import solitudetraveler.chemcraftmod.recipes.DeconstructorRecipe;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class DeconstructorRecipeHandler {
    private static ArrayList<DeconstructorRecipe> recipes = new ArrayList<>();

    static {
        // All ingot recipes
        addRecipe(ItemList.copper_ingot,
                new ArrayList<>(Collections.singletonList(
                        new ItemStack(ItemList.getElementNumber(29), 6)))
        );
        addRecipe(Items.IRON_INGOT,
                new ArrayList<>(Collections.singletonList(
                        new ItemStack(ItemList.getElementNumber(26), 6)))
        );
        addRecipe(ItemList.silicon_ingot,
                new ArrayList<>(Collections.singletonList(
                        new ItemStack(ItemList.getElementNumber(14), 6)))
        );
        addRecipe(ItemList.nickel_ingot,
                new ArrayList<>(Collections.singletonList(
                        new ItemStack(ItemList.getElementNumber(28), 6)))
        );
        addRecipe(ItemList.aluminium_ingot,
                new ArrayList<>(Collections.singletonList(
                        new ItemStack(ItemList.getElementNumber(13), 6)))
        );
        addRecipe(ItemList.tin_ingot,
                new ArrayList<>(Collections.singletonList(
                        new ItemStack(ItemList.getElementNumber(50), 6)))
        );
        addRecipe(Items.GOLD_INGOT,
                new ArrayList<>(Collections.singletonList(
                        new ItemStack(ItemList.getElementNumber(79), 6)))
        );
        // Other item decompositions
        addRecipe(ItemList.vinegar,
                new ArrayList<>(Arrays.asList(
                        new ItemStack(ItemList.acetic_acid, 3),
                        new ItemStack(ItemList.water, 3)))
        );
        addRecipe(ItemList.baking_soda,
                new ArrayList<>(Arrays.asList(
                        new ItemStack(ItemList.sodium_bicarbonate, 6)))
        );
        addRecipe(ItemList.salt,
                new ArrayList<>(Arrays.asList(
                        new ItemStack(ItemList.sodium_chloride, 6)))
        );
        addTagRecipe(ItemTags.LOGS,
                new ArrayList<>(Arrays.asList(
                        new ItemStack(ItemList.getElementNumber(6), 27),
                        new ItemStack(ItemList.getElementNumber(8), 23),
                        new ItemStack(ItemList.getElementNumber(1), 3),
                        new ItemStack(ItemList.getElementNumber(7), 1))));
        addTagRecipe(ItemTags.PLANKS,
                new ArrayList<>(Arrays.asList(
                        new ItemStack(ItemList.getElementNumber(6), 27),
                        new ItemStack(ItemList.getElementNumber(8), 23),
                        new ItemStack(ItemList.getElementNumber(1), 3),
                        new ItemStack(ItemList.getElementNumber(7), 1))));
        addTagRecipe(ItemTags.SAND,
                new ArrayList<>(Arrays.asList(
                        new ItemStack(ItemList.getElementNumber(14), 18),
                        new ItemStack(ItemList.getElementNumber(8), 36))));
        addRecipe(Items.DIRT,
                new ArrayList<>(Arrays.asList(
                        new ItemStack(ItemList.getElementNumber(6), 12),
                        new ItemStack(ItemList.getElementNumber(1), 12),
                        new ItemStack(ItemList.getElementNumber(8), 12),
                        new ItemStack(ItemList.getElementNumber(7), 12),
                        new ItemStack(ItemList.getElementNumber(16), 6))));
        addRecipe(Items.COARSE_DIRT,
                new ArrayList<>(Arrays.asList(
                        new ItemStack(ItemList.getElementNumber(6), 12),
                        new ItemStack(ItemList.getElementNumber(1), 12),
                        new ItemStack(ItemList.getElementNumber(8), 12),
                        new ItemStack(ItemList.getElementNumber(7), 12),
                        new ItemStack(ItemList.getElementNumber(16), 6))));
        addRecipe(Items.GRAVEL,
                new ArrayList<>(Arrays.asList(
                        new ItemStack(ItemList.getElementNumber(14), 12),
                        new ItemStack(ItemList.getElementNumber(8), 24),
                        new ItemStack(ItemList.getElementNumber(11), 6),
                        new ItemStack(ItemList.getElementNumber(20), 6),
                        new ItemStack(ItemList.getElementNumber(56), 6))));

    }

    private static void addRecipe(Item itemIn, ArrayList<ItemStack> resultStacks) {
        recipes.add(new DeconstructorRecipe(itemIn, resultStacks));
    }

    private static void addTagRecipe(Tag<Item> tag, ArrayList<ItemStack> outList) {
        for(Item item : tag.getAllElements()) {
            addRecipe(item, outList);
        };
    }

    // Returns recipe with matching input
    // Otherwise returns null
    public static DeconstructorRecipe getRecipeForInputItem(Item input) {
        for(DeconstructorRecipe dr : recipes) {
            if(dr.inputEqual(input)) {
                return dr;
            }
        }

        return null;
    }
}
