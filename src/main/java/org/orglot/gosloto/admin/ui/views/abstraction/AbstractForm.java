package org.orglot.gosloto.admin.ui.views.abstraction;

import com.vaadin.flow.component.AbstractField;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.Binder;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import lombok.Getter;
import org.orglot.gosloto.admin.ui.utils.ByteField;
import org.orglot.gosloto.admin.ui.utils.LongField;
import org.orglot.gosloto.admin.ui.utils.NumbersHelper;
import org.orglot.gosloto.admin.ui.utils.ShortField;
import org.orglot.gosloto.dao.managed.dao.ManagedEntity;
import org.springframework.context.ApplicationContext;

public abstract class AbstractForm<T extends ManagedEntity> extends FormLayout {

  private static final String ADMIN_I18N_PREFIX = "admin.";

  private final ApplicationContext applicationContext;

  private final Class<T> persistentEntity;
  private final Binder<T> entityBinder;
  private final List<AbstractField> vaadinComponentList = new ArrayList<>();

  private final Button save = new Button("Сохранить");
  private final Button delete = new Button("Удалить");
  private final Button close = new Button("Закрыть");


  public AbstractForm(ApplicationContext applicationContext) {
    this.applicationContext = applicationContext;

    persistentEntity = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass())
        .getActualTypeArguments()[0];

    //    addClassName("entity-form");
    addClassName("achievement-type-form");

    entityBinder = new BeanValidationBinder<>(persistentEntity);

    collectFieldsForVaadinForm(ManagedEntity.class);
    collectFieldsForVaadinForm(persistentEntity);

    add(vaadinComponentList.toArray(Component[]::new));
    add(createButtonLayout());

    addListener(AbstractForm.SaveEvent.class, this::saveAchievementType);
    addListener(AbstractForm.DeleteEvent.class, this::deleteAchievementType);
    addListener(AbstractForm.CloseEvent.class, event -> this.closeEditor());
  }

  public void setEntity(T entity) {
    entityBinder.setBean(entity);
  }

  public <T extends ManagedEntity> void deleteAchievementType(AbstractForm.DeleteEvent event) {
    //    achievementTypeService.delete(event.getEntity());
    //    updateList();
    closeEditor();
  }

  public void saveAchievementType(AbstractForm.SaveEvent event) {
    //    achievementTypeService.save(event.getEntity());
    //    updateList();
    closeEditor();
  }

  public void editEntity(T entity) {
    if (entity == null) {
      closeEditor();
    } else {
      setEntity(entity);
      setVisible(true);
      addClassName("editing");
    }
  }

  public void closeEditor() {
    setEntity(null);
    setVisible(false);
    removeClassName("editing");
  }


  private void collectFieldsForVaadinForm(Class<?> entity) {

    final var className = entity.getSimpleName();

    for (var field : entity.getDeclaredFields()) {
      if (field.getName().equals("createdById")) {
        continue;
      }

      final var fieldName = field.getName();
      final var i18nFieldName = applicationContext.getMessage(
          ADMIN_I18N_PREFIX + className.toLowerCase() + "." + fieldName,
          null,
          fieldName,
          Locale.getDefault()
      );

      AbstractField vaadinField = resolveFieldAsVaadinAbstractField(field, i18nFieldName);

      if (vaadinField != null) {
        entityBinder.bind(vaadinField, fieldName);
        vaadinComponentList.add(vaadinField);
      }
    }
  }

  private AbstractField resolveFieldAsVaadinAbstractField(Field field, String i18nFieldName) {
    AbstractField vaadinField = null;
    if (String.class.isAssignableFrom(field.getType())) {
      vaadinField = new TextField(i18nFieldName);
    } else if (NumbersHelper.BOOLEAN_VALUES.contains(field.getType())) {
      vaadinField = new Checkbox(i18nFieldName);
    } else if (NumbersHelper.FLOATING_NUMBERS.contains(field.getType())) {
      vaadinField = new NumberField(i18nFieldName);
    } else if (NumbersHelper.checkFieldType(field.getType(), Integer.class, int.class)) {
      vaadinField = new IntegerField(i18nFieldName);
    } else if (NumbersHelper.checkFieldType(field.getType(), Long.class, long.class)) {
      vaadinField = new LongField(i18nFieldName);
    } else if (NumbersHelper.checkFieldType(field.getType(), Byte.class, byte.class)) {
      vaadinField = new ByteField(i18nFieldName);
    } else if (NumbersHelper.checkFieldType(field.getType(), Short.class, short.class)) {
      vaadinField = new ShortField(i18nFieldName);
    } else if (Date.class.isAssignableFrom(field.getType())) {// Create a DateField with the default style
      //        DateField date = new DateField();
      //        vaadinField = new DateField(fieldName);
    }
    return vaadinField;
  }


  private Component createButtonLayout() {
    save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
    delete.addThemeVariants(ButtonVariant.LUMO_ERROR);
    close.addThemeVariants(ButtonVariant.LUMO_TERTIARY);

    save.addClickShortcut(Key.ENTER);
    close.addClickShortcut(Key.ESCAPE);

    save.addClickListener(click -> validateAndSave());
    delete.addClickListener(click -> fireEvent(new DeleteEvent(this, entityBinder.getBean())));
    close.addClickListener(click -> fireEvent(new CloseEvent(this)));

    entityBinder.addStatusChangeListener(event -> save.setEnabled(entityBinder.isValid()));

    return new HorizontalLayout(save, delete, close);
  }

  private void validateAndSave() {
    if (entityBinder.isValid()) {
      fireEvent(new SaveEvent(this, entityBinder.getBean()));
    }
  }


  @Getter
  public static abstract class AbstractFormEvent extends ComponentEvent<AbstractForm<?>> {

    private final Object entity;

    protected AbstractFormEvent(AbstractForm<?> source, Object entity) {
      super(source, false);
      this.entity = entity;
    }
  }

  public static class SaveEvent extends AbstractFormEvent {

    SaveEvent(AbstractForm<?> source, Object entity) {
      super(source, entity);
    }
  }

  public static class DeleteEvent extends AbstractFormEvent {

    DeleteEvent(AbstractForm<?> source, Object entity) {
      super(source, entity);
    }
  }

  public static class CloseEvent extends AbstractFormEvent {

    CloseEvent(AbstractForm<?> source) {
      super(source, null);
    }
  }

}
