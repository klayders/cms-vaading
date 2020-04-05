package org.orglot.gosloto.admin.ui.views.list.achievementtype;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.shared.Registration;
import java.util.List;
import lombok.Getter;
import org.orglot.gosloto.components.entity.image.Image;
import org.orglot.gosloto.domain.achievement.AchievementType;

public class AchievementTypeForm extends FormLayout {

  private final Checkbox published = new Checkbox("Опубликовано");
  private final TextField type = new TextField("Внешний айди");
  private final IntegerField row = new IntegerField("Группа");
  private final IntegerField version = new IntegerField("Версия");
  private final IntegerField position = new IntegerField("Порядок");
  private final TextField shortDescription = new TextField("Название");
  private final TextField shortEnDescription = new TextField("Англ. название");
  private final TextField description = new TextField("Описание");
  private final TextField enDescription = new TextField("Англ. описание");
  private final TextField fullDescription = new TextField("Полное описание");
  private final TextField fullEnDescription = new TextField("Англ. полное описание");

  private final ComboBox<Image> smallImage = new ComboBox<>("Маленькая картинка");
  private final ComboBox<Image> bigImage = new ComboBox<>("Большая картинка");
  private final ComboBox<Image> emptyImage = new ComboBox<>("Пустая картинка");

  private final Button save = new Button("Сохранить");
  private final Button delete = new Button("Удалить");
  private final Button close = new Button("Закрыть");

  private Binder<AchievementType> binder = new BeanValidationBinder<>(AchievementType.class);

  public AchievementTypeForm(List<Image> images) {
    addClassName("achievement-type-form");

    binder.bindInstanceFields(this);
    smallImage.setItems(images);
    smallImage.setItemLabelGenerator(Image::getSliceName);

    add(
        published,
        type,
        row,
        position,
        shortDescription,
        shortEnDescription,
        description,
        enDescription,
        fullDescription,
        fullEnDescription,
        smallImage,
        version,
        createButtonLayout()
    );
  }

  public void setAchievementType(AchievementType achievementType) {
    binder.setBean(achievementType);
  }

  private Component createButtonLayout() {
    save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
    delete.addThemeVariants(ButtonVariant.LUMO_ERROR);
    close.addThemeVariants(ButtonVariant.LUMO_TERTIARY);

    save.addClickShortcut(Key.ENTER);
    close.addClickShortcut(Key.ESCAPE);

    save.addClickListener(click -> validateAndSave());
    delete.addClickListener(click -> fireEvent(new DeleteEvent(this, binder.getBean())));
    close.addClickListener(click -> fireEvent(new CloseEvent(this)));

    binder.addStatusChangeListener(event -> save.setEnabled(binder.isValid()));

    return new HorizontalLayout(save, delete, close);
  }

  private void validateAndSave() {
    if (binder.isValid()) {
      fireEvent(new SaveEvent(this, binder.getBean()));
    }
  }

  // Events
  @Getter
  public static abstract class AchievementTypeFormEvent extends ComponentEvent<AchievementTypeForm> {

    private AchievementType achievementType;

    protected AchievementTypeFormEvent(AchievementTypeForm source, AchievementType achievementType) {
      super(source, false);
      this.achievementType = achievementType;
    }

  }

  public static class SaveEvent extends AchievementTypeFormEvent {

    SaveEvent(AchievementTypeForm source, AchievementType AchievementType) {
      super(source, AchievementType);
    }
  }

  public static class DeleteEvent extends AchievementTypeFormEvent {

    DeleteEvent(AchievementTypeForm source, AchievementType AchievementType) {
      super(source, AchievementType);
    }

  }

  public static class CloseEvent extends AchievementTypeFormEvent {

    CloseEvent(AchievementTypeForm source) {
      super(source, null);
    }
  }

  public <T extends ComponentEvent<?>> Registration addListener(Class<T> eventType,
                                                                ComponentEventListener<T> listener) {
    return getEventBus().addListener(eventType, listener);
  }


  public void doIt() {
    //    AchievementType achievementType = new AchievementType();
    //    achievementType.setCreationDate(new Date());
    //    //опубликовано
    //    achievementType.setPublished();
    //    //Внешний айди
    //    achievementType.setType();
    //    //Группа
    //    achievementType.setRow();
    //    //Порядок
    //    achievementType.setPosition();
    //
    //    //Название
    //    achievementType.setShortDescription();
    //    //Название en
    //    achievementType.setEnShortDescription();
    //    //Описание
    //    achievementType.setDescription();
    //    //Описание en
    //    achievementType.setEnDescription();
    //    //Полное описание
    //    achievementType.setFullDescription();
    //    //Полное описание en
    //    achievementType.setEnFullDescription();
    //
    //    // Начало публикации
    //    achievementType.setFromDate();
    //    // Конец публикации
    //    achievementType.setFromDate();
  }
}
