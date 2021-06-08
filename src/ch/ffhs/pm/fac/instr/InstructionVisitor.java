package ch.ffhs.pm.fac.instr;

public interface InstructionVisitor<R>
{
    public R visitScript(InstructionScript instructionScript);

    public R visitBinaryOperation(InstructionBinaryOperation instructionBinOperation);

    public R visitIfStatement(InstructionIfStatement instructionIfStatement);

    public R visitElseStatement(InstructionElseStatement instructionElseStatement);

    public R visitWhileStatement(InstructionWhileStatement instructionWhileStatement);

    public R visitFuncStatement(InstructionFuncStatement instructionFuncStatement);

    public R visitFuncCallStatement(InstructionFuncCallStatement instructionFuncCallStatement);

    public R visitConditionalStatement(InstructionConditionalStatement instructionConditionalStatement);

    public R visitNegation(InstructionNegate instructionUnaryOperation);

    public R visitConstant(InstructionConstant instructionConstant);

    public R visitGetVariable(InstructionGetVariable instructionGetVariable);

    public R visitSetVariable(InstructionSetVariable instructionSetVariable);


}
