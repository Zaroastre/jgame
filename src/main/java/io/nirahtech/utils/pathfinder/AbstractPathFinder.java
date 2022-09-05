package io.nirahtech.utils.pathfinder;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractPathFinder implements PathFinder {

    protected final List<Node> openedNodes = new ArrayList<>();
    protected final List<Node> checkedNodes = new ArrayList<>();

    protected final Node[][] nodes;
    protected final int columns;
    protected final int rows;
    protected int step = 0;
    protected Node startNode, goalNode, currentNode;
    protected boolean goalReached = false;

    protected AbstractPathFinder(final int columns, final int rows) {
        this.columns = columns;
        this.rows = rows;
        this.nodes = new Node[this.columns][this.rows];
    }

}
