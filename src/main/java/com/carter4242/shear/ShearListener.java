package com.carter4242.shear;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.entity.EntityType;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.entity.Player;
import org.bukkit.advancement.AdvancementProgress;
import org.bukkit.inventory.EquipmentSlot;

public class ShearListener implements Listener {
    private final ShearPlugin plugin;

    public ShearListener(ShearPlugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerInteractEntity(PlayerInteractEntityEvent event) {
        Player player = event.getPlayer();
        Entity clicked = event.getRightClicked();
        EquipmentSlot hand = event.getHand();

        // Is Wolf?
        if (clicked.getType() != EntityType.WOLF) return;

        // Determine the item in the hand used for this interaction
        ItemStack held = (hand == EquipmentSlot.HAND)
            ? player.getInventory().getItemInMainHand()
            : player.getInventory().getItemInOffHand();
        // Holding Shears?
        if (held == null || held.getType() != Material.SHEARS) return;

        // Wolf wearing something?
        if (!(clicked instanceof LivingEntity)) return;
        EntityEquipment equip = ((LivingEntity) clicked).getEquipment();
        if (equip == null) return;

        // It is WOLF_ARMOR?
        ItemStack bodyItem = equip.getItem(EquipmentSlot.BODY);
        if (bodyItem == null || bodyItem.getType() != Material.WOLF_ARMOR) return;

        // All checks passed — grant advancement if not already completed
        if (ShearPlugin.ADV != null) {
            AdvancementProgress progress = player.getAdvancementProgress(ShearPlugin.ADV);
            if (!progress.isDone()) { // Player has it already?
                for (String criterion : progress.getRemainingCriteria()) { // Award!
                    progress.awardCriteria(criterion);
                }
                plugin.getLogger().info("Granted advancement husbandry/remove_wolf_armor to " + player.getName());
            }
        }
    }
}
