package ch.ffhs.pm.fac.instr;

import java.math.BigInteger;
import java.util.HashMap;

/**
 * Context Klasse für Variablen.
 * In dieser Implementierung wird sie nicht verwendet, Variablen werden in einfachen Maps abgelegt.
 * Contexts sind dann notwendig, wenn es verschachtelte Variablen getypt sind oder wenn es
 * Gültigkeitsbereiche von Variablen gibt (z.B. in Java: Instanzvariable, 
 * lokale Variable in den Methoden und Variable, die nur in einem Teilblock einer Methode definiert sind.
 * 
 * @author urs-martin
 */
public class Context
{
    /** Context für  umfassenden Namensraum. */
    private Context outerContext;
    
    /** Eigene Variable des Contexts. */
    private HashMap<String,BigInteger> vars = new HashMap<String,BigInteger>();
    
    public Context()
    {}
    
    public Context(Context outerContext)
    {
        this.outerContext = outerContext;
    }
    
    /**
     * Liefert den Wert einer Variablen zurück. 
     * Wenn die Variable als eigene Variable definiert ist, wird dieser Wert zurückgegeben;
     * sonst wird sie im äußeren Kontext gesucht (falls dieser vorhanden ist).
     * @param name
     * @return Wert der Variablen oder null
     */
    public BigInteger getValue(String name)
    {
        BigInteger result = vars.get(name);
        if (result == null && outerContext != null) return outerContext.getValue(name);
        return result;
    }
    
    /**
     * Ändert den Wert einer Variablen; falls die Variable noch nicht exisitiert, wirdsie als eigene Variable angelegt.
     * @param name
     * @param value
     */
    public void setValue(String name, BigInteger value)
    {
        if (changeValue(name, value))
        {
            vars.put(name,  value);
        }
    }
    
    /**
     * Ändert den Wert einer exisitierenden Variablen,
     * @param name
     * @param newValue
     * @return true, falls Variable geändert wurde, false falls die Variable nicht existiert.
     */
    public boolean changeValue(String name, BigInteger newValue)
    {
        if (vars.containsKey(name))
        {
            vars.put(name,  newValue);
            return false;
        }
        else if (outerContext != null)
        {
            return outerContext.changeValue(name, newValue);
        }
        else
        {
            return true;
        }
    }
}
