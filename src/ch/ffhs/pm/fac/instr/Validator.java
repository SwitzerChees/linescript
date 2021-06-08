package ch.ffhs.pm.fac.instr;

import java.util.HashSet;
import java.util.Set;

/**
 * Ein Validator, der kontrolliert, ob verwendete Variablen deklariet sind und
 * ob eine zugewiesene Variable auch verwendet wird. Die Klasse ist eine
 * Visitorklasse; der Rückgabewert der visit-Methoden ist immer null (ist nur
 * hier, da das Visitor-Interface das verlangt.) Verwendung:
 * instruction.acceptVisitor(validator);
 * 
 * @author urs-martin
 */
public class Validator implements InstructionVisitor<Object> {
    /** Ein Set mit den definierten Variablen. */
    private Set<String> definedVariables = new HashSet<String>();

    /** Ein Set mit den nicht definierten Variablen. */
    private Set<String> undefinedVariables = new HashSet<String>();

    /** Ein Set mit den definierten, aber nicht verwendeten Variablen. */
    private Set<String> unusedVariables = new HashSet<String>();

    /**
     * Liefert alle Variablen, die verwendet werden, aber zum Zeitpunkt der
     * Verwendung nicht definiert sind. Muss leer sein, sonst läuft Skript nicht.
     * 
     * @return Menge der nicht definierten Variablen.
     */
    public Set<String> getUndefinedVariables() {
        return undefinedVariables;
    }

    public void cleanup() {
        undefinedVariables = new HashSet<String>();
        unusedVariables = new HashSet<String>();
    }

    /**
     * Liefert alle Variablen, die nach einer Zuordnung nicht mehr verwendet werden.
     * Sollte leer sein.
     * 
     * @return Menge der nicht verwendeten Variablen.
     */
    public Set<String> getUnusedVariables() {
        return unusedVariables;
    }

    // Ohne weitere Kommentare: Auswertungsmethoden für alle Instruktionstypen.

    @Override
    public Object visitScript(InstructionScript instructionScript) {
        for (Instruction instr : instructionScript.assignments) {
            instr.acceptVisitor(this);
        }
        instructionScript.lastInstruction.acceptVisitor(this);
        return null;
    }

    @Override
    public Object visitConstant(InstructionConstant instructionConstant) {
        return null;
    }

    @Override
    public Object visitGetVariable(InstructionGetVariable instructionGetVariable) {

        if (definedVariables.contains(instructionGetVariable.name)) {
            unusedVariables.remove(instructionGetVariable.name);
        } else {
            undefinedVariables.add(instructionGetVariable.name);
        }
        return null;
    }

    @Override
    public Object visitSetVariable(InstructionSetVariable instructionSetVariable) {
        instructionSetVariable.value.acceptVisitor(this);
        definedVariables.add(instructionSetVariable.name);
        unusedVariables.add(instructionSetVariable.name);
        return null;
    }

    @Override
    public Object visitBinaryOperation(InstructionBinaryOperation instructionBinOp) {
        instructionBinOp.leftOperand.acceptVisitor(this);
        instructionBinOp.rightOperand.acceptVisitor(this);
        return null;
    }

    @Override
    public Object visitNegation(InstructionNegate instructionNegate) {
        instructionNegate.operand.acceptVisitor(this);
        return null;
    }

    @Override
    public Object visitConditionalStatement(InstructionConditionalStatement instructionConditionalStatement) {
        instructionConditionalStatement.leftOperand.acceptVisitor(this);
        instructionConditionalStatement.rightOperand.acceptVisitor(this);
        return null;
    }

    @Override
    public Object visitIfStatement(InstructionIfStatement instructionIfStatement) {
        instructionIfStatement.conditionalStatement.acceptVisitor(this);
        instructionIfStatement.ifStatement.acceptVisitor(this);
        return null;
    }

    @Override
    public Object visitElseStatement(InstructionElseStatement instructionElseStatement) {
        instructionElseStatement.ifStatement.acceptVisitor(this);
        instructionElseStatement.elseStatement.acceptVisitor(this);
        return null;
    }

    @Override
    public Object visitWhileStatement(InstructionWhileStatement instructionWhileStatement) {
        instructionWhileStatement.conditionalStatement.acceptVisitor(this);
        for (Instruction instr : instructionWhileStatement.statementList) {
            instr.acceptVisitor(this);
        }
        return null;
    }

    @Override
    public Object visitFuncStatement(InstructionFuncStatement instructionFuncStatement) {
        return null;
    }

    @Override
    public Object visitFuncCallStatement(InstructionFuncCallStatement instructionFuncCallStatement) {
        for (Instruction instr : instructionFuncCallStatement.statements) {
            instr.acceptVisitor(this);
        }
        return null;
    }
}
