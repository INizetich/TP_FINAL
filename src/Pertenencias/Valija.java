package Pertenencias;

public class Valija {
    private String dimension;
    private double peso;

    public Valija(String dimension, double peso) {
        this.dimension = dimension;
        this.peso = peso;
    }

    public Valija(){
        this.dimension = "";
        this.peso = 0.0;
    }


    public String getDimension() {
        return dimension;
    }

    public void setDimension(String dimension) {
        this.dimension = dimension;
    }

    public double getPeso() {
        return peso;
    }

    public void setPeso(double peso) {
        this.peso = peso;
    }



    @Override
    public String toString() {
        return "Valija{" +
                "dimension='" + dimension + '\'' +
                ", peso=" + peso +
                '}';
    }
}
