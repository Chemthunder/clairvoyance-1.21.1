package dev.chemthunder;

import dev.chemthunder.index.ClairDataComponents;
import dev.chemthunder.index.ClairItemGroups;
import dev.chemthunder.index.ClairItems;
import dev.chemthunder.index.ClairStatusEffects;
import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerEntityEvents;
import net.minecraft.network.packet.s2c.play.PlayerListS2CPacket;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.UUID;

public class Clairvoyance implements ModInitializer {
	public static final String MOD_ID = "clairvoyance";

    public static Identifier id (String path){
        return Identifier.of(MOD_ID, path);
    }
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    public static final ArrayList<UUID> banishedUUIDs = new ArrayList<>();

	@Override
	public void onInitialize() {
        ClairItems.init();
        ClairItemGroups.init();
        ClairDataComponents.init();
        ClairStatusEffects.init();


            ServerEntityEvents.EQUIPMENT_CHANGE.register((entity, slot, previous, next) -> {
                if (entity instanceof ServerPlayerEntity player && (next.isOf(ClairItems.TEST_MASK) || previous.isOf(ClairItems.TEST_MASK))) {
                    ServerPlayNetworkHandler handler = player.networkHandler;
                    if (handler != null) {
                        MinecraftServer server = player.getServer();
                        if (server != null) {
                            ServerPlayerEntity playerEntity = server.getPlayerManager().getPlayer(player.getUuid());
                            if (playerEntity != null) {
                                server.getPlayerManager().sendToAll(new PlayerListS2CPacket(PlayerListS2CPacket.Action.UPDATE_DISPLAY_NAME, playerEntity));
                            }
                        }
                    }
                }
            });


		LOGGER.info("Hello Fabric world!");
	}
}