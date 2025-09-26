package dev.chemthunder.item;

import dev.chemthunder.index.ClairDamageSources;
import dev.chemthunder.index.ClairDataComponents;
import net.minecraft.block.Block;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.damage.DamageSources;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class CallingStoneItem extends Item {
    public CallingStoneItem(Settings settings) {
        super(settings);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack stack = user.getStackInHand(hand);
        ItemStack offStack = user.getOffHandStack();
        if (user.isSneaking()) {
            stack.set(ClairDataComponents.WAYPOINT, user.getBlockPos());
            user.getItemCooldownManager().set(this, 25);
            user.damage(ClairDamageSources.waypoint(user), 5);
            user.sendMessage(Text.literal("Placed a casting stone at " + user.getBlockPos()).withColor(0x512c66), true);
        }

        if (offStack.isOf(this)) {
            if (stack.contains(ClairDataComponents.WAYPOINT)) {
                BlockPos pos = stack.get(ClairDataComponents.WAYPOINT);
                BlockPos userPos = user.getBlockPos();
                if (pos != null) {
                    user.teleport(pos.getX(), pos.getY(), pos.getZ(), false);
                    user.updatePosition(pos.getX(), pos.getY(), pos.getZ());
                }

                if (world instanceof ServerWorld serverWorld) {
                    serverWorld.spawnParticles(ParticleTypes.END_ROD,
                            userPos.getX(),
                            userPos.getY() + 1,
                            userPos.getZ(),
                            15,
                            0.1,
                            0.1,
                            0.1,
                            0.05
                    );
                }
            }
        }

        return super.use(world, user, hand);
    }
}
