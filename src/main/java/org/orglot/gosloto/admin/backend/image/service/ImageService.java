package org.orglot.gosloto.admin.backend.image.service;

import java.util.List;
import lombok.AllArgsConstructor;
import org.orglot.gosloto.admin.backend.image.repostiry.ImageRepository;
import org.orglot.gosloto.components.entity.image.Image;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ImageService {

  private final ImageRepository imageRepository;

  public List<Image> findAll() {
    return imageRepository.findAll();
  }
}
