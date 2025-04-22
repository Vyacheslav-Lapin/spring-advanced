package com.luxoft.data.examples.controllers;

import com.luxoft.data.examples.model.Address;
import com.luxoft.data.examples.repositories.AddressRepository;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("addresses")
public class HateoasAddressController {

  AddressRepository addressRepository;

  @GetMapping("{id:\\d+}")
  ResponseEntity<EntityModel<Address>> findById(@PathVariable Long id) {
    return addressRepository.findById(id)
                     .map(content -> EntityModel.of(content,
                                                    linkTo(methodOn(HateoasAddressController.class).findById(id)).withSelfRel()))
                     .map(ResponseEntity::ok)
                     .orElseGet(() -> ResponseEntity.notFound().build());
  }

  @GetMapping
  ResponseEntity<CollectionModel<Address>> findAll() {
    val addresses = addressRepository.findAll();//.stream()
//        .map(EntityModel::of)
//        .toList();
    val selfRel = linkTo(methodOn(HateoasAddressController.class).findAll()).withSelfRel();
    val model = CollectionModel.of(addresses, selfRel);
    return ResponseEntity.ok(model);
  }
}
