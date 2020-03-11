package solitudetraveler.chemcraftmod.item;

import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;

import java.util.List;

public class ElementInfo {
    private static final String[] ELEMENT_NAMES = new String[] {
            "hydrogen", "helium", "lithium", "beryllium", "boron", "carbon", "nitrogen",
            "oxygen", "fluorine", "neon", "sodium", "magnesium", "aluminium", "silicon",
            "phosphorus", "sulphur", "chlorine", "argon", "potassium", "calcium", "scandium",
            "titanium", "vanadium", "chromium", "manganese", "iron", "cobalt", "nickel",
            "copper", "zinc", "gallium", "germanium", "arsenic", "selenium", "bromine", "krypton",
            "rubidium", "strontium", "yttrium", "zirconium", "niobium", "molybdenum", "technetium", "ruthenium",
            "rhodium", "palladium", "silver", "cadmium", "indium", "tin", "antimony", "tellurium", "iodine",
            "xenon", "cesium", "barium", "lanthanum", "cerium", "praseodymium", "neodymium", "promethium",
            "samarium", "europium", "gadolinium", "terbium", "dysprosium", "holmium", "erbium", "thulium", "ytterbium",
            "lutetium", "hafnium", "tantalum", "tungsten", "rhenium", "osmium", "iridium", "platinum", "gold", "mercury",
            "thallium", "lead", "bismuth", "polonium", "astatine", "radon", "francium", "radium", "actinium", "thorium",
            "protactinium", "uranium", "neptunium", "plutonium", "americium", "curium", "berkelium", "californium",
            "einsteinium", "fermium", "mendelevium", "nobelium", "lawrencium", "rutherfordium", "dubnium", "seaborgium",
            "bohrium", "hassium", "meitnerium", "darmstadtium", "roentgenium", "copernicium", "nihonium", "flerovium",
            "moscovium", "livermorium", "tennessine", "oganesson"
    };

    private static final int[] NEUTRON_COUNTS = new int[] {
            0, 2, 4, 5, 6, 6, 7, 8, 10, 10, 12, 12, 14, 14, 16, 16, 18, 22, 21, 20, 24, 26, 28, 28, 30, 30, 31, 30, 35,
            35, 39, 41, 42, 45, 45, 48, 48, 50, 50, 51, 52, 54, 55, 57, 58, 60, 61, 64, 66, 69, 71, 76, 74, 77, 78, 81,
            82, 82, 82, 84, 88, 89, 93, 94, 97, 98, 99, 100, 103, 104, 106, 108, 110, 111, 114, 115, 116, 118, 121, 123,
            125, 126, 125, 125, 136, 136, 138, 138, 142, 140, 146, 144, 150, 148, 151, 150, 153, 153, 157, 157, 157, 159,
            157, 163, 157, 157, 161, 159, 162, 162, 165, 173, 175, 173, 176, 175, 175, 176
    };

    public static String getName(int a) {
        if(a < 1 || a > 118) throw new IndexOutOfBoundsException();
        else return ELEMENT_NAMES[a - 1];
    }

    public static int getCount() {
        return ELEMENT_NAMES.length;
    }

    public static void appendToolTip(List<ITextComponent> tiplist, int atomicNumber) {
        if (atomicNumber > 0) {
            tiplist.add(new StringTextComponent("Element number " + atomicNumber + "."));
        }
    }

    public static int getNeutronCount(int a) {
        return NEUTRON_COUNTS[a - 1];
    }
}
