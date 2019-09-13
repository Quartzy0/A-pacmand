package com.quartzy.pathfinding.gui.login;

import com.lambdaworks.crypto.SCryptUtil;
import com.quartzy.pathfinding.Game;
import com.quartzy.pathfinding.utils.Handler;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import javax.swing.*;
import java.net.URL;
import java.security.SecureRandom;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.UUID;

public class Controller implements Initializable{
    public Button loginBtn;
    public Button signupBtn;
    public TextField usernameTxt;
    public PasswordField passwordTxt;
    
    private static final int MAX_LOGIN_ATTEMPTS = 5;
    public CheckBox checkBox;
    private int incorrectAttempts;
    
    private static Handler handler;
    
    @Override
    public void initialize(URL location, ResourceBundle resources){
        
        boolean shouldClose = false;
    
        DateFormat dateFormat = new SimpleDateFormat("d.M.yyyy HH:mm:ss");
    
        String string = handler.getConfig().getString();
        if(string!=null && !string.isEmpty() && !string.equals("||")){
            String string1 = string.split("\\|")[0];
            if(string1!=null && !string1.isEmpty()){
                Date parse = null;
                try{
                    parse = dateFormat.parse(string1);
                } catch(ParseException e){
                    e.printStackTrace();
                }
                String difference = Handler.getDifference(new Date(), parse);
                if(new Date().before(parse)){
                    JOptionPane.showMessageDialog(null, "You can try logging in again after " + difference);
                    System.exit(0);
                }
            }
        }
    
        if(string!=null){
            String[] split = string.split("\\|");
            if(split.length>2 && split[1]!=null && split[2]!=null && !split[1].isEmpty() && !split[2].isEmpty()){
                int i = JOptionPane.showConfirmDialog(null, "Do you want to log in as " + split[1] + "?");
                if(i==0){
                    handler.getDatabase().getValuesFromSpreadSheets();
                    String stringWhere = handler.getDatabase().getStringWhere("Usernames", split[1], "UUIDS");
                    Game.highScore = handler.getDatabase().getIntWhere("UUIDS", stringWhere, "Highscore");
                    handler.setCurrentPlayerUUID(UUID.fromString(stringWhere));
                    shouldClose = true;
                }else {
                    rememberUser("", "");
                }
            }
        }
        
        loginBtn.setOnAction(event -> {
            handler = Handler.getInstance();
            handler.getDatabase().getValuesFromSpreadSheets();
            String s = passwordTxt.getText();
    
            String stringWhere1 = handler.getDatabase().getStringWhere("Usernames", usernameTxt.getText(), "Salts");
            if(stringWhere1==null || stringWhere1.isEmpty()){
                JOptionPane.showMessageDialog(null, "Username/Password incorrect!");
                incorrectAttempts++;
                if(incorrectAttempts>=MAX_LOGIN_ATTEMPTS){
                    JOptionPane.showMessageDialog(null, "Try again in 5 minutes");
                    setLockedTime();
                    System.exit(0);
                }
                return;
            }
    
            handler.getDatabase().getValuesFromSpreadSheets();
    
            if(handler.getDatabase().getColumnByName("Usernames").contains(usernameTxt.getText())){
                boolean willPassWork = SCryptUtil.check(s + handler.getDatabase().getStringWhere("Usernames", usernameTxt.getText(), "Salts"), handler.getDatabase().getStringWhere("Usernames", usernameTxt.getText(), "Passwords"));
                if(willPassWork){
                    String stringWhere = handler.getDatabase().getStringWhere("Usernames", usernameTxt.getText(), "UUIDS");
                    Game.highScore = handler.getDatabase().getIntWhere("UUIDS", stringWhere, "Highscore");
                    handler.setCurrentPlayerUUID(UUID.fromString(stringWhere));
                    ((Stage) loginBtn.getScene().getWindow()).close();
                }else {
                    JOptionPane.showMessageDialog(null, "Username/Password incorrect!");
                    incorrectAttempts++;
                    if(incorrectAttempts>=MAX_LOGIN_ATTEMPTS){
                        JOptionPane.showMessageDialog(null, "Try again in 5 minutes");
                        setLockedTime();
                        System.exit(0);
                    }
                }
            }else {
                JOptionPane.showMessageDialog(null, "Username/Password incorrect!");
                incorrectAttempts++;
                if(incorrectAttempts>=MAX_LOGIN_ATTEMPTS){
                    JOptionPane.showMessageDialog(null, "Try again in 5 minutes");
                    setLockedTime();
                    System.exit(0);
                }
            }
            if(checkBox.isSelected()){
                rememberUser(usernameTxt.getText(), s);
            }
        });
        
        signupBtn.setOnAction(event -> {
            handler = Handler.getInstance();
            handler.getDatabase().getValuesFromSpreadSheets();
            String s = passwordTxt.getText();
    
            SecureRandom secureRandom = new SecureRandom();
            secureRandom.setSeed(secureRandom.generateSeed(256));
            byte[] bytes = new byte[64];
            secureRandom.nextBytes(bytes);
            String salt = new String(bytes);
            s = SCryptUtil.scrypt(s + salt, 2, 2, 2);
    
            handler.getDatabase().getValuesFromSpreadSheets();
    
            if(handler.getDatabase().getColumnByName("Usernames").contains(usernameTxt.getText())){
                JOptionPane.showInputDialog("Username already taken!");
                return;
            }
    
            handler.getDatabase().getColumnByName("Usernames").add(usernameTxt.getText());
            handler.getDatabase().getColumnByName("Salts").add(salt);
            UUID uuid = UUID.randomUUID();
            handler.getDatabase().getColumnByName("UUIDS").add(uuid.toString());
            handler.getDatabase().getColumnByName("Passwords").add(s);
            handler.getDatabase().getColumnByName("Highscore").add("0");
    
            handler.getDatabase().refreshDatabase();
            Game.highScore = handler.getDatabase().getIntWhere("UUIDS", uuid.toString(), "Highscore");
            handler.setCurrentPlayerUUID(uuid);
    
            ((Stage) loginBtn.getScene().getWindow()).close();
        });
        
        if(shouldClose){
            Platform.runLater(new Runnable(){
                @Override
                public void run(){
                    ((Stage) loginBtn.getScene().getWindow()).close();
                }
            });
        }
    }
    
    public static Handler getHandler(){
        return handler;
    }
    
    public static void setHandler(Handler handler){
        Controller.handler = handler;
    }
    
    private void rememberUser(String username, String password){
        String string = handler.getConfig().getString();
        String[] split;
        if(string==null){
            split = new String[3];
            split[0] = "";
        }else{
            split = string.split("\\|");
        }
        if(split.length!=3){
            String[] sploit =new String[3];
            if(split.length==0){
                sploit[0] = "";
            }else{
                sploit[0] = split[0];
            }
            sploit[1] = username;
            sploit[2] = password;
            
            String s = "";
            for(int i = 0; i < sploit.length; i++){
                if(i!=sploit.length-1){
                    s+=sploit[i] + "|";
                }else {
                    s+=sploit[i];
                }
            }
            handler.getConfig().saveString(s);
            return;
        }
        split[1] = username;
        split[2] = password;
        String s = "";
        for(int i = 0; i < split.length; i++){
            if(i!=split.length-1){
                s+=split[i] + "|";
            }else {
                s+=split[i];
            }
        }
        handler.getConfig().saveString(s);
    }
    
    private void setLockedTime(){
        Calendar date = Calendar.getInstance();
        Date dateAfter = new Date(date.getTimeInMillis()+(5*60000));
        DateFormat dateFormat = new SimpleDateFormat("d.M.yyyy HH:mm:ss");
        
        String[] split = handler.getConfig().getString().split("\\|");
        split[0] = dateFormat.format(dateAfter);
        String s = "";
        for(int i = 0; i < split.length; i++){
            if(i!=split.length-1){
                s+=split[i] + "|";
            }else {
                s+=split[i];
            }
        }
        handler.getConfig().saveString(s);
    }
}
