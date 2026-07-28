/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package Telas;

import Conexao.ModuloConexao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
//import java.text.SimpleDateFormat;
import javax.swing.JOptionPane;
import net.proteanit.sql.DbUtils;

public class Tela_Vendedor extends javax.swing.JFrame {

    Connection conexao = null;
    PreparedStatement pst = null;
    ResultSet rs = null;
   
 //      private final SimpleDateFormat formatador1 = 
 //         new  SimpleDateFormat("dd/MM/yyyy"); 
    
    java.text.SimpleDateFormat formatador = 
            new java.text.SimpleDateFormat("dd/MM/yyyy");
        
    int controle;
        
    public Tela_Vendedor() {
        initComponents();
        
        conexao = ModuloConexao.conector(); 
        
        jdcDataNascimento.setDateFormatString("dd/MM/yyyy");
    }

    private void pesquisarVendedor(){
      String sql = " select idVendedor as Codigo, "
              + "Nome_Vendedor as Nome, "
              + "Rua_Vendedor as Rua, "
              + "Bairro_Vendedor as Bairro, "
              + "Cidade_Vendedor as Cidade, "
              + "Estado_Vendedor as Uf, "
              + "Cep_Vendedor as Cep, "
              + "Telefone_Vendedor as Telefone, "
              + "Email_Vendedor as Email, "
              + "DataNasc_Vendedor as DataNasc, "
              + "Status_Vendedor as Estatus "
              + "from vendedor "
              + "where Nome_Vendedor like ?";
        try {
            pst=conexao.prepareStatement(sql);
            pst.setString(1, txtPesquisar.getText() + "%");
            rs=pst.executeQuery();
            tblPesquisar.setModel(DbUtils.resultSetToTableModel(rs));
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        } // fim try        
    }  //fim metodo pesquisar vendedor

    private void carregarCampos(){
        int carga = tblPesquisar.getSelectedRow();
        txtCodigo.setText(tblPesquisar.getModel().
                getValueAt(carga,0).toString());      
        txtNome.setText(tblPesquisar.getModel().
                getValueAt(carga,1).toString()); 
        txtRua.setText(tblPesquisar.getModel().
                getValueAt(carga,2).toString()); 
        txtBairro.setText(tblPesquisar.getModel().
                getValueAt(carga,3).toString()); 
        txtCidade.setText(tblPesquisar.getModel().
                getValueAt(carga,4).toString());         
         cbxEstado.setSelectedItem(tblPesquisar.getModel().
                getValueAt(carga,5).toString());        
         ftxtCep.setText(tblPesquisar.getModel().
                getValueAt(carga,6).toString());
         ftxtTelefone.setText(tblPesquisar.getModel().
                getValueAt(carga,7).toString());     
         txtEmail.setText(tblPesquisar.getModel().
                getValueAt(carga,8).toString());    
         
         java.util.Date datanascimento = 
            (java.util.Date) tblPesquisar.getValueAt(carga, 9);   
         jdcDataNascimento.setDate(datanascimento); 
 
        if (carga != -1) {
           try {
               // Pega a data da tabela (como String)
               String dataDaTabela = 
                  tblPesquisar.getValueAt(carga, 9).toString();
        
               // Converte a String para um objeto Date real
               java.util.Date dataConvertida = 
                       formatador.parse(dataDaTabela);
        
               // Exibe no JDateChooser (que já está configurado para mostrar dd/MM/yyyy)
               jdcDataNascimento.setDate(dataConvertida);
        
            } catch (java.text.ParseException e) {
              System.err.println
                  ("Erro ao converter formato da data: "
                          + e.getMessage());
           }
          } // fim if
 
         String status = tblPesquisar.getModel().
                getValueAt(carga, 10).toString();
                
        if (status.equals("A"))
           {
            rbtAtivo.setSelected(true);
           } 
          else
           {
           rbtInativo.setSelected(true);
           }     
         
         btnIncluir.setEnabled(false);
         Desabilitar();
    }  // fim metodo carregarCampos
    private void Desabilitar(){
         txtCodigo.setEditable(false);
         txtNome.setEditable(false);
         txtRua.setEditable(false);
         txtBairro.setEditable(false);
         txtCidade.setEditable(false);
         cbxEstado.setEditable(false);
         ftxtCep.setEditable(false);       
         ftxtTelefone.setEditable(false);
         txtEmail.setEditable(false);
         ((com.toedter.calendar.JTextFieldDateEditor) 
             jdcDataNascimento.
                  getDateEditor()).setEditable(false);         

    }
    private void habilitar(){
         txtCodigo.setEditable(false);
         txtNome.setEditable(true);
         txtRua.setEditable(true);
         txtBairro.setEditable(true);
         txtCidade.setEditable(true);
         cbxEstado.setEditable(true);
         ftxtCep.setEditable(true);       
         ftxtTelefone.setEditable(true);
         txtEmail.setEditable(true);
         ((com.toedter.calendar.JTextFieldDateEditor) jdcDataNascimento.
                  getDateEditor()).setEditable(true);
    } // fim metodo habilitar
    
    private void Alterar(){
        String sql = "update vendedor set "
                + "Nome_Vendedor=?, "
                + "Rua_Vendedor=?, "
                + "Bairro_Vendedor=?, "
                + "Cidade_Vendedor=?, "
                + "Estado_Vendedor=?, "
                + "Cep_Vendedor=?, "
                + "Telefone_Vendedor=?, "
                + "Email_Vendedor=?, "
                + "DataNasc_Vendedor=?, "
                + "Status_Vendedor=? "
                + "where idVendedor=?";   
        try {
           pst=conexao.prepareStatement(sql);
           pst.setString(1, txtNome.getText());
           pst.setString(2, txtRua.getText());
           pst.setString(3, txtBairro.getText());
           pst.setString(4, txtCidade.getText());           
           pst.setString(5, cbxEstado.getSelectedItem().toString());
           pst.setString(6, ftxtCep.getText());
           pst.setString(7, ftxtTelefone.getText());
           pst.setString(8, txtEmail.getText());
 
           if (jdcDataNascimento.getDate() != null) {
                    // Cria o formatador no padrão aceito pelo MySQL (yyyy-MM-dd)
              java.text.SimpleDateFormat formatadorMySQL = 
                   new java.text.SimpleDateFormat("yyyy-MM-dd");  
                     // converte a data selecionada em String formatada para o banco
              String dataParaBanco = formatadorMySQL.format
                    (jdcDataNascimento.getDate());
              pst.setString(9, dataParaBanco); 
            } 
          else 
            {
             JOptionPane.showMessageDialog(null, 
                     "Selecione uma data.");
            }
           
           String status = rbtAtivo.isSelected() ? "A" : "I";
           pst.setString(10, status);           
       
           pst.setString(11, txtCodigo.getText());  
         
           if ((txtNome.getText().isEmpty()) || 
               (ftxtTelefone.getText().isEmpty()) ||
    //           (ftxtDataNascimento.getText().isEmpty()) ||   //arrumar
               (status.isEmpty())){
               JOptionPane.showMessageDialog
                   (null, "Obrigatorio todos os campo");
           } // fim if true
           else {
               int ok = pst.executeUpdate(); // atualiza tabela bd
               if (ok > 0){
                   JOptionPane.showMessageDialog(null, "Alterado Com Sucesso");
     //              limparCampos();
               }
           } // fim if false
        } catch (Exception e) {
            JOptionPane.showConfirmDialog(null, e);
        }  // fim try
    } // fim metodo salvar
    
    private void Incluir(){
        String sql = "insert into vendedor "
                + "Nome_Vendedor, "
                + "Rua_Vendedor, "
                + "Bairro_Vendedor, "
                + "Cidade_Vendedor, "
                + "Estado_Vendedor, "
                + "Cep_Vendedor, "
                + "Telefone_Vendedor, "
                + "Email_Vendedor, "
                + "DataNasc_Vendedor, "
                + "Status_Vendedor) "                        
                + "values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";   
    
        try {
           pst=conexao.prepareStatement(sql);
           pst.setString(1, txtNome.getText());
           pst.setString(2, txtRua.getText());
           pst.setString(3, txtBairro.getText());
           pst.setString(4, txtCidade.getText());           
           pst.setString(5, cbxEstado.getSelectedItem().toString());
           pst.setString(6, ftxtCep.getText());
           pst.setString(7, ftxtTelefone.getText());
           pst.setString(8, txtEmail.getText());
                                      
          if (jdcDataNascimento.getDate() != null) {
                    // Cria o formatador no padrão aceito pelo MySQL (yyyy-MM-dd)
              java.text.SimpleDateFormat formatadorMySQL = 
                  new java.text.SimpleDateFormat("yyyy-MM-dd");  
                     // converte a data selecionada em String formatada para o banco
              String dataParaBanco = formatadorMySQL.
                      format(jdcDataNascimento.getDate());
              pst.setString(9, dataParaBanco); 
            } 
          else 
            {
             JOptionPane.showMessageDialog
                          (null, "Selecione uma data.");
            }
           
           
             String status = rbtAtivo.isSelected() ? "A" : "I";
             pst.setString(10, status); 

           if ((txtNome.getText().isEmpty()) || 
               (ftxtTelefone.getText().isEmpty()) ||
    //           (ftxtDataNascimento.getText().isEmpty()) ||    // EERUME
               (status.isEmpty())){
               JOptionPane.showMessageDialog
                   (null, "Obrigatorio todos os campo");
           } // fim if true
           else {
               int ok = pst.executeUpdate(); // atualiza tabela bd
               if (ok > 0){
                   JOptionPane.showMessageDialog(null, "Incluido Com Sucesso");
     //              limparCampos();
               }
           } // fim if false
        } catch (Exception e) {
            JOptionPane.showConfirmDialog(null, e);
        }  // fim try
    } // fim metodo Incluir
        
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        btgStatus = new javax.swing.ButtonGroup();
        jLabel1 = new javax.swing.JLabel();
        txtPesquisar = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblPesquisar = new javax.swing.JTable();
        lblCodigo = new javax.swing.JLabel();
        txtCodigo = new javax.swing.JTextField();
        lblNome = new javax.swing.JLabel();
        txtNome = new javax.swing.JTextField();
        lblRua = new javax.swing.JLabel();
        txtRua = new javax.swing.JTextField();
        lblBairro = new javax.swing.JLabel();
        txtBairro = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        txtCidade = new javax.swing.JTextField();
        cbxEstado = new javax.swing.JComboBox<>();
        lblEstado = new javax.swing.JLabel();
        lblCep = new javax.swing.JLabel();
        ftxtCep = new javax.swing.JFormattedTextField();
        lblDatanascimento = new javax.swing.JLabel();
        jdcDataNascimento = new com.toedter.calendar.JDateChooser();
        lblTelefone = new javax.swing.JLabel();
        ftxtTelefone = new javax.swing.JFormattedTextField();
        lblEmail = new javax.swing.JLabel();
        txtEmail = new javax.swing.JTextField();
        lblStatus = new javax.swing.JLabel();
        rbtAtivo = new javax.swing.JRadioButton();
        rbtInativo = new javax.swing.JRadioButton();
        btnIncluir = new javax.swing.JButton();
        btnEditar = new javax.swing.JButton();
        btnExcluir = new javax.swing.JButton();
        btnSalvar = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Cadastro de Vendedores");

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icones/procurar64.png"))); // NOI18N
        jLabel1.setText("jLabel1");

        txtPesquisar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtPesquisarKeyReleased(evt);
            }
        });

        tblPesquisar.setModel(new javax.swing.table.DefaultTableModel(
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
        tblPesquisar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblPesquisarMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblPesquisar);

        lblCodigo.setText("Codigo");

        lblNome.setText("Nome");

        lblRua.setText("Rua");

        lblBairro.setText("Bairro");

        txtBairro.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtBairroActionPerformed(evt);
            }
        });

        jLabel2.setText("Cidade");

        cbxEstado.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Rs", "Sc", "Pr", "Sp" }));

        lblEstado.setText("Estado");

        lblCep.setText("Cep");

        try {
            ftxtCep.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("#####-###")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }

        lblDatanascimento.setText("Data Nasc");

        lblTelefone.setText("Telefone");

        try {
            ftxtTelefone.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("(##)#####-####")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }

        lblEmail.setText("Email");

        lblStatus.setText("Status");

        btgStatus.add(rbtAtivo);
        rbtAtivo.setText("Ativo");

        btgStatus.add(rbtInativo);
        rbtInativo.setText("Inativo");

        btnIncluir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icones/adicionar64.png"))); // NOI18N
        btnIncluir.setToolTipText("Incluir");
        btnIncluir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnIncluirActionPerformed(evt);
            }
        });

        btnEditar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icones/editar64.png"))); // NOI18N
        btnEditar.setToolTipText("Editar");
        btnEditar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditarActionPerformed(evt);
            }
        });

        btnExcluir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icones/lixo64.png"))); // NOI18N
        btnExcluir.setToolTipText("Excluir");

        btnSalvar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icones/salve64.png"))); // NOI18N
        btnSalvar.setToolTipText("Salvar");
        btnSalvar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSalvarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(51, 51, 51)
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtPesquisar, javax.swing.GroupLayout.PREFERRED_SIZE, 415, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(32, 32, 32)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 720, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(lblTelefone, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 53, Short.MAX_VALUE)
                                    .addComponent(lblCodigo, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(lblRua, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(txtCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(lblNome, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(txtNome, javax.swing.GroupLayout.PREFERRED_SIZE, 275, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(lblDatanascimento, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jdcDataNascimento, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(txtRua, javax.swing.GroupLayout.PREFERRED_SIZE, 308, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(txtCidade, javax.swing.GroupLayout.PREFERRED_SIZE, 235, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGap(18, 18, 18)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addComponent(lblBairro, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(lblEstado, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addGroup(layout.createSequentialGroup()
                                                .addComponent(cbxEstado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 41, Short.MAX_VALUE)
                                                .addComponent(lblCep, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                .addComponent(ftxtCep, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE))
                                            .addComponent(txtBairro)))
                                    .addGroup(layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                                .addComponent(ftxtTelefone, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(39, 39, 39)
                                                .addComponent(lblEmail, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))
                                            .addGroup(layout.createSequentialGroup()
                                                .addGap(17, 17, 17)
                                                .addComponent(btnIncluir)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addComponent(btnEditar, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(layout.createSequentialGroup()
                                                .addGap(54, 54, 54)
                                                .addComponent(btnExcluir)
                                                .addGap(56, 56, 56)
                                                .addComponent(btnSalvar))
                                            .addGroup(layout.createSequentialGroup()
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(txtEmail, javax.swing.GroupLayout.PREFERRED_SIZE, 224, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(41, 41, 41)
                                                .addComponent(lblStatus, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(rbtAtivo, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(rbtInativo)))))))))
                .addContainerGap(48, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtPesquisar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(31, 31, 31)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblCodigo)
                            .addComponent(txtCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblNome)
                            .addComponent(txtNome, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblDatanascimento))
                        .addGap(28, 28, 28))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jdcDataNascimento, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(4, 4, 4)))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblRua)
                    .addComponent(txtRua, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblBairro)
                    .addComponent(txtBairro, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(24, 24, 24)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2)
                            .addComponent(txtCidade, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(cbxEstado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblEstado)
                            .addComponent(lblCep)
                            .addComponent(ftxtCep, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(34, 34, 34)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblTelefone)
                    .addComponent(ftxtTelefone, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblEmail)
                    .addComponent(txtEmail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblStatus)
                    .addComponent(rbtAtivo)
                    .addComponent(rbtInativo))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnEditar, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnIncluir, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnExcluir, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnSalvar, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(40, Short.MAX_VALUE))
        );

        setSize(new java.awt.Dimension(814, 506));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void txtBairroActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtBairroActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtBairroActionPerformed

    private void txtPesquisarKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtPesquisarKeyReleased
        // TODO add your handling code here:
        pesquisarVendedor();
    }//GEN-LAST:event_txtPesquisarKeyReleased

    private void tblPesquisarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblPesquisarMouseClicked
        // TODO add your handling code here:
        carregarCampos();        
    }//GEN-LAST:event_tblPesquisarMouseClicked

    private void btnEditarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditarActionPerformed
        // TODO add your handling code here:
        habilitar();
        controle = 2;
    }//GEN-LAST:event_btnEditarActionPerformed

    private void btnIncluirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnIncluirActionPerformed
        // TODO add your handling code here:
        habilitar();
        controle = 1;
    }//GEN-LAST:event_btnIncluirActionPerformed

    private void btnSalvarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSalvarActionPerformed
        // TODO add your handling code here:
        if (controle == 1)
            Incluir();
          else
            if (controle == 2)
                Alterar();            
    }//GEN-LAST:event_btnSalvarActionPerformed

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
            java.util.logging.Logger.getLogger(Tela_Vendedor.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Tela_Vendedor.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Tela_Vendedor.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Tela_Vendedor.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Tela_Vendedor().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup btgStatus;
    private javax.swing.JButton btnEditar;
    private javax.swing.JButton btnExcluir;
    private javax.swing.JButton btnIncluir;
    private javax.swing.JButton btnSalvar;
    private javax.swing.JComboBox<String> cbxEstado;
    private javax.swing.JFormattedTextField ftxtCep;
    private javax.swing.JFormattedTextField ftxtTelefone;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JScrollPane jScrollPane1;
    private com.toedter.calendar.JDateChooser jdcDataNascimento;
    private javax.swing.JLabel lblBairro;
    private javax.swing.JLabel lblCep;
    private javax.swing.JLabel lblCodigo;
    private javax.swing.JLabel lblDatanascimento;
    private javax.swing.JLabel lblEmail;
    private javax.swing.JLabel lblEstado;
    private javax.swing.JLabel lblNome;
    private javax.swing.JLabel lblRua;
    private javax.swing.JLabel lblStatus;
    private javax.swing.JLabel lblTelefone;
    private javax.swing.JRadioButton rbtAtivo;
    private javax.swing.JRadioButton rbtInativo;
    private javax.swing.JTable tblPesquisar;
    private javax.swing.JTextField txtBairro;
    private javax.swing.JTextField txtCidade;
    private javax.swing.JTextField txtCodigo;
    private javax.swing.JTextField txtEmail;
    private javax.swing.JTextField txtNome;
    private javax.swing.JTextField txtPesquisar;
    private javax.swing.JTextField txtRua;
    // End of variables declaration//GEN-END:variables
}
