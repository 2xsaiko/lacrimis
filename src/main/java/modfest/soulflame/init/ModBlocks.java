package modfest.soulflame.init;

import modfest.soulflame.block.*;
import modfest.soulflame.block.ConduitBlock;
import modfest.soulflame.block.rune.*;
import net.minecraft.block.*;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;

import modfest.soulflame.SoulFlame;

public class ModBlocks {
    public static final Block.Settings runeSettings = FabricBlockSettings.copy(Blocks.STONE).nonOpaque();

    //Main blocks
    public static InfusionTableBlock infusionTable;
    public static CrucibleBlock crucible;
    public static ConduitBlock conduit;
    public static TearLantern tearLantern;
    public static CreativeTearsBlock creativeTearsBlock;

    //Rune cage blocks
    public static RuneBlock rune1;
    public static RuneBlock rune2;
    public static PipeConnectorBlock pipeRune;
    public static HealBlock healRune;
    public static SoulExtractionBlock extractionRune;
    public static SoulTeleportBlock destinationRune;
    public static SoulTeleportBlock transportRune;

    public static void register() {
        infusionTable = register("infusion_table", new InfusionTableBlock(FabricBlockSettings.copy(Blocks.CRAFTING_TABLE).nonOpaque()));
        crucible = register("crucible", new CrucibleBlock(FabricBlockSettings.copy(Blocks.CAULDRON).nonOpaque()));
        conduit = register("conduit", new ConduitBlock(AbstractBlock.Settings.of(Material.STONE).strength(0.25f)));
        tearLantern = register("tear_lantern", new TearLantern(AbstractBlock.Settings.of(Material.METAL).requiresTool().strength(3.5F).sounds(BlockSoundGroup.LANTERN).lightLevel((state) -> 5).nonOpaque()));
        creativeTearsBlock = register("creative_tears_block", new CreativeTearsBlock(FabricBlockSettings.copy(Blocks.STONE)));

        rune1 = register("rune/tier1", new RuneBlock(1));
        rune2 = register("rune/tier2", new RuneBlock(2));
        pipeRune = register("rune/pipe1", new PipeConnectorBlock(2));
        healRune = register("rune/heal", new HealBlock());
        extractionRune = register("rune/extraction", new SoulExtractionBlock());
        destinationRune = register("rune/destination", new SoulTeleportBlock(false));
        transportRune = register("rune/transport", new SoulTeleportBlock(true));
    }

    private static <T extends Block> T register(String name, T block) {
        return Registry.register(Registry.BLOCK, new Identifier(SoulFlame.MODID, name), block);
    }

}
