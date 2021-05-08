package com.logub.logcontroller.api;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Optional;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class SystemPropertiesDto {
  @Builder.Default
  private Optional<String> imageName = Optional.empty();

  @Builder.Default
  private Optional<String> containerName = Optional.empty();

  @Builder.Default
  private Optional<String> env = Optional.empty();

  @Builder.Default
  private Optional<String> host = Optional.empty();

}
