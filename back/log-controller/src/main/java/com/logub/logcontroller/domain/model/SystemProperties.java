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
 private Optional<String> imageName;
 private Optional<String>  containerName;
 private Optional<String>  service;
 private Optional<String>  env;
 private Optional<String>  host;
}
