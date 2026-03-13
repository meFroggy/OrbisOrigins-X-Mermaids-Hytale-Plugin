package plugin.siren.Utils.Config;

import com.hypixel.hytale.codec.Codec;
import com.hypixel.hytale.codec.KeyedCodec;
import com.hypixel.hytale.codec.builder.BuilderCodec;
import plugin.siren.Mermaids;
import plugin.siren.OrbisOriginsMermaids;

public class OOMConfig {

    public static final BuilderCodec<OOMConfig> CODEC = BuilderCodec.builder(OOMConfig.class, OOMConfig::new)
            .append(new KeyedCodec<String>("Config-Information", Codec.STRING),
                    (merConfig, ciStr, extraInfo) -> merConfig.Information = ciStr, // Setter
                    (merConfig, extraInfo) -> merConfig.Information)                    // Getter
            .add()
            .append(new KeyedCodec<Integer>("ConfigVersion", Codec.INTEGER),
                    (merConfig, cvInt, extraInfo) -> merConfig.ConfigVersion = cvInt, // Setter
                    (merConfig, extraInfo) -> merConfig.ConfigVersion)                    // Getter
            .add()
            .append(new KeyedCodec<String>("PluginName", Codec.STRING),
                    (merConfig, pnStr, extraInfo) -> merConfig.PluginName = pnStr, // Setter
                    (merConfig, extraInfo) -> merConfig.PluginName)                    // Getter
            .add()
            .append(new KeyedCodec<String>("Version", Codec.STRING),
                    (merConfig, vStr, extraInfo) -> merConfig.Version = vStr, // Setter
                    (merConfig, extraInfo) -> merConfig.Version)                    // Getter
            .add()
            .append(new KeyedCodec<String>("Website", Codec.STRING),
                    (merConfig, wStr, extraInfo) -> merConfig.Website = wStr, // Setter
                    (merConfig, extraInfo) -> merConfig.Website)                    // Getter
            .add()
            .append(new KeyedCodec<String>("Download-Site", Codec.STRING),
                    (merConfig, dsStr, extraInfo) -> merConfig.DownloadSite = dsStr, // Setter
                    (merConfig, extraInfo) -> merConfig.DownloadSite)                    // Getter
            .add()
            .append(new KeyedCodec<Boolean>("Disable-Default-Mermaids-Content", Codec.BOOLEAN),
                    (merConfig, ddmcBool, extraInfo) -> merConfig.DisableMermaidsContent = ddmcBool, // Setter
                    (merConfig, extraInfo) -> merConfig.DisableMermaidsContent)                    // Getter
            .add()
            .append(new KeyedCodec<Boolean>("Mermaid-Only-In-Water", Codec.BOOLEAN),
                    (merConfig, moiwBool, extraInfo) -> merConfig.MermaidOnlyInWater = moiwBool, // Setter
                    (merConfig, extraInfo) -> merConfig.MermaidOnlyInWater)                    // Getter
            .add()
            .append(new KeyedCodec<Boolean>("New-Version-Message", Codec.BOOLEAN),
                    (merConfig, nvmBool, extraInfo) -> merConfig.NewVersion = nvmBool, // Setter
                    (merConfig, extraInfo) -> merConfig.NewVersion)                    // Getter
            .add()
            .build();

    private String InformationDefault = "Confused about what one of these statement do? Go to https://mermaids.dev/mermaids/compatibilities/orbis-origins/config/ or check out the Curseforge website and scroll down to Config Extra Info.";
    private String Information = InformationDefault;
    private final int ConfigVersionDefault = 2;
    private int ConfigVersion = ConfigVersionDefault;
    private String PluginName = "OrbisOrigins X Mermaids";
    private String Version = OrbisOriginsMermaids.getVersion();
    private String WebsiteDefault = "https://mermaids.dev/mermaids/compatibilities/orbis-origins";
    private String Website = WebsiteDefault;
    private String DownloadSiteDefault = "https://www.curseforge.com/hytale/mods/orbis-origins-mermaids";
    private String DownloadSite = DownloadSiteDefault;
    private boolean DisableMermaidsContent = true;
    private boolean MermaidOnlyInWater = false;
    private boolean NewVersion = true;

    public OOMConfig() {}

    public boolean ifConfigUpdate(){
        boolean configUpdated = false;

        if(ConfigVersionDefault > ConfigVersion){
            configUpdated = true;
            ConfigVersion = ConfigVersionDefault;
        }
        if(!Version.equalsIgnoreCase(Mermaids.getVersion())){
            configUpdated = true;
            Version = Mermaids.getVersion();
        }
        if(!Information.equalsIgnoreCase(InformationDefault)){
            configUpdated = true;
            Information = InformationDefault;
        }
        if(!Website.equalsIgnoreCase(WebsiteDefault)){
            configUpdated = true;
            Website = WebsiteDefault;
        }
        if(!DownloadSite.equalsIgnoreCase(DownloadSiteDefault)){
            configUpdated = true;
            DownloadSite = DownloadSiteDefault;
        }

        return configUpdated;
    }

    public int getConfigVersionDefault(){
        return ConfigVersionDefault;
    }

    public int getConfigVersion(){
        return ConfigVersion;
    }

    public void setConfigVersion(int version){
        this.ConfigVersion = version;
    }

    public String getPluginVersion(){
        return Version;
    }

    public void setPluginVersion(String version){
        this.Version = version;
    }

    public boolean getDisabledMermaidContent(){
        return this.DisableMermaidsContent;
    }

    public void setDisableMermaidsContent(boolean disableContent){
        this.DisableMermaidsContent = disableContent;
    }

    public boolean ifMermaidOnlyInWater(){
        return this.MermaidOnlyInWater;
    }

    public void setMermaidOnlyInWater(boolean onlyInWater){
        this.MermaidOnlyInWater = onlyInWater;
    }

    public boolean ifNewVersion(){
        return NewVersion;
    }
}
