package solitudetraveler.chemcraftmod.handler;

import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import solitudetraveler.chemcraftmod.item.ElementInfo;
import solitudetraveler.chemcraftmod.item.ItemList;
import solitudetraveler.chemcraftmod.recipes.ReconstructorRecipe;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class ReconstructorRecipeHandler {
    private static ArrayList<ReconstructorRecipe> recipes = new ArrayList<>();

    static {
        // Ingot recipes
        addRecipe(new ArrayList<>(Collections.singletonList(
                new ItemStack(ItemList.getElementNumber(26), 54))),

                new ItemStack(Items.IRON_INGOT)
        );
        addRecipe(new ArrayList<>(Collections.singletonList(
                new ItemStack(ItemList.getElementNumber(79), 54))),

                new ItemStack(Items.GOLD_INGOT)
        );
        addRecipe(new ArrayList<>(Collections.singletonList(
                new ItemStack(ItemList.getElementNumber(29), 54))),

                new ItemStack(ItemList.copper_ingot)
        );
        addRecipe(new ArrayList<>(Collections.singletonList(
                new ItemStack(ItemList.getElementNumber(14), 54))),

                new ItemStack(ItemList.silicon_ingot)
        );
        addRecipe(new ArrayList<>(Collections.singletonList(
                new ItemStack(ItemList.getElementNumber(28), 54))),

                new ItemStack(ItemList.nickel_ingot)
        );
        addRecipe(new ArrayList<>(Collections.singletonList(
                new ItemStack(ItemList.getElementNumber(13), 54))),

                new ItemStack(ItemList.aluminium_ingot)
        );
        addRecipe(new ArrayList<>(Collections.singletonList(
                new ItemStack(ItemList.getElementNumber(50), 54))),

                new ItemStack(ItemList.tin_ingot)
        );
        addRecipe(new ArrayList<>(Collections.singletonList(
                new ItemStack(ItemList.getElementNumber(78), 54))),

                new ItemStack(ItemList.platinum_ingot)
        );
        addRecipe(new ArrayList<>(Collections.singletonList(
                new ItemStack(ItemList.getElementNumber(47), 54))),

                new ItemStack(ItemList.silver_ingot)
        );
        addRecipe(new ArrayList<>(Collections.singletonList(
                new ItemStack(ItemList.getElementNumber(24), 54))),

                new ItemStack(ItemList.chromium_ingot)
        );
        addRecipe(new ArrayList<>(Collections.singletonList(
                new ItemStack(ItemList.getElementNumber(82), 54))),

                new ItemStack(ItemList.lead_ingot)
        );

        // Covalent compounds
        addRecipe(new ArrayList<>(Arrays.asList(
                new ItemStack(ItemList.getElementNumber(1), 2),
                new ItemStack(ItemList.getElementNumber(8)))),

                new ItemStack(ItemList.water)
        );
        addRecipe(new ArrayList<>(Arrays.asList(
                new ItemStack(ItemList.getElementNumber(1), 2),
                new ItemStack(ItemList.getElementNumber(8), 2))),

                new ItemStack(ItemList.hydrogen_peroxide)
        );
        addRecipe(new ArrayList<>(Arrays.asList(
                new ItemStack(ItemList.getElementNumber(6), 2),
                new ItemStack(ItemList.getElementNumber(1), 3),
                new ItemStack(ItemList.getElementNumber(8), 2))),

                new ItemStack(ItemList.acetate)
        );
        addRecipe(new ArrayList<>(Arrays.asList(
                new ItemStack(ItemList.getElementNumber(16)),
                new ItemStack(ItemList.getElementNumber(8), 4))),

                new ItemStack(ItemList.sulfate)
        );
        addRecipe(new ArrayList<>(Arrays.asList(
                new ItemStack(ItemList.getElementNumber(16)),
                new ItemStack(ItemList.getElementNumber(8), 3))),

                new ItemStack(ItemList.sulfite)
        );
        addRecipe(new ArrayList<>(Arrays.asList(
                new ItemStack(ItemList.getElementNumber(7)),
                new ItemStack(ItemList.getElementNumber(8), 3))),

                new ItemStack(ItemList.nitrate)
        );
        addRecipe(new ArrayList<>(Arrays.asList(
                new ItemStack(ItemList.getElementNumber(7)),
                new ItemStack(ItemList.getElementNumber(8), 2))),

                new ItemStack(ItemList.nitrite)
        );
        addRecipe(new ArrayList<>(Arrays.asList(
                new ItemStack(ItemList.getElementNumber(6)),
                new ItemStack(ItemList.getElementNumber(8), 3))),

                new ItemStack(ItemList.carbonate)
        );
        addRecipe(new ArrayList<>(Arrays.asList(
                new ItemStack(ItemList.getElementNumber(1)),
                new ItemStack(ItemList.getElementNumber(6)),
                new ItemStack(ItemList.getElementNumber(8), 3))),

                new ItemStack(ItemList.bicarbonate)
        );
        addRecipe(new ArrayList<>(Arrays.asList(
                new ItemStack(ItemList.getElementNumber(8)),
                new ItemStack(ItemList.getElementNumber(1)))),

                new ItemStack(ItemList.hydroxide)
        );
        addRecipe(new ArrayList<>(Arrays.asList(
                new ItemStack(ItemList.getElementNumber(6)),
                new ItemStack(ItemList.getElementNumber(1), 3))),

                new ItemStack(ItemList.methyl_group)
        );
        addRecipe(new ArrayList<>(Arrays.asList(
                new ItemStack(ItemList.getElementNumber(6)),
                new ItemStack(ItemList.getElementNumber(1), 2))),

                new ItemStack(ItemList.methylene_group)
        );
        addRecipe(new ArrayList<>(Arrays.asList(
                new ItemStack(ItemList.getElementNumber(6), 3),
                new ItemStack(ItemList.getElementNumber(1), 8))),

                new ItemStack(ItemList.propane)
        );
        addRecipe(new ArrayList<>(Arrays.asList(
                new ItemStack(ItemList.methyl_group),
                new ItemStack(ItemList.methylene_group),
                new ItemStack(ItemList.methyl_group))),

                new ItemStack(ItemList.propane)
        );


        // Ionic compounds
        addRecipe(new ArrayList<>(Arrays.asList(
                new ItemStack(ItemList.getElementNumber(30)),
                new ItemStack(ItemList.getElementNumber(8)))),

                new ItemStack(ItemList.zinc_oxide)
        );
        addRecipe(new ArrayList<>(Arrays.asList(
                new ItemStack(ItemList.getElementNumber(11)),
                new ItemStack(ItemList.getElementNumber(17)))),

                new ItemStack(ItemList.sodium_chloride)
        );
        addRecipe(new ArrayList<>(Arrays.asList(
                new ItemStack(ItemList.getElementNumber(11)),
                new ItemStack(ItemList.getElementNumber(1)),
                new ItemStack(ItemList.getElementNumber(6)),
                new ItemStack(ItemList.getElementNumber(8), 3))),

                new ItemStack(ItemList.sodium_bicarbonate)
        );
        addRecipe(new ArrayList<>(Arrays.asList(
                new ItemStack(ItemList.getElementNumber(11)),
                new ItemStack(ItemList.getElementNumber(8)),
                new ItemStack(ItemList.getElementNumber(1)))),

                new ItemStack(ItemList.sodium_hydroxide)
        );
        addRecipe(new ArrayList<>(Arrays.asList(
                new ItemStack(ItemList.getElementNumber(6), 2),
                new ItemStack(ItemList.getElementNumber(1), 4),
                new ItemStack(ItemList.getElementNumber(8), 2))),

                new ItemStack(ItemList.acetic_acid)
        );
        addRecipe(new ArrayList<>(Arrays.asList(
                new ItemStack(ItemList.getElementNumber(47), 2),
                new ItemStack(ItemList.getElementNumber(16)))),

                new ItemStack(ItemList.silver_sulfide)
        );
        // Extra recipes
        addRecipe(new ArrayList<>(Arrays.asList(
                new ItemStack(ItemList.getElementNumber(11)),
                new ItemStack(ItemList.bicarbonate))),

                new ItemStack(ItemList.sodium_bicarbonate)
        );
        addRecipe(new ArrayList<>(Arrays.asList(
                new ItemStack(ItemList.getElementNumber(11)),
                new ItemStack(ItemList.hydroxide))),

                new ItemStack(ItemList.sodium_hydroxide)
        );
        addRecipe(new ArrayList<>(Arrays.asList(
                new ItemStack(ItemList.getElementNumber(1)),
                new ItemStack(ItemList.acetate))),

                new ItemStack(ItemList.acetic_acid)
        );


        // Element recipes from atomic particles
        for(int i = 1; i <= ElementInfo.getCount(); i++) {
            int neutronCount = ElementInfo.getNeutronCount(i);

            if (i <= 64 && neutronCount <= 64) {
                addRecipe(new ArrayList<>(Arrays.asList(
                        new ItemStack(ItemList.proton, i),
                        new ItemStack(ItemList.electron, i),
                        new ItemStack(ItemList.neutron, ElementInfo.getNeutronCount(i)))),

                        new ItemStack(ItemList.getElementNumber(i))
                );
            } else if (i <= 64 && neutronCount <= 128) {
                addRecipe(new ArrayList<>(Arrays.asList(
                        new ItemStack(ItemList.proton, i),
                        new ItemStack(ItemList.electron, i),
                        new ItemStack(ItemList.neutron, 64),
                        new ItemStack(ItemList.neutron, ElementInfo.getNeutronCount(i) - 64))),

                        new ItemStack(ItemList.getElementNumber(i))
                );
            } else if (i <= 128 && neutronCount <= 128) {
                addRecipe(new ArrayList<>(Arrays.asList(
                        new ItemStack(ItemList.proton, 64),
                        new ItemStack(ItemList.proton, i - 64),
                        new ItemStack(ItemList.electron, 64),
                        new ItemStack(ItemList.electron, i - 64),
                        new ItemStack(ItemList.neutron, 64),
                        new ItemStack(ItemList.neutron, ElementInfo.getNeutronCount(i) - 64))),

                        new ItemStack(ItemList.getElementNumber(i))
                );
            } else if (i <= 128 && neutronCount <= 196) {
                addRecipe(new ArrayList<>(Arrays.asList(
                        new ItemStack(ItemList.proton, 64),
                        new ItemStack(ItemList.proton, i - 64),
                        new ItemStack(ItemList.electron, 64),
                        new ItemStack(ItemList.electron, i - 64),
                        new ItemStack(ItemList.neutron, 64),
                        new ItemStack(ItemList.neutron, 64),
                        new ItemStack(ItemList.neutron, ElementInfo.getNeutronCount(i) - 128))),

                        new ItemStack(ItemList.getElementNumber(i))
                );
            } else {
                // NOT A VALID ELEMENT, thus we dont have any recipe added here.
            }
        }
    }

    private static void addRecipe(ArrayList<ItemStack> stacks, ItemStack result) {
        recipes.add(new ReconstructorRecipe(stacks, result));
    }

    // Returns recipe with matching input
    // Otherwise returns null
    public static ReconstructorRecipe getRecipeForInputs(ArrayList<ItemStack> input) {
        for (ReconstructorRecipe rr : recipes) {
            if (rr.ingredientsEqual(input)) {
                return rr;
            }
        }

        return null;
    }
}