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
 private Optional<String> imageName;
 private Optional<String>  containerName;
 private Optional<String>  service;
 private Optional<String>  env;
 private Optional<String>  host;
}
