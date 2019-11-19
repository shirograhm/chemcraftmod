package solitudetraveler.chemcraftmod.handler;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import solitudetraveler.chemcraftmod.item.ItemList;

import java.util.ArrayList;

public class DeconstructorRecipeHandler {

    private static ArrayList<Item> inputs = new ArrayList<>();
    private static ArrayList<ItemStack[]> outputs = new ArrayList<>();

    private static final ItemStack[] EMPTY_OUTPUT = new ItemStack[] {ItemStack.EMPTY, ItemStack.EMPTY, ItemStack.EMPTY, ItemStack.EMPTY, ItemStack.EMPTY, ItemStack.EMPTY};

    static {
        addRecipe(Items.COAL, new ItemStack[] {
                new ItemStack(ItemList.getElementNumber(6), 6),
                new ItemStack(ItemList.hydroxide, 2)
        });
        addRecipe(ItemList.acanthite, new ItemStack[]{
                new ItemStack(ItemList.getElementNumber(49), 6),
                new ItemStack(ItemList.getElementNumber(16), 3)
        });
        addRecipe(ItemList.biotite, new ItemStack[]{
                new ItemStack(ItemList.getElementNumber(19), 1),
                new ItemStack(ItemList.getElementNumber(13), 5),
                new ItemStack(ItemList.getElementNumber(9), 1),
                new ItemStack(ItemList.hydroxide, 2)
        });
        addRecipe(ItemList.sodalite, new ItemStack[]{
                new ItemStack(ItemList.getElementNumber(11), 3),
                new ItemStack(ItemList.getElementNumber(13), 1),
                new ItemStack(ItemList.getElementNumber(14), 1),
                new ItemStack(ItemList.getElementNumber(8), 2),
                new ItemStack(ItemList.getElementNumber(17), 2)
        });
        addRecipe(ItemList.calcite, new ItemStack[] {
                new ItemStack(ItemList.getElementNumber(20), 5),
                new ItemStack(ItemList.getElementNumber(6), 2),
                new ItemStack(ItemList.getElementNumber(8), 2)
        });
        addRecipe(ItemList.aragonite, new ItemStack[] {
                new ItemStack(ItemList.getElementNumber(20), 4),
                new ItemStack(ItemList.getElementNumber(6), 3),
                new ItemStack(ItemList.getElementNumber(30), 2)
        });
        addRecipe(ItemList.fluorite, new ItemStack[] {
                new ItemStack(ItemList.getElementNumber(9), 6),
                new ItemStack(ItemList.getElementNumber(20), 3)
        });
        addRecipe(ItemList.andradite, new ItemStack[] {
                new ItemStack(ItemList.getElementNumber(26), 3),
                new ItemStack(ItemList.getElementNumber(14), 1),
                new ItemStack(ItemList.getElementNumber(8), 2),
                new ItemStack(ItemList.getElementNumber(20), 3)
        });
        addRecipe(ItemList.zircon, new ItemStack[]{
                new ItemStack(ItemList.getElementNumber(40), 3),
                new ItemStack(ItemList.getElementNumber(14), 3),
                new ItemStack(ItemList.getElementNumber(8), 3)
        });
        addRecipe(ItemList.ilmenite, new ItemStack[]{
                new ItemStack(ItemList.getElementNumber(26), 3),
                new ItemStack(ItemList.getElementNumber(22), 3),
                new ItemStack(ItemList.getElementNumber(8), 3)
        });
        addRecipe(Items.WATER_BUCKET, new ItemStack[]{
                new ItemStack(ItemList.water, 6)
        });
        addRecipe(Items.POTION.getDefaultInstance().getItem(), new ItemStack[]{
                new ItemStack(ItemList.water, 2)
        });
        addRecipe(ItemList.copper_ingot, new ItemStack[]{
                new ItemStack(ItemList.getElementNumber(29), 9)
        });
        addRecipe(ItemList.bleach, new ItemStack[]{
                new ItemStack(ItemList.sodium_hydroxide, 9)
        });
        addRecipe(ItemList.vinegar, new ItemStack[]{
                new ItemStack(ItemList.acetic_acid, 9)
        });
        addRecipe(ItemList.baking_soda, new ItemStack[]{
                new ItemStack(ItemList.sodium_bicarbonate, 9)
        });
        addRecipe(ItemList.salt, new ItemStack[]{
                new ItemStack(ItemList.sodium_chloride, 9)
        });
        addRecipe(ItemList.soap, new ItemStack[]{
                new ItemStack(ItemList.getElementNumber(11), 3),
                new ItemStack(ItemList.carbonate, 3),
                new ItemStack(ItemList.alkane_group, 3),
        });
    }

    private static void addRecipe(Item itemIn, ItemStack[] outStacks) {
        ItemStack[] stacks = new ItemStack[6];
        System.arraycopy(outStacks, 0, stacks, 0, outStacks.length);
        if(outStacks.length < 6) {
            System.arraycopy(EMPTY_OUTPUT, 0, stacks, outStacks.length, 6 - outStacks.length);
        }

        inputs.add(itemIn);
        outputs.add(stacks);
    }

    public static boolean isDeconstructible(Item in) {
        return outputNotEmpty(getResultStacksForInput(in));
    }

    public static ItemStack[] getResultStacksForInput(Item in) {
        for (int i = 0; i < inputs.size(); i++) {
            if (inputs.get(i).equals(in)) {
                return outputs.get(i);
            }
        }

        return EMPTY_OUTPUT;
    }

    public static boolean outputNotEmpty(ItemStack[] out) {
        for(ItemStack stack : out) {
            if(!stack.isEmpty()) return true;
        }
        return false;
    }
}
