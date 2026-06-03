# W2 Chamados

Aplicativo Android desenvolvido como atividade de extensão universitária para a disciplina de **Programação para Dispositivos Móveis em Android** — Estácio de Sá.

## Sobre o projeto

O **W2 Chamados** é um aplicativo mobile desenvolvido em Kotlin para a empresa **W2 Tecnologia da Informação** (Aracruz/ES), com o objetivo de integrar ao sistema proprietário de chamados técnicos já utilizado pela empresa, permitindo acesso mobile às funcionalidades principais.

## Funcionalidades

- **Autenticação** — Login seguro com token JWT
- **Chamados** — Listagem, abertura e acompanhamento de chamados técnicos
- **Clientes** — Listagem e cadastro de clientes

## Tecnologias utilizadas

- Kotlin
- Android SDK (minSdk 24 / targetSdk 34)
- Retrofit 2 + OkHttp (consumo de API REST)
- Gson (serialização JSON)
- Material Design Components
- ViewBinding
- Coroutines (operações assíncronas)
- RecyclerView + ListAdapter

## Estrutura do projeto

```
app/src/main/java/br/com/w2ti/chamados/
├── data/
│   └── model/          # Models de dados (Chamado, Cliente, Usuario...)
├── network/            # RetrofitClient, W2ApiService, SessionManager
└── ui/
    ├── login/          # LoginActivity
    ├── chamados/       # ChamadosActivity, NovoChamadoActivity, Adapter
    └── clientes/       # ClientesActivity, NovoClienteActivity, Adapter
```

## Configuração

No arquivo `RetrofitClient.kt`, ajuste a `BASE_URL` para o endereço da API do sistema proprietário:

```kotlin
private const val BASE_URL = "https://sistema.w2ti.com.br/api/v1/"
```

## Empresa parceira

**W2 Tecnologia da Informação**  
Av. dos Coqueiros, 134 — Aracruz/ES  
CNPJ: 15.205.694/0001-70  
Responsável: Weverton Vicente

## Autor

Wendell — Estácio de Sá, 2026
