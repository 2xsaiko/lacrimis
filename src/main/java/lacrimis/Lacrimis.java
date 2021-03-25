package lacrimis;

import com.google.common.collect.ImmutableSet;

import lacrimis.blocks.AzothCauldronBlock;
import lacrimis.blocks.AzothConduitBlock;
import lacrimis.blocks.InfusionTableBlock;
import lacrimis.blocks.entity.AzothCauldronBlockEntity;
import lacrimis.blocks.entity.InfusionTableBlockEntity;
import lacrimis.items.DivinationRodItem;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.fabricmc.fabric.api.tool.attribute.v1.FabricToolTags;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.Material;
import net.minecraft.block.MaterialColor;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.DefaultParticleType;
import net.minecraft.util.Identifier;
import net.minecraft.util.Rarity;
import net.minecraft.util.registry.Registry;

public class Lacrimis {
    public static final String MODID = "lacrimis";
    public static final ItemGroup ITEMGROUP = FabricItemGroupBuilder.build(createID("main"), () -> new ItemStack(Blocks.INFUSION_TABLE));

    public static final Identifier createID(String name) {
        return new Identifier(MODID, name);
    }

    public static class Blocks {
        private static final AbstractBlock.Settings IRON = FabricBlockSettings.of(Material.METAL, MaterialColor.STONE).requiresTool().breakByTool(FabricToolTags.PICKAXES).strength(2.0F).nonOpaque();

        public static final Block AZOTH_CAULDRON = register("cauldron", new AzothCauldronBlock(FabricBlockSettings.copyOf(IRON).dropsLike(net.minecraft.block.Blocks.CAULDRON)));
        public static final Block INFUSION_TABLE = register("infusion_table", new InfusionTableBlock(IRON));
        public static final Block AZOTH_CONDUIT = register("conduit", new AzothConduitBlock(IRON));

        private static Block register(String name, Block entry) {
            return Registry.register(Registry.BLOCK, Lacrimis.createID(name), entry);
        }
    }

    public static class BlockEntityTypes {
        public static final BlockEntityType<AzothCauldronBlockEntity> AZOTH_CAULDRON = register("cauldron", new BlockEntityType<>(AzothCauldronBlockEntity::new, ImmutableSet.of(Blocks.AZOTH_CAULDRON), null));
        public static final BlockEntityType<InfusionTableBlockEntity> INFUSION_TABLE = register("infusion_table", new BlockEntityType<>(InfusionTableBlockEntity::new, ImmutableSet.of(Blocks.INFUSION_TABLE), null));

        private static <T extends BlockEntity> BlockEntityType<T> register(String name, BlockEntityType<T> entry) {
            return Registry.register(Registry.BLOCK_ENTITY_TYPE, Lacrimis.createID(name), entry);
        }
    }

    public static class Items {
        public static final Item AZOTH_CAULDRON = register("cauldron", new BlockItem(Blocks.AZOTH_CAULDRON, createSettings().group(null)));
        public static final Item INFUSION_TABLE = register("infusion_table", new BlockItem(Blocks.INFUSION_TABLE, createSettings()));
        public static final Item AZOTH_CONDUIT = register("conduit", new BlockItem(Blocks.AZOTH_CONDUIT, createSettings()));

        public static final Item DIVINATION_ROD = register("divination_rod", new DivinationRodItem(createSettings().maxDamage(256).rarity(Rarity.UNCOMMON)));

        private static Item.Settings createSettings() {
            return new Item.Settings().group(Lacrimis.ITEMGROUP);
        }

        private static Item register(String name, Item entry) {
            return Registry.register(Registry.ITEM, Lacrimis.createID(name), entry);
        }
    }

    public static class ParticleTypes {
        public static final DefaultParticleType AZOTH_MIST = register("azoth_mist", new AzothParticleType(true));

        private static DefaultParticleType register(String name, DefaultParticleType entry) {
            return Registry.register(Registry.PARTICLE_TYPE, Lacrimis.createID(name), entry);
        }

        private static final class AzothParticleType extends DefaultParticleType {
            private AzothParticleType(boolean alwaysSpawn) {
                super(alwaysSpawn);
            }
        }
    }
}