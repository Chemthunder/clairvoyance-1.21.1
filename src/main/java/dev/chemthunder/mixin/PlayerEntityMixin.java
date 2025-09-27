package dev.chemthunder.mixin;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import dev.chemthunder.index.ClairEnchantments;
import dev.chemthunder.index.ClairItems;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.mob.VindicatorEntity;
import net.minecraft.entity.mob.WitherSkeletonEntity;
import net.minecraft.entity.passive.IronGolemEntity;
import net.minecraft.entity.passive.PigEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PlayerEntity.class)
public abstract class PlayerEntityMixin extends LivingEntity {
    @Shadow
    public abstract boolean giveItemStack(ItemStack stack);

    @Shadow
    @Final
    private PlayerInventory inventory;

    protected PlayerEntityMixin(EntityType<? extends LivingEntity> entityType, World world) {
        super(entityType, world);
    }

    @ModifyReturnValue(method = "getDisplayName", at = @At("RETURN"))
    private Text gilded$maskName(Text original) {
        PlayerEntity player = (PlayerEntity)(Object)this;
        if (player.getEquippedStack(EquipmentSlot.HEAD).isOf(ClairItems.TEST_MASK)) {
            if (EnchantmentHelper.hasAnyEnchantmentsWith(player.getEquippedStack(EquipmentSlot.HEAD), ClairEnchantments.OBSCURE)) {
                return Text.translatable("name.mask").withColor(0x481b52).formatted(Formatting.ITALIC);
            }
        }
        return original;
    }

    @Inject(method = "onKilledOther", at = @At("HEAD"))
    private void onKilledOther(ServerWorld world, LivingEntity other, CallbackInfoReturnable<Boolean> cir) {
        ItemStack offStack = this.getOffHandStack();

        if (offStack.isOf(ClairItems.EFFIGY_VESSEL)) {
            if (other instanceof WitherSkeletonEntity) {
                offStack.decrement(1);
                this.giveItemStack(ClairItems.ABSTINENT_EFFIGY.getDefaultStack());
                this.playSound(SoundEvents.ENTITY_WITHER_HURT);
            }

            if (other instanceof IronGolemEntity) {
                offStack.decrement(1);
                this.giveItemStack(ClairItems.ENGRAVED_EFFIGY.getDefaultStack());
                this.playSound(SoundEvents.ENTITY_WITHER_HURT);
            }

            if (other instanceof VindicatorEntity) {
                offStack.decrement(1);
                this.giveItemStack(ClairItems.CHIPPED_EFFIGY.getDefaultStack());
                this.playSound(SoundEvents.ENTITY_WITHER_HURT);
            }
        }
    }
}
