package backend.interpreter.executors;

import backend.interpreter.Executor;
import intermediate.ICodeNode;
import intermediate.icodeimpl.ICodeNodeTypeImpl;

import java.util.List;

public class LoopExecutor extends StatementExecutor {

    public LoopExecutor(Executor parent) {
        super(parent);
    }

    @Override
    public Object execute(ICodeNode node) {
        boolean exitLoop = false;
        ICodeNode exprNode = null;
        List<ICodeNode> loopChildren = node.getChildren();

        ExpressionExecutor expressionExecutor = new ExpressionExecutor(this);
        StatementExecutor statementExecutor = new StatementExecutor(this);

        while (!exitLoop) {
            ++executionCount;

            for (ICodeNode child : loopChildren) {
                ICodeNodeTypeImpl childType = (ICodeNodeTypeImpl) child.getType();

                if (childType == ICodeNodeTypeImpl.TEST) {
                    if (exprNode == null) {
                        exprNode = child.getChildren().get(0);
                    }
                    exitLoop = (Boolean) expressionExecutor.execute(exprNode);
                } else {
                    statementExecutor.execute(child);
                }

                if (exitLoop) {
                    break;
                }
            }
        }

        return null;
    }
}
