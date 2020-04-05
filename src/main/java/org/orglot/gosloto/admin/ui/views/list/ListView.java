package org.orglot.gosloto.admin.ui.views.list;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.orglot.gosloto.admin.backend.achievement.service.AchievementTypeService;
import org.orglot.gosloto.admin.backend.image.service.ImageService;
import org.orglot.gosloto.admin.ui.MainLayout;
import org.orglot.gosloto.admin.ui.views.abstraction.AbstractForm;
import org.orglot.gosloto.admin.ui.views.list.test.TestForm;
import org.orglot.gosloto.domain.achievement.AchievementType;
import org.springframework.context.ApplicationContext;
import org.springframework.context.MessageSource;


@Route(value = "admin", layout = MainLayout.class)
@PageTitle("Медаль | Столото cms")
public class ListView extends VerticalLayout {

  private final TestForm entityForm;

  private final Grid<AchievementType> grid = new Grid<>(AchievementType.class);
  private final AchievementTypeService achievementTypeService;
  private final IntegerField versionFilter = new IntegerField("Фильтр по версии", null, event -> updateList());
  private final IntegerField countSelectedRows = new IntegerField("Число записей на странице:", 50, event -> updateList());

  public ListView(AchievementTypeService achievementTypeService,
                  ImageService imageService,
                  ApplicationContext applicationContext) {
    this.achievementTypeService = achievementTypeService;

    entityForm = new TestForm(imageService.findAll(), applicationContext);
//    entityForm.addListener(AbstractForm.SaveEvent.class, entityForm::saveAchievementType);
//    entityForm.addListener(AbstractForm.DeleteEvent.class, entityForm::deleteAchievementType);
//    entityForm.addListener(AbstractForm.CloseEvent.class, event -> entityForm.closeEditor());

    var content = new Div(grid, entityForm);
    content.addClassName("content");
    content.setSizeFull();

    addClassName("list-view");
    setSizeFull();

    configureGrid();
    getToolbar();

    add(getToolbar(), content);

    updateList();

    entityForm.closeEditor();
  }


  private HorizontalLayout getToolbar() {
    versionFilter.setMaxWidth("150px");
    versionFilter.setClearButtonVisible(true);
    versionFilter.setValueChangeMode(ValueChangeMode.LAZY);

    if (countSelectedRows.getValue() == null || countSelectedRows.getValue() <= 0) {
      countSelectedRows.setValue(50);
    }
    countSelectedRows.setMaxWidth("150px");
    countSelectedRows.setClearButtonVisible(true);
    countSelectedRows.setValueChangeMode(ValueChangeMode.LAZY);

    var addAchievementTypeButton = new Button("Создать медаль", click -> addAchievementType());

    var toolBar = new HorizontalLayout(versionFilter, countSelectedRows, addAchievementTypeButton);
    toolBar.setClassName("toolbar");

    return toolBar;

  }

  private void addAchievementType() {
    grid.asSingleSelect().clear();
    entityForm.editEntity(new AchievementType());
  }


  private void configureGrid() {
    grid.addClassName("main-grid");
    grid.setSizeFull();
    //      grid.setColumns("type", "shortDescription", "row", "position", "description", "fullDescription", "smallPicId");
    grid.setColumns("id", "published", "shortDescription", "type", "version");
    grid.getColumns().forEach(column -> column.setAutoWidth(true));

    grid.asSingleSelect().addValueChangeListener(event -> entityForm.editEntity(event.getValue()));
  }






  private void updateList() {
    grid.setItems(achievementTypeService.findAll(countSelectedRows.getValue(), versionFilter.getValue()));
  }


}
