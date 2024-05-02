import com.google.gson.annotations.SerializedName;

import java.text.DecimalFormat;

public class Transaccion {

    private double valorInicial = 0;
    @SerializedName("base_code")
    private String codigoPaisBase = "";
    @SerializedName("conversion_result")
    private double valorFinal = 0;
    @SerializedName("target_code")
    private String codigoPaisFinal = "";
    @SerializedName("conversion_rate")
    private double valorConversion = 0;

    private String fecha = "";


    public Transaccion(double  valorInicial, String codigoPaisBase, double valorFinal, String codigoPaisFinal, double valorConversion) {
        this.valorInicial = valorInicial;
        this.codigoPaisBase = codigoPaisBase;
        this.codigoPaisBase = codigoPaisBase;
        this.valorFinal = valorFinal;
        this.valorConversion = valorConversion;
    }


    public double getValorInicial() {
        return valorInicial;
    }

    public void setValorInicial(double valorInicial) {
        this.valorInicial = valorInicial;
    }

    public String getCodigoPaisBase() {
        return codigoPaisBase;
    }

/*    public void setCodigoPaisBase(String codigoPaisBase) {
        this.codigoPaisBase = codigoPaisBase;
    }*/

    public double getValorFinal() {
        return valorFinal;
    }

/*    public void setValorFinal(double  valorFinal) {
        this.valorFinal = valorFinal;
    }*/

    public String getCodigoPaisFinal() {
        return codigoPaisFinal;
    }

/*    public void setCodigoPaisFinal(String codigoPaisFinal) {
        this.codigoPaisFinal = codigoPaisFinal;
    }*/

    public double getValorConversion() {
        return valorConversion;
    }

/*    public void setValorConversion(float valorConversion) {
        this.valorConversion = valorConversion;
    }*/

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    private static final DecimalFormat df = new DecimalFormat("0.00");
    @Override
    public String toString() {
        return "El monto ingresado de [" + codigoPaisBase + "] " + df.format(valorInicial) +
                " es equivalente a [" + codigoPaisFinal + "] " + df.format(valorFinal) +
                ".\n- Valor de conversión = " + valorConversion+ " ["+codigoPaisFinal +"]"+
                ".\n- Fecha de transacción = " + fecha;
    }

}

