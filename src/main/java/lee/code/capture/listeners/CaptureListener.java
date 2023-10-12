package lee.code.capture.listeners;

import lee.code.capture.Capture;
import lee.code.capture.enums.GlobalSetting;
import lee.code.capture.lang.Lang;
import lee.code.capture.utils.EntityUtil;
import lee.code.capture.utils.ItemUtil;
import lee.code.capture.utils.WandLoreBuilder;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.util.Vector;

public class CaptureListener implements Listener {
  private final Capture capture;

  public CaptureListener(Capture capture) {
    this.capture = capture;
  }

  @EventHandler(priority = EventPriority.MONITOR)
  public void onCapture(PlayerInteractAtEntityEvent e) {
    if (e.isCancelled()) return;
    final ItemStack handItem = e.getPlayer().getInventory().getItemInMainHand();
    final ItemMeta handMeta = handItem.getItemMeta();
    if (handMeta == null) return;
    if (!handMeta.hasCustomModelData() || GlobalSetting.WAND_ID.getId() != handMeta.getCustomModelData()) return;
    e.setCancelled(true);
    if (hasEntityKey(handMeta)) return;
    final Player player = e.getPlayer();
    final Entity entity = e.getRightClicked();
    if (capture.getDelayManager().isOnDelay(player.getUniqueId(), "wand")) return;
    capture.getDelayManager().setOnDelay(player.getUniqueId(), "wand", 500);
    if (!capture.getData().getSupportedEntity().contains(entity.getType())) {
      player.sendActionBar(Lang.ERROR_ENTITY_NOT_SUPPORTED.getComponent(null));
      return;
    }
    if (entity.getVehicle() != null) entity.getVehicle().removePassenger(player);
    setItemEntityKey(handMeta, EntityUtil.serializeEntity(entity), entity.getType().name());
    new WandLoreBuilder(entity, handMeta).build();
    setWandOrCreateWandData(player, handItem.getAmount(), handItem, handMeta);
    spawnEffect(entity.getLocation(), entity.getWorld());
    entity.remove();
  }

  @EventHandler(priority = EventPriority.MONITOR)
  public void onRelease(PlayerInteractEvent e) {
    if (!e.useInteractedBlock().equals(Event.Result.ALLOW)) return;
    if (!e.getAction().equals(Action.RIGHT_CLICK_BLOCK)) return;
    final Player player = e.getPlayer();
    final ItemStack handItem = player.getInventory().getItemInMainHand();
    final ItemMeta handMeta = handItem.getItemMeta();
    if (handMeta == null) return;
    if (!handMeta.hasCustomModelData() || GlobalSetting.WAND_ID.getId() != handMeta.getCustomModelData()) return;
    e.setCancelled(true);
    if (capture.getDelayManager().isOnDelay(player.getUniqueId(), "wand")) return;
    capture.getDelayManager().setOnDelay(player.getUniqueId(), "wand", 500);
    if (!hasEntityKey(handMeta)) return;
    final Block block = e.getClickedBlock();
    if (block == null) return;
    final Vector box = block.getBoundingBox().getCenter();
    final Location location = box.equals(new Vector(0, 0, 0)) ? block.getLocation() : new Location(block.getWorld(), box.getX(), box.getY() + 0.5, box.getZ());
    EntityUtil.spawnEntity(getEntityKey(handMeta), getEntityType(handMeta), location);
    removeItemEntityKey(handMeta);
    ItemUtil.setItemLore(handMeta, Lang.WAND_LORE.getString(null));
    handItem.setItemMeta(handMeta);
    spawnEffect(location, block.getWorld());
  }

  private void spawnEffect(Location location, World world) {
    world.playSound(location, Sound.ENTITY_ENDERMAN_TELEPORT, (float) 0.5, (float) 1);
    world.playEffect(location, Effect.ENDER_SIGNAL, 0);
  }

  private void setWandOrCreateWandData(Player player, int stackAmount, ItemStack handItem, ItemMeta handMeta) {
    if (stackAmount > 1) {
      handItem.setAmount(stackAmount - 1);
      final ItemStack handItemCopy = new ItemStack(handItem);
      handItemCopy.setAmount(1);
      handItemCopy.setItemMeta(handMeta);
      ItemUtil.giveItemOrDrop(player, handItemCopy, 1);
    } else {
      handItem.setItemMeta(handMeta);
    }
  }

  private void setItemEntityKey(ItemMeta itemMeta, String key, String type) {
    final PersistentDataContainer container = itemMeta.getPersistentDataContainer();
    container.set(new NamespacedKey(capture, "entity-key"), PersistentDataType.STRING, key);
    container.set(new NamespacedKey(capture, "entity-type"), PersistentDataType.STRING, type);
  }

  private void removeItemEntityKey(ItemMeta itemMeta) {
    final PersistentDataContainer container = itemMeta.getPersistentDataContainer();
    container.remove(new NamespacedKey(capture, "entity-key"));
    container.remove(new NamespacedKey(capture, "entity-type"));
  }

  private boolean hasEntityKey(ItemMeta itemMeta) {
    return itemMeta.getPersistentDataContainer().has(new NamespacedKey(capture, "entity-key"), PersistentDataType.STRING);
  }

  private String getEntityKey(ItemMeta itemMeta) {
    return itemMeta.getPersistentDataContainer().get(new NamespacedKey(capture, "entity-key"), PersistentDataType.STRING);
  }

  private EntityType getEntityType(ItemMeta itemMeta) {
    return EntityType.valueOf(itemMeta.getPersistentDataContainer().get(new NamespacedKey(capture, "entity-type"), PersistentDataType.STRING));
  }
}
