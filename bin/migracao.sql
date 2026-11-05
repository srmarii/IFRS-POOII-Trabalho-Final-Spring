-- Rode DEPOIS de subir o Spring uma vez (o ddl-auto=update cria as colunas novas
-- "tipo" em tb_quarto e "situacao" em tb_reserva, caso ainda não existam).
-- Se o banco já veio do Swing, estes UPDATEs não alteram nada.

-- Quartos sem tipo (cadastrados pelo Spring antigo) viram "Simples", como no Swing.
UPDATE tb_quarto SET tipo = 'Simples' WHERE tipo IS NULL OR tipo NOT IN ('Simples', 'VIP');

-- Reservas sem situação (cadastradas pelo Spring antigo) viram "Pendente".
UPDATE tb_reserva SET situacao = 'Pendente' WHERE situacao IS NULL;

-- Opcional: quartos/funcionários sem hotel não aparecem no Swing (INNER JOIN com tb_hotel).
-- Confira se existe algum:
SELECT id, numero FROM tb_quarto WHERE hotel_id IS NULL;
SELECT id, nome   FROM tb_funcionario WHERE hotel_id IS NULL;
