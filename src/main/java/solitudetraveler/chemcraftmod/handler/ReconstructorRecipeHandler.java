package solitudetraveler.chemcraftmod.handler;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import solitudetraveler.chemcraftmod.item.ItemList;

import java.util.ArrayList;
import java.util.Arrays;

public class ReconstructorRecipeHandler {

    private static ArrayList<Item[]> inputs = new ArrayList<>();
    private static ArrayList<ItemStack> outputs = new ArrayList<>();

    static {
        addRecipe(
                new Item[]{
                        Items.AIR, ItemList.hydrogen, ItemList.oxygen,
                        ItemList.carbon, ItemList.carbon, ItemList.carbon,
                        ItemList.carbon, ItemList.carbon, ItemList.carbon
                }, new ItemStack(Items.COAL, 1)
        );
        addRecipe(
                new Item[]{
                        ItemList.oxygen, Items.AIR, ItemList.oxygen,
                        ItemList.calcium, ItemList.carbon, ItemList.calcium,
                        ItemList.calcium, ItemList.calcium, ItemList.calcium
                }, new ItemStack(ItemList.calcite, 1)
        );
        addRecipe(
                new Item[]{
                        Items.AIR, Items.AIR, Items.AIR,
                        ItemList.carbon, ItemList.zinc, ItemList.carbon,
                        ItemList.calcium, ItemList.calcium, ItemList.calcium
                }, new ItemStack(ItemList.aragonite, 1)
        );
        addRecipe(
                new Item[]{
                        Items.AIR, ItemList.fluorine, Items.AIR,
                        ItemList.fluorine, ItemList.calcium, ItemList.fluorine,
                        Items.AIR, ItemList.fluorine, Items.AIR,
                }, new ItemStack(ItemList.fluorite, 1)
        );
        addRecipe(
                new Item[]{
                        ItemList.oxygen, ItemList.silicon, ItemList.oxygen,
                        ItemList.iron, ItemList.iron, ItemList.iron,
                        ItemList.calcium, ItemList.calcium, ItemList.calcium
                }, new ItemStack(ItemList.andradite, 1)
        );
        addRecipe(
                new Item[]{
                        Items.AIR, Items.AIR, Items.AIR,
                        ItemList.oxygen, Items.AIR, ItemList.oxygen,
                        ItemList.silicon, ItemList.zirconium, ItemList.silicon
                }, new ItemStack(ItemList.zircon, 1)
        );
        addRecipe(
                new Item[]{
                        Items.AIR, ItemList.titanium, Items.AIR,
                        ItemList.oxygen, ItemList.titanium, ItemList.oxygen,
                        ItemList.iron, ItemList.iron, ItemList.iron
                }, new ItemStack(ItemList.ilmenite, 1)
        );
    }

    private static void addRecipe(Item[] items, ItemStack itemStack) {
        inputs.add(items);
        outputs.add(itemStack);
    }

    public static ItemStack getResultForInputSet(Item[] items) {
        for (int i = 0; i < inputs.size(); i++) {
            if (Arrays.equals(inputs.get(i), items)) {
                return outputs.get(i);
            }
        }

        return ItemStack.EMPTY;
    }
}

