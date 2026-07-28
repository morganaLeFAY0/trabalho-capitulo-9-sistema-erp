/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package Telas;

import Pesquisa.PesquisaVendedor;
import Conexao.ModuloConexao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import net.proteanit.sql.DbUtils;

/**
 *
 * @author Computador 10
 */
public class TelaPedidos extends javax.swing.JFrame {

    Connection conexao = null;
    PreparedStatement pst = null;
    ResultSet rs = null;

    java.text.SimpleDateFormat formatador = new java.text.SimpleDateFormat("dd/MM/yyyy");
    int controle;

    public TelaPedidos() {
        initComponents();
        conexao = ModuloConexao.conector();
        jdcData.setDateFormatString("dd/MM/yyyy");
       
    }


    private void pesquisarPedido() {
    String sql = "SELECT p.idPedido AS Código, "
               + "p.DataDia_Pedido AS Data, "
               + "p.Cliente_idCliente AS 'Cod. Cliente', "
               + "(SELECT Nome_Cliente FROM cliente WHERE idCliente = p.Cliente_idCliente) AS Cliente, "
               + "p.Vendedor_idVendedor AS 'Cod. Vendedor', "
               + "(SELECT Nome_Vendedor FROM vendedor WHERE idVendedor = p.Vendedor_idVendedor) AS Vendedor, "
               + "p.Total_Pedido AS Total, "
               + "p.Status_Pedido AS Status, "
               + "p.Obs_Pedido AS Obs "
               + "FROM pedido p "
               + "WHERE p.idPedido LIKE ? OR p.DataDia_Pedido LIKE ?";
    try {
        pst = conexao.prepareStatement(sql);
        String pesquisa = txtPesquisar.getText() + "%";
        pst.setString(1, pesquisa);
        pst.setString(2, pesquisa);
        rs = pst.executeQuery();
        tblPesquisar.setModel(DbUtils.resultSetToTableModel(rs));
    } catch (Exception e) {
        JOptionPane.showMessageDialog(null, "Erro ao pesquisar: " + e.getMessage());
    }

    }

    private void carregarCampos() {
    int linha = tblPesquisar.getSelectedRow();
    if (linha == -1) {
        JOptionPane.showMessageDialog(null, "Selecione um registro.");
        return;
    }

    txtCodigo.setText(tblPesquisar.getValueAt(linha, 0).toString());

    // Data (coluna 1)
    try {
        String dataStr = tblPesquisar.getValueAt(linha, 1).toString();
        java.util.Date data = formatador.parse(dataStr);
        jdcData.setDate(data);
    } catch (Exception e) {
        jdcData.setDate(null);
    }

    txtCdCliente.setText(tblPesquisar.getValueAt(linha, 2).toString());
    txtNomeCliente.setText(tblPesquisar.getValueAt(linha, 3).toString());

    txtCdVendedor.setText(tblPesquisar.getValueAt(linha, 4).toString());
    txtNomeVendedor.setText(tblPesquisar.getValueAt(linha, 5).toString());

    ftxtTotal.setText(tblPesquisar.getValueAt(linha, 6).toString());

    // Status – coluna 7 (caractere)
    String statusChar = tblPesquisar.getValueAt(linha, 7).toString();
    switch (statusChar) {
        case "P": cbxStatus.setSelectedItem("Pendente"); break;
        case "E": cbxStatus.setSelectedItem("Entregue"); break;
        case "C": cbxStatus.setSelectedItem("Cancelado"); break;
        default: cbxStatus.setSelectedIndex(0);
    }

    txtObs.setText(tblPesquisar.getValueAt(linha, 8) != null ? tblPesquisar.getValueAt(linha, 8).toString() : "");

    btnIncluir.setEnabled(false);
    Desabilitar();
}

    private void Desabilitar() {
        txtCodigo.setEditable(false);
        jdcData.setEnabled(false);
        txtCdCliente.setEditable(false);
        txtNomeCliente.setEditable(false);
        txtCdVendedor.setEditable(false);
        txtNomeVendedor.setEditable(false);
        ftxtTotal.setEditable(false);
        cbxStatus.setEnabled(false);
        txtObs.setEditable(false);
        btnVendedor.setEnabled(false);
        // btnCliente.setEnabled(false); // se existir
    }

    private void habilitar() {
        txtCodigo.setEditable(false);
        jdcData.setEnabled(true);
        txtCdCliente.setEditable(true);
        txtNomeCliente.setEditable(true);
        txtCdVendedor.setEditable(true);
        txtNomeVendedor.setEditable(true);
        ftxtTotal.setEditable(true);
        cbxStatus.setEnabled(true);
        txtObs.setEditable(true);
        btnVendedor.setEnabled(true);
        // btnCliente.setEnabled(true);
    }

    private void limparCampos() {
        txtCodigo.setText("");
        jdcData.setDate(null);
        txtCdCliente.setText("");
        txtNomeCliente.setText("");
        txtCdVendedor.setText("");
        txtNomeVendedor.setText("");
        ftxtTotal.setText("");
        cbxStatus.setSelectedIndex(0);
        txtObs.setText("");
    }

    
   private void Incluir() throws SQLException {
        String sql = null;
       System.out.println("SQL: INSERT INTO pedido (idPedido, DataDia_Pedido, Cliente_idCliente, Vendedor_idVendedor, Total_Pedido, Status_Pedido, Obs_Pedido) VALUES (?, ?, ?, ?, ?, ?)" + sql);
    pst = conexao.prepareStatement(sql);
    try {
        pst = conexao.prepareStatement(sql);

        // Data
        if (jdcData.getDate() != null) {
            java.text.SimpleDateFormat fmtMySQL = new java.text.SimpleDateFormat("yyyy-MM-dd");
            pst.setString(1, fmtMySQL.format(jdcData.getDate()));
        } else {
            JOptionPane.showMessageDialog(null, "Selecione uma data.");
            return;
        }

        pst.setInt(2, Integer.parseInt(txtCdCliente.getText()));
        pst.setInt(3, Integer.parseInt(txtCdVendedor.getText()));
        pst.setDouble(4, Double.parseDouble(ftxtTotal.getText().replace(",", ".")));

        // Mapeia o status selecionado para o caractere
        String status = "";
        String selected = cbxStatus.getSelectedItem().toString();
        if (selected.equals("Pendente")) status = "P";
        else if (selected.equals("Entregue")) status = "E";
        else if (selected.equals("Cancelado")) status = "C";
        pst.setString(5, status);

        pst.setString(6, txtObs.getText());

        int ok = pst.executeUpdate();
        if (ok > 0) {
            JOptionPane.showMessageDialog(null, "Pedido incluído!");
            limparCampos();
            pesquisarPedido();
            Desabilitar();
            btnIncluir.setEnabled(true);
        }
    } catch (Exception e) {
        JOptionPane.showMessageDialog(null, "Erro ao incluir: " + e.getMessage());
    }
}

    private void Alterar() {
    String sql = "UPDATE pedido SET DataDia_Pedido = ?, Cliente_idCliente = ?, Vendedor_idVendedor = ?, "
               + "Total_Pedido = ?, Status_Pedido = ?, Obs_Pedido = ? WHERE idPedido = ?";
    try {
        pst = conexao.prepareStatement(sql);

        if (jdcData.getDate() != null) {
            java.text.SimpleDateFormat fmtMySQL = new java.text.SimpleDateFormat("yyyy-MM-dd");
            pst.setString(1, fmtMySQL.format(jdcData.getDate()));
        } else {
            JOptionPane.showMessageDialog(null, "Selecione uma data.");
            return;
        }

        pst.setInt(2, Integer.parseInt(txtCdCliente.getText()));
        pst.setInt(3, Integer.parseInt(txtCdVendedor.getText()));
        pst.setDouble(4, Double.parseDouble(ftxtTotal.getText().replace(",", ".")));

        String selected = cbxStatus.getSelectedItem().toString();
        String status = "";
        if (selected.equals("Pendente")) status = "P";
        else if (selected.equals("Entregue")) status = "E";
        else if (selected.equals("Cancelado")) status = "C";
        pst.setString(5, status);

        pst.setString(6, txtObs.getText());
        pst.setInt(7, Integer.parseInt(txtCodigo.getText()));

        int ok = pst.executeUpdate();
        if (ok > 0) {
            JOptionPane.showMessageDialog(null, "Pedido alterado!");
            limparCampos();
            pesquisarPedido();
            Desabilitar();
            btnIncluir.setEnabled(true);
        }
    } catch (Exception e) {
        JOptionPane.showMessageDialog(null, "Erro ao alterar: " + e.getMessage());
    }
}

    private void Excluir() {
    int codigo = Integer.parseInt(txtCodigo.getText());
    int confirm = JOptionPane.showConfirmDialog(null, "Deseja excluir o pedido " + codigo + "?", "Confirmar", JOptionPane.YES_NO_OPTION);
    if (confirm == JOptionPane.YES_OPTION) {
        String sql = "DELETE FROM pedido WHERE idPedido = ?";
        try {
            pst = conexao.prepareStatement(sql);
            pst.setInt(1, codigo);
            int ok = pst.executeUpdate();
            if (ok > 0) {
                JOptionPane.showMessageDialog(null, "Pedido excluído!");
                limparCampos();
                pesquisarPedido();
                Desabilitar();
                btnIncluir.setEnabled(true);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao excluir: " + e.getMessage());
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

        lblPesquisar = new javax.swing.JLabel();
        txtPesquisar = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblPesquisar = new javax.swing.JTable();
        lblCodigo = new javax.swing.JLabel();
        txtCodigo = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        txtCdCliente = new javax.swing.JTextField();
        txtNomeCliente = new javax.swing.JTextField();
        lblVendedor = new javax.swing.JLabel();
        txtCdVendedor = new javax.swing.JTextField();
        txtNomeVendedor = new javax.swing.JTextField();
        lblData = new javax.swing.JLabel();
        jdcData = new com.toedter.calendar.JDateChooser();
        lblTotal = new javax.swing.JLabel();
        ftxtTotal = new javax.swing.JFormattedTextField();
        lblStatus = new javax.swing.JLabel();
        cbxStatus = new javax.swing.JComboBox<>();
        lblObs = new javax.swing.JLabel();
        btnSalvar = new javax.swing.JButton();
        btnIncluir = new javax.swing.JButton();
        btnExcluir = new javax.swing.JButton();
        btnAlterar = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        txtObs = new javax.swing.JTextArea();
        btnVendedor = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Pedidos");

        lblPesquisar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icones/procurar64.png"))); // NOI18N

        txtPesquisar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtPesquisarKeyReleased(evt);
            }
        });

        tblPesquisar.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null}
            },
            new String [] {
                "Codigo", "Cod. Cliente", "Cliente", "Cod. Vendedor", "Total", "Status", "Observação"
            }
        ));
        tblPesquisar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblPesquisarMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblPesquisar);

        lblCodigo.setText("Codigo");

        jLabel1.setText("Cliente");

        lblVendedor.setText("Vendedor");

        lblData.setText("Data");

        lblTotal.setText("Total");

        lblStatus.setText("Status");

        cbxStatus.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Pedente", "Entrege", "Cancelado", " " }));

        lblObs.setText("Obeservação");

        btnSalvar.setText("salvar");
        btnSalvar.setToolTipText("Salvar");
        btnSalvar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSalvarActionPerformed(evt);
            }
        });

        btnIncluir.setText("incluir");
        btnIncluir.setToolTipText("Incluir");
        btnIncluir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnIncluirActionPerformed(evt);
            }
        });

        btnExcluir.setText("excluir");
        btnExcluir.setToolTipText("Excluir");
        btnExcluir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnExcluirActionPerformed(evt);
            }
        });

        btnAlterar.setText("alterar");
        btnAlterar.setToolTipText("Alterar");
        btnAlterar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAlterarActionPerformed(evt);
            }
        });

        txtObs.setColumns(20);
        txtObs.setRows(5);
        jScrollPane2.setViewportView(txtObs);

        btnVendedor.setText("Vendedor");
        btnVendedor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnVendedorActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(lblPesquisar, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtPesquisar, javax.swing.GroupLayout.PREFERRED_SIZE, 257, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(btnSalvar)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(btnIncluir)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(btnExcluir)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(btnAlterar)))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                        .addComponent(lblTotal)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(ftxtTotal))
                                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                        .addComponent(lblVendedor)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(txtCdVendedor, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                        .addComponent(jLabel1)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(txtCdCliente)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(lblStatus)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(cbxStatus, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(txtNomeCliente)
                                            .addComponent(txtNomeVendedor, javax.swing.GroupLayout.PREFERRED_SIZE, 240, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(btnVendedor))))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(lblCodigo)
                                .addGap(4, 4, 4)
                                .addComponent(txtCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(lblData)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jdcData, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 120, Short.MAX_VALUE)
                                .addComponent(lblObs)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 263, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(lblPesquisar, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(16, 16, 16)
                        .addComponent(txtPesquisar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(lblCodigo)
                                .addComponent(txtCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(lblData)
                            .addComponent(jdcData, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblObs))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(txtCdCliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(txtNomeCliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(txtCdVendedor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(txtNomeVendedor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(btnVendedor))
                            .addComponent(lblVendedor)))
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblTotal)
                    .addComponent(ftxtTotal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblStatus)
                    .addComponent(cbxStatus, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(60, 60, 60)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnSalvar)
                    .addComponent(btnIncluir)
                    .addComponent(btnExcluir)
                    .addComponent(btnAlterar))
                .addContainerGap(117, Short.MAX_VALUE))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btnVendedorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnVendedorActionPerformed
        // TODO add your handling code here:
                 // Exemplo de como abrir a PesquisaVendedor e capturar o retorno
        // Adapte conforme a implementação real da sua classe PesquisaVendedor
        PesquisaVendedor dialog = new PesquisaVendedor(null, true);
        dialog.setVisible(true);
        // Supondo que a classe tenha métodos getCodigoSelecionado() e getNomeSelecionado()
        // Exemplo fictício:
        // Integer codVendedor = dialog.getCodigoSelecionado();
        // String nomeVendedor = dialog.getNomeSelecionado();
        // if (codVendedor != null) {
        //     txtCdVendedor.setText(codVendedor.toString());
        //     txtNomeVendedor.setText(nomeVendedor);
        // }
        // Se não houver esses métodos, você pode fazer a pesquisa diretamente na tabela e usar
        // variáveis estáticas ou um callback.
    }//GEN-LAST:event_btnVendedorActionPerformed

    private void btnIncluirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnIncluirActionPerformed
        // TODO add your handling code here:
        limparCampos();
        habilitar();
        controle = 1;
        btnIncluir.setEnabled(false);
        txtCodigo.setText(""); // será gerado pelo banco

    }//GEN-LAST:event_btnIncluirActionPerformed

    private void btnExcluirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExcluirActionPerformed
        // TODO add your handling code here:
        if (txtCodigo.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Selecione um pedido para excluir.");
            return;
        }
        Excluir();
    }//GEN-LAST:event_btnExcluirActionPerformed

    private void btnAlterarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAlterarActionPerformed
        // TODO add your handling code here:
        if (txtCodigo.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Selecione um pedido para alterar.");
            return;
        }
        habilitar();
        controle = 2;
        btnIncluir.setEnabled(false);
    }//GEN-LAST:event_btnAlterarActionPerformed

    private void btnSalvarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSalvarActionPerformed
        // TODO add your handling code here:
         if (txtCdCliente.getText().isEmpty() || txtCdVendedor.getText().isEmpty() || ftxtTotal.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Preencha todos os campos obrigatórios.");
            return;
        }
        try {
            Double.parseDouble(ftxtTotal.getText().replace(",", "."));
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Total inválido. Use vírgula para decimal.");
            return;
        }

        if (controle == 1) {
             try {
                 Incluir();
             } catch (SQLException ex) {
                 Logger.getLogger(TelaPedidos.class.getName()).log(Level.SEVERE, null, ex);
             }
        } else if (controle == 2) {
            Alterar();
        } else {
            JOptionPane.showMessageDialog(null, "Nenhuma operação selecionada.");
        }
    }//GEN-LAST:event_btnSalvarActionPerformed

    private void txtPesquisarKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtPesquisarKeyReleased
        // TODO add your handling code here:
        pesquisarPedido();
    }//GEN-LAST:event_txtPesquisarKeyReleased

    private void tblPesquisarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblPesquisarMouseClicked
        // TODO add your handling code here:
        carregarCampos();
    }//GEN-LAST:event_tblPesquisarMouseClicked

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(TelaPedidos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(TelaPedidos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(TelaPedidos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(TelaPedidos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new TelaPedidos().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAlterar;
    private javax.swing.JButton btnExcluir;
    private javax.swing.JButton btnIncluir;
    private javax.swing.JButton btnSalvar;
    private javax.swing.JButton btnVendedor;
    private javax.swing.JComboBox<String> cbxStatus;
    private javax.swing.JFormattedTextField ftxtTotal;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private com.toedter.calendar.JDateChooser jdcData;
    private javax.swing.JLabel lblCodigo;
    private javax.swing.JLabel lblData;
    private javax.swing.JLabel lblObs;
    private javax.swing.JLabel lblPesquisar;
    private javax.swing.JLabel lblStatus;
    private javax.swing.JLabel lblTotal;
    private javax.swing.JLabel lblVendedor;
    private javax.swing.JTable tblPesquisar;
    private javax.swing.JTextField txtCdCliente;
    private javax.swing.JTextField txtCdVendedor;
    private javax.swing.JTextField txtCodigo;
    private javax.swing.JTextField txtNomeCliente;
    private javax.swing.JTextField txtNomeVendedor;
    private javax.swing.JTextArea txtObs;
    private javax.swing.JTextField txtPesquisar;
    // End of variables declaration//GEN-END:variables
}
