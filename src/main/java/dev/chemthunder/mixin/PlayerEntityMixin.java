package dev.chemthunder.mixin;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import dev.chemthunder.index.ClairItems;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(PlayerEntity.class)
public abstract class PlayerEntityMixin extends LivingEntity {
    protected PlayerEntityMixin(EntityType<? extends LivingEntity> entityType, World world) {
        super(entityType, world);
    }

    @ModifyReturnValue(method = "getDisplayName", at = @At("RETURN"))
    private Text gilded$maskName(Text original) {
        PlayerEntity player = (PlayerEntity)(Object)this;
        if (player.getEquippedStack(EquipmentSlot.HEAD).isOf(ClairItems.TEST_MASK)) {
            return Text.translatable("name.mask").withColor(0x481b52).formatted(Formatting.ITALIC);
        }
        return original;
    }
}
