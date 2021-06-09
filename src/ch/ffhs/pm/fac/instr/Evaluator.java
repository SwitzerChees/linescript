package ch.ffhs.pm.fac.instr;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.MathContext;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Ein Evaluator zum Auswerten von Instructions. Die Klasse ist eine
 * Visitorklasse und wird wie folgt verwendet:
 * instruction.acceptVisitor(evaluator);
 * 
 * @author urs-martin
 */
public class Evaluator implements InstructionVisitor<Object> {
    /** Eine Map mit Namen-Wert Paaren für Variable */
    private Map<String, Object> context;

    /**
     * Erzeugt einen Evaluator mit leerem Context und leerer FunktionsLibrary.
     */
    public Evaluator() {
        this(new HashMap<String, Object>());
    }

    /**
     * Erzeugt einen Evaluator
     * 
     * @param context Vordefinierte Variablenwerte
     */
    public Evaluator(Map<String, Object> context) {
        this.context = context;
    }

    // Ohne weitere Kommentare: Auswertungsmethoden für alle Instruktionstypen.

    @Override
    public Object visitConstant(InstructionConstant instructionConstant) {
        if (instructionConstant.value instanceof Instruction) {
            Instruction instruction = (Instruction) instructionConstant.value;
            return instruction.acceptVisitor(this);
        }
        return instructionConstant.value;
    }

    @Override
    public Object visitGetVariable(InstructionGetVariable instructionGetVariable) {
        if (context.containsKey(instructionGetVariable.name)) {
            return context.get(instructionGetVariable.name);
        } else {
            throw new RuntimeException("Variable " + instructionGetVariable.name + " not initialized.");
        }
    }

    @Override
    public Object visitSetVariable(InstructionSetVariable instructionSetVariable) {
        if (instructionSetVariable.value instanceof InstructionConstant
                || instructionSetVariable.value instanceof InstructionGetVariable) {
            Object setValue = instructionSetVariable.value.acceptVisitor(this);
            if (setValue instanceof BigDecimal) {
                BigDecimal evaluatedValue = (BigDecimal) setValue;
                BigDecimal existingValue = new BigDecimal(0);
                Object exValue = context.get(instructionSetVariable.name);
                if (exValue instanceof BigDecimal) {
                    existingValue = (BigDecimal) exValue;
                }
                switch (instructionSetVariable.assignOperator) {
                    case ASSIGN:
                        existingValue = evaluatedValue;
                        break;
                    case ASSIGN_PLUS:
                        existingValue = existingValue.add(evaluatedValue);
                        break;
                    case ASSIGN_MINUS:
                        existingValue = existingValue.add(new BigDecimal(-1 * evaluatedValue.doubleValue()));
                        break;
                    case ASSIGN_DIVIDE:
                        existingValue = new BigDecimal(existingValue.doubleValue() / evaluatedValue.doubleValue());
                        break;
                    case ASSIGN_MUL:
                        existingValue = new BigDecimal(existingValue.doubleValue() * evaluatedValue.doubleValue());
                        break;
                }
                context.put(instructionSetVariable.name, existingValue);
            } else if (setValue instanceof String) {
                String evaluatedValue = (String) instructionSetVariable.value.acceptVisitor(this);
                for (String var : context.keySet()) {
                    evaluatedValue = evaluatedValue.replaceAll(String.format("\\$\\{%s\\}", var),
                            context.get(var).toString());
                }
                context.put(instructionSetVariable.name, evaluatedValue);
            } else if (setValue instanceof Boolean) {
                boolean evaluatedValue = (boolean) instructionSetVariable.value.acceptVisitor(this);
                context.put(instructionSetVariable.name, evaluatedValue);
            }
        } else if (instructionSetVariable.value instanceof InstructionBinaryOperation) {
            InstructionBinaryOperation instBinaryOperation = (InstructionBinaryOperation) instructionSetVariable.value;
            context.put(instructionSetVariable.name, instBinaryOperation.acceptVisitor(this));
        } else if (instructionSetVariable.value instanceof InstructionNegate) {
            InstructionNegate instructionNegate = (InstructionNegate) instructionSetVariable.value;
            context.put(instructionSetVariable.name, instructionNegate.acceptVisitor(this));
        } else if (instructionSetVariable.value instanceof InstructionIfStatement) {
            InstructionIfStatement instructionIfElse = (InstructionIfStatement) instructionSetVariable.value;
            Object ifelseValue = instructionIfElse.acceptVisitor(this);
            if (ifelseValue != null) {
                context.put(instructionSetVariable.name, ifelseValue);
            }
        } else if (instructionSetVariable.value instanceof InstructionFuncCallStatement) {
            InstructionFuncCallStatement instructionFuncCall = (InstructionFuncCallStatement) instructionSetVariable.value;
            ArrayList<?> funcResult = (ArrayList<?>) instructionFuncCall.acceptVisitor(this);
            if (funcResult.size() > 0) {
                context.put(instructionSetVariable.name, funcResult);
            }
        }
        return null;
    }

    @Override
    public BigDecimal visitBinaryOperation(InstructionBinaryOperation instructionBinOp) {
        BigDecimal left = (BigDecimal) instructionBinOp.leftOperand.acceptVisitor(this);
        BigDecimal right = (BigDecimal) instructionBinOp.rightOperand.acceptVisitor(this);
        switch (instructionBinOp.operator) {
            case PLUS:
                return left.add(right);
            case MINUS:
                return left.subtract(right);
            case MUL:
                return left.multiply(right);
            case DIV:
                return left.divide(right, MathContext.DECIMAL128);
            case MOD:
                BigInteger intValue = left.toBigInteger();
                if (new BigDecimal(intValue).equals(left))
                    return new BigDecimal(intValue.mod(right.toBigInteger()));
                return new BigDecimal(intValue.mod(right.toBigInteger())).subtract(new BigDecimal(intValue)).add(left);
            case POW:
                return left.pow(right.intValue());
            default:
                assert false;
                return null;
        }
    }

    @Override
    public Object visitConditionalStatement(InstructionConditionalStatement instructionCondExpr) {
        Object left = instructionCondExpr.leftOperand.acceptVisitor(this);
        Object right = instructionCondExpr.rightOperand.acceptVisitor(this);
        if (!left.getClass().equals(right.getClass())) {
            throw new RuntimeException(
                    "The conditional statement do not have the same type of class on both sites left: "
                            + left.getClass() + " right: " + right.getClass());
        }
        if (left instanceof BigDecimal) {
            BigDecimal leftNumber = (BigDecimal) left;
            BigDecimal rightNumber = (BigDecimal) right;
            switch (instructionCondExpr.conditionalExpression) {
                case EQUAL:
                    return leftNumber.equals(rightNumber);
                case NOT_EQUAL:
                    return !leftNumber.equals(rightNumber);
                case LESS_THAN:
                    return leftNumber.compareTo(rightNumber) < 0;
                case LESS_EQUAL:
                    return leftNumber.compareTo(rightNumber) <= 0;
                case GREATER:
                    return leftNumber.compareTo(rightNumber) > 0;
                case GREATER_EQUAL:
                    return leftNumber.compareTo(rightNumber) >= 0;
            }
        } else if (left instanceof String) {
            String leftString = (String) left;
            String rightString = (String) right;
            switch (instructionCondExpr.conditionalExpression) {
                case EQUAL:
                    return leftString.equals(rightString);
                case NOT_EQUAL:
                    return !leftString.equals(rightString);
                case LESS_THAN:
                    return leftString.compareTo(rightString) < 0;
                case LESS_EQUAL:
                    return leftString.compareTo(rightString) <= 0;
                case GREATER:
                    return leftString.compareTo(rightString) > 0;
                case GREATER_EQUAL:
                    return leftString.compareTo(rightString) >= 0;
            }
        }
        return false;
    }

    @Override
    public Object visitIfStatement(InstructionIfStatement instructionIfElseStatement) {
        Boolean conditionalStatement = (Boolean) instructionIfElseStatement.conditionalStatement.acceptVisitor(this);
        if (conditionalStatement) {
            return instructionIfElseStatement.ifStatement.acceptVisitor(this);
        }
        return null;
    }

    @Override
    public Object visitElseStatement(InstructionElseStatement instructionElseStatement) {
        Object ifResult = instructionElseStatement.ifStatement.acceptVisitor(this);
        if (ifResult == null) {
            return instructionElseStatement.elseStatement.acceptVisitor(this);
        }
        return ifResult;
    }

    @Override
    public Object visitWhileStatement(InstructionWhileStatement instructionWhileStatement) {
        Boolean conditionalStatement = (Boolean) instructionWhileStatement.conditionalStatement.acceptVisitor(this);
        ArrayList<Object> results = new ArrayList<Object>();
        while (conditionalStatement) {
            for (Instruction instr : instructionWhileStatement.statementList) {
                Object result = instr.acceptVisitor(this);
                if (result != null) {
                    results.add(result);
                }
            }
            conditionalStatement = (Boolean) instructionWhileStatement.conditionalStatement.acceptVisitor(this);
        }
        return results;
    }

    @Override
    public BigDecimal visitNegation(InstructionNegate instructionNegate) {
        BigDecimal operand = (BigDecimal) instructionNegate.operand.acceptVisitor(this);
        return operand.negate();
    }

    @Override
    public Object visitScript(InstructionScript instructionScript) {
        Object result = null;
        for (Instruction instr : instructionScript.assignments) {
            result = instr.acceptVisitor(this);
        }
        return result;
    }

    @Override
    public Object visitFuncStatement(InstructionFuncStatement instructionFuncStatement) {
        context.put(instructionFuncStatement.name, instructionFuncStatement);
        return null;
    }

    @Override
    public Object visitFuncCallStatement(InstructionFuncCallStatement instructionFuncCallStatement) {
        InstructionFuncStatement funcStatement = (InstructionFuncStatement) context
                .get(instructionFuncCallStatement.name);
        if (funcStatement.name.equals("exit")) {
            System.out.println("Bye! 👋");
            System.exit(0);
        }
        Map<String, Object> funcContext = new HashMap<String, Object>();
        for (int i = 0; i < funcStatement.parameters.size(); i++) {
            funcContext.put(funcStatement.parameters.get(i),
                    instructionFuncCallStatement.statements.get(i).acceptVisitor(this));
        }
        funcContext.put(instructionFuncCallStatement.name, funcStatement);
        Evaluator evaluator = new Evaluator(funcContext);
        ArrayList<Object> results = new ArrayList<Object>();
        for (Instruction instr : funcStatement.statementList) {
            Object result = instr.acceptVisitor(evaluator);
            if (result != null) {
                results.add(result);
            }
        }
        if (results.size() == 0 || results.size() > 1)
            return results;
        return results.get(0);
    }
}
