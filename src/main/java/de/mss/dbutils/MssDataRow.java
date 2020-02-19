package de.mss.dbutils;

import java.util.ArrayList;

public class MssDataRow {

   private ArrayList<String> values = null;


   public MssDataRow(int size) {
      this.values = new ArrayList<>();
      for (int i = 0; i < size; i++ )
         this.values.add(null);
   }


   public String getValue(int index) {
      if (index < 0 || index >= this.values.size())
         return null;

      return this.values.get(index);
   }


   public void setValue(String value, int index) {
      if (index < 0 || index >= this.values.size())
         return;

      this.values.set(index, value);
   }


   public int getSize() {
      return this.values.size();
   }


   @Override
   public String toString() {
      StringBuilder sb = new StringBuilder();

      sb.append(this.values.get(0));

      for (int i = 1; i < this.values.size(); i++ ) {
         String s = this.values.get(i);
         if (s == null)
            sb.append(";NULL");
         else if (s.length() > 255)
            sb.append(";binary data not displayed");
         else
            sb.append(";" + s);

      }

      return sb.toString();
   }

}
