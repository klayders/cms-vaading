package org.orglot.gosloto.admin.ui;

import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.FlexComponent.Alignment;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.HighlightConditions;
import com.vaadin.flow.router.RouterLink;
import org.orglot.gosloto.admin.ui.views.dashboard.DashBoardView;
import org.orglot.gosloto.admin.ui.views.list.test.TestListView;

//помоему эта страница использует дефолтный роутинг - localhost:8080/
@CssImport("./styles/shared-styles.css")
public class MainLayout extends AppLayout {

  public MainLayout() {
    createHeader();
    createDrawer();
  }

  private void createHeader() {
    var logo = new H1("Stoloto-Admin");
    logo.addClassName("logo");

    var header = new HorizontalLayout(new DrawerToggle(), logo);
    header.addClassName("header");
    header.setWidth("100%");
    header.setDefaultVerticalComponentAlignment(Alignment.CENTER);

    addToNavbar(header);
  }

  private void createDrawer() {
    var listLink = new RouterLink("List", TestListView.class);
    var dashboardRouting = new RouterLink("dashboard", DashBoardView.class);
    listLink.setHighlightCondition(HighlightConditions.sameLocation());

    addToDrawer(new VerticalLayout(
        listLink,
        dashboardRouting
    ));
  }
}
