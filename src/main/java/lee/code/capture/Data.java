package lee.code.capture;

import lee.code.capture.enums.SupportedEntity;
import lombok.Getter;
import org.bukkit.entity.EntityType;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class Data {
  @Getter private final Set<EntityType> supportedEntity = ConcurrentHashMap.newKeySet();

  public Data() {
    loadData();
  }

  private void loadData() {
    for (SupportedEntity supported : SupportedEntity.values()) supportedEntity.add(supported.getEntityType());
  }
}
