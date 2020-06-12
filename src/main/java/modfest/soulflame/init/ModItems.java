package modfest.soulflame.init;

import modfest.soulflame.SoulFlame;
import modfest.soulflame.item.BottleOfTearsItem;
import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class ModItems {
	private static final Item.Settings SETTINGS = new Item.Settings().group(SoulFlame.ITEM_GROUP);

    public static BlockItem infusionTable;
    public static BlockItem crucible;
    public static BlockItem conduit;
	public static final Item BOTTLE_OF_TEARS = new BottleOfTearsItem(SETTINGS);

    public static void register() {
        infusionTable = register("infusion_table", ModBlocks.infusionTable);
        crucible = register("crucible", ModBlocks.crucible);
        conduit = register("conduit", ModBlocks.conduit);

		register("bottle_of_tears", BOTTLE_OF_TEARS);
	}

	private static <T extends Item> T register(String name, T item) {
		return Registry.register(Registry.ITEM, new Identifier(SoulFlame.MODID, name), item);
	}

	private static BlockItem register(String name, Block block) {
		return register(name, new BlockItem(block, SETTINGS));
	}

}
