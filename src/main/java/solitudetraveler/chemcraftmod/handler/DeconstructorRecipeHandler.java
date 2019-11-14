package solitudetraveler.chemcraftmod.handler;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import solitudetraveler.chemcraftmod.item.ItemList;

import java.util.ArrayList;

public class DeconstructorRecipeHandler {

    private static ArrayList<Item> inputs = new ArrayList<>();
    private static ArrayList<ItemStack[]> outputs = new ArrayList<>();

    private static final ItemStack[] emptyOutput = new ItemStack[] {ItemStack.EMPTY, ItemStack.EMPTY, ItemStack.EMPTY, ItemStack.EMPTY, ItemStack.EMPTY, ItemStack.EMPTY};

    static {
        addRecipe(Items.COAL, new ItemStack[] {
                new ItemStack(ItemList.getElementNumber(6), 6),
                new ItemStack(ItemList.getElementNumber(8), 1),
                new ItemStack(ItemList.getElementNumber(1), 1)
        });
        addRecipe(ItemList.calcite, new ItemStack[] {
                new ItemStack(ItemList.getElementNumber(20), 5),
                new ItemStack(ItemList.getElementNumber(6), 1),
                new ItemStack(ItemList.getElementNumber(8), 2)
        });
        addRecipe(ItemList.aragonite, new ItemStack[] {
                new ItemStack(ItemList.getElementNumber(20), 3),
                new ItemStack(ItemList.getElementNumber(6), 2),
                new ItemStack(ItemList.getElementNumber(30), 1)
        });

        addRecipe(ItemList.fluorite, new ItemStack[] {
                new ItemStack(ItemList.getElementNumber(9), 4),
                new ItemStack(ItemList.getElementNumber(20), 1)
        });
        addRecipe(ItemList.andradite, new ItemStack[] {
                new ItemStack(ItemList.getElementNumber(26), 3),
                new ItemStack(ItemList.getElementNumber(14), 1),
                new ItemStack(ItemList.getElementNumber(8), 2),
                new ItemStack(ItemList.getElementNumber(20), 3)
        });
        addRecipe(ItemList.zircon, new ItemStack[]{
                new ItemStack(ItemList.getElementNumber(40), 1),
                new ItemStack(ItemList.getElementNumber(14), 2),
                new ItemStack(ItemList.getElementNumber(8), 2)
        });
        addRecipe(ItemList.ilmenite, new ItemStack[]{
                new ItemStack(ItemList.getElementNumber(26), 3),
                new ItemStack(ItemList.getElementNumber(22), 2),
                new ItemStack(ItemList.getElementNumber(8), 2)
        });
        addRecipe(Items.WATER_BUCKET, new ItemStack[]{
                new ItemStack(ItemList.water, 6)
        });
        addRecipe(Items.POTION.getDefaultInstance().getItem(), new ItemStack[]{
                new ItemStack(ItemList.water, 2)
        });
    }

    private static void addRecipe(Item itemIn, ItemStack[] outStacks) {
        ItemStack[] stacks = new ItemStack[6];
        System.arraycopy(outStacks, 0, stacks, 0, outStacks.length);
        if(outStacks.length < 6) {
            System.arraycopy(emptyOutput, 0, stacks, outStacks.length, 6 - outStacks.length);
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

        return emptyOutput;
    }

    public static boolean outputNotEmpty(ItemStack[] out) {
        for(ItemStack is : out) {
            if(!is.isEmpty()) return true;
        }

        return false;
    }
}
