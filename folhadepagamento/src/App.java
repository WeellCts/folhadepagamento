public class App {
    public static void main(String[] args) {
        System.out.println("=== TABELA PRINCIPAL DE TESTES ===");
        
        Funcionario ana = new Funcionario("Ana", 3000.00);
        Funcionario bruno = new Funcionario("Bruno", 6000.00);
        FuncionarioPJ carla = new FuncionarioPJ("Carla", 50.00, 120.0);
        Professor daniel = new Professor("Daniel", 50.00, 20.0);
        Professor elisa = new Professor("Elisa", 100.00, 20.0);

        Funcionario[] lista = {ana, bruno, carla, daniel, elisa};

        System.out.printf("%-10s | %-6s | %-10s | %-10s | %-10s | %-10s | %-10s | %-10s\n", 
                "Nome", "Regime", "Bruto", "INSS", "IRRF", "FGTS", "DAS", "Líquido");
        System.out.println("-----------------------------------------------------------------------------------------");

        for (Funcionario f : lista) {
            String regime = f.isPJ() ? "PJ" : "CLT";
            System.out.printf("%-10s | %-6s | R$ %7.2f | R$ %7.2f | R$ %7.2f | R$ %7.2f | R$ %7.2f | R$ %7.2f\n",
                    f.getNome(),
                    regime,
                    f.calcularBruto(),
                    f.calcularINSS(),
                    f.calcularIRRF(),
                    f.calcularFGTS(),
                    f.calcularMEI(),
                    f.calcularLiquido()
            );
        }

        System.out.println("\n=== TESTES ADICIONAIS DE VALIDAÇÃO E REGRAS DA ETAPA 6 ===");

        // Teste 1: Valores negativos
        try {
            new Funcionario("Teste", -100);
        } catch (IllegalArgumentException e) {
            System.out.println("Test 1 (Negativos): Sucesso -> Exceção capturada: " + e.getMessage());
        }

        // Teste 2: CLT com salário zero
        Funcionario zero = new Funcionario("Zero", 0);
        System.out.printf("Test 2 (CLT Zero): Bruto: %.2f | INSS: %.2f | Líquido: %.2f\n", 
                zero.calcularBruto(), zero.calcularINSS(), zero.calcularLiquido());

        // Teste 3: PJ com 0 horas
        FuncionarioPJ pjZero = new FuncionarioPJ("PJ Zero", 50, 0);
        System.out.printf("Test 3 (PJ 0 horas): Bruto: %.2f | DAS: %.2f | Líquido: %.2f\n", 
                pjZero.calcularBruto(), pjZero.calcularMEI(), pjZero.calcularLiquido());

        // Teste 4: Teto INSS (8000 vs 10500 vs 12000)
        Funcionario f8k = new Funcionario("8k", 8000);
        Funcionario f10k = new Funcionario("10.5k", 10500);
        Funcionario f12k = new Funcionario("12k", 12000);
        System.out.printf("Test 4 (Teto INSS): 8k=%.2f | 10.5k=%.2f | 12k=%.2f\n", 
                f8k.calcularINSS(), f10k.calcularINSS(), f12k.calcularINSS());

        // Teste 5: Isenção vs Redução IRRF (5000 vs 6000)
        System.out.printf("Test 5 (IRRF 5k vs 6k): 5k IRRF=%.2f | 6k IRRF=%.2f\n", 
                new Funcionario("5k", 5000).calcularIRRF(), bruno.calcularIRRF());

        // Teste 6: Chamadas repetidas no mesmo objeto
        double liq1 = ana.calcularLiquido();
        double liq2 = ana.calcularLiquido();
        System.out.println("Test 6 (Imutabilidade de chamadas repetidas): " + (liq1 == liq2 ? "Passou (Invariante)" : "Falhou"));

        // Teste 7: Independência entre objetos
        double anaAntes = ana.calcularLiquido();
        new Funcionario("Bruno2", 9000.00);
        double anaDepois = ana.calcularLiquido();
        System.out.println("Test 7 (Independência de Objetos): " + (anaAntes == anaDepois ? "Passou (Objetos independentes)" : "Falhou"));
    }
}