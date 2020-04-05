package org.orglot.gosloto.admin.backend.achievement.service;

import java.util.List;
import lombok.AllArgsConstructor;
import org.orglot.gosloto.admin.backend.achievement.dao.AchievementTypeRepository;
import org.orglot.gosloto.domain.achievement.AchievementType;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AchievementTypeService {

  private final AchievementTypeRepository achievementTypeRepository;


  public List<AchievementType> findAll(Integer countRows, Integer versionFilter) {
    if (versionFilter == null) {
      return achievementTypeRepository.findAll(PageRequest.of(0, countRows))
          .getContent();
    } else {
      return achievementTypeRepository.findAllByVersion(versionFilter, PageRequest.of(0, countRows))
          .getContent();
    }
  }

  public AchievementType save(AchievementType achievementType) {
    return achievementTypeRepository.save(achievementType);
  }

  public void delete(AchievementType achievementType) {
     achievementTypeRepository.delete(achievementType);
  }
}
