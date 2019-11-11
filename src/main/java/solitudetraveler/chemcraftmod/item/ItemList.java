package solitudetraveler.chemcraftmod.item;

import net.minecraft.item.Item;

public class ItemList {
    // Blocks
    public static Item dolostone;
    public static Item reconstructor;
    public static Item deconstructor;
    public static Item volcano;
    // Minerals
    public static Item aragonite;
    public static Item calcite;
    public static Item sodalite;
    public static Item fluorite;
    public static Item andradite;
    public static Item zircon;
    public static Item ilmenite;
    // Items
    public static Item diamond_dust;
    public static Item gold_dust;
    // Covalent Compounds
    public static Item sulfate;
    public static Item sulfite;
    public static Item nitrate;
    public static Item nitrite;
    public static Item carbonate;
    public static Item bicarbonate;
    public static Item hydroxide;
    public static Item acetate;
    // Ionic Compounds
    public static Item zinc_oxide;
    public static Item sodium_chloride;
    public static Item sodium_bicarbonate;
    public static Item acetic_acid;
    // Elements
    public static Item proton;
    public static Item neutron;
    public static Item electron;
    public static Item[] elementList = new Item[118];

    public static Item getElementNumber(int n) {
        return elementList[n - 1];
    }
}
