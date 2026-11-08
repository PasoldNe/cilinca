# cilinca

trabalho do senai feito em Java com Spring Boot e MySQL.

da pra cadastrar, listar, atualizar e excluir pacientes e consultas.

pra rodar, inicia o MySQL, cria o banco `clinica` e roda a `CilincaApplication` no IntelliJ.

## pacientes

- `POST http://localhost:8080/paciente` cadastra um paciente
- `GET http://localhost:8080/paciente` lista os pacientes
- `PUT http://localhost:8080/paciente/{email}` atualiza pelo email atual
- `DELETE http://localhost:8080/paciente/{email}` exclui o paciente se ele nao tiver consultas

json pro POST e pro PUT. no PUT, o email da rota é o atual e o do json é o que vai ficar.

```json
{
  "nome": "Ana Lima",
  "email": "ana@example.com"
}
```

## consultas

- `POST http://localhost:8080/consulta` cadastra uma consulta
- `GET http://localhost:8080/consultas` lista todas as consultas
- `GET http://localhost:8080/consultas/paciente/{pacienteId}` lista as consultas de um paciente
- `PUT http://localhost:8080/consulta/{id}` atualiza a consulta pelo id dela
- `DELETE http://localhost:8080/consulta/{id}` exclui a consulta pelo id dela

json pro POST e pro PUT. usa o id de um paciente que ja ta cadastrado.

```json
{
  "titulo": "Retorno",
  "data": "2026-11-10",
  "hora": "10:30",
  "status": "AGENDADA",
  "pacienteId": 1
}
```

os status sao `AGENDADA`, `EM_ANDAMENTO`, `CONCLUIDA` e `CANCELADA`.

GET e DELETE nao precisam de json.

## falta fazer

bloquear duas consultas ativas do mesmo paciente no mesmo minuto.
