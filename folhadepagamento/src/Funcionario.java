public class Funcionario {
    private String nome;
    private double salarioMensal;
    private final boolean pj;

    public Funcionario(String nome, double salarioMensal) {
        this(nome, salarioMensal, false);
    }

    protected Funcionario(String nome, double salarioMensal, boolean pj) {
        if (nome == null || nome.isBlank() || salarioMensal < 0) {
            throw new IllegalArgumentException("Nome e salário inválidos.");
        }
        this.nome = nome;
        this.salarioMensal = salarioMensal;
        this.pj = pj;
    }

    public String getNome() {
        return nome;
    }

    public double getSalarioMensal() {
        return salarioMensal;
    }

    public boolean isPJ() {
        return pj;
    }

    protected double arredondar(double valor) {
        return Math.round(valor * 100.0) / 100.0;
    }

    public double calcularBruto() {
        return meuarredondar(this.salarioMensal);
    }

    // Auxiliar de arredondamento
    private double meuarredondar(double valor) {
        return Math.round(valor * 100.0) / 100.0;
    }

    public double calcularINSS() {
        if (isPJ()) {
            return 0.0;
        }
        double bruto = calcularBruto();
        if (bruto <= 0) return 0.0;

        double inss = 0.0;

        // Faixa 1: até 1.621,00 (7,5%)
        if (bruto > 1621.00) {
            inss += 1621.00 * 0.075;
        } else {
            inss += bruto * 0.075;
            return meuarredondar(inss);
        }

        // Faixa 2: 1.621,00 até 2.902,84 (9%)
        if (bruto > 2902.84) {
            inss += (2902.84 - 1621.00) * 0.09;
        } else {
            inss += (bruto - 1621.00) * 0.09;
            return meuarredondar(inss);
        }

        // Faixa 3: 2.902,84 até 4.354,27 (12%)
        if (bruto > 4354.27) {
            inss += (4354.27 - 2902.84) * 0.12;
        } else {
            inss += (bruto - 2902.84) * 0.12;
            return meuarredondar(inss);
        }

        // Faixa 4: 4.354,27 até 8.475,55 (14%)
        if (bruto > 8475.55) {
            inss += (8475.55 - 4354.27) * 0.14;
        } else {
            inss += (bruto - 4354.27) * 0.14;
        }

        return meuarredondar(inss);
    }

    public double calcularIRRF() {
        if (isPJ()) {
            return 0.0;
        }
        double bruto = calcularBruto();
        if (bruto <= 0) return 0.0;

        double inssCalculado = calcularINSS();
        double deducaoSimplificada = 607.20;
        double deducaoEscolhida = Math.max(inssCalculado, deducaoSimplificada);

        double baseIR = Math.max(0.0, bruto - deducaoEscolhida);

        double aliquota = 0.0;
        double parcelaDeducao = 0.0;

        if (baseIR <= 2428.80) {
            aliquota = 0.0;
            parcelaDeducao = 0.0;
        } else if (baseIR <= 2826.65) {
            aliquota = 0.075;
            parcelaDeducao = 182.16;
        } else if (baseIR <= 3751.05) {
            aliquota = 0.15;
            parcelaDeducao = 394.16;
        } else if (baseIR <= 4664.68) {
            aliquota = 0.225;
            parcelaDeducao = 675.49;
        } else {
            aliquota = 0.275;
            parcelaDeducao = 908.73;
        }

        double impostoInicial = Math.max(0.0, (baseIR * aliquota) - parcelaDeducao);
        impostoInicial = meuarredondar(impostoInicial);

        // Cálculo da Redução
        double reducao = 0.0;
        if (bruto <= 5000.00) {
            return 0.0; // Isento / Final zero
        } else if (bruto <= 7350.00) {
            reducao = 978.62 - (0.133145 * bruto);
            reducao = Math.max(0.0, meuarredondar(reducao));
        } else {
            reducao = 0.0;
        }

        double irrfFinal = Math.max(0.0, impostoInicial - reducao);
        return meuarredondar(irrfFinal);
    }

    public double calcularFGTS() {
        if (isPJ()) {
            return 0.0;
        }
        return meuarredondar(calcularBruto() * 0.08);
    }

    public double calcularMEI() {
        if (isPJ()) {
            return 86.05;
        }
        return 0.0;
    }

    public double calcularLiquido() {
        if (isPJ()) {
            return meuarredondar(calcularBruto() - calcularMEI());
        } else {
            return meuarredondar(calcularBruto() - calcularINSS() - calcularIRRF());
        }
    }
}