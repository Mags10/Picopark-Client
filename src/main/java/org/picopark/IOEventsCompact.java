package org.picopark;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

public class IOEventsCompact {
    private java.util.Map<String, Boolean> keys = new java.util.HashMap<>();

    public IOEventsCompact() {
        // Inicializar teclas
        keys.put("up", false);
        keys.put("down", false);
        keys.put("left", false);
        keys.put("right", false);
        keys.put("space", false);
        keys.put("shift", false);
    }

    public void setupKeyBindings(JPanel panel) {
        InputMap inputMap = panel.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
        ActionMap actionMap = panel.getActionMap();

        // Método auxiliar para reducir código repetitivo
        bindKey(inputMap, actionMap, "left", KeyEvent.VK_A, KeyEvent.VK_LEFT);
        bindKey(inputMap, actionMap, "right", KeyEvent.VK_D, KeyEvent.VK_RIGHT);
        bindKey(inputMap, actionMap, "jump", KeyEvent.VK_SPACE, KeyEvent.VK_W);
        bindKey(inputMap, actionMap, "exit", KeyEvent.VK_S);
        bindKey(inputMap, actionMap, "resume", KeyEvent.VK_R);
        bindKey(inputMap, actionMap, "menu", KeyEvent.VK_ESCAPE, KeyEvent.VK_M);
    }

    private void bindKey(InputMap inputMap, ActionMap actionMap, String action, int... keyCodes) {
        for (int keyCode : keyCodes) {
            // Presionado
            inputMap.put(KeyStroke.getKeyStroke(keyCode, 0, false), action + "Pressed");
            // Soltado
            inputMap.put(KeyStroke.getKeyStroke(keyCode, 0, true), action + "Released");
        }

        actionMap.put(action + "Pressed", new AbstractAction() {
            public void actionPerformed(ActionEvent e) { keys.put(action, true); }
        });

        actionMap.put(action + "Released", new AbstractAction() {
            public void actionPerformed(ActionEvent e) { keys.put(action, false); }
        });
    }

    public boolean isPressed(String key) {
        return keys.getOrDefault(key, false);
    }

    // Getters compatibles
    public boolean isJumpPressed() { return isPressed("jump"); }
    public boolean isLeftPressed() { return isPressed("left"); }
    public boolean isRightPressed() { return isPressed("right"); }
    public boolean isMenuPressed() { return isPressed("menu"); }
    public boolean isResumePressed() { return isPressed("resume"); }
    public boolean isExitPressed() { return isPressed("exit"); }
}
