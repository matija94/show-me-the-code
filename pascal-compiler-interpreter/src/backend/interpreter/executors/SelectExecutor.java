package backend.interpreter.executors;

import backend.interpreter.Executor;
import intermediate.ICodeNode;
import intermediate.icodeimpl.ICodeKeyImpl;

import java.util.ArrayList;

public class SelectExecutor extends StatementExecutor {

    // TODO: optimize select executor by using jump tables

    public SelectExecutor(Executor parent) {
        super(parent);
    }

    @Override
    public Object execute(ICodeNode node) {
        ArrayList<ICodeNode> selectChildren = node.getChildren();
        ICodeNode exprNode = selectChildren.get(0);

        ExpressionExecutor expressionExecutor = new ExpressionExecutor(this);
        Object selectValue = expressionExecutor.execute(exprNode);

        ICodeNode selectedBranchNode = searchBranches(selectValue, selectChildren);

        if (selectedBranchNode != null) {
            ICodeNode stmtNode = selectedBranchNode.getChildren().get(1);
            StatementExecutor statementExecutor = new StatementExecutor(this);
            statementExecutor.execute(stmtNode);
        }

        ++executionCount;
        return null;
    }

    private ICodeNode searchBranches(Object selectValue, ArrayList<ICodeNode> selectChildren) {
        for (int i=1; i < selectChildren.size(); i++) {
            ICodeNode branchNode = selectChildren.get(i);

            if (searchConstants(selectValue, branchNode)) {
                return branchNode;
            }
        }
        return null;
    }

    private boolean searchConstants(Object selectValue, ICodeNode branchNode) {
        ICodeNode constantsNode = branchNode.getChildren().get(0);
        ArrayList<ICodeNode> constantsList = constantsNode.getChildren();
        if (selectValue instanceof Integer) {
            for(ICodeNode constantNode : constantsList) {
                Integer value = (Integer) constantNode.getAttribute(ICodeKeyImpl.VALUE);
                if (selectValue == value) {
                    return true;
                }
            }
        }
        else if (selectValue instanceof String) {
            for(ICodeNode constantNode : constantsList) {
                String value = (String) constantNode.getAttribute(ICodeKeyImpl.VALUE);
                if (selectValue == value) {
                    return true;
                }
            }
        }
        return false;
    }
}
