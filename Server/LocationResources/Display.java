package Server.LocationResources;

import java.util.ArrayList;
import java.util.List;

public class Display {
    private List<Panel> panels;
    private int width;
    private int height;

    private String topBorder;
    private String sideBorder;

    public Display(int width, int height) {
        this.width = width;
        this.height = height;
        this.topBorder = "=";
        this.sideBorder = "|";

        this.panels = new ArrayList<>();
    }

    public void addPanel(Panel newPanel) {
        panels.add(newPanel);
    }
    
    @Override
    public String toString() {
        StringBuilder display = new StringBuilder();
        List<String[]> panelList = new ArrayList<>();

        int numPanels = panels.size();
        int borderSpace = (numPanels + 1) * sideBorder.length() + numPanels; 
        int contentWidth = (width - borderSpace) / numPanels;

        // Prepare panel contents split into lines
        for (Panel panel : panels) {
            panelList.add(panel.toString().split("\\R"));
        }

        for (int i = 0; i < height; i++) {
            StringBuilder line = new StringBuilder();
            if (i == 0 || i == height - 1) {
                line.append(topBorder.repeat(width / topBorder.length()));
            } else if (i == 1 || i == height - 2) {
                line.append(sideBorder);
                line.append(" ".repeat(width - (sideBorder.length() * 2)));
                line.append(sideBorder);
            } else {
                // Content row
                line.append(sideBorder);
                for (int p = 0; p < numPanels; p++) {
                    line.append(" ");
                    String[] panelLines = panelList.get(p);
                    int contentLineIdx = i - 2;
                    String panelContent = (contentLineIdx < panelLines.length && contentLineIdx >= 0) ? panelLines[contentLineIdx] : "";
                    if (panelContent.length() > contentWidth) {
                        line.append(panelContent.substring(0, contentWidth));
                    } else {
                        line.append(panelContent);
                        line.append(" ".repeat(contentWidth - panelContent.length()));
                    }
                    line.append(" ");
                    line.append(sideBorder);
                }
            }
            display.append(line).append(System.lineSeparator());
        }

        return display.toString();
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

}
