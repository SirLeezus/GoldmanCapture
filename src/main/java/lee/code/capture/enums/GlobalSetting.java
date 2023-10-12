package lee.code.capture.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum GlobalSetting {
  WAND_ID(2),
  ;
  @Getter private final int id;
}
