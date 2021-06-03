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
public class Evaluator implements InstructionVisitor<Object>
{
    /** Eine Map mit Namen-Wert Paaren für Variable*/
    private Map<String, Object> context;
        
    /**
     * Erzeugt einen Evaluator mit leerem Context und leerer FunktionsLibrary.
     */
    public Evaluator()
    {
        this(new HashMap<String,Object>());
    }

    /**
     * Erzeugt einen Evaluator
     * @param context Vordefinierte Variablenwerte
     */
    public Evaluator(Map<String,Object> context)
    {
        this.context = context;
    }

    // Ohne weitere Kommentare: Auswertungsmethoden für alle Instruktionstypen.
    
    @Override
    public Object visitConstant(
            InstructionConstant instructionConstant)
    {
        return instructionConstant.value;
    }

    @Override
    public Object visitGetVariable(
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
    public Object visitSetVariable(
            InstructionSetVariable instructionSetVariable)
    {
        Object evaluatedValue = instructionSetVariable.value.acceptVisitor(this);
        context.put(instructionSetVariable.name, evaluatedValue);
        return null;
    }


    @Override
    public BigInteger visitBinaryOperation(
            InstructionBinaryOperation instructionBinOp)
    {
        BigInteger left =  (BigInteger)instructionBinOp.leftOperand.acceptVisitor(this);
        BigInteger right = (BigInteger)instructionBinOp.rightOperand.acceptVisitor(this);
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
        BigInteger operand = (BigInteger)instructionNegate.operand.acceptVisitor(this);
        return operand.negate();
    }

    @Override
    public Object visitScript(InstructionScript instructionScript)
    {
        for (Instruction instr : instructionScript.assignments)
        {
            instr.acceptVisitor(this);
        }
        return instructionScript.lastInstruction.acceptVisitor(this);
    }






}
