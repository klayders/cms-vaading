package org.orglot.gosloto.admin.ui.views.list.test;

import com.vaadin.flow.router.Route;
import org.orglot.gosloto.admin.backend.repository.GenericRepository;
import org.orglot.gosloto.admin.ui.MainLayout;
import org.orglot.gosloto.admin.ui.views.abstraction.AbstractListView;
import org.orglot.gosloto.domain.achievement.AchievementType;
import org.springframework.context.ApplicationContext;

//@Route(value = "admin", layout = MainLayout.class)
public class TestListView extends AbstractListView<AchievementType> {

  public TestListView(ApplicationContext applicationContext, GenericRepository genericRepository) {
    super(applicationContext, genericRepository);
  }
}
