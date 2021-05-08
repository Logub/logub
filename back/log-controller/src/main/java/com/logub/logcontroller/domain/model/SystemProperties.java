package com.logub.logcontroller.domain.model;

import static lombok.AccessLevel.PRIVATE;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;

import java.util.Optional;

@Value
@AllArgsConstructor(access = PRIVATE)
@Builder(toBuilder = true)
public class SystemProperties {
 @Builder.Default
 Optional<String> imageName = Optional.empty();

 @Builder.Default
 Optional<String>  containerName= Optional.empty();

 @Builder.Default
 Optional<String>  env= Optional.empty();

 @Builder.Default
 Optional<String>  host= Optional.empty();

 @Builder.Default
 Optional<String>  service = Optional.empty();

 @Builder.Default
 Optional<String>  logger = Optional.empty();

 @Builder.Default
 Optional<String>  thread = Optional.empty();

}
