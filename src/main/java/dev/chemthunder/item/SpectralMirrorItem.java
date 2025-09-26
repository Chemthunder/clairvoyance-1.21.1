package dev.chemthunder.item;

import dev.chemthunder.index.ClairItems;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

import java.util.UUID;

import static dev.chemthunder.Clairvoyance.banishedUUIDs;

public class SpectralMirrorItem extends Item {
    public SpectralMirrorItem(Settings settings) {
        super(settings);
    }


    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        if (!banishedUUIDs.contains(user.getUuid())) {
            banishedUUIDs.add(UUID.fromString(user.getUuidAsString()));

            user.sendMessage(Text.translatable("text.mirror.uuid_add").withColor(0x512c66), true);
            user.getItemCooldownManager().set(ClairItems.SPECTRAL_MIRROR, 180);
        } else {
            banishedUUIDs.remove(UUID.fromString(user.getUuidAsString()));
            user.playSound(SoundEvents.BLOCK_TRIAL_SPAWNER_BREAK);
        }
        return super.use(world, user, hand);
    }
}
