package org.orglot.gosloto.admin.backend.achievement.dao;

import org.orglot.gosloto.domain.achievement.AchievementType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AchievementTypeRepository extends JpaRepository<AchievementType, Long> {

  Page<AchievementType> findAllByVersion(int version, Pageable of);

}
