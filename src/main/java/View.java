import com.pdc.library.view.book.BookMenu;
import com.pdc.library.view.MainMenu;
import com.pdc.library.view.MenuAction;
import com.pdc.library.util.Listener;
import com.pdc.library.view.user.UserMenu;
import com.pdc.library.view.userbook.UserBookDisplay;
import com.pdc.library.view.userbook.UserBookMenu;

import javax.swing.*;

public class View extends JFrame implements Listener<MenuAction> {

    private JPanel currentPanel;
    private final Controller controller;

    public View(Controller c) {
        this.controller = c;
        this.setTitle("Library Management System");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(1000, 600);
        this.setLayout(new BoxLayout(this.getContentPane(), BoxLayout.Y_AXIS));
        setCurrentPanel(new MainMenu(this));
    }

    @Override
    public void onEvent(MenuAction event) {
        switch (event) {
            case EXIT -> System.exit(0);
            case USERS -> {
                setCurrentPanel(new UserMenu(this, controller.repository));
            }
            case BOOKS -> {
                setCurrentPanel(new BookMenu(this, controller.repository));
            }
            case LOANS -> {
                setCurrentPanel(new UserBookMenu(this, controller.repository));
            }
            case HOME -> {
                setCurrentPanel(new MainMenu(this));
            }
            case null, default -> {
            }
        }
    }

    private void setCurrentPanel(JPanel panel) {
        if (this.currentPanel != null) {
            this.getContentPane().remove(this.currentPanel);
        }
        this.currentPanel = panel;
        this.getContentPane().add(this.currentPanel);
        this.revalidate();
        this.repaint();
    }
}
