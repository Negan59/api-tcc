package com.sgpd.control;

import com.sgpd.model.Erro;
import com.sgpd.model.FMS;
import com.sgpd.model.SingletonConexao;

public class FMSController {
    public Erro salvar(FMS fms) {
        // Validações
        if (fms.getPaciente() == null || fms.getPaciente().getId() <= 0) {
            return new Erro("ID do paciente inválido", true, 400);
        }

        if (fms.getQuestao() <= 0 || fms.getQuestao() > 3) {
            return new Erro("Questão inválida", true, 400);
        }

        if (fms.getNivel() <= 0 || fms.getNivel() > 5) {
            return new Erro("Nível inválido", true, 400);
        }

        if (fms.getDescricao() == null || fms.getDescricao().isEmpty()) {
            return new Erro("Descrição inválida", true, 400);
        }

        try {
            String sql = "INSERT INTO fms (paciente_id, questao, nivel, descricao) values ($1, $2, $3, '$4')";
            sql = sql.replace("$1", "" + fms.getPaciente().getId())
                    .replace("$2", "" + fms.getQuestao())
                    .replace("$3", "" + fms.getNivel())
                    .replace("$4", fms.getDescricao());

            SingletonConexao con = SingletonConexao.getConexao();
            boolean flag = con.manipular(sql);
            con.fecharConexao();

            if (!flag) {
                return new Erro("Erro no banco de dados", true, 500);
            }

            return new Erro("Sucesso", false, 200);
        } catch (Exception e) {
            e.printStackTrace();
            return new Erro("Erro ao salvar no banco de dados", true, 500);
        }
    }

    public Erro excluir(int id){
        try {
            FMS fms = new FMS();
            boolean excluiu = fms.excluir(id);
            
            if (excluiu) {
                return new Erro("Registro excluído com sucesso", false, 200);
            } else {
                return new Erro("Falha ao excluir registro", true, 500);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new Erro("Erro interno", true, 500);
        }
    }

    public FMS buscar(int id){
        return new FMS().buscar(id);
    }
}
