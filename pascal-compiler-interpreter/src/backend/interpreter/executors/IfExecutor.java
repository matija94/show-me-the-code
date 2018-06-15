package backend.interpreter.executors;

import backend.interpreter.Executor;
import intermediate.ICodeNode;

import java.util.ArrayList;

public class IfExecutor extends StatementExecutor {

    public IfExecutor(Executor parent) {
        super(parent);
    }

    @Override
    public Object execute(ICodeNode node) {
        ArrayList<ICodeNode> children = node.getChildren();
        ICodeNode exprNode = children.get(0);
        ICodeNode thenStmtNode = children.get(1);
        ICodeNode elseStmtNode = children.size() > 2 ? children.get(2) : null;

        ExpressionExecutor expressionExecutor = new ExpressionExecutor(this);
        StatementExecutor statementExecutor = new StatementExecutor(this);

        boolean b = (Boolean) expressionExecutor.execute(exprNode);
        if (b) {
            statementExecutor.execute(thenStmtNode);
        }
        else if (elseStmtNode != null) {
            statementExecutor.execute(elseStmtNode);
        }

        ++executionCount;
        return null;
    }
}
