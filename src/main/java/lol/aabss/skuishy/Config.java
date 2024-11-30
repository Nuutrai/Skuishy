package lol.aabss.skuishy;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

import static lol.aabss.skuishy.Skuishy.instance;

public class Config {

    private FileConfiguration config;
    private File configFile;

    public Config() {
        setupConfig();
    }

    public final Map<String, Boolean> ELEMENT_STATUS = new HashMap<String, Boolean>();
    public boolean SHOULD_AUTO_UPDATE;
    public boolean CHECK_FOR_UPDATES;
    public boolean SEND_VERSION_AVAILABLE_MESSAGE;
	public boolean PREFER_SKRIPT_INDEX;
    public String SKIN_API_KEY;
    public String SKIN_API_USER_AGENT; // TODO: decide whether I want this it's optional
	public String COMMNAD_PERMISSION_MESSAGE;

    private void setupConfig() {
        if (configFile == null) {
            configFile = new File(instance.getDataFolder(), "config.yml");
        }
        if (!configFile.exists()) {
			instance.saveResource("config.yml", false);
        }
        config = YamlConfiguration.loadConfiguration(configFile);
        matchConfig();
        loadSettings();
    }

    private void matchConfig() {

        try {
            boolean hasUpdate = false;
            InputStream inputStream = instance.getResource("config.yml");
            assert inputStream != null;
            InputStreamReader streamReader = new InputStreamReader(inputStream);
            YamlConfiguration defaultConfig = YamlConfiguration.loadConfiguration(streamReader);

            // Remove keys that do not exist in default plugin config
            for (String key : config.getKeys(true)) {
                if (!defaultConfig.contains(key)) {
                    config.set(key, null);
                    hasUpdate = true;
                }
            }

            // Add keys that exist in default config but not the existing config
            for (String key : defaultConfig.getKeys(true)) {
                if (!config.contains(key)) {
                    config.set(key, defaultConfig.get(key));
                    hasUpdate = true;
                }
            }

            if (hasUpdate) {
                config.save(configFile);
            }
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    private void loadSettings() {
		SHOULD_AUTO_UPDATE = config.getBoolean("auto-update-on-shutdown");
		CHECK_FOR_UPDATES = config.getBoolean("check-for-updates");
		SEND_VERSION_AVAILABLE_MESSAGE = config.getBoolean("update-available-message");
		PREFER_SKRIPT_INDEX = config.getBoolean("prefer-skript-index");
		SKIN_API_KEY = config.getString("skin-api.api-key");
		SKIN_API_USER_AGENT = config.contains("skin-api.user-agent") ? config.getString("skin-api.user-agent") : null;
		COMMNAD_PERMISSION_MESSAGE = config.getString("command.permission-message");
		ConfigurationSection elementSection = config.getConfigurationSection("disable-elements");
		assert elementSection != null;
		for (String element : elementSection.getKeys(false)) {
			ELEMENT_STATUS.put(element, elementSection.getBoolean(element));
		}
    }

	public boolean isElementEnabled(String element) {
		// Default to True so it returns False
		return !ELEMENT_STATUS.getOrDefault(element, true);
	}

}
