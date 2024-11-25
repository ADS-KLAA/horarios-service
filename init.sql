
CREATE TABLE IF NOT EXISTS t_classroom (
  id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
  nome varchar(255) NOT NULL,
  capacidade INTEGER NOT NULL,
  caracteristicas varchar(255) NOT NULL 
);

CREATE TABLE IF NOT EXISTS t_class (
  id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
  curso varchar(255) NOT NULL,
  uc varchar(255) NOT NULL,
  turma varchar(255) NOT NULL,
  inscritos INTEGER NOT NULL,
  dia_semana varchar(255) NOT NULL,
  inicio varchar(255) NOT NULL,
  fim varchar(255) NOT NULL,
  dia varchar(255) NOT NULL,
  sala UUID REFERENCES t_classroom(id) ON DELETE CASCADE
);
