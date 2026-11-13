# 💱 Conversor de Moedas

[![Java 17](https://img.shields.io/badge/Java-17-orange.svg)](https://www.oracle.com/java/)
[![Maven](https://img.shields.io/badge/Maven-Build-blue.svg)](https://maven.apache.org/)
[![API](https://img.shields.io/badge/API-AwesomeAPI-green.svg)](https://docs.awesomeapi.com.br/api-de-moedas)
[![Status](https://img.shields.io/badge/Status-Concluído-success.svg)]()

Aplicação em linha de comando (CLI) desenvolvida em **Java 17** para conversão de moedas em tempo real, consumindo cotações atualizadas através da [AwesomeAPI](https://docs.awesomeapi.com.br/api-de-moedas).

O sistema oferece tanto conversão direta entre moedas estrangeiras quanto conversão para Real Brasileiro (**BRL**) com cálculo detalhado de alíquotas de **IOF** (Imposto sobre Operações Financeiras) conforme o tipo de operação cambial.

---

## 🚀 Funcionalidades

- **Cotações em Tempo Real:** Consulta de cotações comerciais atualizadas via AwesomeAPI.
- **Lista de Moedas Suportadas:** Visualização rápida dos códigos e nomes de moedas aceitas pelo sistema.
- **Conversão Sem Taxa:** Conversão direta entre diferentes moedas estrangeiras utilizando a cotação de mercado (`bid`).
- **Conversão com Taxa (IOF):** Conversão de moedas estrangeiras para **BRL** simulando cenários fiscais reais:
  - Compra ou saque de moeda em espécie (IOF de 3,5%)
  - Compras internacionais via cartão de crédito/débito (IOF de 3,5%)
  - Investimento no mercado financeiro/capitais no exterior (IOF de 1,1%)
  - Recebimentos do exterior (IOF de 0,38% deduzido)
- **Precisão Financeira com `BigDecimal`:** Todos os cálculos monetários e arredondamentos são tratados com `BigDecimal` (`HALF_UP`), garantindo precisão e evitando perdas de precisão por ponto flutuante.
- **Validações Robustas:** Verificação de moedas suportadas, tratamento de exceções para entradas incorretas e mensagens claras para pares de moedas indisponíveis na API.

---

## 💵 Moedas Suportadas

O conversor possui suporte às seguintes moedas:

| Código | Moeda | Código | Moeda |
| :---: | :--- | :---: | :--- |
| **AED** | Dirham dos Emirados | **BRL** | Real Brasileiro |
| **AMD** | Dram Armênio | **BSD** | Dólar das Bahamas |
| **AOA** | Kwanza Angolano | **BYN** | Rublo Bielorrusso |
| **ARS** | Peso Argentino | **CAD** | Dólar Canadense |
| **AUD** | Dólar Australiano | **CHF** | Franco Suíço |
| **BBD** | Dólar de Barbados | **CLP** | Peso Chileno |
| **BDT** | Taka de Bangladesh | **EUR** | Euro |
| **BGN** | Lev Búlgaro | **USD** | Dólar Americano |
| **BOB** | Boliviano | | |

---

## 📊 Regras de Tributação (IOF)

Na opção de conversão com taxa (Moeda Estrangeira ➔ BRL), o usuário seleciona a modalidade de operação para apuração do imposto:

| ID | Tipo de Transação | Alíquota IOF | Operação Matemática |
|:--:|:------------------|:------------:|:-------------------|
| **1** | Moeda em espécie (compra ou saque) | `3,5%` | Valor Convertido + Imposto |
| **2** | Cartão internacional (crédito/débito) | `3,5%` | Valor Convertido + Imposto |
| **3** | Remessa para investimento no exterior | `1,1%` | Valor Convertido + Imposto |
| **4** | Recebimentos do exterior | `0,38%` | Valor Convertido - Imposto |

---

## 🛠️ Tecnologias Utilizadas

- **Linguagem:** [Java 17](https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html)
- **Gerenciador de Dependências:** [Apache Maven](https://maven.apache.org/)
- **Desserialização JSON:** [Jackson Databind](https://github.com/FasterXML/jackson-databind) (`tools.jackson.core:jackson-databind:3.2.4`)
- **Cliente HTTP:** `java.net.http.HttpClient` (nativo do Java)
- **API Externa:** [AwesomeAPI - Cotações de Moedas](https://docs.awesomeapi.com.br/api-de-moedas)

---

## 📂 Estrutura do Projeto

```text
conversor-de-moedas/
├── pom.xml
├── README.md
└── src/
    └── main/
        └── java/
            ├── ConversorApplication.java       # Ponto de entrada (Main)
            ├── app/
            │   └── Application.java            # Controle de fluxo do menu principal
            ├── model/
            │   ├── DadosConversao.java         # Record para mapeamento da resposta JSON
            │   ├── MoedasSuportadas.java       # Enum das moedas aceitas
            │   └── TipoTransacao.java          # Enum dos tipos de operação e alíquotas IOF
            └── service/
                ├── ConsumoApi.java             # Cliente HTTP para requisições à AwesomeAPI
                ├── Conversor.java              # Regras de conversão, cálculos e formatações
                ├── MenuConverter.java          # Menus interativos e captura de dados
                └── PegarDados.java             # Conversão do JSON retornado para objeto Java
```

---

## ⚙️ Pré-requisitos

Antes de iniciar, certifique-se de possuir instalado em sua máquina:
- **Java JDK 17** ou superior: [Download JDK](https://www.oracle.com/java/technologies/downloads/)
- **Git**: [Download Git](https://git-scm.com/)
- **Maven** (opcional, caso vá compilar e executar via linha de comando): [Download Maven](https://maven.apache.org/)

---

## 💻 Como Executar

### 1. Clonar o repositório

```bash
git clone https://github.com/danielandradechaves/conversor-de-moedas.git
cd conversor-de-moedas
```

### 2. Compilar o projeto com Maven

```bash
mvn clean compile
```

### 3. Executar a aplicação

**Via Maven Exec Plugin:**
```bash
mvn exec:java -Dexec.mainClass="ConversorApplication"
```

**Ou compilando e executando com Java diretamente:**
```bash
mvn package
java -cp target/classes;target/dependency/* ConversorApplication
```
*(No Linux/macOS, utilize `:` em vez de `;` no classpath).*

**Via IDE (IntelliJ IDEA, Eclipse, VS Code):**
Basta abrir a pasta do projeto como um projeto Maven e executar a classe `ConversorApplication.java`.

---

## 🖥️ Exemplo de Uso

Ao iniciar o programa, o menu interativo é apresentado:

```text
*********************************************
 Bem vindo ao conversor de moedas!
 Selecione uma opção:

 0 - Sair
 1 - Exibir lista de moedas suportadas
 2 - Conversão sem taxa
 3 - Conversão com taxa
*********************************************
```

### Exemplo: Conversão com taxa (USD para BRL)
```text
Escolha o tipo de transação: 
1 - Compra ou saque de moeda em espécie
2 - Compras internacionais com cartão de crédito/débito
3 - Investimento no mercado financeiro de capitais no exterior
4 - Recebimentos do exterior
> 1
Digite o código da moeda de origem: USD
Digite o valor a ser convertido: USD 100

------------------------------------------------
Conversão de USD para BRL:
Valor original: 100.00 USD
Valor convertido com taxa: 598.24 BRL
------------------------------------------------
Cotação: 5.7801 BRL
Imposto: 20.23
Alíquota: 3.5%
------------------------------------------------
```

---

## 👤 Autor

Desenvolvido por **Daniel Andrade Chaves**.

- GitHub: [@danielandradechaves](https://github.com/danielandradechaves)
