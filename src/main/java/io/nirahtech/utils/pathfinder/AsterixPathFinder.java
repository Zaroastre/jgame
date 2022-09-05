package io.nirahtech.utils.pathfinder;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

public final class AsterixPathFinder extends AbstractPathFinder {

    private int limit = 1024 * 32;

    public AsterixPathFinder(int columns, int rows) {
        super(columns, rows);
        this.initialize(columns, rows);
    }

    private final void initialize(final int columns, final int rows) {
        int columnIndex = 0;
        int rowIndex = 0;
        while (columnIndex < columns && rowIndex < rows) {
            super.nodes[columnIndex][rowIndex] = new Node(columnIndex, rowIndex);
            columnIndex++;
            if (columnIndex == columns) {
                columnIndex = 0;
                rowIndex++;
            }
        }
    }

    public final void setLimit(final int limit) {
        this.limit = limit;
    }

    @Override
    public final List<Node> search() {
        List<Node> path = new ArrayList<>();
        int attempts = 0;
        while (attempts < this.limit) {
            if (!this.goalReached) {
                int col = this.currentNode.getColumns();
                int row = this.currentNode.getRows();
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
                    if (this.openedNodes.get(index).getfCost() < bestNodeFCost) {
                        bestNodeIndex = index;
                        bestNodeFCost = this.openedNodes.get(index).getfCost();
                    } else if (this.openedNodes.get(index).getfCost() == bestNodeFCost) {
                        if (this.openedNodes.get(index).getgCost() < this.openedNodes.get(bestNodeIndex).getgCost()) {
                            bestNodeIndex = index;
                        }
                    }
                }
                this.currentNode = this.openedNodes.get(bestNodeIndex);

                if (this.currentNode == goalNode) {
                    this.goalReached = true;
                    path = this.trackThePath();
                }
            }
            this.limit++;
        }

        return path;
    }

    @Override
    public final List<Node> search(final Point startPoint, final Point destinationPoint) {
        this.startNode = new Node(startPoint.y, startPoint.x);
        this.goalNode = new Node(destinationPoint.y, destinationPoint.x);

        return this.search();
    }

    @Override
    public final void setStartNode(final int column, final int row) {
        this.nodes[column][row].setAsStart();
        startNode = this.nodes[column][row];
        currentNode = startNode;
        this.computeCostOnNodes();
    }

    @Override
    public final void setGoalNode(final int column, final int row) {
        this.nodes[column][row].setAsGoal();
        goalNode = this.nodes[column][row];
        this.computeCostOnNodes();
    }

    @Override
    public final void setSolidNode(final int column, final int row) {
        this.nodes[column][row].setAsSolid();
        this.computeCostOnNodes();
    }

    private final void computeCostOnNodes() {
        int columnIndex = 0;
        int rowsIndex = 0;
        while (columnIndex < super.columns && rowsIndex < super.rows) {
            this.computeCost(nodes[columnIndex][rowsIndex]);
            columnIndex++;
            if (columnIndex == super.columns) {
                columnIndex = 0;
                rowsIndex++;
            }
        }

    }

    private final void computeCost(final Node node) {
        int xDistance = Math.abs(node.getColumns() - startNode.getColumns());
        int yDistance = Math.abs(node.getRows() - startNode.getRows());
        node.setgCost(xDistance + yDistance);
        xDistance = Math.abs(node.getColumns() - goalNode.getColumns());
        yDistance = Math.abs(node.getRows() - goalNode.getRows());
        node.sethCost(xDistance + yDistance);
        node.setfCost(node.getgCost() + node.gethCost());

    }

    private final void openNode(final Node node) {
        if (!node.isOpen() && !node.isChecked() && !node.isSolid()) {
            node.setAsOpen();
            node.setParent(currentNode);
            super.openedNodes.add(node);
        }
    }

    private final List<Node> trackThePath() {
        List<Node> path = new ArrayList<>();
        Node current = super.goalNode;
        while (current != super.startNode) {
            current = current.getParent();
            if (current != startNode) {
                current.setAsPath();
                path.add(current);
            }
        }
        return path;
    }

}
