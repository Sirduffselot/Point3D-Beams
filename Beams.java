// Beams.java: Generating input files for a spiral of beams. The
//    values of n, a and alpha (in degrees) as well as the output 
//    file name are to be supplied as program arguments.
//    Uses: Point3D (Section 3.9).
import java.io.*;
import java.util.Scanner;

public class Beams {
   public static void main(String[] arg) throws IOException {
      Scanner sc = new Scanner(System.in);

      String args[] = new String[4];

      System.out.println("Please enter the n value: ");
      args[0] = sc.nextLine();

      System.out.println("Please enter the a value: ");
      args[1] = sc.nextLine();

      System.out.println("Please enter the alphadeg value: ");
      args[2] = sc.nextLine();

      System.out.println("Please enter the filename value: ");
      args[3] = sc.nextLine();

      if (args.length != 4) {
         System.out.println(
                 "Supply n (> 0), a (>= 0.5), alpha (in degrees)\n" +
                         "and a filename as program arguments.\n");
         System.exit(1);
      }

      int n = 0;
      double a = 0.0;
      double alphaDeg = 0.0;

      try {
         n = Integer.valueOf(args[0]).intValue();
         a = Double.valueOf(args[1]).doubleValue();
         alphaDeg = Double.valueOf(args[2]).doubleValue();
         if (n <= 0 || a < 0.5)
            throw new NumberFormatException();
      } catch (NumberFormatException e) {
         System.out.println("n must be an integer > 0");
         System.out.println("a must be a real number >= 0.5");
         System.out.println("alpha must be a real number");
         System.exit(1);
      }
      new BeamsObj(n, a, alphaDeg * Math.PI / 180, args[3]);
   }
}

class BeamsObj {
   FileWriter fw;

   BeamsObj(int n, double a, double alpha, String fileName)
           throws IOException {
      fw = new FileWriter(fileName);
      Point3D[] P = new Point3D[25];
      double b = a - 1;
      P[1] = new Point3D(a, -a, 0);
      P[2] = new Point3D(a, a, 0);
      P[3] = new Point3D(b, a, 0);
      P[4] = new Point3D(b, -a, 0);
      P[5] = new Point3D(a, -a, 1);
      P[6] = new Point3D(a, a, 1);
      P[7] = new Point3D(b, a, 1);
      P[8] = new Point3D(b, -a, 1);
      P[9] = new Point3D(a, -a, 0);
      P[10] = new Point3D(a, a, 0);
      P[11] = new Point3D(b, a, 0);
      P[12] = new Point3D(b, -a, 0);
      P[13] = new Point3D(a, -a, 1);
      P[14] = new Point3D(a, a, 1);
      P[15] = new Point3D(b, a, 1);
      P[16] = new Point3D(b, -a, 1);
      P[17] = new Point3D(a, -a, 0);
      P[18] = new Point3D(a, a, 0);
      P[19] = new Point3D(b, a, 0);
      P[20] = new Point3D(b, -a, 0);
      P[21] = new Point3D(a, -a, 1);
      P[22] = new Point3D(a, a, 1);
      P[23] = new Point3D(b, a, 1);
      P[24] = new Point3D(b, -a, 1);

      String cubes[] = new String[25];

      for (int k = 0; k < n; k++) { // Beam k:
         double phi = k * alpha,
                 cosPhi = Math.cos(phi), sinPhi = Math.sin(phi);
         int m = 8 * k;
         for (int i = 1; i <= 8; i++) {
            double x = P[i].x, y = P[i].y;
            float x1 = (float) (x * cosPhi - y * sinPhi),
                    y1 = (float) (x * sinPhi + y * cosPhi),
                    z1 = (float) (P[i].z + k);
            fw.write((m + i) + " " + x1 + " " + y1 + " " + z1 + "\r\n");
            cubes[i + 8] = Integer.toString(m + i + 8) + " " + Float.toString(x1) + " " + Float.toString(y1 * 2) + " " + Float.toString(z1 + (float) (-1.0)) + "\r\n";
            cubes[i + 16] = Integer.toString(m + i + 16) + " " + Float.toString(x1) + " " + Float.toString(y1 * 3) + " " + Float.toString(z1 + (float) (-2.0)) + "\r\n";
         }

         for (int i = 9; i <= 24; i++) {
            fw.write(cubes[i]);
         }
      }

      fw.write("Faces:\r\n");
      for (int k = 0; k < n; k++) { // Beam k again:
         int m = 8 * k;
         face(m, 1, 2, 6, 5);
         face(m, 4, 8, 7, 3);
         face(m, 5, 6, 7, 8);
         face(m, 1, 4, 3, 2);
         face(m, 2, 3, 7, 6);
         face(m, 1, 5, 8, 4);

         face(m, 9, 10, 14, 13);
         face(m, 12, 16, 15, 11);
         face(m, 13, 14, 15, 16);
         face(m, 9, 12, 11, 10);
         face(m, 10, 11, 15, 14);
         face(m, 9, 13, 16, 12);

         face(m, 17, 18, 22, 21);
         face(m, 20, 24, 23, 19);
         face(m, 21, 22, 23, 24);
         face(m, 17, 20, 19, 18);
         face(m, 18, 19, 23, 22);
         face(m, 17, 21, 24, 20);
      }

      fw.close();
   }

   void face(int m, int a, int b, int c, int d) throws IOException {
      a += m;
      b += m;
      c += m;
      d += m;
      fw.write(a + " " + b + " " + c + " " + d + ".\r\n");
   }
}