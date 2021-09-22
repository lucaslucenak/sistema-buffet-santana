/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.infox.telas;

import java.sql.*;
import br.com.infox.dal.ModuloConexao;
import javax.swing.JOptionPane;
import net.proteanit.sql.DbUtils;

/**
 *
 * @author Lucas
 */
public class TelaCliente extends javax.swing.JInternalFrame {

    Connection conexao = null;
    PreparedStatement pst = null;
    ResultSet rs = null;

    /**
     * Creates new form TelaCliente
     */
    public TelaCliente() {
        initComponents();
        conexao = ModuloConexao.conector();
    }

    private void adicionar() {
        String sql = "insert into tbclientes(nomecli, cpf, cellcli, fonecli, emailcli, numrescli, ruacli, bairrocli, cidadecli, estadocli, complementocli, cepcli) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try {
            pst = conexao.prepareStatement(sql);
            pst.setString(1, txtCliNome.getText());
            pst.setString(2, txtCliCpf.getText());
            pst.setString(3, txtCliCelular.getText());
            pst.setString(4, txtCliTelefone.getText());
            pst.setString(5, txtCliEmail.getText());
            pst.setString(6, txtCliNumAp.getText());
            pst.setString(7, txtCliRua.getText());
            pst.setString(8, txtCliBairro.getText());
            pst.setString(9, txtCliCidade.getText());
            pst.setString(10, txtCliEstado.getText());
            pst.setString(11, txtCliId.getText());
            pst.setString(12, txtCliCep.getText());

            //Validação  dos campos obrigatórios
            if (txtCliNome.getText().isEmpty() || txtCliCpf.getText().isEmpty() || txtCliCelular.getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Preencha todos os dados obrigatórios");
            } else {
                // Atualiza a tabela usuario com os dados do formulário
                int adicionado = pst.executeUpdate();

                if (adicionado > 0) {
                    JOptionPane.showMessageDialog(null, "Cliente cadastrado com sucesso");
                    txtCliNome.setText(null);
                    txtCliCpf.setText(null);
                    txtCliCelular.setText(null);
                    txtCliTelefone.setText(null);
                    txtCliEmail.setText(null);
                    txtCliNumAp.setText(null);
                    txtCliRua.setText(null);
                    txtCliBairro.setText(null);
                    txtCliCidade.setText(null);
                    txtCliEstado.setText(null);
                    txtCliId.setText(null);
                    txtCliCep.setText(null);
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    private void alterar() {
        String sql = "update tbclientes set nomecli = ?, cpf = ?, cellcli = ?, fonecli = ?, emailcli = ?, numrescli = ?, ruacli = ?, bairrocli = ?, cidadecli = ?, estadocli = ?, complementocli = ?, cepcli = ? where idcli = ?";

        try {
            pst = conexao.prepareStatement(sql);
            pst.setString(1, txtCliNome.getText());
            pst.setString(2, txtCliCpf.getText());
            pst.setString(3, txtCliCelular.getText());
            pst.setString(4, txtCliTelefone.getText());
            pst.setString(5, txtCliEmail.getText());
            pst.setString(6, txtCliNumAp.getText());
            pst.setString(7, txtCliRua.getText());
            pst.setString(8, txtCliBairro.getText());
            pst.setString(9, txtCliCidade.getText());
            pst.setString(10, txtCliEstado.getText());
            pst.setString(11, txtCliComplemento.getText());
            pst.setString(12, txtCliCep.getText());
            pst.setString(13, txtCliId.getText());

            if (txtCliNome.getText().isEmpty() || txtCliCpf.getText().isEmpty() || txtCliCelular.getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Preencha todos os dados obrigatórios");
            } else {
                // Atualiza a tabela usuario com os dados do formulário
                int adicionado = pst.executeUpdate();

                if (adicionado > 0) {
                    JOptionPane.showMessageDialog(null, "Dados do cliente alterados com sucesso");
                    txtCliNome.setText(null);
                    txtCliCpf.setText(null);
                    txtCliCelular.setText(null);
                    txtCliTelefone.setText(null);
                    txtCliEmail.setText(null);
                    txtCliNumAp.setText(null);
                    txtCliRua.setText(null);
                    txtCliBairro.setText(null);
                    txtCliCidade.setText(null);
                    txtCliEstado.setText(null);
                    txtCliId.setText(null);
                    txtCliCep.setText(null);
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    //Metodo para pesquisa clientes com filtro
    private void pesquisarCliente() {
        String sql = "select * from tbclientes where nomecli like ?";

        try {
            pst = conexao.prepareStatement(sql);
            //atencao ao "%" que é a continuacao do string sql
            pst.setString(1, txtCliPesquisar.getText() + "%");
            rs = pst.executeQuery();

            //Recuros que organiza a pesquisa na tabela de pesquisa
            tblClientes.setModel(DbUtils.resultSetToTableModel(rs));
        } catch (Exception e) {
        }
    }

    //Metodo que seta os campos do fomulario com os campos da tabela quando selecionado
    public void setarCampos() {
        int setar = tblClientes.getSelectedRow();
        txtCliId.setText(tblClientes.getModel().getValueAt(setar, 0).toString());
        txtCliNome.setText(tblClientes.getModel().getValueAt(setar, 1).toString());
        txtCliCpf.setText(tblClientes.getModel().getValueAt(setar, 2).toString());
        txtCliCelular.setText(tblClientes.getModel().getValueAt(setar, 3).toString());
        txtCliTelefone.setText(tblClientes.getModel().getValueAt(setar, 4).toString());
        txtCliEmail.setText(tblClientes.getModel().getValueAt(setar, 5).toString());
        txtCliNumAp.setText(tblClientes.getModel().getValueAt(setar, 6).toString());
        txtCliRua.setText(tblClientes.getModel().getValueAt(setar, 7).toString());
        txtCliBairro.setText(tblClientes.getModel().getValueAt(setar, 8).toString());
        txtCliCidade.setText(tblClientes.getModel().getValueAt(setar, 9).toString());
        txtCliEstado.setText(tblClientes.getModel().getValueAt(setar, 10).toString());
        txtCliComplemento.setText(tblClientes.getModel().getValueAt(setar, 11).toString());
        txtCliCep.setText(tblClientes.getModel().getValueAt(setar, 12).toString());
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel15 = new javax.swing.JLabel();
        txtCliPesquisar = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblClientes = new javax.swing.JTable();
        txtCliNome = new javax.swing.JTextField();
        txtCliCpf = new javax.swing.JTextField();
        txtCliTelefone = new javax.swing.JTextField();
        txtCliCelular = new javax.swing.JTextField();
        txtCliEmail = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        txtCliRua = new javax.swing.JTextField();
        txtCliNumAp = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        txtCliBairro = new javax.swing.JTextField();
        txtCliCep = new javax.swing.JTextField();
        txtCliCidade = new javax.swing.JTextField();
        txtCliEstado = new javax.swing.JTextField();
        txtCliId = new javax.swing.JTextField();
        jLabel14 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        btnAdicionar = new javax.swing.JButton();
        btnAlterar = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        txtCliComplemento = new javax.swing.JTextField();

        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setTitle("Clientes");
        setPreferredSize(new java.awt.Dimension(977, 634));
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        getContentPane().add(jLabel15, new org.netbeans.lib.awtextra.AbsoluteConstraints(975, 450, -1, -1));

        txtCliPesquisar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCliPesquisarActionPerformed(evt);
            }
        });
        txtCliPesquisar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtCliPesquisarKeyReleased(evt);
            }
        });
        getContentPane().add(txtCliPesquisar, new org.netbeans.lib.awtextra.AbsoluteConstraints(6, 6, 274, -1));

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/infox/icones/read-cli.png"))); // NOI18N
        getContentPane().add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 5, 20, -1));

        tblClientes.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        tblClientes.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblClientesMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tblClientes);

        getContentPane().add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(6, 37, 963, 90));
        getContentPane().add(txtCliNome, new org.netbeans.lib.awtextra.AbsoluteConstraints(126, 167, 352, -1));
        getContentPane().add(txtCliCpf, new org.netbeans.lib.awtextra.AbsoluteConstraints(126, 197, 352, -1));
        getContentPane().add(txtCliTelefone, new org.netbeans.lib.awtextra.AbsoluteConstraints(126, 257, 352, -1));
        getContentPane().add(txtCliCelular, new org.netbeans.lib.awtextra.AbsoluteConstraints(126, 227, 352, -1));
        getContentPane().add(txtCliEmail, new org.netbeans.lib.awtextra.AbsoluteConstraints(126, 287, 352, -1));

        jLabel5.setText("* Nome");
        getContentPane().add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(79, 171, -1, -1));

        jLabel6.setText("* CPF");
        getContentPane().add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 201, -1, -1));

        jLabel7.setText("* Celular");
        getContentPane().add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(72, 231, -1, -1));

        jLabel8.setText("Email");
        getContentPane().add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(89, 291, -1, -1));

        jLabel9.setText("Telefone");
        getContentPane().add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(71, 261, -1, -1));
        getContentPane().add(txtCliRua, new org.netbeans.lib.awtextra.AbsoluteConstraints(126, 317, 352, -1));
        getContentPane().add(txtCliNumAp, new org.netbeans.lib.awtextra.AbsoluteConstraints(126, 347, 352, -1));

        jLabel10.setText("Rua");
        getContentPane().add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(98, 321, -1, -1));

        jLabel11.setText("Número/Ap");
        getContentPane().add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(57, 351, -1, -1));
        getContentPane().add(txtCliBairro, new org.netbeans.lib.awtextra.AbsoluteConstraints(575, 197, 352, -1));
        getContentPane().add(txtCliCep, new org.netbeans.lib.awtextra.AbsoluteConstraints(575, 227, 352, -1));
        getContentPane().add(txtCliCidade, new org.netbeans.lib.awtextra.AbsoluteConstraints(575, 287, 352, -1));
        getContentPane().add(txtCliEstado, new org.netbeans.lib.awtextra.AbsoluteConstraints(575, 257, 352, -1));

        txtCliId.setEnabled(false);
        txtCliId.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCliIdActionPerformed(evt);
            }
        });
        getContentPane().add(txtCliId, new org.netbeans.lib.awtextra.AbsoluteConstraints(680, 350, 130, -1));

        jLabel14.setText("Bairro");
        getContentPane().add(jLabel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(534, 201, -1, -1));

        jLabel16.setText("CEP");
        getContentPane().add(jLabel16, new org.netbeans.lib.awtextra.AbsoluteConstraints(546, 231, -1, -1));

        jLabel17.setText("Estado");
        getContentPane().add(jLabel17, new org.netbeans.lib.awtextra.AbsoluteConstraints(530, 261, -1, -1));

        jLabel18.setText("Complemento");
        getContentPane().add(jLabel18, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 321, -1, -1));

        jLabel19.setText("Cidade");
        getContentPane().add(jLabel19, new org.netbeans.lib.awtextra.AbsoluteConstraints(530, 291, -1, -1));

        jLabel12.setText("* Campos obrigatórios");
        getContentPane().add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(766, 6, -1, -1));

        btnAdicionar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/infox/icones/create.png"))); // NOI18N
        btnAdicionar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAdicionarActionPerformed(evt);
            }
        });
        getContentPane().add(btnAdicionar, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 450, 150, 140));

        btnAlterar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/infox/icones/update.png"))); // NOI18N
        btnAlterar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAlterarActionPerformed(evt);
            }
        });
        getContentPane().add(btnAlterar, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 450, 140, 140));

        jButton3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/infox/icones/delete.png"))); // NOI18N
        getContentPane().add(jButton3, new org.netbeans.lib.awtextra.AbsoluteConstraints(670, 450, 140, 140));

        jLabel2.setText("ID Cliente");
        getContentPane().add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(510, 350, -1, -1));
        getContentPane().add(txtCliComplemento, new org.netbeans.lib.awtextra.AbsoluteConstraints(575, 317, 352, -1));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtCliPesquisarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCliPesquisarActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCliPesquisarActionPerformed

    private void btnAdicionarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAdicionarActionPerformed
        //Metodo que adiciona clientes
        adicionar();
    }//GEN-LAST:event_btnAdicionarActionPerformed

    private void txtCliPesquisarKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCliPesquisarKeyReleased
        // Equanto algo estiver sendo digitado o que estiver abaixo será executado
        pesquisarCliente();
    }//GEN-LAST:event_txtCliPesquisarKeyReleased

    //Evento para setar os campos da tabela quando o botao esquerdo do mouse pressiona a tabela
    private void tblClientesMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblClientesMouseClicked
        setarCampos();
    }//GEN-LAST:event_tblClientesMouseClicked

    private void btnAlterarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAlterarActionPerformed
        // TODO add your handling code here:
        alterar();
    }//GEN-LAST:event_btnAlterarActionPerformed

    private void txtCliIdActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCliIdActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCliIdActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAdicionar;
    private javax.swing.JButton btnAlterar;
    private javax.swing.JButton jButton3;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable tblClientes;
    private javax.swing.JTextField txtCliBairro;
    private javax.swing.JTextField txtCliCelular;
    private javax.swing.JTextField txtCliCep;
    private javax.swing.JTextField txtCliCidade;
    private javax.swing.JTextField txtCliComplemento;
    private javax.swing.JTextField txtCliCpf;
    private javax.swing.JTextField txtCliEmail;
    private javax.swing.JTextField txtCliEstado;
    private javax.swing.JTextField txtCliId;
    private javax.swing.JTextField txtCliNome;
    private javax.swing.JTextField txtCliNumAp;
    private javax.swing.JTextField txtCliPesquisar;
    private javax.swing.JTextField txtCliRua;
    private javax.swing.JTextField txtCliTelefone;
    // End of variables declaration//GEN-END:variables
}
