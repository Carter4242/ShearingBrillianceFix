package com.carter4242.shear;

import org.bukkit.NamespacedKey;
import org.bukkit.plugin.java.JavaPlugin;

public class ShearPlugin extends JavaPlugin {
    public static org.bukkit.advancement.Advancement ADV;

    @Override
    public void onEnable() {
        getLogger().info("ShearPlugin enabled");
        getServer().getPluginManager().registerEvents(new ShearListener(this), this);
        ADV = getServer().getAdvancement(NamespacedKey.minecraft("husbandry/remove_wolf_armor"));
    }

    @Override
    public void onDisable() {
        getLogger().info("ShearPlugin disabled");
        ADV = null;
    }
}
