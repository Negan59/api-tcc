package com.sgpd.dao;

import java.sql.ResultSet;

import com.sgpd.model.FMS;
import com.sgpd.model.Paciente;
import com.sgpd.model.SingletonConexao;

public class DAOFMS {
    public boolean salvar(FMS fms) {
        try {
            String sql = "INSERT INTO fms (paciente_id,questao,nivel,descricao) values ($1,$2,$3,'$4')";
            sql = sql.replace("$1", ""+fms.getPaciente().getId())
                     .replace("$2", ""+fms.getQuestao())
                     .replace("$3", ""+fms.getNivel())
                     .replace("$4", fms.getDescricao());

            SingletonConexao con = SingletonConexao.getConexao();
            boolean flag = con.manipular(sql);
            con.fecharConexao();
            return flag;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean excluir(int id) {
    try {
        String sql = "DELETE FROM fms WHERE paciente_id = " + id;

        SingletonConexao con = SingletonConexao.getConexao();
        boolean flag = con.manipular(sql);
        con.fecharConexao();
        return flag;
    } catch (Exception e) {
        e.printStackTrace();
        return false;
    }
}

public FMS buscar(int id) {
    try {
        String sql = "SELECT * FROM fms WHERE id = " + id;

        SingletonConexao con = SingletonConexao.getConexao();
        ResultSet rs = con.consultar(sql);

        FMS fms = null;
        if (rs.next()) {
            // Preencher o objeto FMS com os dados do ResultSet
            fms = new FMS();
            Paciente paciente = new Paciente();
            paciente = paciente.buscarUm(id);
            fms.setPaciente(paciente);
            fms.setQuestao(rs.getInt("questao"));
            fms.setNivel(rs.getInt("nivel"));
            fms.setDescricao(rs.getString("descricao"));
        }

        con.fecharConexao();
        return fms;
    } catch (Exception e) {
        e.printStackTrace();
        return null;
    }
}

}
