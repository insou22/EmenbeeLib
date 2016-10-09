package net.emenbee.lib.locale;

import com.google.common.base.Optional;
import net.emenbee.lib.except.TryUtils;
import net.emenbee.lib.message.ChatUtil;
import net.emenbee.lib.message.Strings;
import net.emenbee.lib.plugin.LibPlugin;
import org.apache.commons.collections4.map.CaseInsensitiveMap;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.util.Map;

public class Locale {

    private Map<String, String> locale = new CaseInsensitiveMap<>();

    public void populate(LibPlugin plugin) {
        File file = new File(plugin.getDataFolder(), "messages.yml");

        if (!file.exists()) {
            file.mkdirs();

            if (plugin.getResource("messages.yml") != null) {
                plugin.saveResource("messages.yml", false);
            } else {
                TryUtils.sneaky(file::createNewFile);
            }
        }

        YamlConfiguration config = YamlConfiguration.loadConfiguration(file);

        config.getKeys(false).forEach(key -> this.register(key, config.getString(key)));
    }

    private void register(String key, String value) {
        this.locale.put(key, value);
    }

    public String getRaw(String key) {
        return Optional.of(this.locale.get(key)).or(key);
    }

    public String getFormatted(String key, Object... args) {
        return ChatUtil.color(Strings.format(this.getRaw(key), args));
    }

}
