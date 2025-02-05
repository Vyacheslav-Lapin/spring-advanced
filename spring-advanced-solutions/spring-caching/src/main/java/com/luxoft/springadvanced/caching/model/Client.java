package com.luxoft.springadvanced.caching.model;

import lombok.Data;
import lombok.NonNull;

@Data
public class Client {
  @NonNull String name;
  @NonNull String address;
  String phone;
}
