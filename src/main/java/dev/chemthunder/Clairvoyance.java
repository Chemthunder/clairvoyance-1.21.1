package dev.chemthunder;

import dev.chemthunder.index.ClairDataComponents;
import dev.chemthunder.index.ClairItemGroups;
import dev.chemthunder.index.ClairItems;
import dev.chemthunder.index.ClairStatusEffects;
import net.fabricmc.api.ModInitializer;

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

		LOGGER.info("Hello Fabric world!");
	}
}