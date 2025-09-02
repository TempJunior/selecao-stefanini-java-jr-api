Desafio de Seleção: Gerenciamento de Autores e Obras

Olá! Este projeto é a minha solução para o desafio de seleção para a vaga de Desenvolvedor Java na Stefanini. A aplicação é um sistema completo de gerenciamento de autores e obras, com uma API REST e uma interface web para cadastro, consulta, edição e remoção de dados.

Funcionalidades da Aplicação

O projeto é composto por um back-end em Java e um front-end para interação com o usuário, ambos desenvolvidos para atender às seguintes regras de negócio:

    Relacionamento: Cada autor pode ter várias obras, e cada obra pode ter vários autores.

    Exclusão: Um autor só pode ser excluído se não tiver nenhuma obra associada. Para obras, não há restrições de exclusão.

1. Back-end (API REST)

Desenvolvida em Java, a API REST gerencia as entidades Autor e Obra com as seguintes validações:

    Autor:

        Nome e Data de Nascimento são obrigatórios.

        E-mail: opcional, mas se preenchido, deve ser único.

        País de Origem: obrigatório e deve ser um país válido.

        CPF: obrigatório somente se o país de origem for o Brasil. Deve ser único e validado.

    Obra:

        Nome e Descrição (máximo de 240 caracteres) são obrigatórios.

        Data de Publicação ou Data de Exposição deve ser informada; não podem ser nulas ao mesmo tempo.

2. Front-end

A interface web consome a API REST e oferece as seguintes telas:

    Autores:

        Cadastro/Edição: Formulário para registrar e editar autores, com a possibilidade de associar obras já cadastradas.

        Consulta: Tabela para listar os autores cadastrados.

    Obras:

        Cadastro/Edição: Formulário para registrar e editar obras, permitindo associar autores já cadastrados.

        Consulta: Tabela para listar as obras cadastradas.

Extras Implementados

Aqui estão os diferenciais que foram adicionados ao projeto:

    Extra 1 (Autenticação/Segurança): Implementei autenticação para proteger os endpoints da API.

    Extra 2 (Testes): Foram criados testes unitários utilizando JUnit para validar as regras de negócio de inserção, edição, consulta e exclusão.

    Extra 3 (Padrão de Projeto): Para melhorar a manutenibilidade do código e evitar a replicação, utilizei padrões de projeto como DTOs (Data Transfer Objects) e o padrão Repositório.

    Extra 4 (Filtragem e Paginação): As telas de consulta de obras contam com filtragem por nome e descrição, além de paginação para uma melhor experiência do usuário.

    Extra 5 (Deploy/Hospedagem): A aplicação está hospedada e acessível diretamente pela URL https://coodesh.com/blog/candidates/heroku-acabou-e-agora-veja-alternativas/.
