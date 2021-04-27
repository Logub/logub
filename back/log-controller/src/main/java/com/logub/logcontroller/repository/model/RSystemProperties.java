package com.logub.logcontroller.repository.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Optional;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class RSystemProperties implements Serializable {
 private String imageName;
 private String  containerName;
 private String  service;
 private String  env;
 private String  host;
}
