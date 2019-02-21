import java.sql.*;
import java.util.*;
import java.util.Date;
import java.text.SimpleDateFormat;

public class DatabaseConnection//访问阿里云数据库类，封装所有对阿里云数据库的访问操作
{
    //向账户表里插入账户信息 从左往右参数依次为 账户id（6位字符串） 账户名（10位以那即可） 账户密码（6位字符串）
    public void insertAccount(String accid, String accname, String accpw) {
        try {
            Connection ct = null;
            PreparedStatement ps = null;

            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            ct = DriverManager.getConnection("jdbc:sqlserver://rm-bp1272p8m0ht7qxh0go.sqlserver.rds.aliyuncs.com:3433;DatabaseName=ICBC-DB","goods1","@xx191023");
            ps = ct.prepareStatement("insert into account (account_id,account_name,account_password) values(?,?,?)");
            ps.setString(1, accid);
            ps.setString(2, accname);
            ps.setString(3, accpw);
            ps.executeUpdate();

            ps.close();
            ct.close();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    //查找是否存在该账户 若存在则返回true 若不存在则返回false
    //从左往右参数依次为 账户id（6位字符串） 账户密码（6位字符串）
    public String loadAccount(String accid,String accpw) {

        try {
            Connection ct =null;
            PreparedStatement ps = null;
            PreparedStatement ps1 = null;

            ResultSet rs = null;
            ResultSet rs1 = null;
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            ct = DriverManager.getConnection("jdbc:sqlserver://rm-bp1272p8m0ht7qxh0go.sqlserver.rds.aliyuncs.com:3433;DatabaseName=ICBC-DB","goods1","@xx191023");
            ps1 = ct.prepareStatement("select * from account where account_id = ?");
            ps1.setString(1, accid);

            rs1 = ps1.executeQuery();
            //不存在账户
            if(!rs1.next())
                return "1";

            ps = ct.prepareStatement("select * from account where account_id = ? and account_password = ?");
            ps.setString(1, accid);
            ps.setString(2, accpw);

            //账户或密码错误
            rs = ps.executeQuery();
            if(!rs.next())
                return "2";

            rs.close();
            ps.close();
            rs1.close();
            ps1.close();
            ct.close();

        } catch (ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        //成功登陆
        return "0";

    }

    //在账户内添加银行卡 参数从左往右依次是 卡号（19位字符串表示储蓄卡 16位表示是信用卡） 卡密码（6位字符串） 账户号(6位字符串) 卡类型(1位字符c串 0表示信用卡、1和2是 借记卡)
    public boolean insertCards(String card_id,String card_password,String account_id,String card_type) {
        try {
            //把信用卡的卡片号加到19位
            if(card_type.equals("0"))
                card_id = card_id + "   ";

            Connection ct =null;
            PreparedStatement ps = null;
            PreparedStatement ps0 = null;
            PreparedStatement ps1 = null;
            PreparedStatement ps2 = null;
            ResultSet rs = null;
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            ct = DriverManager.getConnection("jdbc:sqlserver://rm-bp1272p8m0ht7qxh0go.sqlserver.rds.aliyuncs.com:3433;DatabaseName=ICBC-DB","goods1","@xx191023");
            ps = ct.prepareStatement("select account_id from cards where card_id = ? ");
            ps.setString(1, card_id);
            rs = ps.executeQuery();
            if(!rs.next()) {//如果不存在则创建
                ps0 = ct.prepareStatement("insert into cards(card_id,card_password,account_id,card_money,card_type) values(?,?,?,?,?) ");
                ps0.setString(1, card_id);
                ps0.setString(2, card_password);
                ps0.setString(3, account_id);
                ps0.setInt(4, 100000);
                ps0.setString(5, card_type);
                ps0.executeUpdate();
                System.out.println("添加成功");
                return true;
            }
            else { //如果存在的话
                ps1 = ct.prepareStatement("select account_id from cards where card_id = ? and card_password = ?");
                ps1.setString(1, card_id);
                ps1.setString(2, card_password);
                rs = ps1.executeQuery();
                if(!rs.next()) {//账户密码不匹配
                    System.out.println("账户密码不匹配！");
                    return false;
                }
                else {
                    if( rs.getString(1)!=null) {
                        System.out.println("该卡已被其他账户注册使用");
                        return false;
                    }
                    else {//更新卡片信息（主要是将卡片的所有者信息更改）
                        ps2 = ct.prepareStatement("update cards set account_id = ? where card_id = ? and card_password = ? ");
                        ps2.setString(1, account_id);
                        ps2.setString(2, card_id);
                        ps2.setString(3, card_password);
                        ps2.executeUpdate();
                        System.out.println("修改成功！");
                        return true;
                    }

                }


            }

        } catch (ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }

    //查找某个账户的银行卡 返回List<String>对象(里面的内容位 卡号|密码)  函数的参数为账户id（account_id  6位字符串）
    public List<String> findCard(String account_id) {
        List<String> list = new ArrayList<String>();
        try {
            int number=0;  //表示出现的卡的个数

            Connection ct =null;
            PreparedStatement ps = null;
            ResultSet rs = null;
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            ct = DriverManager.getConnection("jdbc:sqlserver://rm-bp1272p8m0ht7qxh0go.sqlserver.rds.aliyuncs.com:3433;DatabaseName=ICBC-DB","goods1","@xx191023");
            ps = ct.prepareStatement("select card_id, card_password from card where account_id = ? ");
            ps.setString(1, account_id);
            rs = ps.executeQuery();

            while(rs.next())
            {
                String a = rs.getString(1);
                String b = rs.getString(2);
                //输出结果
                String c =(a + ","+ b );
                list.add(c);
            }


        } catch (ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return list;

    }

    public List<String> findCoupon(String account_id){
        List<String> list = new ArrayList<String>();
        try {
            String results = DatabaseConnection.findCardtype(account_id);//查找该账户有哪些类别的卡

            char [] c = results.toCharArray();

            Connection ct =null;
            PreparedStatement ps = null;
            ResultSet rs = null;


            Calendar cal=Calendar.getInstance();

            //获取年、月、日
            int y=cal.get(Calendar.YEAR);
            int m=cal.get(Calendar.MONTH)+1;
            int d=cal.get(Calendar.DATE);

            String data = "";
            if(d<10) {
                data += new String(y+"-"+m+"-0"+d);  //将今天的时间生成为String
            }
            else {
                data += new String(y+"-"+m+"-"+d);
            }
            //查找三天内的数据信息

            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            ct = DriverManager.getConnection("jdbc:sqlserver://rm-bp1272p8m0ht7qxh0go.sqlserver.rds.aliyuncs.com:3433;DatabaseName=ICBC-DB","goods1","@xx191023");
            for(int i=0;i<c.length;i++) {
                ps = ct.prepareStatement("select shop_id, card_type  from coupon where data = ? and card_type = ?");
                ps.setString(1, data);
                ps.setString(2, ""+c[i]);
                rs = ps.executeQuery();
                while(rs.next())
                {
                    String a = rs.getString(1);
                    String b = rs.getString(2);
                    //输出结果
                    String ab = (a + ","+ b );
                    list.add(ab);
                }
                return list;
            }

            rs.close();
            ps.close();
            ct.close();
        } catch (ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return list;
    }


    //获取账户拥有的卡片的类型
    public static String findCardtype(String account_id) {
        String results = "";
        try {
            Connection ct =null;
            PreparedStatement ps = null;
            ResultSet rs = null;
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            ct = DriverManager.getConnection("jdbc:sqlserver://rm-bp1272p8m0ht7qxh0go.sqlserver.rds.aliyuncs.com:3433;DatabaseName=ICBC-DB","goods1","@xx191023");

            ps = ct.prepareStatement("select distinct card_type from cards where account_id = ? ");
            ps.setString(1, account_id);
            rs = ps.executeQuery();

            while(rs.next())
            {
                String a = rs.getString(1);
                results += a;
            }
            rs.close();
            ps.close();
            ct.close();

            return results;

        } catch (ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return results;
    }


    //这个函数用来展示账户的卡的信息 输入为 账户号(6位)
    //返回一个List对象 里面的是String内容是 卡号|卡密码|卡类型
    public List<String> showCards(String account_id) {
        List<String> list = new ArrayList<String>();
        try {
            Connection ct =null;
            PreparedStatement ps = null;
            ResultSet rs = null;
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            ct = DriverManager.getConnection("jdbc:sqlserver://rm-bp1272p8m0ht7qxh0go.sqlserver.rds.aliyuncs.com:3433;DatabaseName=ICBC-DB","goods1","@xx191023");
            ps = ct.prepareStatement("select card_id, card_type from cards where account_id = ? ");
            ps.setString(1, account_id);
            rs = ps.executeQuery();
            while(rs.next())
            {
                String a = rs.getString(1);
                String b = rs.getString(2);
                if(b.equals("0"))
                    b = new String("../../assets/imgs/cardImgs/credit.png");
                else if(b.equals("1"))
                    b = new String("../../assets/imgs/cardImgs/mudan.png");
                else if(b.equals("2"))
                    b = new String("../../assets/imgs/cardImgs/debit.png");

                //输出结果
                String d =(a + ","+ b );
                list.add(d);
            }
            rs.close();
            ps.close();
            ct.close();
            return list;

        } catch (ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return list;
    }


    //这个函数用于显示商户名 输入shop_id（3位字符串） 输出的是商户名（String）
    public String showShopName(String shop_id) {
        String name = "";
        try {
            Connection ct =null;
            PreparedStatement ps = null;
            ResultSet rs = null;
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            ct = DriverManager.getConnection("jdbc:sqlserver://rm-bp1272p8m0ht7qxh0go.sqlserver.rds.aliyuncs.com:3433;DatabaseName=ICBC-DB","goods1","@xx191023");
            ps = ct.prepareStatement("select shop_name from shop where shop_id = ? ");
            ps.setString(1, shop_id);
            rs = ps.executeQuery();

            rs.next();
            name += rs.getString(1);
            rs.close();
            ps.close();
            ct.close();
            return name;
        } catch (ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return name;

    }


    //这个函数用于选择卡片 输入位账户id 和 卡的类型 选择排序的第一张卡片的id（19位String）
    public static String selectCard(String account_id, String card_type) {
        String card_id = "";

        try {
            Connection ct =null;
            PreparedStatement ps = null;
            ResultSet rs = null;
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            ct = DriverManager.getConnection("jdbc:sqlserver://rm-bp1272p8m0ht7qxh0go.sqlserver.rds.aliyuncs.com:3433;DatabaseName=ICBC-DB","goods1","@xx191023");
            ps = ct.prepareStatement("select card_id from cards where account_id = ? and card_type = ?");
            ps.setString(1, account_id);
            ps.setString(2, card_type);
            rs = ps.executeQuery();
            rs.next();
            card_id += rs.getString(1);
            rs.close();
            ps.close();
            ct.close();
            return card_id;

        } catch (ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return card_id;
    }
    public String findBase64(String account_id) {
        List<String> list = new ArrayList<String>();
        String a = "";
        try {
            Connection ct =null;
            PreparedStatement ps = null;
            ResultSet rs = null;
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            ct = DriverManager.getConnection("jdbc:sqlserver://rm-bp1272p8m0ht7qxh0go.sqlserver.rds.aliyuncs.com:3433;DatabaseName=ICBC-DB","goods1","@xx191023");
            ps = ct.prepareStatement("select base64 from account where account_id = ? ");
            ps.setString(1, account_id);
            rs = ps.executeQuery();

            while(rs.next())
            {
                a = rs.getString(1);
            }


        } catch (ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return a;

    }
    public String loadByFace(String faceid) {
        Connection ct =null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        String result = "";
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            ct = DriverManager.getConnection("jdbc:sqlserver://rm-bp1272p8m0ht7qxh0go.sqlserver.rds.aliyuncs.com:3433;DatabaseName=ICBC-DB","goods1","@xx191023");
            ps = ct.prepareStatement("select account_id, account_password from account where faceid = ? ");
            ps.setString(1, faceid);
            rs = ps.executeQuery();
            if(rs.next()) {
                String account_id = rs.getString(1);
                String account_password = rs.getString(2);
                result = account_id+","+account_password;
                return result;
            }
        } catch (ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return result;
    }
    public String getUserId(String account_id) {
        Connection ct =null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        String result = "";
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            ct = DriverManager.getConnection("jdbc:sqlserver://rm-bp1272p8m0ht7qxh0go.sqlserver.rds.aliyuncs.com:3433;DatabaseName=ICBC-DB","goods1","@xx191023");
            ps = ct.prepareStatement("select useid from account where account_id = ? ");
            ps.setString(1, account_id);
            rs = ps.executeQuery();
            if(rs.next()) {
                result = rs.getString(1);
                return result;
            }
        } catch (ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return result;
    }
    public String paidByLocation(String account_id,String latitude,String longitude) {
        Connection ct =null;
        PreparedStatement ps = null;
        PreparedStatement ps1 = null;
        ResultSet rs = null;
        ResultSet rs1 = null;
        String result = "";
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            ct = DriverManager.getConnection("jdbc:sqlserver://rm-bp1272p8m0ht7qxh0go.sqlserver.rds.aliyuncs.com:3433;DatabaseName=ICBC-DB","goods1","@xx191023");
            ps = ct.prepareStatement("select shop_id from location where latitude = ? and longitude = ?");
            ps.setString(1, latitude);
            ps.setString(2, longitude);
            rs = ps.executeQuery();
            String shop_id = "";
            if(rs.next()) {//如果该地理位置有商家的话则选取卡片 否则随机返回一张随机选取的卡片
                System.out.println("1");
                shop_id = rs.getString(1);
                ps1 = ct.prepareStatement("select card_type from coupon where shop_id = ?");
                ps1.setString(1, shop_id);
                rs1=ps1.executeQuery();
                if(rs1.next()) {
                    System.out.println("2");
                    String card_type = rs1.getString(1);
                    System.out.println(card_type);

                    result = selectCard(account_id,card_type);
                    return result;
                }
                else {
                    System.out.println("3");
                    result = selectCard(account_id,"0");
                    return result;
                }

            }
            else {
                System.out.println("4");
                result = selectCard(account_id,"0");
                return result;
            }

        } catch (ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return result;

    }

}
