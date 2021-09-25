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
public class TelaEvento extends javax.swing.JInternalFrame {

    Connection conexao = null;
    PreparedStatement pst = null;
    ResultSet rs = null;

    //Cria uma variavel pra armazenar uma string relatica ao radioButton
    String tipoEveOrc;

    /**
     * Creates new form TelaEvento
     */
    public TelaEvento() {
        initComponents();
        conexao = ModuloConexao.conector();

    }

    private void pesquisarCliente() {
        /*String sql = "select idcli as Id, nomecli as Nome, cellcli as Celular, "
                + "fonecli as Telefone, emailcli as Email, numrescli as Numero, "
                + "bairrocli as Bairro, cidadecli as Cidade, estadocli as Estado, "
                + "complementocli as Complemento, cepcli as CEP from tbclientes where nomecli like ?";*/
        String sql = "select idcli as Id, nomecli as Nome, cellcli as Celular from tbclientes where nomecli like ?";

        try {
            pst = conexao.prepareStatement(sql);
            pst.setString(1, txtCliPesquisar.getText() + "%");
            rs = pst.executeQuery();
            tblClientes.setModel(DbUtils.resultSetToTableModel(rs));

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    private void setarCampos() {
        int setar = tblClientes.getSelectedRow();
        txtCliId.setText(tblClientes.getModel().getValueAt(setar, 0).toString());
    }

    private void emitirEvento() {
        String sql = "insert into tbeventos(tipo_eve_orc, situacao, tipo, dia, hora, lugar, valor, descricao, idcli) values (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try {
            pst = conexao.prepareStatement(sql);
            pst.setString(1, tipoEveOrc);
            pst.setString(2, cboEventoSit.getSelectedItem().toString());
            pst.setString(3, txtEventoTipo.getText());
            pst.setString(4, txtEventoDia.getText());
            pst.setString(5, txtEventoHora.getText());
            pst.setString(6, txtEventoLocal.getText());
            pst.setString(7, txtEventoValor.getText().replace(",", "."));
            pst.setString(8, txtEventoDescricao.getText());
            pst.setString(9, txtCliId.getText());

            if ((txtCliId.getText().isEmpty()) || (txtEventoDia.getText().isEmpty()) || (txtEventoHora.getText().isEmpty())) {
                JOptionPane.showMessageDialog(null, "Preencha todos os campos obrigatorios!");
            } else {
                int adicionado = pst.executeUpdate();
                if (adicionado > 0) {
                    JOptionPane.showMessageDialog(null, "Evento emitido com sucesso!");
                }
                txtCliId.setText(null);
                txtEventoTipo.setText(null);
                txtEventoDia.setText(null);
                txtEventoHora.setText(null);
                txtEventoLocal.setText(null);
                txtEventoValor.setText(null);
                txtEventoDescricao.setText(null);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    private void pesquisarEvento() {
        //cria uma caixa de entrada e atribui a uma variavel
        String numEvento = JOptionPane.showInputDialog("Número do evento: ");
        String sql = "select * from tbeventos where evento = " + numEvento;
        try {
            pst = conexao.prepareStatement(sql);
            rs = pst.executeQuery();

            if (rs.next()) {
                txtEvento.setText(rs.getString(1));
                txtData.setText(rs.getString(2));

                //Setando os radio buttons
                String rbtTipoEveOrc = rs.getString(3);

                if (rbtTipoEveOrc.equals("Evento")) {
                    rbtEvento.setSelected(true);
                    tipoEveOrc = "Evento";
                } else {
                    rbtOrc.setSelected(true);
                    tipoEveOrc = "Orçamento";
                }

                cboEventoSit.setSelectedItem(rs.getString(4));
                txtEventoTipo.setText(rs.getString(5));
                txtEventoDia.setText(rs.getString(6));
                txtEventoHora.setText(rs.getString(7));
                txtEventoLocal.setText(rs.getString(8));
                txtEventoValor.setText(rs.getString(9));
                txtEventoDescricao.setText(rs.getString(10));
                txtCliId.setText(rs.getString(11));

                btnEventoAdicionar.setEnabled(false);
                txtCliPesquisar.setEnabled(false);
                tblClientes.setVisible(false);

            } else {
                JOptionPane.showMessageDialog(null, "Evento não cadastrado!");
            }

        } catch (java.sql.SQLSyntaxErrorException e) {
            JOptionPane.showMessageDialog(null, "Número de evento inválido");
            System.out.println(e);
        } catch (Exception e2) {
            JOptionPane.showMessageDialog(null, e2);
        }
    }

    private void alterarEvento() {
        String sql = "update tbeventos set tipo_eve_orc = ?, situacao = ?, tipo = ?, dia = ?, hora = ?, lugar = ?, valor = ?, descricao = ? where evento = ?";
        try {
            pst = conexao.prepareStatement(sql);
            pst.setString(1, tipoEveOrc);
            pst.setString(2, cboEventoSit.getSelectedItem().toString());
            pst.setString(3, txtEventoTipo.getText());
            pst.setString(4, txtEventoDia.getText());
            pst.setString(5, txtEventoHora.getText());
            pst.setString(6, txtEventoLocal.getText());
            pst.setString(7, txtEventoValor.getText().replace(",", "."));
            pst.setString(8, txtEventoDescricao.getText());
            pst.setString(9, txtEvento.getText());

            if ((txtCliId.getText().isEmpty()) || (txtEventoDia.getText().isEmpty()) || (txtEventoHora.getText().isEmpty())) {
                JOptionPane.showMessageDialog(null, "Preencha todos os campos obrigatorios!");
            } else {
                int adicionado = pst.executeUpdate();
                if (adicionado > 0) {
                    JOptionPane.showMessageDialog(null, "Evento alterada com sucesso!");
                }
                txtEvento.setText(null);
                txtData.setText(null);
                txtCliId.setText(null);
                txtEventoTipo.setText(null);
                txtEventoDia.setText(null);
                txtEventoHora.setText(null);
                txtEventoLocal.setText(null);
                txtEventoValor.setText(null);
                txtEventoDescricao.setText(null);

                //habilitar botoes
                btnEventoAdicionar.setEnabled(true);
                txtCliPesquisar.setEnabled(true);
                tblClientes.setVisible(true);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    private void excluirEvento() {
        int confirma = JOptionPane.showConfirmDialog(null, "Tem certeza que deseja excluir esse evento?", "Atenção!", JOptionPane.YES_NO_OPTION);
        if (confirma == JOptionPane.YES_OPTION) {
            String sql = "delete from tbeventos where evento = ?";

            try {
                pst = conexao.prepareStatement(sql);
                pst.setString(1, txtEvento.getText());
                int apagado = pst.executeUpdate();
                if (apagado > 0) {
                    JOptionPane.showMessageDialog(null, "Evento excluído com sucesso!");
                    txtEvento.setText(null);
                    txtData.setText(null);
                    txtCliId.setText(null);
                    txtEventoTipo.setText(null);
                    txtEventoDia.setText(null);
                    txtEventoHora.setText(null);
                    txtEventoLocal.setText(null);
                    txtEventoValor.setText(null);
                    txtEventoDescricao.setText(null);

                    //habilitar botoes
                    btnEventoAdicionar.setEnabled(true);
                    txtCliPesquisar.setEnabled(true);
                    tblClientes.setVisible(true);
                }

            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, e);
            }
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        txtEvento = new javax.swing.JTextField();
        txtData = new javax.swing.JTextField();
        rbtEvento = new javax.swing.JRadioButton();
        rbtOrc = new javax.swing.JRadioButton();
        jLabel3 = new javax.swing.JLabel();
        cboEventoSit = new javax.swing.JComboBox<>();
        jPanel2 = new javax.swing.JPanel();
        txtCliPesquisar = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblClientes = new javax.swing.JTable();
        txtCliId = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        txtEventoValor = new javax.swing.JTextField();
        txtEventoDia = new javax.swing.JTextField();
        txtEventoHora = new javax.swing.JTextField();
        txtEventoLocal = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        txtEventoTipo = new javax.swing.JTextField();
        jScrollPane2 = new javax.swing.JScrollPane();
        txtEventoDescricao = new javax.swing.JTextArea();
        btnEventoAdicionar = new javax.swing.JButton();
        btnEventoPesquisar = new javax.swing.JButton();
        btnEventoExcluir = new javax.swing.JButton();
        btnEventoAlterar = new javax.swing.JButton();
        btnEventoImprimir = new javax.swing.JButton();

        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setTitle("Eventos");
        setPreferredSize(new java.awt.Dimension(977, 634));
        addInternalFrameListener(new javax.swing.event.InternalFrameListener() {
            public void internalFrameActivated(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameClosed(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameClosing(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameDeactivated(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameDeiconified(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameIconified(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameOpened(javax.swing.event.InternalFrameEvent evt) {
                formInternalFrameOpened(evt);
            }
        });
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel1.setText("N° Evento");

        jLabel2.setText("Data");

        txtEvento.setEditable(false);
        txtEvento.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtEventoActionPerformed(evt);
            }
        });

        txtData.setEditable(false);

        buttonGroup1.add(rbtEvento);
        rbtEvento.setText("Evento");
        rbtEvento.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rbtEventoActionPerformed(evt);
            }
        });

        buttonGroup1.add(rbtOrc);
        rbtOrc.setText("Orçamento");
        rbtOrc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rbtOrcActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(txtData, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 194, Short.MAX_VALUE)
                            .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtEvento, javax.swing.GroupLayout.Alignment.LEADING))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(rbtOrc)
                            .addComponent(rbtEvento))
                        .addContainerGap(14, Short.MAX_VALUE))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtEvento, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(rbtOrc))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtData, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(rbtEvento))
                .addGap(16, 16, 16))
        );

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 320, 130));

        jLabel3.setText("Situação");
        getContentPane().add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 160, 70, 20));

        cboEventoSit.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Orçamento Pendente", "Orçamento Aprovado", "Orçamento Reprovado", "Pagamento Pendente", "Evento Confirmado", "Evento Realizado", "Evento Cancelado" }));
        getContentPane().add(cboEventoSit, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 160, 250, -1));

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder("Cliente"));

        txtCliPesquisar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtCliPesquisarKeyReleased(evt);
            }
        });

        jLabel4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/infox/icones/read-cli.png"))); // NOI18N

        jLabel5.setText("* ID");

        tblClientes.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "ID", "Nome", "Celular"
            }
        ));
        tblClientes.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblClientesMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblClientes);

        txtCliId.setEditable(false);
        txtCliId.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtCliIdMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(txtCliPesquisar, javax.swing.GroupLayout.PREFERRED_SIZE, 236, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(txtCliId, javax.swing.GroupLayout.PREFERRED_SIZE, 197, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(31, 31, 31))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 600, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(txtCliId, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtCliPesquisar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(18, Short.MAX_VALUE))
        );

        getContentPane().add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 0, 620, 190));

        jLabel6.setText("Valor");
        getContentPane().add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 400, 30, 20));

        jLabel7.setText("Descrição");
        getContentPane().add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 220, -1, 20));

        jLabel8.setText("* Data");
        getContentPane().add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 280, 40, 20));

        jLabel9.setText("Local");
        getContentPane().add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 360, 40, 20));
        getContentPane().add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(61, 330, 40, -1));

        jLabel11.setText("* Hora");
        getContentPane().add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 320, 40, 20));

        txtEventoValor.setText("0");
        getContentPane().add(txtEventoValor, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 400, 270, -1));
        getContentPane().add(txtEventoDia, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 280, 270, -1));
        getContentPane().add(txtEventoHora, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 320, 270, -1));
        getContentPane().add(txtEventoLocal, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 360, 270, -1));

        jLabel12.setText("Tipo de evento");
        getContentPane().add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 240, -1, 20));
        getContentPane().add(txtEventoTipo, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 240, 270, -1));

        txtEventoDescricao.setColumns(20);
        txtEventoDescricao.setRows(5);
        jScrollPane2.setViewportView(txtEventoDescricao);

        getContentPane().add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 240, 540, 190));

        btnEventoAdicionar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/infox/icones/create.png"))); // NOI18N
        btnEventoAdicionar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEventoAdicionarActionPerformed(evt);
            }
        });
        getContentPane().add(btnEventoAdicionar, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 470, 130, 130));

        btnEventoPesquisar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/infox/icones/read.png"))); // NOI18N
        btnEventoPesquisar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEventoPesquisarActionPerformed(evt);
            }
        });
        getContentPane().add(btnEventoPesquisar, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 470, 130, 130));

        btnEventoExcluir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/infox/icones/delete.png"))); // NOI18N
        btnEventoExcluir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEventoExcluirActionPerformed(evt);
            }
        });
        getContentPane().add(btnEventoExcluir, new org.netbeans.lib.awtextra.AbsoluteConstraints(590, 470, 130, 130));

        btnEventoAlterar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/infox/icones/update.png"))); // NOI18N
        btnEventoAlterar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEventoAlterarActionPerformed(evt);
            }
        });
        getContentPane().add(btnEventoAlterar, new org.netbeans.lib.awtextra.AbsoluteConstraints(420, 470, 130, 130));

        btnEventoImprimir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/infox/icones/imprimir.png"))); // NOI18N
        btnEventoImprimir.setToolTipText("Imprimir Evento");
        btnEventoImprimir.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        getContentPane().add(btnEventoImprimir, new org.netbeans.lib.awtextra.AbsoluteConstraints(760, 470, 130, 130));

        setBounds(0, 0, 975, 636);
    }// </editor-fold>//GEN-END:initComponents

    private void txtEventoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtEventoActionPerformed

    }//GEN-LAST:event_txtEventoActionPerformed

    private void txtCliPesquisarKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCliPesquisarKeyReleased

        pesquisarCliente();
    }//GEN-LAST:event_txtCliPesquisarKeyReleased

    private void txtCliIdMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtCliIdMouseClicked

        setarCampos();
    }//GEN-LAST:event_txtCliIdMouseClicked

    private void rbtOrcActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbtOrcActionPerformed

        tipoEveOrc = "Orcamento";
    }//GEN-LAST:event_rbtOrcActionPerformed

    private void rbtEventoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbtEventoActionPerformed

        tipoEveOrc = "Evento";
    }//GEN-LAST:event_rbtEventoActionPerformed

    private void formInternalFrameOpened(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameOpened
        // Muda aqui o tipo padrão que vai ser asim que abrir a tela de Eventos
        rbtOrc.setSelected(true);
        tipoEveOrc = "Orcamento";
    }//GEN-LAST:event_formInternalFrameOpened

    private void btnEventoAdicionarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEventoAdicionarActionPerformed

        emitirEvento();
    }//GEN-LAST:event_btnEventoAdicionarActionPerformed

    private void btnEventoPesquisarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEventoPesquisarActionPerformed
        pesquisarEvento();
    }//GEN-LAST:event_btnEventoPesquisarActionPerformed

    private void btnEventoAlterarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEventoAlterarActionPerformed
        alterarEvento();
    }//GEN-LAST:event_btnEventoAlterarActionPerformed

    private void btnEventoExcluirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEventoExcluirActionPerformed
        excluirEvento();
    }//GEN-LAST:event_btnEventoExcluirActionPerformed

    private void tblClientesMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblClientesMouseClicked
        // TODO add your handling code here:
        setarCampos();
    }//GEN-LAST:event_tblClientesMouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnEventoAdicionar;
    private javax.swing.JButton btnEventoAlterar;
    private javax.swing.JButton btnEventoExcluir;
    private javax.swing.JButton btnEventoImprimir;
    private javax.swing.JButton btnEventoPesquisar;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JComboBox<String> cboEventoSit;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JRadioButton rbtEvento;
    private javax.swing.JRadioButton rbtOrc;
    private javax.swing.JTable tblClientes;
    private javax.swing.JTextField txtCliId;
    private javax.swing.JTextField txtCliPesquisar;
    private javax.swing.JTextField txtData;
    private javax.swing.JTextField txtEvento;
    private javax.swing.JTextArea txtEventoDescricao;
    private javax.swing.JTextField txtEventoDia;
    private javax.swing.JTextField txtEventoHora;
    private javax.swing.JTextField txtEventoLocal;
    private javax.swing.JTextField txtEventoTipo;
    private javax.swing.JTextField txtEventoValor;
    // End of variables declaration//GEN-END:variables
}
