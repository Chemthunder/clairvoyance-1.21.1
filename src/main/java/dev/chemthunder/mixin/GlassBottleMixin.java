package dev.chemthunder.mixin;

import dev.chemthunder.index.ClairItems;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.GlassBottleItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(GlassBottleItem.class)
public abstract class GlassBottleMixin extends Item {
    public GlassBottleMixin(Settings settings) {
        super(settings);
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        World world = context.getWorld();
        LivingEntity entity = context.getPlayer();
        BlockPos pos = context.getBlockPos();
        BlockState state = context.getWorld().getBlockState(pos);
        Hand hand = context.getHand();
        ItemStack stack = entity.getStackInHand(hand);

        if (entity.isSneaking() && state.isOf(Blocks.SOUL_SAND) || state.isOf(Blocks.SOUL_SOIL)) {
            if (entity instanceof PlayerEntity playerEntity) {
                stack.decrement(1);
                playerEntity.giveItemStack(ClairItems.SOUL_BOTTLE.getDefaultStack());
                playerEntity.playSound(SoundEvents.BLOCK_SCULK_BREAK);
            }
            return ActionResult.SUCCESS;
        }

        if (world instanceof ServerWorld serverWorld) {
            serverWorld.spawnParticles(ParticleTypes.SOUL_FIRE_FLAME,
                    pos.getX(),
                    pos.getY(),
                    pos.getZ(),
                    45,
                    0.3,
                    0.3,
                    0.3,
                    0.05
            );
        }
        return super.useOnBlock(context);
    }
}
