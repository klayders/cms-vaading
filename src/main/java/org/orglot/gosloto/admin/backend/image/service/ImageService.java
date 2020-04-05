package org.orglot.gosloto.admin.backend.image.service;

import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.mapping;
import static java.util.stream.Collectors.toMap;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.transaction.Transactional;
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

  @Transactional
  public Map<String, Integer> getStats() {
//    var stats = new HashMap<String, Integer>();
    return findAll().stream()
        .collect(toMap(Image::getSliceName, ss -> ss.getResizes().size(), (a, b) -> a));
  }
}
