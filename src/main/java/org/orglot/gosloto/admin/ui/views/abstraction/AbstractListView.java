package org.orglot.gosloto.admin.ui.views.abstraction;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.data.value.ValueChangeMode;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.ParameterizedType;
import java.util.HashSet;
import java.util.Locale;
import org.orglot.gosloto.admin.backend.repository.GenericRepository;
import org.orglot.gosloto.annotation.admin.table.AdminColumn;
import org.orglot.gosloto.dao.managed.dao.ManagedEntity;
import org.springframework.context.ApplicationContext;
import org.springframework.data.jpa.repository.JpaRepository;


//@PageTitle("Медаль | Столото cms")
public abstract class AbstractListView<T extends ManagedEntity> extends VerticalLayout {

  private final AbstractEditForm<T> entityForm;
  private final Class<T> persistentEntity;
  private final ApplicationContext applicationContext;

  private final Grid<T> grid;
  private final JpaRepository repository;
  private final IntegerField versionFilter = new IntegerField("Фильтр по версии", null, event -> updateList());
  private final IntegerField countSelectedRows = new IntegerField("Число записей на странице:", 50, event -> updateList());

  public AbstractListView(ApplicationContext applicationContext,
                          GenericRepository genericRepository) {
    this.applicationContext = applicationContext;
    persistentEntity = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass())
        .getActualTypeArguments()[0];
    this.repository = genericRepository.getRepository(persistentEntity);
    grid = new Grid<>(persistentEntity);
    entityForm = new AbstractEditForm<>(applicationContext, persistentEntity, repository, grid) {};

    //    entityForm = new TestForm(imageService.findAll(), applicationContext);

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

    var addAchievementTypeButton = new Button("Создать медаль", click -> createEntity());

    var toolBar = new HorizontalLayout(versionFilter, countSelectedRows, addAchievementTypeButton);
    toolBar.setClassName("toolbar");

    return toolBar;

  }

  private void configureGrid() {
    grid.addClassName("main-grid");
    grid.setSizeFull();
    //      grid.setColumns("type", "shortDescription", "row", "position", "description", "fullDescription", "smallPicId");

    var fieldsList = new HashSet<String>();
    fieldsList.add("published");
    fieldsList.add("id");
    for (var field : persistentEntity.getDeclaredFields()) {
      for (var annotation : field.getAnnotations()) {
        if (AdminColumn.class.equals(annotation.annotationType())) {
          fieldsList.add(field.getName());
        }
      }
    }
    grid.setColumns(fieldsList.toArray(String[]::new));

    grid.getColumns().forEach(column -> {
      column.setAutoWidth(true);
      var fieldName  = column.getKey();
      final var i18nFieldName = applicationContext.getMessage(
          "admin." + persistentEntity.getSimpleName().toLowerCase() + "." + fieldName,
          null,
          fieldName,
          Locale.getDefault()
      );
      column.setHeader(i18nFieldName);
    });
    //    grid.setColumns("id", "published", "shortDescription", "type", "version");

    grid.asSingleSelect().addValueChangeListener(event -> entityForm.editEntity(event.getValue()));
  }

  private void updateList() {
    //    final List<AchievementType> all = repository.findAll(countSelectedRows.getValue(), versionFilter.getValue());
    var all = repository.findAll();
    grid.setItems(all);
  }

  private void createEntity() {
    grid.asSingleSelect().clear();
    T entity = null;
    try {
      entity = persistentEntity.getDeclaredConstructor().newInstance();
    } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
      e.printStackTrace();
    }
    entityForm.editEntity(entity);
  }


}
