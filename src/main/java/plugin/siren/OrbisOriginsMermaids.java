package plugin.siren;

import com.hypixel.hytale.common.plugin.PluginIdentifier;
import com.hypixel.hytale.event.EventRegistration;
import com.hypixel.hytale.logger.HytaleLogger;
import com.hypixel.hytale.server.core.HytaleServer;
import com.hypixel.hytale.server.core.Message;
import com.hypixel.hytale.server.core.command.system.CommandRegistration;
import com.hypixel.hytale.server.core.event.events.player.PlayerReadyEvent;
import com.hypixel.hytale.server.core.plugin.JavaPlugin;
import com.hypixel.hytale.server.core.plugin.JavaPluginInit;
import com.hypixel.hytale.server.core.util.Config;
import plugin.siren.Commands.OriginsMermaidsCmd;
import plugin.siren.Contributions.al3x.HStats;
import plugin.siren.Dependencies.Mermaids.MermaidRegister;
import plugin.siren.Dependencies.OrbisOrigin.OrbisOriginRegister;
import plugin.siren.Events.PlayerReadyEventOOM;
import plugin.siren.Utils.API.UpdateChecker;
import plugin.siren.Utils.Config.OOMConfig;

import javax.annotation.Nonnull;
import java.awt.*;
import java.util.concurrent.TimeUnit;

public class OrbisOriginsMermaids extends JavaPlugin {
    private static final String VERSION = "1.0.1";

    private static OrbisOriginsMermaids plugin;
    public static final HytaleLogger LOGGER = HytaleLogger.forEnclosingClass();
    private final Config<OOMConfig> config;

    public OrbisOriginsMermaids(@Nonnull JavaPluginInit init){
        super(init);

        plugin = this;
        this.config = this.withConfig("OrbisOrigins-Mermaids", OOMConfig.CODEC);

        new HStats("a5d03352-3549-473f-81e7-bc0f3192d40c", VERSION);
    }

    @Override
    protected void setup(){
        LOGGER.atInfo().log("===---==---==---== Orbis Origins X Mermaids ==---==---==---===");
        LOGGER.atInfo().log("Orbis Origins X Mermaids has began to load.");

        LOGGER.atInfo().log("Checking for Orbis Origins Mod.");
        if (HytaleServer.get().getPluginManager().getPlugin(PluginIdentifier.fromString("hexvane:OrbisOrigins")) != null) {
            LOGGER.atInfo().log("Orbis Origins found.");
        }else{
            LOGGER.atSevere().log("Orbis Origins not installed!");
        }

        LOGGER.atInfo().log("Checking for Mermaids Mod.");
        if (HytaleServer.get().getPluginManager().getPlugin(PluginIdentifier.fromString("Siren:Mermaids")) != null) {
            LOGGER.atInfo().log("Mermaids found.");
        }else{
            LOGGER.atSevere().log("Mermaids not installed!");
        }

        EventRegistration<String, PlayerReadyEvent> playerReadyEventRegistration = this.getEventRegistry().registerGlobal(PlayerReadyEvent.class, PlayerReadyEventOOM::onPlayerReadyEvent);
        if(playerReadyEventRegistration != null && playerReadyEventRegistration.isRegistered()) {
            LOGGER.atInfo().log("Registered Player Ready Event.");
        }else{
            LOGGER.atSevere().log("Failed to register Player Ready Event.");
        }

        CommandRegistration originsMermaidsCmdRegistration = this.getCommandRegistry().registerCommand(new OriginsMermaidsCmd());
        if(originsMermaidsCmdRegistration != null && originsMermaidsCmdRegistration.isRegistered()) {
            LOGGER.atInfo().log("Registered OriginsMermaids Command.");
        }else{
            LOGGER.atSevere().log("Failed to register OriginsMermaids Command.");
        }

        config.save();
        LOGGER.atInfo().log("Loaded config settings.");
        boolean configUpdated = config.get().ifConfigUpdate();
        if(configUpdated){
            config.save();
            LOGGER.atInfo().log("Updated config to latest version.");
        }

        LOGGER.atInfo().log("Version " + VERSION + " of Orbis Origins X Mermaids has successfully loaded.");

        String recentVersion = UpdateChecker.checkForUpdate();
        if(!VERSION.equalsIgnoreCase(recentVersion)){
            LOGGER.atInfo().log("= =- -=- -=- -=- -=- -=- -=- -=- -=- -=- -= =");
            String versionMessage = "The Orbis Origins X Mermaids Mod version is outdated, Orbis Origins X Mermaids has released v" + recentVersion +".";
            LOGGER.atInfo().log(versionMessage);

            Runnable updateCheckRunnable = new Runnable() {
                @Override
                public void run() {
                    LOGGER.atInfo().log(versionMessage);
                }
            };
            HytaleServer.SCHEDULED_EXECUTOR.schedule(updateCheckRunnable,15, TimeUnit.SECONDS);
        }
        LOGGER.atInfo().log("===---==---==---==---==---==---==---==---==---==---==---==---===");

        Runnable registerDependencies = new Runnable() {
            @Override
            public void run() {
                MermaidRegister.register();
                OrbisOriginRegister.register();
            }
        };

        HytaleServer.SCHEDULED_EXECUTOR.schedule(registerDependencies,2, TimeUnit.SECONDS);
    }

    @Override
    protected void shutdown(){
        LOGGER.atInfo().log("===---==---==---== Orbis Origins X Mermaids ==---==---==---===");
        LOGGER.atInfo().log("Orbis Origins X Mermaids has began to shutdown.");
        LOGGER.atInfo().log("Saving any necessary data.");
        LOGGER.atInfo().log("Version " + VERSION + " of Orbis Origins X Mermaids has successfully shutdown.");
        LOGGER.atInfo().log("===---==---==---==---==---==---==---==---==---==---==---==---===");
    }

    public static OrbisOriginsMermaids get(){
        return plugin;
    }

    public static String getVersion(){
        return VERSION;
    }

    public static Config<OOMConfig> getConfig(){
        return plugin.config;
    }
}
