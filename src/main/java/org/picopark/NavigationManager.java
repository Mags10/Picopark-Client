package org.picopark;

import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.util.function.Supplier;

public class NavigationManager {
    private JFrame frame;
    private Map<String, JPanel> panelCache;              // Paneles ya creados
    private Map<String, Supplier<JPanel>> panelFactories; // Funciones para crear paneles
    private Set<String> noCachePanels = new HashSet<>();
    private Stack<String> history;
    private String currentPanelName;
    private JPanel currentPanel;


    public NavigationManager(JFrame frame) {
        this.frame = frame;
        this.panelCache = new HashMap<>();
        this.panelFactories = new HashMap<>();
        this.history = new Stack<>();
    }

    // Registrar un panel usando una función (NO se crea aún)
    public void registerPanel(String name, Supplier<JPanel> panelFactory) {
        registerPanel(name, panelFactory, true); // Por defecto usa cache
    }

    // Registrar con opción de cache
    public void registerPanel(String name, Supplier<JPanel> panelFactory, boolean useCache) {
        panelFactories.put(name, panelFactory);
        if (!useCache) {
            // Marcar para NO cachear
            noCachePanels.add(name);
        }
    }


    // Navegar a un panel (se crea solo si es la primera vez)
    public void navigateTo(String panelName, String windowTitle) {
        // Verificar que existe la factory
        if (!panelFactories.containsKey(panelName)) {
            System.err.println("Panel no registrado: " + panelName);
            return;
        }

        // Guardar en historial
        if (currentPanelName != null && !currentPanelName.equals(panelName)) {
            history.push(currentPanelName);
        }

        // Obtener o crear el panel
        currentPanel = getOrCreatePanel(panelName);

        // Cambiar título
        if (windowTitle != null) {
            frame.setTitle(windowTitle);
        }

        // Mostrar panel
        currentPanelName = panelName;
        showPanel(currentPanel);
    }

    public void navigateTo(String panelName) {
        navigateTo(panelName, null);
    }

    // Obtener panel del cache o crearlo por primera vez
    private JPanel getOrCreatePanel(String panelName) {
        // Si NO debe usar cache, siempre crear uno nuevo
        if (noCachePanels.contains(panelName)) {
            System.out.println("🔄 Recreando panel (sin cache): " + panelName);
            Supplier<JPanel> factory = panelFactories.get(panelName);
            return factory.get();
        }

        // Si ya existe en cache, devolverlo
        if (panelCache.containsKey(panelName)) {
            System.out.println("♻️ Reutilizando panel: " + panelName);
            return panelCache.get(panelName);
        }

        // Si no existe, crearlo usando la factory
        System.out.println("✨ Creando panel por primera vez: " + panelName);
        Supplier<JPanel> factory = panelFactories.get(panelName);
        JPanel panel = factory.get();

        // Guardarlo en cache
        panelCache.put(panelName, panel);

        return panel;
    }

    public void navigateBack() {
        if (history.isEmpty()) {
            System.out.println("No hay historial para volver");
            return;
        }

        currentPanelName = history.pop();
        JPanel panel = getOrCreatePanel(currentPanelName);
        showPanel(panel);
    }

    private void showPanel(JPanel panel) {
        frame.getContentPane().removeAll();
        frame.getContentPane().add(panel);
        frame.revalidate();
        frame.repaint();
    }

    public void resizeWindow(int width, int height) {
        frame.setSize(width, height);
    }

    // Limpiar cache (útil para refrescar paneles)
    public void clearCache(String panelName) {
        panelCache.remove(panelName);
        System.out.println("🗑️ Cache eliminado para: " + panelName);
    }

    public void clearAllCache() {
        panelCache.clear();
        System.out.println("🗑️ Todo el cache ha sido limpiado");
    }

    public void clearHistory() {
        history.clear();
    }

    public JPanel getPanel(String panelName) {
        return currentPanel;
    }

}