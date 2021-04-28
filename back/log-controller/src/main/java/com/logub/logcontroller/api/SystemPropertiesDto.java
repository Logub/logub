package com.logub.logcontroller.api;

import static lombok.AccessLevel.PRIVATE;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Value;

import java.util.Optional;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class SystemPropertiesDto {
 @Builder.Default
 Optional<String> imageName = Optional.empty();

 @Builder.Default
 Optional<String>  containerName= Optional.empty();

 @Builder.Default
 Optional<String>  service= Optional.empty();

 @Builder.Default
 Optional<String>  env= Optional.empty();

 @Builder.Default
 Optional<String>  host= Optional.empty();
}
