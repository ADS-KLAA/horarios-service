### `AlunoResource` Endpoints

1. **`/aluno/aulas`**
   - **Method:** `GET`
   - **Produces:** `application/json`
   - **Roles Allowed:** `Aluno`
   - **Output:** JSON object with `aulas` (Set of `Aula` objects)
   - **Description:** Retrieves the list of `aulas` (classes) for the authenticated `Aluno` (student).
   - **Example Response:**
     ```json
     {
       "aulas": [
         "A1",
         "A2",
         "A3"
       ]
     }
     ```
 
### `AulaResource` Endpoints

1. **`/aula/cursos`**
   - **Method:** `GET`
   - **Produces:** `application/json`
   - **Output:** JSON object with `cursos` (Set of `String` objects)
   - **Description:** Retrieves the list of `cursos` (courses).
   - **Example Response:**
     ```json
     {
       "cursos": [
         "Computer Science",
         "Mathematics",
         "Physics"
       ]
     }
     ```

2. **`/aula/disciplinas`**
   - **Method:** `GET`
   - **Produces:** `application/json`
   - **Query Parameter:** `curso` (String)
   - **Output:** JSON object with `disciplinas` (Set of `String` objects)
   - **Description:** Retrieves the list of `disciplinas` (subjects) for a given `curso` (course).
   - **Example Request:** `/aula/disciplinas?curso=Computer Science`
   - **Example Response:**
     ```json
     {
       "disciplinas": [
         "Algorithms",
         "Data Structures",
         "Operating Systems"
       ]
     }
     ```

3. **`/aula/turmas`**
   - **Method:** `GET`
   - **Produces:** `application/json`
   - **Query Parameter:** `disciplina` (String)
   - **Output:** JSON object with `turmas` (Set of `String` objects)
   - **Description:** Retrieves the list of `turmas` (classes) for a given `disciplina` (subject).
   - **Example Request:** `/aula/turmas?disciplina=Algorithms`
   - **Example Response:**
     ```json
     {
       "turmas": [
         "T1",
         "T2",
         "T3"
       ]
     }
     ```

### `AuthResource` Endpoints

1. **`/auth/register/aluno`**
   - **Method:** `POST`
   - **Consumes:** `application/json`
   - **Produces:** `application/json`
   - **Input:** JSON object with `username`, `email`, `password`, and `turma`
   - **Output:** JSON object with `token` and `expiresIn`
   - **Description:** Registers a new `Aluno` (student) account and returns an access token.
   - **Example Request:**
     ```json
     {
       "username": "student1",
       "email": "student1@example.com",
       "password": "password123",
       "turma": "T1"
     }
     ```
   - **Example Response:**
     ```json
     {
       "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
       "expiresIn": 3600
     }
     ```

2. **`/auth/register/professor`**
   - **Method:** `POST`
   - **Consumes:** `application/json`
   - **Produces:** `application/json`
   - **Input:** JSON object with `username`, `email`, and `password`
   - **Output:** JSON object with `token` and `expiresIn`
   - **Description:** Registers a new `Professor` account and returns an access token.
   - **Example Request:**
     ```json
     {
       "username": "professor1",
       "email": "professor1@example.com",
       "password": "password123"
     }
     ```
   - **Example Response:**
     ```json
     {
       "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
       "expiresIn": 3600
     }
     ```

3. **`/auth/login/professor`**
   - **Method:** `POST`
   - **Consumes:** `application/json`
   - **Produces:** `application/json`
   - **Input:** JSON object with `email` and `password`
   - **Output:** JSON object with `token` and `expiresIn`
   - **Description:** Authenticates a `Professor` and returns an access token.
   - **Example Request:**
     ```json
     {
       "email": "professor1@example.com",
       "password": "password123"
     }
     ```
   - **Example Response:**
     ```json
     {
       "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
       "expiresIn": 3600
     }
     ```

4. **`/auth/login/aluno`**
   - **Method:** `POST`
   - **Consumes:** `application/json`
   - **Produces:** `application/json`
   - **Input:** JSON object with `email` and `password`
   - **Output:** JSON object with `token` and `expiresIn`
   - **Description:** Authenticates an `Aluno` (student) and returns an access token.
   - **Example Request:**
     ```json
     {
       "email": "student1@example.com",
       "password": "password123"
     }
     ```
   - **Example Response:**
     ```json
     {
       "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
       "expiresIn": 3600
     }
     ```

### `ProfessorResource` Endpoints

1. **`/professor/aulas`**
   - **Method:** `GET`
   - **Produces:** `application/json`
   - **Roles Allowed:** `Professor`
   - **Output:** JSON object with `aulas` (Set of `Aula` objects)
   - **Description:** Retrieves the list of `aulas` (classes) for the authenticated `Professor`.
   - **Example Response:**
     ```json
     {
       "aulas": [
          "A1",
          "A2",
          "A3" 
       ]
     }
     ```

2. **`/professor/register/aulas`**
   - **Method:** `POST`
   - **Consumes:** `application/json`
   - **Produces:** `application/json`
   - **Roles Allowed:** `Professor`
   - **Input:** JSON object with `curso`, `uc`, and `turma`
   - **Output:** JSON object with a success or error message
   - **Description:** Registers a new `Aula` (class) for the authenticated `Professor`.
   - **Example Request:**
     ```json
     {
       "curso": "Computer Science",
       "uc": "Algorithms",
       "turma": "T1"
     }
     ```
   - **Example Response:**
     ```json
     {
       "message": "Aula registered successfully"
     }
     ```

### `SalaResource` Endpoints

1. **`/sala`**
   - **Method:** `GET`
   - **Produces:** `application/json`
   - **Output:** JSON object with `salas` (Set of `Sala` objects)
   - **Description:** Retrieves the list of `salas` (rooms).
   - **Example Response:**
     ```json
     {
       "salas": [
        "S1",
        "S2",
        "S3" 
       ]
     }
     ```