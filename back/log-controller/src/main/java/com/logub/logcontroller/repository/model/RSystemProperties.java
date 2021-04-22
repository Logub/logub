package com.logub.logcontroller.repository.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Optional;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class RSystemProperties {
 private Optional<String> imageName;
 private Optional<String>  containerName;
 private Optional<String>  service;
 private Optional<String>  env;
 private Optional<String>  host;
}
