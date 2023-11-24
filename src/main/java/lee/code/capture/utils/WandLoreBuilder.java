package lee.code.capture.utils;

import lee.code.capture.lang.Lang;
import org.bukkit.Material;
import org.bukkit.entity.*;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.meta.ItemMeta;

public class WandLoreBuilder {
  private final Entity entity;
  private final ItemMeta itemMeta;

  public WandLoreBuilder(Entity entity, ItemMeta itemMeta) {
    this.entity = entity;
    this.itemMeta = itemMeta;
  }

  public void build() {
    setWandLore(entity, itemMeta);
  }

  private void setWandLore(Entity entity, ItemMeta itemMeta) {
    final StringBuilder line = new StringBuilder();
    line.append(Lang.MOB_DATA_TYPE.getString(new String[]{CoreUtil.capitalize(entity.getType().name())}));
    line.append("\n");

    final String customName = createCustomName(entity);
    if (!customName.isBlank()) line.append(customName);

    final String mobHealth = createMobHeath(entity);
    if (!mobHealth.isBlank()) line.append(mobHealth);

    final String onFire = createMobLoreOnFire(entity);
    if (!onFire.isBlank()) line.append(onFire);

    final String hasChest = createMobLoreHasChest(entity);
    if (!hasChest.isBlank()) line.append(hasChest);

    final String hasSaddle = createMobLoreHasSaddle(entity);
    if (!hasSaddle.isBlank()) line.append(hasSaddle);

    final String owner = createMobLoreOwner(entity);
    if (!owner.isBlank()) line.append(owner);

    final String color = createMobLoreColor(entity);
    if (!color.isBlank()) line.append(color);

    final String variant = createMobLoreVariant(entity);
    if (!variant.isBlank()) line.append(variant);

    final String villagerLore = createMobLoreVillager(entity);
    if (!villagerLore.isBlank()) line.append(villagerLore);

    final String fishLore = createMobLoreFish(entity);
    if (!fishLore.isBlank()) line.append(fishLore);

    final String pandaLore = createMobLorePanda(entity);
    if (!pandaLore.isBlank()) line.append(pandaLore);

    final String beeLore = createMobLoreBee(entity);
    if (!beeLore.isBlank()) line.append(beeLore);

    final String horseLore = createMobLoreHorse(entity);
    if (!horseLore.isBlank()) line.append(horseLore);

    final String equipped = createMobLoreEquipped(entity);
    if (!equipped.isBlank()) line.append(equipped);

    ItemUtil.setItemLore(itemMeta, line.toString());
  }

  @SuppressWarnings("deprecation")
  private static String createCustomName(Entity entity) {
    final StringBuilder line = new StringBuilder();
    if (entity.getCustomName() != null) line.append(Lang.MOB_DATA_CUSTOM_NAME.getString(new String[]{entity.getCustomName()}));
    return line.isEmpty() ? line.toString() : line + "\n";
  }

  private String createMobHeath(Entity entity) {
    final StringBuilder line = new StringBuilder();
    if (entity instanceof LivingEntity livingEntity) {
      line.append(Lang.MOB_DATA_HEALTH.getString(new String[]{String.valueOf(livingEntity.getHealth())}));
    }
    return line.isEmpty() ? line.toString() : line + "\n";
  }

  private String createMobLoreVillager(Entity entity) {
    final StringBuilder line = new StringBuilder();
    if (entity instanceof Villager villager) {
      line.append(Lang.MOB_DATA_VILLAGER_PROFESSION.getString(new String[]{CoreUtil.capitalize(villager.getProfession().name())}));
      line.append("\n").append(Lang.MOB_DATA_VILLAGER_TYPE.getString(new String[]{CoreUtil.capitalize(villager.getVillagerType().name())}));
      line.append("\n").append(Lang.MOB_DATA_VILLAGER_LEVEL.getString(new String[]{String.valueOf(villager.getVillagerLevel())}));
    }
    return line.isEmpty() ? line.toString() : line + "\n";
  }

  private String createMobLoreFish(Entity entity) {
    final StringBuilder line = new StringBuilder();
    if (entity instanceof TropicalFish fish) {
      line.append(Lang.MOB_DATA_FISH_PATTERN.getString(new String[]{CoreUtil.capitalize(fish.getPattern().name())}));
      line.append("\n").append(Lang.MOB_DATA_FISH_PATTERN_COLOR.getString(new String[]{CoreUtil.capitalize(fish.getPatternColor().name())}));
      line.append("\n").append(Lang.MOB_DATA_FISH_BODY_COLOR.getString(new String[]{CoreUtil.capitalize(fish.getBodyColor().name())}));
    }
    return line.isEmpty() ? line.toString() : line + "\n";
  }

  private String createMobLorePanda(Entity entity) {
    final StringBuilder line = new StringBuilder();
    if (entity instanceof Panda panda) {
      line.append(Lang.MOB_DATA_PANDA_GENE.getString(new String[]{CoreUtil.capitalize(panda.getMainGene().name())}));
      line.append("\n").append(Lang.MOB_DATA_PANDA_HIDDEN_GENE.getString(new String[]{CoreUtil.capitalize(panda.getHiddenGene().name())}));
    }
    return line.isEmpty() ? line.toString() : line + "\n";
  }

  private String createMobLoreBee(Entity entity) {
    final StringBuilder line = new StringBuilder();
    if (entity instanceof Bee bee) {
      final String nectar = bee.hasNectar() ? Lang.YES_RESULT.getString(null) : Lang.NO_RESULT.getString(null);
      final String stung = bee.hasStung() ? Lang.YES_RESULT.getString(null) : Lang.NO_RESULT.getString(null);
      line.append(Lang.MOB_DATA_BEE_HAS_NECTAR.getString(new String[]{nectar}));
      line.append("\n").append(Lang.MOB_DATA_BEE_HAS_STUNG.getString(new String[]{stung}));
    }
    return line.isEmpty() ? line.toString() : line + "\n";
  }

  private String createMobLoreOnFire(Entity entity) {
    final StringBuilder line = new StringBuilder();
    if (entity.getFireTicks() > 0) {
      line.append(Lang.MOB_DATA_ON_FIRE.getString(new String[]{Lang.YES_RESULT.getString(null)}));
    }
    return line.isEmpty() ? line.toString() : line + "\n";
  }

  private String createMobLoreHasChest(Entity entity) {
    final StringBuilder line = new StringBuilder();
    if (entity instanceof Mule mule) {
      if (mule.isCarryingChest()) line.append(Lang.MOB_DATA_HAS_CHEST.getString(new String[]{Lang.YES_RESULT.getString(null)}));
    }
    return line.isEmpty() ? line.toString() : line + "\n";
  }

  private String createMobLoreHasSaddle(Entity entity) {
    final StringBuilder line = new StringBuilder();
    if (entity instanceof Mule mule) {
      if (mule.getInventory().getSaddle() != null) line.append(Lang.MOB_DATA_HAS_SADDLE.getString(new String[]{Lang.YES_RESULT.getString(null)}));
    } else if (entity instanceof Horse horse) {
      if (horse.getInventory().getSaddle() != null) line.append(Lang.MOB_DATA_HAS_SADDLE.getString(new String[]{Lang.YES_RESULT.getString(null)}));
    } else if (entity instanceof Pig pig) {
      if (pig.hasSaddle()) line.append(Lang.MOB_DATA_HAS_SADDLE.getString(new String[] {Lang.YES_RESULT.getString(null)}));
    } else if (entity instanceof Strider strider) {
      if (strider.hasSaddle()) line.append(Lang.MOB_DATA_HAS_SADDLE.getString(new String[] {Lang.YES_RESULT.getString(null)}));
    } else if (entity instanceof Camel camel) {
      if (camel.getInventory().getSaddle() != null) line.append(Lang.MOB_DATA_HAS_SADDLE.getString(new String[] {Lang.YES_RESULT.getString(null)}));
    }
    return line.isEmpty() ? line.toString() : line + "\n";
  }

  private String createMobLoreOwner(Entity entity) {
    final StringBuilder line = new StringBuilder();
    if (entity instanceof Wolf wolf) {
      if (wolf.getOwner() != null) line.append(Lang.MOB_DATA_OWNER.getString(new String[]{wolf.getOwner().getName()}));
    } else if (entity instanceof Horse horse) {
      if (horse.getOwner() != null) line.append(Lang.MOB_DATA_OWNER.getString(new String[]{horse.getOwner().getName()}));
    } else if (entity instanceof Parrot parrot) {
      if (parrot.getOwner() != null) line.append(Lang.MOB_DATA_OWNER.getString(new String[]{parrot.getOwner().getName()}));
    } else if (entity instanceof Mule mule) {
      if (mule.getOwner() != null) line.append(Lang.MOB_DATA_OWNER.getString(new String[]{mule.getOwner().getName()}));
    }
    return line.isEmpty() ? line.toString() :  line + "\n";
  }

  private String createMobLoreColor(Entity entity) {
    final StringBuilder line = new StringBuilder();
    if (entity instanceof Sheep sheep) {
      if (sheep.getColor() != null) line.append(Lang.MOB_DATA_COLOR.getString(new String[]{CoreUtil.capitalize(sheep.getColor().name())}));
    } else if (entity instanceof Shulker shulker) {
      if (shulker.getColor() != null) line.append(Lang.MOB_DATA_COLOR.getString(new String[]{CoreUtil.capitalize(shulker.getColor().name())}));
    } else if (entity instanceof Llama llama) {
      line.append(Lang.MOB_DATA_COLOR.getString(new String[]{CoreUtil.capitalize(llama.getColor().name())}));
    }
    return line.isEmpty() ? line.toString() : line + "\n";
  }

  private String createMobLoreHorse(Entity entity) {
    final StringBuilder line = new StringBuilder();
    if (entity instanceof Horse horse) {
      line.append(Lang.MOB_DATA_HORSE_STYLE.getString(new String[]{CoreUtil.capitalize(horse.getStyle().name())}));
    }
    return line.isEmpty() ? line.toString() : line + "\n";
  }

  private String createMobLoreEquipped(Entity entity) {
    final StringBuilder line = new StringBuilder();
    if (entity instanceof LivingEntity livingEntity) {
      if (livingEntity.getEquipment() != null) {
        final EntityEquipment entityEquipment = livingEntity.getEquipment();
        if (entityEquipment.getHelmet() != null && !entityEquipment.getHelmet().getType().equals(Material.AIR)) {
          line.append(Lang.MOB_DATA_HELMET.getString(new String[]{CoreUtil.capitalize(entityEquipment.getHelmet().getType().name())}));
        }
        if (entityEquipment.getChestplate() != null && !entityEquipment.getChestplate().getType().equals(Material.AIR)) {
          if (!line.isEmpty()) line.append("\n");
          line.append(Lang.MOB_DATA_CHESTPLATE.getString(new String[]{CoreUtil.capitalize(entityEquipment.getChestplate().getType().name())}));
        }
        if (entityEquipment.getLeggings() != null && !entityEquipment.getLeggings().getType().equals(Material.AIR)) {
          if (!line.isEmpty()) line.append("\n");
          line.append(Lang.MOB_DATA_LEGGINGS.getString(new String[]{CoreUtil.capitalize(entityEquipment.getLeggings().getType().name())}));
        }
        if (entityEquipment.getBoots() != null && !entityEquipment.getBoots().getType().equals(Material.AIR)) {
          if (!line.isEmpty()) line.append("\n");
          line.append(Lang.MOB_DATA_BOOTS.getString(new String[]{CoreUtil.capitalize(entityEquipment.getBoots().getType().name())}));
        }
        if (!entityEquipment.getItemInMainHand().getType().equals(Material.AIR)) {
          if (!line.isEmpty()) line.append("\n");
          line.append(Lang.MOB_DATA_MAIN_HAND.getString(new String[]{CoreUtil.capitalize(entityEquipment.getItemInMainHand().getType().name())}));
        }
        if (!entityEquipment.getItemInOffHand().getType().equals(Material.AIR)) {
          if (!line.isEmpty()) line.append("\n");
          line.append(Lang.MOB_DATA_OFF_HAND.getString(new String[]{CoreUtil.capitalize(entityEquipment.getItemInOffHand().getType().name())}));
        }
      }
    }
    return line.isEmpty() ? line.toString() : line + "\n";
  }

  private String createMobLoreVariant(Entity entity) {
    final StringBuilder line = new StringBuilder();
    if (entity instanceof Frog frog) {
      line.append(Lang.MOB_DATA_VARIANT.getString(new String[]{CoreUtil.capitalize(frog.getVariant().name())}));
    } else if (entity instanceof Axolotl axolotl) {
      line.append(Lang.MOB_DATA_VARIANT.getString(new String[]{CoreUtil.capitalize(axolotl.getVariant().name())}));
    } else if (entity instanceof Parrot parrot) {
      line.append(Lang.MOB_DATA_VARIANT.getString(new String[]{CoreUtil.capitalize(parrot.getVariant().name())}));
    } else if (entity instanceof Cat cat) {
      line.append(Lang.MOB_DATA_VARIANT.getString(new String[]{CoreUtil.capitalize(cat.getCatType().name())}));
    } else if (entity instanceof Fox fox) {
      line.append(Lang.MOB_DATA_VARIANT.getString(new String[]{CoreUtil.capitalize(fox.getFoxType().name())}));
    } else if (entity instanceof Rabbit rabbit) {
      line.append(Lang.MOB_DATA_VARIANT.getString(new String[]{CoreUtil.capitalize(rabbit.getRabbitType().name())}));
    }
    return line.isEmpty() ? line.toString() : line + "\n";
  }
}
