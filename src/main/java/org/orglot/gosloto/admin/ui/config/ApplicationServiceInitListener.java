package org.orglot.gosloto.admin.ui.config;

import com.vaadin.flow.router.RouteConfiguration;
import com.vaadin.flow.server.ServiceInitEvent;
import com.vaadin.flow.server.VaadinServiceInitListener;
import lombok.RequiredArgsConstructor;
import org.orglot.gosloto.admin.backend.repository.GenericRepository;
import org.orglot.gosloto.admin.ui.MainLayout;
import org.orglot.gosloto.admin.ui.views.abstraction.AbstractListView;
import org.orglot.gosloto.domain.achievement.AchievementType;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class ApplicationServiceInitListener implements VaadinServiceInitListener {

  private final ApplicationContext applicationContext;
  private final GenericRepository genericRepository;

  @Override
  public void serviceInit(ServiceInitEvent event) {
//    new AbstractListView(){};
//    configuration.setAnnotatedRoute();
    //TODO: create factory with bean
    var abstractListView = new AbstractListView<AchievementType>(applicationContext, genericRepository) {};
    // add view only during development time
        if (!event.getSource().getDeploymentConfiguration().isProductionMode()) {
          RouteConfiguration configuration = RouteConfiguration.forApplicationScope();
          configuration.setRoute("crudec", abstractListView.getClass(), MainLayout.class);

        }
  }
}
