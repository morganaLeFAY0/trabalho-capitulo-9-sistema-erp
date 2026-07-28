/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package Telas;

import Conexao.ModuloConexao;
import Pesquisa.PesquisaProfissao;
import java.sql.Connection;
//import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
//import java.text.DateFormat;
import java.text.SimpleDateFormat;
import javax.swing.JOptionPane;
import net.proteanit.sql.DbUtils;

/**
 *
 * @author beto_
 */
public class TelaCliente extends javax.swing.JFrame {
    
    Connection conexao = null;
    PreparedStatement pst = null;
    ResultSet rs = null;

 //   private final SimpleDateFormat formatador = 
  //        new  SimpleDateFormat("dd/MM/yyyy"); 

                // final = sempre sera isto
      // novo formatador
    java.text.SimpleDateFormat formatador = 
            new java.text.SimpleDateFormat("dd/MM/yyyy");
    
    int controle;
    
    /**
     * Creates new form TelaCliente
     */
    public TelaCliente() {
        initComponents();
        
             conexao = ModuloConexao.conector();   
             
       // Definir o formato visual para o JDateChooser
     jdcDataNascimento.setDateFormatString("dd/MM/yyyy");  
    
    }
    private void pesquisarUsuario(){
      String sql = " select idCliente as Codigo, "
              + "Profissao_idProfissao as Profissao, "
              + "DescriProfissao as Descrição, "
              + "Nome_Cliente as Nome, "
              + "Rua_Cliente as Rua, "
              + "Bairro_Cliente as Bairro, "
              + "Cidade_Cliente as Cidade, "
              + "Estado_Cliente as Estado, "
              + "Cep_Cliente as Cep, "
              + "Telefone_Cliente as Telefone, "
              + "Email_Cliente as Email, "
              + "DataNasc_Cliente as DataNasc, "
              + "Stattus_Cliente as Stattus "
              + "from cliente "
              + "Inner join profissao on idProfissao =  Profissao_idProfissao "
              + "where Nome_Cliente like ?";
        try {
            pst=conexao.prepareStatement(sql);
            pst.setString(1, txtPesquisar.getText() + "%");
            rs=pst.executeQuery();
            tblPesquisar.setModel(DbUtils.resultSetToTableModel(rs));
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        } // fim try        
    }  //fim metodo pesquisar usuario
    
    private void carregarCampos(){
        int carga = tblPesquisar.getSelectedRow();
        txtCodigo.setText(tblPesquisar.getModel().
                getValueAt(carga,0).toString());
        txtIdProfissao.setText(tblPesquisar.getModel().
                getValueAt(carga,1).toString()); 
        txtDescriProfissao.setText(tblPesquisar.getModel().
                getValueAt(carga,2).toString());         
        txtNome.setText(tblPesquisar.getModel().
                getValueAt(carga,3).toString()); 
        txtRua.setText(tblPesquisar.getModel().
                getValueAt(carga,4).toString()); 
        txtBairro.setText(tblPesquisar.getModel().
                getValueAt(carga,5).toString()); 
        txtCidade.setText(tblPesquisar.getModel().
                getValueAt(carga,6).toString());         
         cbxEstado.setSelectedItem(tblPesquisar.getModel().
                getValueAt(carga,7).toString());        
         ftxtCep.setText(tblPesquisar.getModel().
                getValueAt(carga,8).toString());
         ftxtTelefone.setText(tblPesquisar.getModel().
                getValueAt(carga,9).toString());     
         txtEmail.setText(tblPesquisar.getModel().
                getValueAt(carga,10).toString()); 
         
         java.util.Date datanascimento = 
            (java.util.Date) tblPesquisar.getValueAt(carga, 11);   
         jdcDataNascimento.setDate(datanascimento); 
 
        if (carga != -1) {
           try {
               // Pega a data da tabela (como String)
               String dataDaTabela = 
                  tblPesquisar.getValueAt(carga, 11).toString();
        
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
                getValueAt(carga, 12).toString();
                
        if (status.equals("A"))
           {
            btgAtivo.setSelected(true);
           } 
          else
           {
           btgInativo.setSelected(true);
           }     
         
         btnIncluir.setEnabled(false);
         Desabilitar();

    }  // fim metodo carregarCampos

    private void Desabilitar(){
         txtCodigo.setEditable(false);
         txtIdProfissao.setEditable(false);
         txtDescriProfissao.setEditable(false);
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
         btnPesquisarProfissao.setEnabled(false);  // mudar aq
    }
    
    private void habilitar(){
       txtCodigo.setEditable(false);
         txtIdProfissao.setEditable(true);
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
  //       ftxtDataNascimento.setEditable(true);   
         btnPesquisarProfissao.setEnabled(true);  // mudar aqui
    } // fim metodo habilitar
    
        private void Alterar(){
        String sql = "update cliente set "
                + "Profissao_idProfissao=?, "
                + "Nome_Cliente=?, "
                + "Rua_Cliente=?, "
                + "Bairro_Cliente=?, "
                + "Cidade_Cliente=?, "
                + "Estado_Cliente=?, "
                + "Cep_Cliente=?, "
                + "Telefone_Cliente=?, "
                + "Email_Cliente=?, "
                + "DataNasc_Cliente=?, "
                + "Stattus_Cliente=? "
                + "where idCliente=?";

        
        try {
           pst=conexao.prepareStatement(sql);
           pst.setString(1, txtIdProfissao.getText());
           pst.setString(2, txtNome.getText());
           pst.setString(3, txtRua.getText());
           pst.setString(4, txtBairro.getText());
           pst.setString(5, txtCidade.getText());           
           pst.setString(6, cbxEstado.getSelectedItem().toString());
           pst.setString(7, ftxtCep.getText());
           pst.setString(8, ftxtTelefone.getText());
           pst.setString(9, txtEmail.getText());
 
    //       java.util.Date dataUtil = formatador.
     //              parse(ftxtDataNascimento.getText());

//novo
           if (jdcDataNascimento.getDate() != null) {
                    // Cria o formatador no padrão aceito pelo MySQL (yyyy-MM-dd)
              java.text.SimpleDateFormat formatadorMySQL = 
                   new java.text.SimpleDateFormat("yyyy-MM-dd");  
                     // converte a data selecionada em String formatada para o banco
              String dataParaBanco = formatadorMySQL.format
                    (jdcDataNascimento.getDate());
              pst.setString(10, dataParaBanco); 
            } 
          else 
            {
             JOptionPane.showMessageDialog(null, 
                     "Selecione uma data.");
            }
           
           String status = btgAtivo.isSelected() ? "A" : "I";
           pst.setString(11, status);           
       
           pst.setString(12, txtCodigo.getText());  
         
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
        String sql = "insert into cliente "
                + "(Profissao_idProfissão, "
                + "Nome_Cliente, "
                + "Rua_Cliente, "
                + "Bairro_Cliente, "
                + "Cidade_Cliente, "
                + "Estado_Cliente, "
                + "Cep_Cliente, "
                + "Telefone_Cliente, "
                + "Email_Cliente, "
                + "DataNasc_Cliente, "
                + "Stattus_Cliente) "                        
                + "values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";   
    
        try {
           pst=conexao.prepareStatement(sql);
           pst.setString(1, txtIdProfissao.getText());
           pst.setString(2, txtNome.getText());
           pst.setString(3, txtRua.getText());
           pst.setString(4, txtBairro.getText());
           pst.setString(5, txtCidade.getText());           
           pst.setString(6, cbxEstado.getSelectedItem().toString());
           pst.setString(7, ftxtCep.getText());
           pst.setString(8, ftxtTelefone.getText());
           pst.setString(9, txtEmail.getText());
                                      
          if (jdcDataNascimento.getDate() != null) {
                    // Cria o formatador no padrão aceito pelo MySQL (yyyy-MM-dd)
              java.text.SimpleDateFormat formatadorMySQL = 
                  new java.text.SimpleDateFormat("yyyy-MM-dd");  
                     // converte a data selecionada em String formatada para o banco
              String dataParaBanco = formatadorMySQL.
                      format(jdcDataNascimento.getDate());
              pst.setString(10, dataParaBanco); 
            } 
          else 
            {
             JOptionPane.showMessageDialog
                          (null, "Selecione uma data.");
            }
           
           
             String status = btgAtivo.isSelected() ? "A" : "I";
             pst.setString(11, status);
           
    //       pst.setString(12, txtCodigo.getText());  

           
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
    
    
    private void Excluir(){
        
        int confirmar=JOptionPane.showConfirmDialog(null, 
                "Confirmar a Exclusao do Registro",
                      "Atenção",JOptionPane.YES_NO_OPTION);
        
        if (confirmar == JOptionPane.YES_OPTION){
           String sql = "update cliente set Stattus_Cliente = 'I' where IdCliente = ?";      
           try {
               pst = conexao.prepareStatement(sql);
               pst.setString(1, txtCodigo.getText());         
               int  ok = pst.executeUpdate();
               
               if  (ok > 0){
                    JOptionPane.showMessageDialog(null, "Excluido com sucesso");
                }
        } catch (Exception e) {
            JOptionPane.showConfirmDialog(null, e);
        }       
    }
    }// fim metodo excluir
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        btgStatus = new javax.swing.ButtonGroup();
        lblPesquisar = new javax.swing.JLabel();
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
        lblCidade = new javax.swing.JLabel();
        txtCidade = new javax.swing.JTextField();
        lblEstado = new javax.swing.JLabel();
        cbxEstado = new javax.swing.JComboBox<>();
        lblCep = new javax.swing.JLabel();
        ftxtCep = new javax.swing.JFormattedTextField();
        lblTelefone = new javax.swing.JLabel();
        ftxtTelefone = new javax.swing.JFormattedTextField();
        lblEmail = new javax.swing.JLabel();
        txtEmail = new javax.swing.JTextField();
        lblDataNascimento = new javax.swing.JLabel();
        btgAtivo = new javax.swing.JRadioButton();
        btgInativo = new javax.swing.JRadioButton();
        btnIncluir = new javax.swing.JButton();
        btnEditar = new javax.swing.JButton();
        btnExcluir = new javax.swing.JButton();
        btnSalvar = new javax.swing.JButton();
        lblStatus = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        txtIdProfissao = new javax.swing.JTextField();
        txtDescriProfissao = new javax.swing.JTextField();
        btnPesquisarProfissao = new javax.swing.JButton();
        jdcDataNascimento = new com.toedter.calendar.JDateChooser();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Cadastro de Cliente");
        setPreferredSize(new java.awt.Dimension(800, 600));
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowActivated(java.awt.event.WindowEvent evt) {
                formWindowActivated(evt);
            }
        });

        lblPesquisar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icones/procurar64.png"))); // NOI18N

        txtPesquisar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtPesquisarKeyReleased(evt);
            }
        });

        tblPesquisar.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "Codigo", "Nome", "Rua", "Bairro", "Cidade", "UF", "Cep", "Telefone", "Email", "Data Nasc", "Status"
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

        lblCidade.setText("Cidade");

        lblEstado.setText("Uf");

        cbxEstado.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "SC", "RS", "PR", "SP", "MT", "MS", "MG", " " }));

        lblCep.setText("CEP");

        try {
            ftxtCep.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("#####-###")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }

        lblTelefone.setText("Telefone");

        try {
            ftxtTelefone.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("(##)#####-####")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }

        lblEmail.setText("Email");

        lblDataNascimento.setText("Data Nascimento");

        btgStatus.add(btgAtivo);
        btgAtivo.setText("Ativo");

        btgStatus.add(btgInativo);
        btgInativo.setText("Inativo");

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
        btnExcluir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnExcluirActionPerformed(evt);
            }
        });

        btnSalvar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icones/salve64.png"))); // NOI18N
        btnSalvar.setToolTipText("Salvar");
        btnSalvar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSalvarActionPerformed(evt);
            }
        });

        lblStatus.setText("Status");

        jLabel2.setText("Profissão");

        btnPesquisarProfissao.setText("Pesquisar");
        btnPesquisarProfissao.setToolTipText("Profissão");
        btnPesquisarProfissao.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPesquisarProfissaoActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(31, 31, 31)
                        .addComponent(lblPesquisar, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(txtPesquisar, javax.swing.GroupLayout.PREFERRED_SIZE, 393, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(25, 25, 25)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(41, 41, 41)
                                        .addComponent(btnIncluir)
                                        .addGap(94, 94, 94)
                                        .addComponent(btnEditar)
                                        .addGap(110, 110, 110)
                                        .addComponent(btnExcluir))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(lblEmail, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(txtEmail, javax.swing.GroupLayout.PREFERRED_SIZE, 255, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(lblDataNascimento, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(jdcDataNascimento, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGap(37, 37, 37)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(lblStatus, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(btgAtivo))
                                    .addComponent(btnSalvar, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(btgInativo, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 847, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(lblCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(txtCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(lblNome, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(txtNome))
                                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                        .addComponent(lblRua, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(txtRua, javax.swing.GroupLayout.PREFERRED_SIZE, 411, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(24, 24, 24)
                                        .addComponent(lblBairro, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(txtBairro, javax.swing.GroupLayout.PREFERRED_SIZE, 212, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(layout.createSequentialGroup()
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(txtIdProfissao, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(txtDescriProfissao, javax.swing.GroupLayout.PREFERRED_SIZE, 151, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(btnPesquisarProfissao))))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(lblCidade, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtCidade, javax.swing.GroupLayout.PREFERRED_SIZE, 182, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(27, 27, 27)
                                .addComponent(lblEstado, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(cbxEstado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(52, 52, 52)
                                .addComponent(lblCep, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(ftxtCep, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(lblTelefone, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(ftxtTelefone, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap(110, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(17, 17, 17)
                        .addComponent(lblPesquisar, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(txtPesquisar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(26, 26, 26)))
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 134, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblCodigo)
                    .addComponent(txtCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblNome)
                    .addComponent(txtNome, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2)
                    .addComponent(txtIdProfissao, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtDescriProfissao, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnPesquisarProfissao))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblRua)
                    .addComponent(txtRua, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblBairro)
                    .addComponent(txtBairro, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblCidade)
                    .addComponent(txtCidade, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblEstado)
                    .addComponent(cbxEstado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblCep)
                    .addComponent(ftxtCep, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblTelefone)
                    .addComponent(ftxtTelefone, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(lblEmail)
                        .addComponent(txtEmail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(lblDataNascimento)
                        .addComponent(btgAtivo)
                        .addComponent(btgInativo)
                        .addComponent(lblStatus))
                    .addComponent(jdcDataNascimento, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnEditar, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnIncluir, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnExcluir, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnSalvar, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(53, Short.MAX_VALUE))
        );

        setSize(new java.awt.Dimension(996, 523));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void txtPesquisarKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtPesquisarKeyReleased
        // TODO add your handling code here:
        pesquisarUsuario();
    }//GEN-LAST:event_txtPesquisarKeyReleased

    private void tblPesquisarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblPesquisarMouseClicked
        // TODO add your handling code here:
        carregarCampos();
    }//GEN-LAST:event_tblPesquisarMouseClicked

    private void formWindowActivated(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowActivated
        // TODO add your handling code here:
   //   Desabilitar();  

    }//GEN-LAST:event_formWindowActivated

    private void btnEditarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditarActionPerformed
        // TODO add your handling code here:
        habilitar();
        controle = 2;
    }//GEN-LAST:event_btnEditarActionPerformed

    private void btnSalvarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSalvarActionPerformed
        // TODO add your handling code here:
        if (controle == 1)
            Incluir();
          else
            if (controle == 2)
                Alterar();    
    }//GEN-LAST:event_btnSalvarActionPerformed

    private void btnIncluirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnIncluirActionPerformed
        // TODO add your handling code here:
        habilitar();
        controle = 1;
    }//GEN-LAST:event_btnIncluirActionPerformed

    private void btnPesquisarProfissaoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPesquisarProfissaoActionPerformed
        // TODO add your handling code here:
        PesquisaProfissao  pesquisaprofissao = 
                       new PesquisaProfissao(null, true);
        pesquisaprofissao.setVisible(true);
        
    String codigorecebido = pesquisaprofissao.getPassarcodigo();
        txtIdProfissao.setText(codigorecebido);
    String descrirecebido = pesquisaprofissao.getpassardescri();
        txtDescriProfissao.setText(descrirecebido); 
        
   //    habilitar();  // mudar
    }//GEN-LAST:event_btnPesquisarProfissaoActionPerformed

    private void btnExcluirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExcluirActionPerformed
        // TODO add your handling code here:
        Excluir();
    }//GEN-LAST:event_btnExcluirActionPerformed

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
            java.util.logging.Logger.getLogger(TelaCliente.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(TelaCliente.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(TelaCliente.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(TelaCliente.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new TelaCliente().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JRadioButton btgAtivo;
    private javax.swing.JRadioButton btgInativo;
    private javax.swing.ButtonGroup btgStatus;
    private javax.swing.JButton btnEditar;
    private javax.swing.JButton btnExcluir;
    private javax.swing.JButton btnIncluir;
    private javax.swing.JButton btnPesquisarProfissao;
    private javax.swing.JButton btnSalvar;
    private javax.swing.JComboBox<String> cbxEstado;
    private javax.swing.JFormattedTextField ftxtCep;
    private javax.swing.JFormattedTextField ftxtTelefone;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JScrollPane jScrollPane1;
    private com.toedter.calendar.JDateChooser jdcDataNascimento;
    private javax.swing.JLabel lblBairro;
    private javax.swing.JLabel lblCep;
    private javax.swing.JLabel lblCidade;
    private javax.swing.JLabel lblCodigo;
    private javax.swing.JLabel lblDataNascimento;
    private javax.swing.JLabel lblEmail;
    private javax.swing.JLabel lblEstado;
    private javax.swing.JLabel lblNome;
    private javax.swing.JLabel lblPesquisar;
    private javax.swing.JLabel lblRua;
    private javax.swing.JLabel lblStatus;
    private javax.swing.JLabel lblTelefone;
    private javax.swing.JTable tblPesquisar;
    private javax.swing.JTextField txtBairro;
    private javax.swing.JTextField txtCidade;
    private javax.swing.JTextField txtCodigo;
    private javax.swing.JTextField txtDescriProfissao;
    private javax.swing.JTextField txtEmail;
    private javax.swing.JTextField txtIdProfissao;
    private javax.swing.JTextField txtNome;
    private javax.swing.JTextField txtPesquisar;
    private javax.swing.JTextField txtRua;
    // End of variables declaration//GEN-END:variables
}
