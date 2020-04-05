package org.orglot.gosloto.admin.ui.views.list.test;

import com.vaadin.flow.component.combobox.ComboBox;
import java.util.List;
import org.orglot.gosloto.admin.ui.views.abstraction.AbstractForm;
import org.orglot.gosloto.components.entity.image.Image;
import org.orglot.gosloto.domain.achievement.AchievementType;
import org.springframework.context.ApplicationContext;

public class TestForm extends AbstractForm<AchievementType> {

  private final ComboBox<Image> smallImage = new ComboBox<>("Маленькая картинка");


  public TestForm(List<Image> all, ApplicationContext messageSource) {
    super(messageSource);

    smallImage.setItems(all);
    smallImage.setItemLabelGenerator(Image::getSliceName);
    add(smallImage);
  }
}
