package org.orglot.gosloto.admin.backend.image.repostiry;

import org.orglot.gosloto.components.entity.image.Image;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageRepository extends JpaRepository<Image, Long> {

}
