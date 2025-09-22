package com.example;

import javax.swing.*;
import java.awt.datatransfer.DataFlavor;
import java.awt.dnd.*;
import java.io.*;
import java.util.List;

public class FileDropExample {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new FileDropExample().createAndShowGUI());
    }

    private void createAndShowGUI() {
        JFrame frame = new JFrame("Drag and Drop File Example");
        JTextArea textArea = new JTextArea();
        textArea.setEditable(false);

        JScrollPane scrollPane = new JScrollPane(textArea);
        frame.add(scrollPane);

        // Enable drag and drop
        textArea.setTransferHandler(new FileDropHandler(textArea));

        frame.setSize(600, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null); // center
        frame.setVisible(true);
    }

    // Custom TransferHandler to handle file drops
    private static class FileDropHandler extends TransferHandler {
        private final JTextArea textArea;

        public FileDropHandler(JTextArea textArea) {
            this.textArea = textArea;
        }

        @Override
        public boolean canImport(TransferSupport support) {
            // Only accept file list drops
            return support.isDataFlavorSupported(DataFlavor.javaFileListFlavor);
        }

        @Override
        public boolean importData(TransferSupport support) {
            if (!canImport(support)) return false;

            try {
                @SuppressWarnings("unchecked")
                List<File> files = (List<File>) support.getTransferable().getTransferData(DataFlavor.javaFileListFlavor);

                // Read the first file only (can be expanded to handle multiple)
                File file = files.get(0);
                readFile(file);

                return true;
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        }

        private void readFile(File file) {
            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                textArea.setText(""); // Clear existing text
                String line;
                while ((line = reader.readLine()) != null) {
                    textArea.append(line + "\n");
                }
            } catch (IOException e) {
                textArea.setText("Failed to read file: " + e.getMessage());
            }
        }
    }
}
