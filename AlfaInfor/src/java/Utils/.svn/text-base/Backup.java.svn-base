/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Utils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author DANILO
 */
public class Backup {
   private static String VERSION="4.0.3";
   private static String SEPARATOR=File.separator;

   private static String MYSQL_PATH = "C:"+SEPARATOR+"backup"+SEPARATOR+"bin";


   private static String PRESENTATION =
           "=====================================================\n"+
           "Backup do Banco de Dados superjsf - Versao" + VERSION + "\n"+
           "Autor: Danilo Venturini em 24/08/2011";

   private static String DATABASES = "superjsf";
   private List<String> dbList = new ArrayList<String>();

   public Backup(){
   String command = MYSQL_PATH + "mysqldump.exe";
   String[] databases = DATABASES.split("");

   for(int i= 0; i<databases.length; i++)
   dbList.add(databases[i]);

   System.out.println("Iniciando Backups...\n\n");

   int i =1;

   long time1, time2, time;

   time1 = System.currentTimeMillis();

   for(String dbName:dbList){
   ProcessBuilder pb = new ProcessBuilder(
           command,"--user=root","--password=root",dbName,"--result-file"+"."+SEPARATOR+dbName+".sql");
   try{
       System.out.println("Backup do banco de dados("+i+"):"+dbName+"...");
       pb.start();

   }catch(Exception e){
   e.printStackTrace();
   }

   i++;
   }
   time2 = System.currentTimeMillis();

   time = time2-time1;
   }


}
