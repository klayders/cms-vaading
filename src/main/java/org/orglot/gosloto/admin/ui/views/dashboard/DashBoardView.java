package org.orglot.gosloto.admin.ui.views.dashboard;

import com.vaadin.flow.component.charts.Chart;
import com.vaadin.flow.component.charts.model.ChartType;
import com.vaadin.flow.component.charts.model.DataSeries;
import com.vaadin.flow.component.charts.model.DataSeriesItem;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.orglot.gosloto.admin.backend.achievement.service.AchievementTypeService;
import org.orglot.gosloto.admin.backend.image.service.ImageService;
import org.orglot.gosloto.admin.ui.MainLayout;
import org.orglot.gosloto.annotation.admin.table.AdminColumn;
import org.orglot.gosloto.domain.achievement.AchievementType;

@PageTitle("DashBoard | Stoloto CMS")
@Route(value = "dashboard", layout = MainLayout.class)
public class DashBoardView extends VerticalLayout {

  public static void main(String[] args) {
    Class<AchievementType> achievementTypeClass = AchievementType.class;
    for(var field : achievementTypeClass.getDeclaredFields()){
      for(var annotation : field.getAnnotations()){
        if (annotation instanceof AdminColumn){
          System.out.println(field.getName());
        }
      }
    }
  }

  private final AchievementTypeService achievementTypeService;
  private final ImageService imageService;

  public DashBoardView(AchievementTypeService achievementTypeService,
                       ImageService imageService) {
    this.achievementTypeService = achievementTypeService;
    this.imageService = imageService;

    addClassName("dashboard-view");
    setDefaultHorizontalComponentAlignment(Alignment.CENTER);

    add(
        getAchievementTypeStats(),
        getImagesChart()
    );
  }

  private Chart getImagesChart() {
    var chart = new Chart(ChartType.PIE);
    var dataSeries = new DataSeries();
    var stats = imageService.getStats();
    stats.forEach((name, number) -> dataSeries.add(new DataSeriesItem(name, number)));

    chart.getConfiguration().setSeries(dataSeries);

    return chart;
  }

  private Span getAchievementTypeStats() {
    var stats = new Span(achievementTypeService.count() + " медалей");
    stats.addClassName("achievement-type-stats");
    return stats;
  }
}
