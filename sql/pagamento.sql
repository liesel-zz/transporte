ALTER TABLE transporte.pagamento
  ADD CONSTRAINT pagamento_ano_mes_id_contrato_key UNIQUE(ano, mes, id_contrato);