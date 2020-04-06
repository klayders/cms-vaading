package org.orglot.gosloto.admin.ui.config;

import com.vaadin.flow.router.RouteConfiguration;
import com.vaadin.flow.server.ServiceInitEvent;
import com.vaadin.flow.server.VaadinServiceInitListener;
import lombok.RequiredArgsConstructor;
import org.orglot.gosloto.admin.backend.repository.GenericRepository;
import org.orglot.gosloto.admin.ui.MainLayout;
import org.orglot.gosloto.admin.ui.views.abstraction.AbstractListView;
import org.orglot.gosloto.app.model.config.DefaultSiteConfigService.SiteConfig;
import org.orglot.gosloto.components.entity.image.Image;
import org.orglot.gosloto.domain.achievement.AchievementType;
import org.orglot.gosloto.domain.agent.Agent;
import org.orglot.gosloto.domain.faq.FAQ;
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
//    var agent = new AbstractListView<Agent>(applicationContext, genericRepository) {};
    var faq = new AbstractListView<FAQ>(applicationContext, genericRepository) {};
    // add view only during development time
        if (!event.getSource().getDeploymentConfiguration().isProductionMode()) {
          RouteConfiguration configuration = RouteConfiguration.forApplicationScope();
          configuration.setRoute("crudec", abstractListView.getClass(), MainLayout.class);
//          configuration.setRoute("agent", agent.getClass(), MainLayout.class);
          configuration.setRoute("faq", faq.getClass(), MainLayout.class);

        }
  }
}
