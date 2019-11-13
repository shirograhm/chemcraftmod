package solitudetraveler.chemcraftmod.handler;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import solitudetraveler.chemcraftmod.item.ItemList;

import java.util.ArrayList;

public class DeconstructorRecipeHandler {

    private static ArrayList<Item> inputs = new ArrayList<>();
    private static ArrayList<ItemStack[]> outputs = new ArrayList<>();

    public static ItemStack[] emptyOutput = new ItemStack[] {ItemStack.EMPTY, ItemStack.EMPTY, ItemStack.EMPTY, ItemStack.EMPTY, ItemStack.EMPTY, ItemStack.EMPTY};

    static {
        addRecipe(Items.COAL, new ItemStack[] {
                new ItemStack(ItemList.getElementNumber(6), 6),
                new ItemStack(ItemList.getElementNumber(8), 1),
                new ItemStack(ItemList.getElementNumber(1), 1),
                ItemStack.EMPTY,
                ItemStack.EMPTY,
                ItemStack.EMPTY
        });
        addRecipe(ItemList.calcite, new ItemStack[] {
                new ItemStack(ItemList.getElementNumber(20), 5),
                new ItemStack(ItemList.getElementNumber(6), 1),
                new ItemStack(ItemList.getElementNumber(8), 2),
                ItemStack.EMPTY,
                ItemStack.EMPTY,
                ItemStack.EMPTY
        });
        addRecipe(ItemList.aragonite, new ItemStack[] {
                new ItemStack(ItemList.getElementNumber(20), 3),
                new ItemStack(ItemList.getElementNumber(6), 2),
                new ItemStack(ItemList.getElementNumber(30), 1),
                ItemStack.EMPTY,
                ItemStack.EMPTY,
                ItemStack.EMPTY
        });

        addRecipe(ItemList.fluorite, new ItemStack[] {
                new ItemStack(ItemList.getElementNumber(9), 4),
                new ItemStack(ItemList.getElementNumber(20), 1),
                ItemStack.EMPTY,
                ItemStack.EMPTY,
                ItemStack.EMPTY,
                ItemStack.EMPTY,
        });
        addRecipe(ItemList.andradite, new ItemStack[] {
                new ItemStack(ItemList.getElementNumber(26), 3),
                new ItemStack(ItemList.getElementNumber(14), 1),
                new ItemStack(ItemList.getElementNumber(8), 2),
                new ItemStack(ItemList.getElementNumber(20), 3),
                ItemStack.EMPTY,
                ItemStack.EMPTY
        });
        addRecipe(ItemList.zircon, new ItemStack[]{
                new ItemStack(ItemList.getElementNumber(40), 1),
                new ItemStack(ItemList.getElementNumber(14), 2),
                new ItemStack(ItemList.getElementNumber(8), 2),
                ItemStack.EMPTY,
                ItemStack.EMPTY,
                ItemStack.EMPTY
        });
        addRecipe(ItemList.ilmenite, new ItemStack[]{
                new ItemStack(ItemList.getElementNumber(26), 3),
                new ItemStack(ItemList.getElementNumber(22), 2),
                new ItemStack(ItemList.getElementNumber(8), 2),
                ItemStack.EMPTY,
                ItemStack.EMPTY,
                ItemStack.EMPTY
        });
    }

    private static void addRecipe(Item itemIn, ItemStack[] outStacks) {
        inputs.add(itemIn);
        outputs.add(outStacks);
    }

    public static ItemStack[] getResultStacksForInput(Item in) {
        for (int i = 0; i < inputs.size(); i++) {
            if (inputs.get(i).equals(in)) {
                return outputs.get(i);
            }
        }

        return emptyOutput;
    }

    public static boolean isValidFuelItem(Item item) {
        return getFuelTimeForItem(item) > 0;
    }

    public static int getFuelTimeForItem(Item item) {
        if(item == Items.REDSTONE) return 100;
        if(item == Items.REDSTONE_BLOCK) return 900;

        return 0;
    }

    public static boolean outputNotEmpty(ItemStack[] out) {
        for(ItemStack is : out) {
            if(!is.isEmpty()) return true;
        }

        return false;
    }

    public static boolean outputsAreEqual(ItemStack[] currentOutStacks, ItemStack[] out) {
        for(int i = 0; i < 6; i++) {
            if(currentOutStacks[i] != out[i]) {
                return false;
            }
        }

        return true;
    }
}
