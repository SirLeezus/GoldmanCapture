package lee.code.capture.lang;

import lee.code.capture.utils.CoreUtil;
import lombok.AllArgsConstructor;
import lombok.Getter;
import net.kyori.adventure.text.Component;

@AllArgsConstructor
public enum Lang {
  WAND_LORE("&7To capture a animal, fish or\n&7monster simply &eright-click\n&7one with this wand. It will\n&7be stored in the wand."),
  YES_RESULT("&aYes"),
  NO_RESULT("&cNo"),
  MOB_DATA_TYPE("&e&lType&7: &a{0}"),
  MOB_DATA_CUSTOM_NAME("&e&lCustom Name&7: &f{0}"),
  MOB_DATA_HEALTH("&e&lHealth&7: &a{0} &c❤"),
  MOB_DATA_OWNER("&e&lOwner&7: &a{0}"),
  MOB_DATA_ON_FIRE("&e&lOn Fire&7: {0}"),
  MOB_DATA_HAS_CHEST("&e&lChest&7: {0}"),
  MOB_DATA_HAS_SADDLE("&e&lSaddle&7: {0}"),
  MOB_DATA_COLOR("&e&lColor&7: &a{0}"),
  MOB_DATA_HELMET("&e&lHelmet&7: &a{0}"),
  MOB_DATA_CHESTPLATE("&e&lChestplate&7: &a{0}"),
  MOB_DATA_LEGGINGS("&e&lLeggings&7: &a{0}"),
  MOB_DATA_BOOTS("&e&lBoots&7: &a{0}"),
  MOB_DATA_MAIN_HAND("&e&lMain Hand&7: &a{0}"),
  MOB_DATA_OFF_HAND("&e&lOff Hand&7: &a{0}"),
  MOB_DATA_VILLAGER_PROFESSION("&e&lVillager Profession&7: &a{0}"),
  MOB_DATA_VILLAGER_TYPE("&e&lVillager Type&7: &a{0}"),
  MOB_DATA_FISH_BODY_COLOR("&e&lFish Body Color&7: &a{0}"),
  MOB_DATA_FISH_PATTERN("&e&lFish Pattern&7: &a{0}"),
  MOB_DATA_FISH_PATTERN_COLOR("&e&lFish Pattern Color&7: &a{0}"),
  MOB_DATA_PANDA_GENE("&e&lPanda Gene&7: &a{0}"),
  MOB_DATA_PANDA_HIDDEN_GENE("&e&lPanda Hidden Gene&7: &a{0}"),
  MOB_DATA_BEE_HAS_NECTAR("&e&lHas Nectar&7: {0}"),
  MOB_DATA_BEE_HAS_STUNG("&e&lHas Stung&7: {0}"),
  MOB_DATA_HORSE_STYLE("&e&lHorse Style&7: &a{0}"),
  MOB_DATA_VARIANT("&e&lVariant&7: &a{0}"),
  ERROR_ENTITY_NOT_SUPPORTED("&cThat entity can't be captured!"),

  ;
  @Getter private final String string;

  public String getString(String[] variables) {
    String value = string;
    if (variables == null || variables.length == 0) return value;
    for (int i = 0; i < variables.length; i++) value = value.replace("{" + i + "}", variables[i]);
    return value;
  }

  public Component getComponent(String[] variables) {
    String value = string;
    if (variables == null || variables.length == 0) return CoreUtil.parseColorComponent(value);
    for (int i = 0; i < variables.length; i++) value = value.replace("{" + i + "}", variables[i]);
    return CoreUtil.parseColorComponent(value);
  }
}
