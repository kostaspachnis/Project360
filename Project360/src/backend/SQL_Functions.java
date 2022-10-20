package backend;

import java.sql.*;
import java.sql.Date;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SQL_Functions {

    static int tran_id = 1;


    public static void purchase(int account_num_d , int account_num_cus , double agora) throws SQLException, ClassNotFoundException {
        Civilian civ = new Civilian();
        Company comp = new Company();
        User cust = new User() ;
        cust = User.getUser2(account_num_cus);
        Dealer deal = new Dealer();
        deal = Dealer.getDealer2(account_num_d);
        String tipos = cust.getType();
        double diathesimo , credit_now ;
        if (tipos.equals("Civilian")){
            civ = civ.getCivilian2(account_num_cus);
            diathesimo = civ.getBalance();
            credit_now = civ.getCredit_limit();
            if ( diathesimo >= agora  &&  credit_now >= agora){
                civ.setBalance(diathesimo-agora);
            }else{
                System.out.println("Transaction cannot happen , because of not enough Civillian's balance or Credit_limit.");
                return ;
            }
        }else if ( tipos.equals("Company")){
            comp = comp.getCompany2(account_num_cus);
            diathesimo = comp.getBalance() ;
            credit_now = comp.getCredit_limit();
            if ( diathesimo >= agora && credit_now >= agora){
                comp.setBalance(diathesimo-agora);
            }else{
                System.out.println("Transaction cannot happen , because of not enough Company's balance or Credit Limit.");
                return;
            }
        }

        double updated_earnings = deal.getEarnings() + (agora - (agora* deal.getCommission()));
        deal.setEarnings(updated_earnings);

        String msg = "";
        Statement stmt = null;
        Statement stmt2 = null;

        Connection con = null;

        try {

            con = DB.getConnection();

            stmt = con.createStatement();
            stmt2 = con.createStatement();


            StringBuilder insQuery = new StringBuilder();
            StringBuilder insQuery2 = new StringBuilder();
            StringBuilder insQuery3 = new StringBuilder();

            PreparedStatement preparedStmt, preparedStmt2, prepareStmt3;

            if(tipos.equals("Civilian")){
                insQuery.append("UPDATE civilians ");
                insQuery.append(" SET balance = ").append(civ.getBalance());
                insQuery.append(" WHERE account_no = ").append(account_num_cus);


            }else if(tipos.equals("Company")){
                insQuery.append("UPDATE companies ");
                insQuery.append(" SET balance = ").append(comp.getBalance());
                insQuery.append(" WHERE account_no = ").append(account_num_cus);
            }

            insQuery2.append("UPDATE dealers ");
            insQuery2.append(" SET earnings = ").append(deal.getEarnings());
            insQuery2.append(" WHERE account_no = ").append(account_num_d);



            preparedStmt = con.prepareStatement(insQuery.toString());
            preparedStmt.execute();

            preparedStmt2 = con.prepareStatement(insQuery2.toString());
            preparedStmt2.execute();

            msg = "Balance updated succesfully.";

        } catch (SQLException ex) {
            msg = ex.getMessage();
            Logger.getLogger(DB.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            // close connection
            DB.closeConnection(stmt, con);
            DB.closeConnection(stmt2, con);
        }

        java.sql.Date dat = new Date(2022);
        Transaction.insert_Transaction(++tran_id, deal.getName(), deal.getAccount_no(), cust.getName(), cust.getAccount_no(), dat, agora, "purchase");

        return ;
    }


    public static void return_things(int transs_id) throws SQLException, ClassNotFoundException {
        Transaction tra = new Transaction()  ;
        String new_type = "return";
        Statement stmt = null;
        PreparedStatement stmt3 = null;
        Connection con = null;

        try {
            con = DB.getConnection();

            stmt = con.createStatement();

            StringBuilder insQuery2 = new StringBuilder();
            StringBuilder insQuery = new StringBuilder();

            insQuery.append("SELECT * FROM transactions ")
                    .append("WHERE ")
                    .append(" transactionID = ").append(transs_id);

            insQuery2.append("UPDATE transactions ");
            insQuery2.append(" SET type = ").append("'").append(new_type).append("'");
            insQuery2.append(" WHERE transactionID = ").append(transs_id);

            stmt3 = con.prepareStatement(insQuery2.toString());

            stmt.executeQuery(insQuery.toString());
            stmt3.execute();

            ResultSet res = stmt.getResultSet();
            if (res.next() == true) {
                tra.setTransactionID(res.getInt("transactionID"));
                tra.setDealerName(res.getString("dealerName"));
                tra.setDealerAccount_no(res.getInt("dealerAccount_no"));
                tra.setCustomerName(res.getString("customerName"));
                tra.setCustomerAccount_no(res.getInt("customerAccount_no"));
                tra.setDate(res.getDate("date"));
                tra.setAmount(res.getDouble("amount"));

            } else {
                tra = null;
                System.out.println("Transaction with transaction_id " + transs_id + "was not found");
            }
        } catch (SQLException ex) {
           ex.printStackTrace();
           Logger.getLogger(DB.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            // close connection
            DB.closeConnection(stmt, con);
        }
        System.out.println("Transaction's customer : "+tra.getCustomerName() +"and dealer's name:"+tra.getDealerName());
        if ( tra != null ){
            User us = new User();
            Dealer deal = new Dealer()  ;
            String tipos ;
            int epiloges_dealer = 0 ;
            int c_acc = tra.getCustomerAccount_no() , d_acc = tra.getDealerAccount_no() ;
            double epistrofi = tra.getAmount() ;
            double balance_now = 0 , earnings_now = 0  , debt_now = 0 , upoloipa = 0;
            us = User.getUser2(c_acc);
            deal = Dealer.getDealer2(d_acc);
            tipos = us.getType();
            if ( tipos.equals("Civilian")){
                Civilian civ = new Civilian();
                civ = Civilian.getCivilian2(c_acc);
                balance_now = civ.getBalance();
                civ.setBalance(balance_now+epistrofi);
            }else if ( tipos.equals("Company")){
                Company comp = new Company();
                comp = Company.getCompany2(c_acc);
                balance_now = comp.getBalance();
                comp.setBalance(balance_now+epistrofi);
            }
            earnings_now = deal.getEarnings();
            if ( earnings_now <= epistrofi ){
                debt_now = deal.getDebt() ;
                upoloipa = epistrofi - earnings_now ;
                epiloges_dealer = 1 ;
                deal.setEarnings(0);
                deal.setDebt((debt_now+upoloipa));
            }else {
                deal.setEarnings(earnings_now-epistrofi);
            }

            Statement stmt2 = null ;
            Connection con2 = null;
            try{
                con2 = DB.getConnection();
                stmt2 = con2.createStatement();
               ;
                StringBuilder insQuery2 = new StringBuilder() , insQuery3 = new StringBuilder();
                PreparedStatement preparedStmt2 , preparedStmt3 , preparedStmt4;

                if ( tipos.equals("Civilian")){
                    insQuery2.append("UPDATE civilians ");
                    insQuery2.append(" SET balance = ").append((balance_now+epistrofi));
                    insQuery2.append(" WHERE account_no = ").append(c_acc);
                }else if ( tipos.equals("Company")){
                    insQuery2.append("UPDATE companies ");
                    insQuery2.append(" SET balance = ").append((balance_now+epistrofi));
                    insQuery2.append(" WHERE account_no = ").append(c_acc);
                }
                if ( epiloges_dealer == 0 ){
                    insQuery3.append("UPDATE dealers ");
                    insQuery3.append(" SET earnings = ").append((earnings_now-epistrofi));
                    insQuery3.append(" WHERE account_no = ").append(d_acc);
                } else if ( epiloges_dealer == 1 ){
                    insQuery3.append("UPDATE dealers ");
                    insQuery3.append(" SET earnings = ").append(0).append(", ");
                    insQuery3.append(" debt = ").append((debt_now+upoloipa));
                    insQuery3.append(" WHERE account_no = ").append(d_acc);
                }

                preparedStmt2 = con2.prepareStatement(insQuery2.toString());
                preparedStmt2.execute();
                preparedStmt3 =  con2.prepareStatement(insQuery3.toString());
                preparedStmt3.execute();
                System.out.println("Return of product with transaction id : "+transs_id +"completed successfully.");
            } finally {
                // close connection
                DB.closeConnection(stmt2, con2);
            }

        }
    }

    //leitourgia 5
    public static void pay_debt(int account_num) throws SQLException, ClassNotFoundException {

        User us = new User();
        us = User.getUser2(account_num) ;
        String tipos = us.getType();
        double debt_now = us.getDebt();
        double balance_now = 0  , earnings_now = 0;
        if ( tipos.equals("Civilian")){
            Civilian civ = new Civilian() ;
            civ = civ.getCivilian2(account_num);
            balance_now = civ.getBalance();
            if (balance_now < debt_now){
                System.out.println("Civilian :" +civ.getUsername() +"doesn't have enough money to pay his debt");
                return ;
            }else{
                civ.setBalance(balance_now-debt_now);
                civ.setDebt(0);
            }
        }else if ( tipos.equals("Company")){
            Company comp = new Company() ;
            comp = comp.getCompany2(account_num);
            balance_now = comp.getBalance();
            if (balance_now < debt_now){
                System.out.println("Company :" +comp.getUsername() +"doesn't have enough money to pay his debt");
                return ;
            }else{
                comp.setBalance(balance_now-debt_now);
                comp.setDebt(0);
            }
        }else if ( tipos.equals("Dealer")){
            Dealer deal = new Dealer() ;
            deal = deal.getDealer2(account_num);
            earnings_now = deal.getEarnings();
            if (earnings_now < debt_now){
                System.out.println("Dealer :" +deal.getUsername() +"doesn't have enough money to pay his debt");
                return ;
            }else{
                deal.setEarnings(earnings_now-debt_now);
                deal.setDebt(0);
            }
        }

        Statement stmt = null;
        Statement stmt2 = null;

        Connection con = null;

        try {

            con = DB.getConnection();

            stmt = con.createStatement();
            stmt2 = con.createStatement();

            StringBuilder insQuery = new StringBuilder();
            StringBuilder insQuery2 = new StringBuilder();


            PreparedStatement preparedStmt , preparedStmt2;

            if(tipos.equals("Civilian")){
                insQuery.append("UPDATE civilians ");
                insQuery.append(" SET debt = ").append(0).append(", balance = ").append((balance_now-debt_now));
                insQuery.append(" WHERE account_no = ").append(account_num);


            }else if(tipos.equals("Company")){
                insQuery.append("UPDATE companies ");
                insQuery.append(" SET debt = ").append(0).append(", balance = ").append((balance_now-debt_now));
                insQuery.append(" WHERE account_no = ").append(account_num);

            }else if(tipos.equals("Dealer")) {
                insQuery.append("UPDATE dealers ");
                insQuery.append(" SET debt = ").append(0).append(", earnings = ").append((earnings_now-debt_now));
                insQuery.append(" WHERE account_no = ").append(account_num);
            }

            insQuery2.append("UPDATE users ");
            insQuery2.append(" SET debt = ").append(0);
            insQuery2.append(" WHERE account_no = ").append(account_num);

            preparedStmt = con.prepareStatement(insQuery.toString());
            preparedStmt.execute();
            preparedStmt2 = con.prepareStatement(insQuery2.toString());
            preparedStmt2.execute();




        } catch (SQLException ex) {
            ex.printStackTrace();
            Logger.getLogger(DB.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            // close connection
            DB.closeConnection(stmt, con);
            DB.closeConnection(stmt2, con);
        }
        System.out.println("Debt paid succesfully for the  " +tipos +".");;
        return ;
    }


    public static void  other_questions2a() throws SQLException, ClassNotFoundException {

        List<Transaction> traList = new ArrayList<>();
        Statement stmt = null;
        Connection con = null;

        try {

            con = DB.getConnection();
            stmt = con.createStatement();

            StringBuilder insQuery = new StringBuilder();

            System.out.println("All transactions' infos :");
            insQuery.append("SELECT * FROM transactions");
            stmt.executeQuery(insQuery.toString());


            ResultSet res = stmt.getResultSet();

            while (res.next() == true) {
                Transaction insert_tra = new Transaction();
                insert_tra.setTransactionID(res.getInt("transactionID"));
                insert_tra.setAmount(res.getDouble("amount"));
                insert_tra.setDate(res.getDate("date"));
                insert_tra.setCustomerAccount_no(res.getInt("customerAccount_no"));
                insert_tra.setCustomerName(res.getString("customerName"));
                insert_tra.setDealerAccount_no(res.getInt("dealerAccount_no"));
                insert_tra.setDealerName(res.getString("dealerName"));
                traList.add(insert_tra);
            }
            for (int i = 0; i < traList.size(); i++) {
                System.out.println("Transaction with id :" + traList.get(i).getTransactionID() + " , amount :" + traList.get(i).getAmount()
                        + ", date :" + traList.get(i).getDate() + ", customer's account_no :" + traList.get(i).getCustomerAccount_no() + "customer's name :" + traList.get(i).getCustomerName()
                        + ", dealer's account_no :" + traList.get(i).getDealerAccount_no() + ", dealer's name : " + traList.get(i).getDealerName());
            }

        } catch(SQLException e){
            e.printStackTrace();
        }finally {
            // close connection
            DB.closeConnection(stmt, con);
        }

        return;
    }


    public static void other_questions2b(int account_num) throws SQLException, ClassNotFoundException {
        List<Transaction> traList = new ArrayList<>();
        Statement stmt = null;
        Connection con = null;
        User us = new User();
        us = User.getUser2(account_num);
        String tipos = us.getType();

        try {

            con = DB.getConnection();
            stmt = con.createStatement();
            StringBuilder insQuery = new StringBuilder();

            if (tipos.equals("Dealer")){
                insQuery.append("SELECT * FROM transactions")
                .append(" WHERE")
                .append(" dealerAccount_no = ").append(account_num);
                stmt.executeQuery(insQuery.toString());
            }else{
                insQuery.append("SELECT * FROM transactions")
                .append(" WHERE")
                .append(" customerAccount_no = ").append(account_num);
                stmt.executeQuery(insQuery.toString());
            }
            ResultSet res = stmt.getResultSet();
            while (res.next() == true) {
                Transaction insert_tra = new Transaction();
                insert_tra.setTransactionID(res.getInt("transactionID"));
                insert_tra.setAmount(res.getDouble("amount"));
                insert_tra.setDate(res.getDate("date"));
                insert_tra.setCustomerAccount_no(res.getInt("customerAccount_no"));
                insert_tra.setCustomerName(res.getString("customerName"));
                insert_tra.setDealerAccount_no(res.getInt("dealerAccount_no"));
                insert_tra.setDealerName(res.getString("dealerName"));
                traList.add(insert_tra);
            }
            System.out.println("User with username : " +us.getUsername() +"has the following transactions :");
            for (int i = 0; i < traList.size(); i++) {
                System.out.println("Transaction with id :" + traList.get(i).getTransactionID() + " , amount :" + traList.get(i).getAmount()
                        + ", date :" + traList.get(i).getDate() + ", customer's account_no :" + traList.get(i).getCustomerAccount_no() + "customer's name :" + traList.get(i).getCustomerName()
                        + ", dealer's account_no :" + traList.get(i).getDealerAccount_no() + ", dealer's name : " + traList.get(i).getDealerName());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            DB.closeConnection(stmt, con);
        }

        return ;
    }

    public static void other_questions3(int dealer_accountNo, int times) {
        Connection con = null;
        Statement stmt = null;
        String cus_name;
        int cus_id;
        int ptimes;

        try {
            con = DB.getConnection();
            stmt = con.createStatement();

            StringBuilder insQuery = new StringBuilder();

            insQuery.append("SELECT customerAccount_no AS CustomerID, ");
            insQuery.append("customerName AS CustomerName, ");
            insQuery.append("COUNT(customerAccount_no) AS PurchaseTimes FROM");
            insQuery.append(" (SELECT * FROM transactions");
            insQuery.append(" WHERE dealerAccount_no = ").append(dealer_accountNo).append(")");
            insQuery.append(" AS b");
            insQuery.append(" GROUP BY customerAccount_no");
            insQuery.append(" HAVING COUNT(customerAccount_no) > ").append(times);

            stmt.executeQuery(insQuery.toString());

            ResultSet res = stmt.getResultSet();

            if(res.next()) {
                cus_name = res.getString("CustomerName");
                cus_id = res.getInt("CustomerID");
                ptimes = res.getInt("PurchaseTimes");

                System.out.println(("Dealer's ID: ") + dealer_accountNo + (",") + ("Most Usual Customer's ID: ") + (cus_id) + (",") + ("Most Usual Customer's Name: ") + cus_name + (",") + ("Purchase Times: ") + ptimes);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            DB.closeConnection(stmt, con);
        }
    }

    public static int getidxTr() throws SQLException {
        Statement stmt = null;
        Connection con = null;
        int idx = 0 ;

        try{
            con = DB.getConnection();
            stmt = con.createStatement();
            StringBuilder insQuery = new StringBuilder();

            insQuery.append("SELECT transactionID FROM transactions ORDER BY transactionID DESC LIMIT 1");
            stmt.executeQuery(insQuery.toString());
            ResultSet res = stmt.getResultSet();

            if (res.next() == true){
                idx = res.getInt("transactionID");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }finally{
            DB.closeConnection(stmt, con);
        }
        return idx;
    }

    public static int getidxUser() throws SQLException {
        Statement stmt = null;
        Connection con = null;
        int idx = 0 ;

        try{
            con = DB.getConnection();
            stmt = con.createStatement();
            StringBuilder insQuery = new StringBuilder();

            insQuery.append("SELECT account_no FROM users ORDER BY account_no DESC LIMIT 1");
            stmt.executeQuery(insQuery.toString());
            ResultSet res = stmt.getResultSet();

            if (res.next() == true){
                idx = res.getInt("account_no");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }finally{
            DB.closeConnection(stmt, con);
        }
        return idx;
    }

}
