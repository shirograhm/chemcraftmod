package solitudetraveler.chemcraftmod.creativetab;

import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import solitudetraveler.chemcraftmod.item.ItemList;

public class CreativeTabList {
    public static final ItemGroup elementGroup = new ItemGroup("elementGroup") {
        @Override
        public ItemStack createIcon() {
            return new ItemStack(ItemList.getElementNumber(1));
        }
    };

    public static final ItemGroup mineralGroup = new ItemGroup("mineralGroup") {
        @Override
        public ItemStack createIcon() { return new ItemStack(ItemList.calcite); }
    };

    public static final ItemGroup blockGroup = new ItemGroup("blockGroup") {
        @Override
        public ItemStack createIcon() {
            return new ItemStack(ItemList.dolostone);
        }
    };

    public static final ItemGroup compoundGroup = new ItemGroup("compoundGroup") {
        @Override
        public ItemStack createIcon() {
            return new ItemStack(ItemList.hydroxide);
        }
    };

    public static final ItemGroup basicItemGroup = new ItemGroup("basicItemGroup") {
        @Override
        public ItemStack createIcon() {
            return new ItemStack(ItemList.salt);
        }
    };

    public static final ItemGroup equipmentGroup = new ItemGroup("equipmentGroup") {
        @Override
        public ItemStack createIcon() { return new ItemStack(ItemList.gas_mask); }
    };
}
