package com.examples.game.tetris.ui;

import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JRootPane;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/***
 * Custom ui component
 * for the input of the new high score item
 * and for the list of the existing high scores.
 */
public class CustomDialog {

  private final List<JComponent> components;
  private String title;
  private int messageType;
  private JRootPane rootPane;
  private String[] options;

  public CustomDialog(JRootPane rootPane, String title, String[] options,
      String messageText, JComponent... components) {
    this.components = new ArrayList<>();
    setRootPane(rootPane);
    setTitle(title);
    setMessageType(JOptionPane.PLAIN_MESSAGE);
    setOptions(options);

    if (messageText != null) {
      addMessageText(messageText);
    }

    if (components != null) {
      Arrays.stream(components).forEach(this::addComponent);
    }
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public void setMessageType(int messageType) {
    this.messageType = messageType;
  }

  public void addComponent(JComponent component) {
    components.add(component);
  }

  public void addMessageText(String messageText) {
    JLabel label = new JLabel("<html>" + messageText + "</html>");
    addComponent(label);
  }

  public void setRootPane(JRootPane rootPane) {
    this.rootPane = rootPane;
  }

  public void setOptions(String[] options) {
    this.options = options;
  }

  public int show() {
    return JOptionPane.showOptionDialog(rootPane, components.toArray(), title,
        JOptionPane.OK_CANCEL_OPTION, messageType, null, options, null);
  }
}
