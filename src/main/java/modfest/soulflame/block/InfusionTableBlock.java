package modfest.soulflame.block;

import modfest.soulflame.block.entity.InfusionTableEntity;
import modfest.soulflame.init.ModInfusion;
import net.fabricmc.fabric.api.container.ContainerProviderRegistry;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;

public class InfusionTableBlock extends SoulTankBlock  implements BlockConduitConnect {
	public InfusionTableBlock(AbstractBlock.Settings settings) {
		super(settings);
	}

	@Override
	public BlockEntity createBlockEntity(BlockView world) {
		return new InfusionTableEntity();
	}

	public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
		ActionResult parentResult = super.onUse(state, world, pos, player, hand, hit);
		if(parentResult == ActionResult.PASS) {
			if(world.isClient) {
				return ActionResult.SUCCESS;
			} else {
				ContainerProviderRegistry.INSTANCE.openContainer(ModInfusion.INFUSION_SCREEN_ID, player, buf -> buf.writeBlockPos(pos));

				return ActionResult.CONSUME;
			}
		}
		return parentResult;
	}

	@Override
	public BlockRenderType getRenderType(BlockState state) {
		return BlockRenderType.MODEL;
	}
}