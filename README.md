Desafio Técnico Sicredi, solução BACK-END para Gerenciar Sessões de Votação em Assembleias

Solução Back-end para Gerenciar Sessões de Votação em Assembleias
No cooperativismo, cada associado possui um voto e as decisões são tomadas em assembleias, por votação. A partir disso, você precisa criar uma solução back-end para gerenciar essas sessões de votação. Essa solução deve ser executada na nuvem e promover as seguintes funcionalidades através de uma API REST:

● Cadastrar uma nova pauta;

● Abrir uma sessão de votação em uma pauta (a sessão de votação deve ficar aberta por um tempo determinado na chamada de abertura ou 1 minuto por default);

● Receber votos dos associados em pautas (os votos são apenas 'Sim'/'Não'. Cada associado é identificado por um id único e pode votar apenas uma vez por pauta);

● Contabilizar os votos e dar o resultado da votação na pauta.

## Atualização de usuário

`PATCH /usuarios/{id}` atualiza parcialmente um usuário existente. Envie no corpo
JSON um ou mais campos entre `nome`, `idade`, `cpf`, `email` e `rua`; os campos
omitidos permanecem inalterados. CPF é normalizado para 11 dígitos e e-mail para
minúsculas. Nome, CPF e e-mail não podem duplicar os dados de outro usuário.

Respostas: `200` com o usuário atualizado, `400` para campos inválidos ou corpo
vazio, `404` quando o usuário não existe e `409` para dados únicos duplicados.

```http
PATCH /usuarios/1
Content-Type: application/json

{
  "email": "novo@example.com",
  "rua": "Avenida Central, 100"
}
```
