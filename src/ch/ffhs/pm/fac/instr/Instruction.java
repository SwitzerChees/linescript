package ch.ffhs.pm.fac.instr;

/**
 * Basisklasse für alle Instruktionen.
 * 
 * @author urs-martin
 */
public abstract class Instruction
{
    public abstract <R> R acceptVisitor(InstructionVisitor<R> visitor);
        
}
