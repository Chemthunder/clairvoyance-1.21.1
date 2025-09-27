package dev.chemthunder.mixin;

import dev.chemthunder.index.ClairItems;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ProjectileItem;
import net.minecraft.item.WindChargeItem;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(WindChargeItem.class)
public abstract class WindChargeMixin extends Item implements ProjectileItem {
    public WindChargeMixin(Item.Settings settings) {
        super(settings);
    }

    @Inject(method = "use", at = @At("HEAD"))
    private void use(World world, PlayerEntity user, Hand hand, CallbackInfoReturnable<TypedActionResult<ItemStack>> cir) {
        ItemStack mainStack = user.getMainHandStack();
        if (mainStack.isOf(ClairItems.CASTING_STONE)) {

        }
    }
}
