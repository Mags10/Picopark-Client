package org.picopark;

import org.connection.Connection;
import org.connection.RoomInfo;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;

public class RoomsPanel extends JPanel {

    private BufferedImage bg;


    public RoomsPanel(NavigationManager navigationManager , Connection connection) {

        try{
            this.bg = ImageIO.read(
                    Objects.requireNonNull(
                            getClass().getResource("/assets-login/arcade-rooms.jpg")
                    )
            );
        }catch(IOException e){
            e.printStackTrace();
        }

        setLayout(new BorderLayout());
        setBackground(new Color(30, 30, 40)); // fondo oscuro elegante

        JLabel title = new JLabel("Selecciona un nivel para jugar", SwingConstants.CENTER);
        title.setFont(new Font("Segoe UI", Font.BOLD, 20));
        title.setForeground(Color.WHITE);
        title.setBorder(BorderFactory.createEmptyBorder(20, 0, 10, 0));
        add(title, BorderLayout.NORTH);

        // Panel interno de salas
        RoomsGridPanel roomsGridPanel = new RoomsGridPanel(connection.getRooms(), roomId -> {
            connection.joinRoom(roomId);
        });

        add(roomsGridPanel, BorderLayout.CENTER);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (bg != null) {
            g.drawImage(bg, 0, 0, getWidth(), getHeight(), this);
        }
    }
}

class RoomsGridPanel extends JPanel {

    public RoomsGridPanel(List<RoomInfo> rooms, Consumer<String> onRoomSelected) {
        setLayout(new BorderLayout());
        setOpaque(false);

        // Panel que contendrá los botones de las salas
        JPanel roomsContainer = new JPanel(new GridLayout(0, 3, 20, 20));
        roomsContainer.setOpaque(false);
        roomsContainer.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        for(RoomInfo room : rooms){
            JButton roomButton = new JButton(room.getName());
            roomButton.setFont(new Font("Poppins", Font.PLAIN, 16));
            roomButton.setFocusPainted(false);
            roomButton.setBackground(new Color(255, 255, 255, 120));
            roomButton.setBorder(BorderFactory.createLineBorder(Color.WHITE, 2));
            roomButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            roomButton.setBackground(new Color(255, 255, 255, 120));

            roomButton.addActionListener(e -> onRoomSelected.accept(room.getId()));

            roomsContainer.add(roomButton);
        }

        JScrollPane scrollPane = new JScrollPane(roomsContainer);
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);
        scrollPane.setBorder(null);

        add(scrollPane, BorderLayout.CENTER);
    }
}