package ch.ffhs.pm.fac.instr;


import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;

/**
 * Ein Evaluator zum Auswerten von Instructions.
 * Die Klasse ist eine Visitorklasse und wird wie folgt verwendet:
 * instruction.acceptVisitor(evaluator);
 * 
 * @author urs-martin
 */
public class Evaluator implements InstructionVisitor<BigInteger>
{
    /** Eine Map mit Namen-Wert Paaren für Variable*/
    private Map<String,BigInteger> context;
        
    /**
     * Erzeugt einen Evaluator mit leerem Context und leerer FunktionsLibrary.
     */
    public Evaluator()
    {
        this(new HashMap<String,BigInteger>());
    }

    /**
     * Erzeugt einen Evaluator
     * @param context Vordefinierte Variablenwerte
     */
    public Evaluator(Map<String,BigInteger> context)
    {
        this.context = context;
    }

    // Ohne weitere Kommentare: Auswertungsmethoden für alle Instruktionstypen.
    
    @Override
    public BigInteger visitConstant(
            InstructionConstant instructionConstant)
    {
        return (BigInteger) instructionConstant.value;
    }

    @Override
    public BigInteger visitGetVariable(
            InstructionGetVariable instructionGetVariable)
    {
        if (context.containsKey(instructionGetVariable.name))
        {
            return context.get(instructionGetVariable.name);
        }
        else 
        {
            throw new RuntimeException("Variable " + instructionGetVariable.name + " not initialized."); 
            //TODO spezifischere Exception
        }
    }

    @Override
    public BigInteger visitSetVariable(
            InstructionSetVariable instructionSetVariable)
    {
        BigInteger evaluatedValue = instructionSetVariable.value.acceptVisitor(this);
        context.put(instructionSetVariable.name, evaluatedValue);
        return null;
    }


    @Override
    public BigInteger visitBinaryOperation(
            InstructionBinaryOperation instructionBinOp)
    {
        BigInteger left = instructionBinOp.leftOperand.acceptVisitor(this);
        BigInteger right = instructionBinOp.rightOperand.acceptVisitor(this);
        switch (instructionBinOp.operator)
        {
            case PLUS:  return left.add(right);
            case MINUS: return left.subtract(right);
            case TIMES: return left.multiply(right);
            case DIV:   return left.divide(right);
            case MOD:   return left.mod(right);
            case POW:   return left.pow(right.intValue()); //TODO overflow abfangen
            default:    assert false; return null;
        }
    }

    @Override
    public BigInteger visitNegation(
            InstructionNegate instructionNegate)
    {
        BigInteger operand = instructionNegate.operand.acceptVisitor(this);
        return operand.negate();
    }

    @Override
    public BigInteger visitScript(InstructionScript instructionScript)
    {
        for (Instruction instr : instructionScript.assignments)
        {
            instr.acceptVisitor(this);
        }
        return instructionScript.lastInstruction.acceptVisitor(this);
    }






}
