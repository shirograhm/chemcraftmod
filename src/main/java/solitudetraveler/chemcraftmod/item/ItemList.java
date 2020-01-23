package solitudetraveler.chemcraftmod.item;

import net.minecraft.item.Item;

public class ItemList {
    // Blocks
    public static Item dolostone;
    public static Item copper_ore;
    public static Item nickel_ore;
    public static Item generator;
    public static Item reconstructor;
    public static Item deconstructor;
    public static Item electromagnet;
    public static Item particle_accelerator;
    public static Item volcano;
    // Minerals
    public static Item aragonite;
    public static Item calcite;
    public static Item sodalite;
    public static Item fluorite;
    public static Item andradite;
    public static Item zircon;
    public static Item ilmenite;
    public static Item biotite;
    public static Item acanthite;
    // New Ingots
    public static Item copper_ingot;
    public static Item silicon_ingot;
    public static Item aluminium_ingot;
    public static Item tin_ingot;
    public static Item nickel_ingot;
    // New Items
    public static Item water;
    public static Item salt;
    public static Item soap;
    public static Item baking_soda;
    public static Item vinegar;
    public static Item bleach;
    public static Item electromagnetic_coil;
    public static Item heating_coil;
    public static Item copper_wire;
    public static Item machine_core;
    public static Item advanced_machine_core;
    // Covalent Compounds
    public static Item sulfate;
    public static Item sulfite;
    public static Item nitrate;
    public static Item nitrite;
    public static Item carbonate;
    public static Item bicarbonate;
    public static Item hydroxide;
    public static Item acetate;
    public static Item methyl_group;
    public static Item methylene_group;
    public static Item propane;
    public static Item hydrogen_peroxide;
    // Ionic Compounds
    public static Item zinc_oxide;
    public static Item sodium_chloride;
    public static Item sodium_bicarbonate;
    public static Item sodium_hydroxide;
    public static Item acetic_acid;
    public static Item silver_sulfide;
    // Elements
    public static Item proton;
    public static Item neutron;
    public static Item electron;
    public static Item[] elementList = new Item[118];

    public static Item getElementNumber(int n) {
        return elementList[n - 1];
    }
}
