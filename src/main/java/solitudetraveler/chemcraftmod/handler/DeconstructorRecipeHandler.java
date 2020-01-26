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
                        new ItemStack(ItemList.getElementNumber(7), 16),
                        new ItemStack(ItemList.getElementNumber(6), 12),
                        new ItemStack(ItemList.getElementNumber(1), 12),
                        new ItemStack(ItemList.getElementNumber(8), 8),
                        new ItemStack(ItemList.getElementNumber(16), 6))));
        addRecipe(Items.COARSE_DIRT,
                new ArrayList<>(Arrays.asList(
                        new ItemStack(ItemList.getElementNumber(7), 16),
                        new ItemStack(ItemList.getElementNumber(6), 10),
                        new ItemStack(ItemList.getElementNumber(1), 10),
                        new ItemStack(ItemList.getElementNumber(8), 8),
                        new ItemStack(ItemList.getElementNumber(16), 10))));
        addRecipe(Items.GRAVEL,
                new ArrayList<>(Arrays.asList(
                        new ItemStack(ItemList.getElementNumber(14), 12),
                        new ItemStack(ItemList.getElementNumber(8), 24),
                        new ItemStack(ItemList.getElementNumber(11), 6),
                        new ItemStack(ItemList.getElementNumber(20), 6),
                        new ItemStack(ItemList.getElementNumber(56), 6))));
        // Mineral decompositions
        addRecipe(ItemList.aragonite,
                new ArrayList<>(Arrays.asList(
                        new ItemStack(ItemList.getElementNumber(20), 12),
                        new ItemStack(ItemList.getElementNumber(8), 36),
                        new ItemStack(ItemList.getElementNumber(11), 2),
                        new ItemStack(ItemList.getElementNumber(12), 2),
                        new ItemStack(ItemList.getElementNumber(38), 2))));
        addRecipe(ItemList.calcite,
                new ArrayList<>(Arrays.asList(
                        new ItemStack(ItemList.getElementNumber(20), 13),
                        new ItemStack(ItemList.getElementNumber(8), 39),
                        new ItemStack(ItemList.getElementNumber(26), 1),
                        new ItemStack(ItemList.getElementNumber(38), 1))));
        addRecipe(ItemList.sodalite,
                new ArrayList<>(Arrays.asList(
                        new ItemStack(ItemList.getElementNumber(1), 12),
                        new ItemStack(ItemList.getElementNumber(13), 8),
                        new ItemStack(ItemList.getElementNumber(14), 8),
                        new ItemStack(ItemList.getElementNumber(8), 24),
                        new ItemStack(ItemList.getElementNumber(17), 2))));
        addRecipe(ItemList.fluorite,
                new ArrayList<>(Arrays.asList(
                        new ItemStack(ItemList.getElementNumber(20), 18),
                        new ItemStack(ItemList.getElementNumber(9), 36))));
        addRecipe(ItemList.andradite,
                new ArrayList<>(Arrays.asList(
                        new ItemStack(ItemList.getElementNumber(20), 8),
                        new ItemStack(ItemList.getElementNumber(26), 6),
                        new ItemStack(ItemList.getElementNumber(14), 8),
                        new ItemStack(ItemList.getElementNumber(8), 32))));
        addRecipe(ItemList.zircon,
                new ArrayList<>(Arrays.asList(
                        new ItemStack(ItemList.getElementNumber(40), 9),
                        new ItemStack(ItemList.getElementNumber(14), 9),
                        new ItemStack(ItemList.getElementNumber(8), 36))));
        addRecipe(ItemList.ilmenite,
                new ArrayList<>(Arrays.asList(
                        new ItemStack(ItemList.getElementNumber(26), 9),
                        new ItemStack(ItemList.getElementNumber(22), 9),
                        new ItemStack(ItemList.getElementNumber(8), 36))));
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
