package dev.chemthunder.item;

import dev.chemthunder.index.ClairDamageSources;
import dev.chemthunder.index.ClairDataComponents;
import dev.chemthunder.index.ClairEnchantments;
import dev.chemthunder.index.ClairStatusEffects;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.SmallFireballEntity;
import net.minecraft.entity.projectile.WindChargeEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class CastingStoneItem extends Item {
    public CastingStoneItem(Settings settings) {
        super(settings);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack stack = user.getStackInHand(hand);
        ItemStack offStack = user.getOffHandStack();
        ItemStack mainStack = user.getMainHandStack();
        Vec3d pos = user.getPos();
        BlockPos userPos = user.getBlockPos();
        BlockPos dataPos = stack.get(ClairDataComponents.WAYPOINT);


        if (EnchantmentHelper.hasAnyEnchantmentsWith(user.getStackInHand(hand), ClairEnchantments.WAYPOINT)) {
            if (user.isSneaking() && mainStack.isOf(this) && user.isOnGround()) {
                stack.set(ClairDataComponents.WAYPOINT, user.getBlockPos());
                user.getItemCooldownManager().set(this, 25);
                user.damage(ClairDamageSources.waypoint(user), 5);
                user.sendMessage(Text.literal("Placed a casting stone at " + user.getBlockPos()).withColor(0x512c66), true);
            }

            if (offStack.isOf(this)) {
                if (stack.contains(ClairDataComponents.WAYPOINT)) {
                    if (pos != null) {
                        user.teleport(dataPos.getX(), dataPos.getY(), dataPos.getZ(), false);
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
        }

        if (EnchantmentHelper.hasAnyEnchantmentsWith(user.getStackInHand(hand), ClairEnchantments.MATERIALIZE)) {
            if (mainStack.isOf(this)) {
                if (offStack.isOf(Items.FIRE_CHARGE)) {

                    user.playSound(SoundEvents.ENTITY_GHAST_SHOOT, 1f, 1);
                    SmallFireballEntity fireball = new SmallFireballEntity(world, user, user.getRotationVec(0));

                    fireball.updatePosition(pos.x, pos.y + 1.5f, pos.z);
                    world.spawnEntity(fireball);
                    if (!user.isInCreativeMode()) {
                        user.getItemCooldownManager().set(this, 20);
                    }
                }

                if (offStack.isOf(Items.WIND_CHARGE)) {
                    WindChargeEntity windCharge = new WindChargeEntity(user, world, user.getX(), user.getY() + 1.5f, user.getZ());

                    windCharge.setVelocity(user, user.getPitch(), user.getYaw(), 0.0f, 3f, 1.0f); // power, divergence

                    world.spawnEntity(windCharge);

                    if (!user.isInCreativeMode()) {
                        user.getItemCooldownManager().set(this, 20);
                    }
                }
            }
        }
        return super.use(world, user, hand);
    }

    @Override
    public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
        if (entity instanceof PlayerEntity player) {
            if (EnchantmentHelper.hasAnyEnchantmentsWith(player.getMainHandStack(), ClairEnchantments.IMPERSONATE)) {
                if (player.isSneaking()) {
                    player.setInvisible(true);
                } else {
                    player.setInvisible(false);
                }
            }
        }

        super.inventoryTick(stack, world, entity, slot, selected);
    }
}
