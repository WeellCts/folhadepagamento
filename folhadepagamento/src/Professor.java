public class Professor extends FuncionarioPJ {

    public Professor(String nome, double valorHoraAula, double horasSemanais) {
        super(nome, valorHoraAula, horasSemanais, false);
    }

    @Override
    public double calcularBruto() {
        double horasMensais = getHorasTrabalhadas() * 4.5;
        double remuneracaoAulas = getValorHora() * horasMensais;
        double dsr = remuneracaoAulas / 6.0;
        double bruto = remuneracaoAulas + dsr;
        return Math.round(bruto * 100.0) / 100.0;
    }
}