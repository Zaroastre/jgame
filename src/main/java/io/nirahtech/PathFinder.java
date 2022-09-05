package io.nirahtech;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.event.KeyListener;

public class PathFinder extends JPanel {

    private final int maxCol = 20;
    private final int maxRow = 15;
    private final int nodeSize = 35;
    private final int screenWidth = maxCol * nodeSize;
    private final int screenHeigth = maxRow * nodeSize;

    Node[][] nodes = new Node[maxCol][maxRow];
    Node startNode, goalNode, currentNode;
    List<Node> openedNodes = new ArrayList<>();
    List<Node> checkedNodes = new ArrayList<>();
    private boolean goalReached = false;
    private int step = 0;

    public PathFinder() {
        this.setPreferredSize(new Dimension(screenWidth, screenHeigth));
        this.setBackground(Color.BLACK);
        this.setLayout(new GridLayout(maxRow, maxCol));
        this.addKeyListener(new KeyHandler(this));
        this.setFocusable(true);

        int col = 0;
        int row = 0;
        while (col < maxCol && row < maxRow) {
            nodes[col][row] = new Node(col, row);
            this.add(nodes[col][row]);
            col++;
            if (col == maxCol) {
                col = 0;
                row++;
            }
        }

        this.setStartNode(3, 6);
        this.setGoalNode(11, 2);
        this.setSolidNode(10, 2);
        this.setSolidNode(10, 3);
        this.setSolidNode(11, 3);
        this.setSolidNode(12, 3);
        this.setSolidNode(13, 3);

        this.setCostOnNodes();
    }

    private void setStartNode(final int col, final int row) {
        this.nodes[col][row].setAsStart();
        startNode = this.nodes[col][row];
        currentNode = startNode;
    }

    private void setGoalNode(final int col, final int row) {
        this.nodes[col][row].setAsGoal();
        goalNode = this.nodes[col][row];
    }

    private void setSolidNode(final int col, final int row) {
        this.nodes[col][row].setAsSolid();
    }

    private void setCostOnNodes() {
        int col = 0;
        int row = 0;
        while (col < maxCol && row < maxRow) {
            this.getCost(nodes[col][row]);
            col++;
            if (col == maxCol) {
                col = 0;
                row++;
            }
        }

    }

    private void getCost(Node node) {
        int xDistance = Math.abs(node.col - startNode.col);
        int yDistance = Math.abs(node.row - startNode.row);
        node.gCost = xDistance + yDistance;

        xDistance = Math.abs(node.col - goalNode.col);
        yDistance = Math.abs(node.row - goalNode.row);
        node.hCost = xDistance + yDistance;

        node.fCost = node.gCost + node.hCost;

        if (node != startNode && node != goalNode) {
            node.setText("<html>F: " + node.fCost + "<br>G: " + node.gCost + "<br>H: " + node.hCost + "</html>");
        }

    }

    public void autoSearch() {
        while (!this.goalReached && this.step < 300) {
            this.search();
            this.step++;
        }
    }

    public void search() {
        if (this.goalReached == false) {
            int col = this.currentNode.col;
            int row = this.currentNode.row;
            this.currentNode.setAsChecked();
            this.checkedNodes.add(currentNode);
            this.openedNodes.remove(currentNode);

            // Up Node
            if (row - 1 >= 0)
                this.openNode(nodes[col][row - 1]);
            // Left Node
            if (col - 1 >= 0)
                this.openNode(nodes[col - 1][row]);
            // Down Node
            if (row + 1 >= 0)
                this.openNode(nodes[col][row + 1]);
            // Right Node
            if (col + 1 >= 0)
                this.openNode(nodes[col + 1][row]);

            int bestNodeIndex = 0;
            int bestNodeFCost = 999;

            for (int index = 0; index < this.openedNodes.size(); index++) {
                if (this.openedNodes.get(index).fCost < bestNodeFCost) {
                    bestNodeIndex = index;
                    bestNodeFCost = this.openedNodes.get(index).fCost;
                } else if (this.openedNodes.get(index).fCost == bestNodeFCost) {
                    if (this.openedNodes.get(index).gCost < this.openedNodes.get(bestNodeIndex).gCost) {
                        bestNodeIndex = index;
                    }
                }
            }
            this.currentNode = this.openedNodes.get(bestNodeIndex);

            if (this.currentNode == goalNode) {
                this.goalReached = true;
                this.trackThePath();
            }
        }
    }

    private void openNode(Node node) {
        if (node.open == false && node.checked == false && node.solid == false) {
            node.setAsOpen();
            node.parent = currentNode;
            openedNodes.add(node);
        }
    }

    private void trackThePath() {
        Node current = this.goalNode;
        while (current != this.startNode) {
            current = current.parent;
            if (current != startNode) {
                current.setAsPath();
            }
        }
    }

    private class Node extends JButton implements ActionListener {
        private Node parent;
        private int col = 15;
        private int row = 10;
        private int gCost = 0;
        private int hCost = 0;
        private int fCost = 0;

        private boolean start;
        private boolean solid;
        private boolean goal;
        private boolean open;
        private boolean checked;

        public Node(final int col, final int row) {
            this.col = col;
            this.row = row;

            this.setBackground(Color.WHITE);
            this.setForeground(Color.BLACK);

            this.addActionListener(this);
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            this.setBackground(Color.ORANGE);
        }

        public void setAsStart() {
            this.setBackground(Color.BLUE);
            this.setForeground(Color.WHITE);
            this.setText("Start");
            start = true;
        }

        public void setAsGoal() {
            this.setBackground(Color.YELLOW);
            this.setForeground(Color.BLACK);
            this.setText("Goal");
            goal = true;
        }

        public void setAsSolid() {
            this.setBackground(Color.BLACK);
            solid = true;
        }

        public void setAsOpen() {
            this.open = true;
        }

        public void setAsChecked() {
            if (start == false && goal == false) {
                this.setBackground(Color.ORANGE);
                this.setForeground(Color.BLACK);
            }
            this.checked = true;
        }

        public void setAsPath() {
            this.setBackground(Color.GREEN);
            this.setForeground(Color.BLACK);

        }
    }

    public final class KeyHandler implements KeyListener {

        private final PathFinder pathFinder;

        public KeyHandler(PathFinder pathFinder) {
            this.pathFinder = pathFinder;
        }

        @Override
        public void keyTyped(KeyEvent e) {

        }

        @Override
        public void keyPressed(KeyEvent e) {
            int code = e.getKeyCode();
            if (code == KeyEvent.VK_ENTER) {
                this.pathFinder.autoSearch();
            }
        }

        @Override
        public void keyReleased(KeyEvent e) {

        }

    }

    public static void main(String[] args) {
        JFrame window = new JFrame();
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        PathFinder pathFinder = new PathFinder();
        window.add(pathFinder);
        window.addKeyListener(pathFinder.new KeyHandler(pathFinder));
        window.setFocusable(true);
        window.pack();
        window.setLocationRelativeTo(null);
        window.setVisible(true);
    }

}
