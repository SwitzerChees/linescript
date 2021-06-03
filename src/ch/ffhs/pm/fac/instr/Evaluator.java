package ch.ffhs.pm.fac.instr;

import java.math.BigDecimal;
import java.math.BigInteger;
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
        if (instructionSetVariable.value.variableType == VariableType.NUMBER) {
            BigDecimal evaluatedValue = (BigDecimal) instructionSetVariable.value.acceptVisitor(this);
            BigDecimal existingValue = (BigDecimal) context.get(instructionSetVariable.name);
            if (existingValue == null) {
                existingValue = new BigDecimal(0);
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
        } else if (instructionSetVariable.value.variableType == VariableType.STRING) {
            String evaluatedValue = (String) instructionSetVariable.value.acceptVisitor(this);
            context.put(instructionSetVariable.name, evaluatedValue);
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
            case TIMES:
                return left.multiply(right);
            case DIV:
                return left.divide(right);
            case MOD:
                return new BigDecimal(left.toBigInteger().mod(right.toBigInteger()));
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
        }
        return false;
    }

    @Override
    public Object visitIfElseStatement(InstructionIfElseStatement instructionIfElseStatement) {
        Boolean conditionalStatement = (Boolean)instructionIfElseStatement.conditionalStatement.acceptVisitor(this);
        if (conditionalStatement) {
            return instructionIfElseStatement.ifStatement.acceptVisitor(this);
        }
        return null;
    }

    @Override
    public BigDecimal visitNegation(InstructionNegate instructionNegate) {
        BigDecimal operand = (BigDecimal) instructionNegate.operand.acceptVisitor(this);
        return operand.negate();
    }

    @Override
    public Object visitScript(InstructionScript instructionScript) {
        for (Instruction instr : instructionScript.assignments) {
            instr.acceptVisitor(this);
        }
        return instructionScript.lastInstruction.acceptVisitor(this);
    }
}
