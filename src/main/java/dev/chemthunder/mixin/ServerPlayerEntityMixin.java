package dev.chemthunder.mixin;

import com.mojang.authlib.GameProfile;
import dev.chemthunder.index.ClairItems;
import dev.chemthunder.item.DemonsMaskItem;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ServerPlayerEntity.class)
public abstract class ServerPlayerEntityMixin extends PlayerEntity {
    public ServerPlayerEntityMixin(World world, BlockPos pos, float yaw, GameProfile gameProfile) {
        super(world, pos, yaw, gameProfile);
    }

    @Inject(method = "getPlayerListName", at = @At("TAIL"), cancellable = true)
    private void replaceNameOnTabList(CallbackInfoReturnable<Text> cir) {
        ServerPlayerEntity player = (ServerPlayerEntity)(Object)this;
        if (player.getEquippedStack(EquipmentSlot.HEAD).isOf(ClairItems.TEST_MASK)) {
            cir.setReturnValue(Text.translatable("name.mask").withColor(0x481b52).formatted(Formatting.ITALIC));
        }
    }
}
