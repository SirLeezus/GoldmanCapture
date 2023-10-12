package lee.code.capture.utils;

import net.kyori.adventure.text.Component;
import org.apache.commons.lang3.StringUtils;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class ItemUtil {

  public static void giveItemOrDrop(Player player, ItemStack item, int amount) {
    item.setAmount(1);
    if (canReceiveItems(player, item, amount)) giveItem(player, item, amount);
    else for (int i = 0; i < amount; i++) player.getWorld().dropItemNaturally(player.getLocation(), item);
  }

  public static boolean canReceiveItems(Player player, ItemStack item, int amount) {
    return getFreeSpace(player, item) >= amount;
  }

  public static int getFreeSpace(Player player, ItemStack item) {
    int freeSpaceCount = 0;
    for (int slot = 0; slot <= 35; slot++) {
      final ItemStack slotItem = player.getInventory().getItem(slot);
      if (slotItem == null || slotItem.getType() == Material.AIR) {
        freeSpaceCount += item.getMaxStackSize();
      } else if (slotItem.isSimilar(item))
        freeSpaceCount += Math.max(0, slotItem.getMaxStackSize() - slotItem.getAmount());
    }
    return freeSpaceCount;
  }

  public static void giveItem(Player player, ItemStack item, int amount) {
    if (item.getMaxStackSize() < 64) {
      for (int i = 0; i < amount; i++) {
        player.getInventory().addItem(item);
      }
    } else {
      item.setAmount(amount);
      player.getInventory().addItem(item);
    }
  }

  public static void setItemLore(ItemMeta itemMeta, String lore) {
    if (itemMeta == null) return;
    final String[] split = StringUtils.split(lore, "\n");
    final List<Component> pLines = new ArrayList<>();
    for (String line : split) pLines.add(CoreUtil.parseColorComponent(line));
    itemMeta.lore(pLines);
  }
}
