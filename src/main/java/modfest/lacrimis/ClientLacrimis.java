package modfest.lacrimis;

import net.minecraft.client.render.RenderLayer;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.rendereregistry.v1.BlockEntityRendererRegistry;

import modfest.lacrimis.client.render.blocks.CrucibleEntityRenderer;
import modfest.lacrimis.client.render.blocks.InfusionTableEntityRenderer;
import modfest.lacrimis.init.ClientModCrafting;
import modfest.lacrimis.init.ModBlockEntityTypes;
import modfest.lacrimis.init.ModBlocks;
import modfest.lacrimis.init.ModNetworking;
import modfest.lacrimis.init.ModParticles;

@Environment(EnvType.CLIENT)
public class ClientLacrimis implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.infusionTable, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.tearLantern, RenderLayer.getCutout());

        BlockEntityRendererRegistry.INSTANCE.register(ModBlockEntityTypes.crucible, CrucibleEntityRenderer::new);
        CrucibleEntityRenderer.onInit();

        BlockEntityRendererRegistry.INSTANCE.register(ModBlockEntityTypes.infusionTable, InfusionTableEntityRenderer::new);
        InfusionTableEntityRenderer.onInit();

        ClientModCrafting.register();
        ModNetworking.registerClient();
        ModParticles.registerClient();
    }
}
