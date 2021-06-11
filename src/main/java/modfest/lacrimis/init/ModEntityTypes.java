package modfest.lacrimis.init;

import modfest.lacrimis.block.entity.*;
import modfest.lacrimis.client.render.block.CrucibleEntityRenderer;
import modfest.lacrimis.client.render.block.InfusionTableEntityRenderer;
import modfest.lacrimis.client.render.block.NetworkLinkEntityRenderer;
import modfest.lacrimis.client.render.entity.GhostEntityRenderer;
import modfest.lacrimis.client.render.entity.SoulShellRenderer;
import modfest.lacrimis.entity.SoulShellEntity;
import modfest.lacrimis.entity.TaintedPearlEntity;
import net.fabricmc.fabric.api.client.rendereregistry.v1.BlockEntityRendererRegistry;
import net.fabricmc.fabric.api.client.rendereregistry.v1.EntityRendererRegistry;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.FlyingItemEntityRenderer;
import net.minecraft.client.render.entity.ProjectileEntityRenderer;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.Registry;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;

import modfest.lacrimis.Lacrimis;
import modfest.lacrimis.entity.GhostEntity;
import org.jetbrains.annotations.Nullable;

import java.util.function.Supplier;

public class ModEntityTypes {

    public static EntityType<GhostEntity> ghost;
    public static EntityType<SoulShellEntity> soulShell;
    public static EntityType<TaintedPearlEntity> taintedPearl;

    //Block entities
    public static BlockEntityType<InfusionTableEntity> infusionTable;
    public static BlockEntityType<CrucibleEntity> crucible;
    public static BlockEntityType<CombinerEntity> combiner;
    public static BlockEntityType<TearLanternEntity> tearLantern;
    public static BlockEntityType<NetworkLinkEntity> networkLink;

    public static void register() {
        ghost = register("ghost", buildType(SpawnGroup.MONSTER, GhostEntity::new, 0.75f, 2.0f).trackRangeBlocks(64).trackedUpdateRate(4).fireImmune());
        soulShell = register("soul_shell", buildType(SpawnGroup.MISC, SoulShellEntity::new, 0.6F, 1.8F));
        taintedPearl = register("tainted_pearl", buildType(SpawnGroup.MISC, TaintedPearlEntity::new, 0.25F, 0.25F).trackRangeChunks(4).trackedUpdateRate(10));

        FabricDefaultAttributeRegistry.register(ghost, GhostEntity.createGhostAttributes());
        FabricDefaultAttributeRegistry.register(soulShell, SoulShellEntity.createSoulShellAttributes());

        infusionTable = register("infusion_table_entity", InfusionTableEntity::new, ModBlocks.infusionTable);
        crucible = register("crucible_entity", CrucibleEntity::new, ModBlocks.crucible);
        combiner = register("combiner_entity", CombinerEntity::new, ModBlocks.combiner);
        tearLantern = register("tear_lantern_entity", TearLanternEntity::new, ModBlocks.tearLantern);
        networkLink = register("network_link_entity", NetworkLinkEntity::new, ModBlocks.networkLink);
    }
    
    public static void registerClient() {
        EntityRendererRegistry.INSTANCE.register(ModEntityTypes.ghost, (dispatcher, ctx) -> new GhostEntityRenderer(ctx));
        EntityRendererRegistry.INSTANCE.register(ModEntityTypes.soulShell, (dispatcher, ctx) -> new SoulShellRenderer(dispatcher));
        EntityRendererRegistry.INSTANCE.register(ModEntityTypes.taintedPearl, (dispatcher, ctx) -> new FlyingItemEntityRenderer<TaintedPearlEntity>(dispatcher, ctx.getItemRenderer()));

        BlockEntityRendererRegistry.INSTANCE.register(ModEntityTypes.crucible, CrucibleEntityRenderer::new);
        BlockEntityRendererRegistry.INSTANCE.register(ModEntityTypes.infusionTable, InfusionTableEntityRenderer::new);
        BlockEntityRendererRegistry.INSTANCE.register(ModEntityTypes.networkLink, NetworkLinkEntityRenderer::new);

        CrucibleEntityRenderer.onInit();
        InfusionTableEntityRenderer.onInit();

    }

    private static <T extends Entity> EntityType<T> register(String name, FabricEntityTypeBuilder<T> builder) {
        return Registry.register(Registry.ENTITY_TYPE, new Identifier(Lacrimis.MODID, name), builder.build());
    }

    private static <T extends Entity> FabricEntityTypeBuilder<T> buildType(SpawnGroup group, EntityType.EntityFactory<T> f, float width, float height) {
        return FabricEntityTypeBuilder.create(group, f).dimensions(EntityDimensions.fixed(width, height));
    }

    private static <T extends BlockEntity> BlockEntityType<T> register(String name, FabricBlockEntityTypeBuilder.Factory factory, Block... blocks) {
        return Registry.register(Registry.BLOCK_ENTITY_TYPE, new Identifier(Lacrimis.MODID, name), FabricBlockEntityTypeBuilder.create(factory, blocks).build(null));
    }

    @Nullable
    public static <E extends BlockEntity, A extends BlockEntity> BlockEntityTicker<A> checkType(BlockEntityType<A> givenType, BlockEntityType<E> expectedType, BlockEntityTicker<? super E> ticker) {
        return expectedType == givenType ? (BlockEntityTicker<A>) ticker : null;
    }

    @FunctionalInterface
    interface Constructor<T> {
        T build(BlockPos pos, BlockState state);
    }
}
