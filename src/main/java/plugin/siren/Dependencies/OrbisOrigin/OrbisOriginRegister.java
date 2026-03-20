package plugin.siren.Dependencies.OrbisOrigin;

import com.hexvane.orbisorigins.data.PlayerSpeciesData;
import com.hexvane.orbisorigins.species.*;
import com.hypixel.hytale.component.Ref;
import com.hypixel.hytale.component.Store;
import com.hypixel.hytale.server.core.HytaleServer;
import com.hypixel.hytale.server.core.universe.PlayerRef;
import com.hypixel.hytale.server.core.universe.Universe;
import com.hypixel.hytale.server.core.universe.world.World;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
import plugin.siren.api.MermaidsAPI;

import java.util.*;
import java.util.concurrent.TimeUnit;

public class OrbisOriginRegister {

    private static Runnable checkForMermaidSpecies = new Runnable() {
        @Override
        public void run() {
            List<PlayerRef> onlinePlayerRefs = Universe.get().getPlayers();
            if(!onlinePlayerRefs.isEmpty()) {
                for (PlayerRef playerRef : onlinePlayerRefs) {
                    World world = Universe.get().getWorld(playerRef.getWorldUuid());
                    if (world != null) {
                        world.execute(() -> {
                            Ref<EntityStore> ref = playerRef.getReference();
                            Store<EntityStore> store = ref.getStore();

                            PlayerSpeciesData.SpeciesSelection speciesSelection = PlayerSpeciesData.getSpeciesSelection(ref, store, world);
                            if (speciesSelection != null) {
                                String id = speciesSelection.getSpeciesId();
                                if (id.equalsIgnoreCase("mermaid")) {
                                    if (!MermaidsAPI.isForcedMermaidOrbisOrigin(store, ref)) {
                                        MermaidsAPI.setForcedMermaidOrbisOrigin(store, ref, true);
                                    }
                                } else {
                                    if (MermaidsAPI.isForcedMermaidOrbisOrigin(store, ref)) {
                                        MermaidsAPI.setForcedMermaidOrbisOrigin(store, ref, false);
                                    }
                                }
                            } else {
                                if (MermaidsAPI.isForcedMermaidOrbisOrigin(store, ref)) {
                                    MermaidsAPI.setForcedMermaidOrbisOrigin(store, ref, false);
                                }
                            }
                        });
                    }
                }
            }
        }
    };

    private OrbisOriginRegister(){}

    public static void register() {
        int version = 0;
        String id = "mermaid";
        String displayName = "Mermaid";
        String displayNameKey = "species.mermaid.name";
        String modelBaseName = "Player";
        List<String> variants = new ArrayList<>();
        variants.add("Mermaid");
        List<SpeciesVariantData> variantsV2 = null;
        String description = "A mythical creature who swims faster underwater.";
        String descriptionKey = "species.mermaid.desc";
        int healthModifier = 15;
        int staminaModifier = 5;
        int manaModifier = 25;
        boolean enabled = true;
        boolean usePlayerModel = true;
        boolean enableAttachmentDiscovery = true;
        Map<String, Map<String, AttachmentOption>> manualAttachments = new HashMap<>();
        Map<String, Float> eyeHeightModifiers = new HashMap<>();
        Map<String, Float> hitboxHeightModifiers = new HashMap<>();
        List<String> starterItems = new ArrayList<>();
        starterItems.add("Mermaids_Seashell_Bra");
        starterItems.add("Weapon_Spear_Fishbone");
        Map<String, Float> damageResistance = new HashMap<>();
        damageResistance.put("Physical", 0.95f);
        damageResistance.put("Magic", 0.85f);
        float modelScale = 1f;
        float sleepingRaiseHeight = 0f;
        List<SpeciesAbilityConfig> abilities = new ArrayList<>();

        SpeciesData mermaidSpecies = new SpeciesData(version,id,displayName,displayNameKey,modelBaseName,variants,variantsV2,description,descriptionKey,healthModifier,staminaModifier,manaModifier,enabled,usePlayerModel,enableAttachmentDiscovery,manualAttachments,eyeHeightModifiers,hitboxHeightModifiers,starterItems,damageResistance,modelScale,sleepingRaiseHeight, abilities);

        SpeciesRegistry.registerSpecies(mermaidSpecies);

        HytaleServer.SCHEDULED_EXECUTOR.scheduleAtFixedRate(checkForMermaidSpecies,15, 8, TimeUnit.SECONDS);
    }
}
